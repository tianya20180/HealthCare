package wx.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import wx.enums.VirtualType;
import wx.poj.*;
import wx.service.*;
import wx.util.CategoryUtil;
import wx.util.Result;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
    @Resource
    private ArticleService articleService;
    @Resource
    private DrugService drugService;
    @Resource
    private CategoryService categoryService;

    @GetMapping("/getAllAdmin")
    public Result getAllAdmin(int current,int size){
        Page<Admin> page=new Page<>(current,size);
        adminService.getAllAdmin(page);
        return new Result(page,"成功获取",0);
    }

    @GetMapping("/delete")
    public Result deleteAdmin(Integer id){
        adminService.delete(id);
        return new Result(null,"成功删除",0);
    }

    @GetMapping("/getAllUser")
    public Result getAllUser(int current,int size){
        Page<User> page=new Page<>(current,size);
        userService.getAllUser(page);
        return new Result(page,"成功获取",0);
    }

    @GetMapping("/getData")
    public Result getData(){
        int userCount=userService.getUserCount();
        int doctorCount=doctorService.getDoctorCount();
        int orderCount=orderService.getOrderCount();
        int adminCount=adminService.getAdminCount();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String end= sdf.format(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        String start= sdf.format(zero);
        int addUser=userService.getUserCountBetweenTime(start,end);
        int addDoctor=doctorService.getDoctorCountBetweenTime(start,end);
        int addAdmin=adminService.getAdminCountBetweenTime(start,end);
        int addOrder=orderService.getAllOrder2(start,end).size();
        Map<String,Object>map=new HashMap<String,Object>();
        map.put("userCount",userCount);
        map.put("doctorCount",doctorCount);
        map.put("orderCount",orderCount);
        map.put("adminCount",adminCount);
        map.put("newUser",addUser);
        map.put("newOrder",addOrder);
        map.put("newAdmin",addAdmin);
        map.put("newDoctor",addDoctor);
        return new Result(map,"成功获取",0);
    }



    @PostMapping("/add")
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
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time= sdf.format(new Date());
        admin.setCreateTime(time);
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


    @PostMapping("/loginV2")
    public Result LoginV2(@RequestBody LoginUser loginUser, HttpSession session){
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

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time= sdf.format(new Date());
        UserToken token = new UserToken(phone, password, VirtualType.Admin.toString());
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()){
            //使用shiro来验证
            token.setRememberMe(true);
            currentUser.login(token);//验证角色和权限
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
    public Result getAllAuthentication(int current,int size){
        Page<Authentication> page=new Page<>(current,size);
        authenticationService.getAllAuthentication(page);
        List<Authentication>authenticationList=page.getRecords();
        for(Authentication authentication:authenticationList){
            int doctorId=authentication.getDoctorId();
            Doctor doctor=doctorService.getDoctorById(doctorId);
            authentication.setDoctorName(doctor.getUserName());
            authentication.setCategoryName(CategoryUtil.convertCategory(authentication.getCategory()));
        }
        page.setRecords(authenticationList);
        return new Result(page,"成功获取",0);
    }



    @GetMapping("/banUser")
    public Result banUser(Integer id){
        userService.changeUserStatus(id,0);
        return new Result(null,"修改成功",0);
    }
    @GetMapping("/releaseUser")
    public Result releaseUser(Integer id){
        userService.changeUserStatus(id,1);
        return new Result(null,"修改成功",0);
    }
    @GetMapping("/deleteUser")
    public Result deleteUser(Integer id){
        userService.deleteUser(id);
        return new Result(null,"修改成功",0);
    }
    @GetMapping("/banDoctor")
    public Result banDoctor(Integer id){
        doctorService.changeDoctorStatus(id,2);
        return new Result(null,"修改成功",0);
    }
    @GetMapping("deleteDoctor")
    public Result deleteDoctor(Integer id){
        doctorService.deleteDoctor(id);
        return new Result(null,"修改成功",0);
    }

    @GetMapping("/getAllDoctor")
    public Result getAllDoctor(int current,int size){
        Page<Doctor> page=new Page<>(current,size);
        doctorService.getAllDoctor(page);
        List<Doctor>doctorList=page.getRecords();
        for(Doctor doctor:doctorList){
            doctor.setCategoryName(CategoryUtil.convertCategory(doctor.getCategory()));
        }
        page.setRecords(doctorList);
        return new Result(page,"成功获取",0);
    }

    @GetMapping("/getAllOrder")
    public Result getAllOrder(String start, String end,int current,int size){
        Page<Order>page=new Page<>(current,size);
        orderService.getAllOrder(start,end,page);
        List<Order>orderList=page.getRecords();
        for(Order order:orderList){
            Doctor doctor=doctorService.getDoctorById(order.getDoctorId());
            User user=userService.getUserById(order.getUserId());
            order.setUserName(user.getUserName());
            if(doctor!=null)
                order.setDoctorName(doctor.getUserName());
        }
        page.setRecords(orderList);
        return new Result(page,"成功获取",0);
    }

    @GetMapping("/deleteOrder")
    public Result deleteOrder(Integer id){
        orderService.deleteOrder(id);
        return new Result(null,"成功获取",0);
    }


    @GetMapping("/getAllArticle")
    public Result getAllArtilce(int current,int size){
        Page<Article> page=new Page<>(current,size);
        articleService.getAllArticle(page);
        List<Article>articleList=page.getRecords();
        for(Article article:articleList){
            article.setLink("http://192.144.236.155:8000/#/articleDeatil?id="+article.getId());
        }
        page.setRecords(articleList);
        return new Result(articleList,"成功获取",0);
    }

    @GetMapping("/deleteArticleById")
    public Result deleteArticleById(Integer id){
        articleService.deleteArticleById(id);
        return new Result(null,"成功删除",0);
    }

    @GetMapping("/getAllDrug")
    public Result getAllDrug(){
       List<Drug>drugList =drugService.getAllDrug();
        return new Result(drugList,"成功获取",0);
    }
    @PostMapping("/addDrug")
    public Result addDrug(@RequestBody Drug drug,Integer pid){
        if(drug==null)
            return new Result(null,"Drug为空",1);
        int id=drugService.addDrug(drug);
        return new Result(null,"新增成功",0);
    }
    @GetMapping("/getAllCategory")
    public Result getAllCategory(int current,int size){
        Page<Category> page=new Page<>(current,size);
        categoryService.getAll(page);
        return new Result(page,"成功获取",0);
    }

}
