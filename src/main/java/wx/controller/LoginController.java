package wx.controller;


import com.zhenzi.sms.ZhenziSmsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.security.provider.MD5;
import wx.poj.Doctor;
import wx.poj.LoginUser;
import wx.poj.MessageResult;
import wx.poj.User;
import wx.service.DoctorService;
import wx.service.UserService;
import wx.util.JsonUtil;
import wx.util.MessageConfig;
import wx.util.Result;
import java.util.*;
import javax.annotation.Resource;
import javax.print.Doc;
import javax.servlet.http.HttpSession;

@Controller
@ResponseBody
public class LoginController {

    @Resource
    private UserService userService;
    @Resource
    private DoctorService doctorService;


    @GetMapping("/isLogin")
    public Result isLogin(HttpSession session){
        User user= (User) session.getAttribute("user");
        Doctor doctor= (Doctor) session.getAttribute("doctor");
        Map<String,Object>map=new HashMap<String, Object>();
        Result result;
        if(user!=null){
            map.put("identity",0);
            map.put("data",user);
            result=new Result(map,"已登录 身份为用户",1);
            return result;
        }
        if(doctor!=null){
            map.put("identity",1);
            map.put("data",doctor);
            result=new Result(map,"已登录 身份为医生",1);
            return result;
        }
        result=new Result(null,"未登录",0);
        return result;
    }

    @PostMapping("/login")
    public Result Login(@RequestBody LoginUser loginUser,HttpSession session){
        if(loginUser==null){
            return new Result(null,"user为空",1);
        }
        String username=loginUser.getUserName();
        String password=loginUser.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        Integer identity=loginUser.getIdentity();
        if (username==null||password==null||identity==null){
            return new Result(null,"用户名或者密码或者身份为空",1);
        }
        if(identity==0){

            User user=userService.checkUser(username,password);
            if(user!=null){
                session.setAttribute("user",user);
                session.setAttribute("identity",identity);
            }
            return new Result(null,"登录成功",0);

        }else if(identity==1){
            Doctor doctor=doctorService.checkDoctor(username,password);
            if(doctor!=null){
                session.setAttribute("user",doctor);
                session.setAttribute("identity",identity);
            }
            return new Result(null,"登录成功",0);
        }
        return new Result(null,"登录失败 用户名或密码错误",1);
    }


    @GetMapping("/sendMessage")
    public Result sendMessage(String phone) throws Exception {
        if(phone==null||phone.equals("")){
            return new Result(null,"手机号为空",1);
        }
        ZhenziSmsClient client=new ZhenziSmsClient(MessageConfig.APIURL,MessageConfig.APPID,MessageConfig.APPSECRET);
        Map<String,Object>params=new HashMap<String, Object>();
        params.put("number",phone);
        params.put("templateId","0");
        String[]templateParams=new String[2];
        templateParams[0]="test";
        params.put("templateParams",templateParams);
        String result=client.send(params);
        MessageResult ms=JsonUtil.jsonToPojo(result,MessageResult.class);
        if(ms.getCode()==0){
            return new Result(null,"发送成功",0);
        }
        return new Result(null,"发送失败",1);
    }


    @GetMapping("/checkPhone")
    public Result checkPhone(String phone){





    }

    @GetMapping("/loginByCode")
    public Result loginByCode(String phone,String code,int identity,HttpSession session) throws Exception {

        //检查该号码是否已注册





    }


    @PostMapping("/register")
    public Result register(@RequestBody Doctor doctor){
        if(doctor==null){
            return new Result(null,"doctor为null",1);
        }
        doctor.setPassword(DigestUtils.md5DigestAsHex(doctor.getPassword().getBytes()));
        doctorService.addDoctor(doctor);
        return new Result(null,"登录成功",0);
    }

}
