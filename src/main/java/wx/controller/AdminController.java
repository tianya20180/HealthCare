package wx.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import wx.poj.*;
import wx.service.*;
import wx.util.CategoryUtil;
import wx.util.Result;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/admin")
public class AdminController {


    @Resource
    private AuthenticationService authenticationService;
    @Resource
    private DoctorService doctorService;
    @Resource
    private UserService userService;
    @Resource
    private OrderService orderService;
    @Resource
    private AdminService adminService;


    @GetMapping("/getAllAdmin")
    public Result getAllAdmin(){
        List<Admin>adminList=adminService.getAllAdmin();
        return new Result(adminList,"成功获取",0);
    }

    @GetMapping("/getAllUser")
    public Result getAllUser(){
        List<User>userList=userService.getAllUser();
        return new Result(userList,"成功获取",0);
    }



    @PostMapping("/register")
    public Result register(@RequestBody Admin admin){
        if(admin==null){
            return new Result(null,"doctor为null",1);
        }
        String phone=admin.getPhone();
        Doctor exists=doctorService.getByPhone(phone);
        if(exists!=null){
            log.info("手机3号："+admin.getPhone()+"已注册");
            return new Result(null,"已注册",0);
        }
        admin.setPassword(DigestUtils.md5DigestAsHex(admin.getPassword().getBytes()));
        adminService.addMapper(admin);
        return new Result(null,"登录成功",0);
    }

    @PostMapping("/login")
    public Result Login(@RequestBody LoginUser loginUser, HttpSession session){
        if(loginUser==null){
            return new Result(null,"user为空",1);
        }
        String phone=loginUser.getPhone();
        String password=loginUser.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        Integer identity=loginUser.getIdentity();
        if (phone==null||password==null||identity==null){
            return new Result(null,"用户名或者密码或者身份为空",1);
        }
        Admin user=adminService.checkMapper(phone,password);
        if(user!=null){
                session.setAttribute("admin",user);
                session.setAttribute("identity",identity);
                log.info(session.getId());
                return new Result(user,"登录成功",0);
        }
        return new Result(null,"登录失败 用户名或密码错误",1);
    }


    @GetMapping("/authenticationAgree")
    public Result AuthenticationAgree(Integer doctorId,Integer authenticationId){
          if(doctorId==null||authenticationId==null){
              return new Result(null,"doctorId为null或者authenticationId为null",1);
          }
          Doctor doctor=doctorService.getDoctorById(doctorId);
          doctor.setStatus(1);
          doctorService.updateDoctor(doctorId,doctor);
          authenticationService.setStatus(authenticationId,1);
          return new Result(null,"已成功批准",0);
    }
    @GetMapping("/authenticationReject")
    public Result AuthenticationReject(Integer doctorId,Integer authenticationId){
        if(doctorId==null||authenticationId==null){
            return new Result(null,"doctorId为null或者authenticationId为null",1);
        }
        Doctor doctor=doctorService.getDoctorById(doctorId);
        doctor.setStatus(0);
        doctorService.updateDoctor(doctorId,doctor);
        authenticationService.setStatus(authenticationId,0);
        return new Result(null,"已成功批准",0);
    }

    @GetMapping("/getAllAuthentiation")
    public Result getAllAuthentication(){
        List<Authentication>authenticationList=authenticationService.getAllAuthentication();
        for(Authentication authentication:authenticationList){
            int doctorId=authentication.getDoctorId();
            Doctor doctor=doctorService.getDoctorById(doctorId);
            authentication.setDoctorName(doctor.getUserName());
            authentication.setCategoryName(CategoryUtil.convertCategory(authentication.getCategory()));
        }
        return new Result(authenticationList,"成功获取",0);
    }



    @GetMapping("/banUser")
    public Result banUser(Integer id){
        userService.changeUserStatus(id,0);
        return new Result(null,"修改成功",0);
    }
    @GetMapping("/deleteUser")
    public Result deleteUser(Integer id){
        userService.deleteUser(id);
        return new Result(null,"修改成功",0);
    }
    @GetMapping("/banDoctor")
    public Result banDoctor(Integer id){
        doctorService.changeDoctorStatus(id,0);
        return new Result(null,"修改成功",0);
    }
    @GetMapping("deleteDoctor")
    public Result deleteDoctor(Integer id){
        doctorService.deleteDoctor(id);
        return new Result(null,"修改成功",0);
    }

    @GetMapping("/getAllDoctor")
    public Result getAllDoctor(){
        List<Doctor>doctorList=doctorService.getAllDoctor();
        for(Doctor doctor:doctorList){
            doctor.setCategoryName(CategoryUtil.convertCategory(doctor.getCategory()));
        }
        return new Result(doctorList,"成功获取",0);
    }

    @GetMapping("/getAllOrder")
    public Result getAllOrder(String start, String end){
        List<Order>orderList=orderService.getAllOrder(start,end);
        for(Order order:orderList){
            Doctor doctor=doctorService.getDoctorById(order.getDoctorId());
            User user=userService.getUserById(order.getUserId());
            order.setUserName(user.getUserName());
            order.setDoctorName(doctor.getUserName());
        }
        return new Result(orderList,"成功获取",0);
    }

    @GetMapping("/deleteOrder")
    public Result deleteOrder(Integer id){
        orderService.deleteOrder(id);
        return new Result(null,"成功获取",0);
    }
}
