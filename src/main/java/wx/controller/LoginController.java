package wx.controller;


import com.zhenzi.sms.ZhenziSmsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import wx.poj.Doctor;
import wx.poj.LoginUser;
import wx.poj.MessageResult;
import wx.poj.User;
import wx.service.DoctorService;
import wx.service.UserService;
import wx.util.JsonUtil;
import wx.config.MessageConfig;
import wx.util.PhoneCodeUtil;
import wx.util.Result;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/v1")
public class LoginController {

    @Resource
    private UserService userService;
    @Resource
    private DoctorService doctorService;

    private static String code="";


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
        log.info("name:"+loginUser.getPhone()+"password:"+loginUser.getPassword()+"code:"+loginUser.getCaptchaCode());
        String phone=loginUser.getPhone();
        String password=loginUser.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        Integer identity=loginUser.getIdentity();

        if (phone==null||password==null||identity==null){
            return new Result(null,"用户名或者密码或者身份为空",1);
        }
        if(identity==0){
            User user=userService.checkUser(phone,password);
            if(user.getStatus()==0)
                return new Result(user,"账号被封禁",1);

            if(user!=null){
                user.setIdentity(0);
                session.setAttribute("user",user);
                session.setAttribute("identity",identity);
                log.info(session.getId());
                return new Result(user,"登录成功",0);

            }

        }else if(identity==1){
            Doctor doctor=doctorService.checkDoctor(phone,password);
            if(doctor.getStatus()==0)
                return new Result(doctor,"账号被封禁",1);
            if(doctor!=null){
                doctor.setIdentity(1);
                session.setAttribute("user",doctor);
                session.setAttribute("identity",identity);
                return new Result(doctor,"登录成功",0);
            }
        }

        return new Result(null,"登录失败 用户名或密码错误",1);
    }


    @GetMapping("/sendMessage")
    public Result sendMessage(String phone) throws Exception {
        if(phone==null||phone.equals("")){
            return new Result(null,"手机号为空",1);
        }
        log.info("start send ========================================start send");
        ZhenziSmsClient client=new ZhenziSmsClient(MessageConfig.APIURL,MessageConfig.APPID,MessageConfig.APPSECRET);
        Map<String,Object>params=new HashMap<String, Object>();
        params.put("number",phone);
        params.put("templateId","3501");
        String[]templateParams=new String[1];
        code= PhoneCodeUtil.getCode();
        templateParams[0]=code;
        params.put("templateParams",templateParams);
        String result=client.send(params);
        MessageResult ms=JsonUtil.jsonToPojo(result,MessageResult.class);
        Map<String,Object>map=new HashMap<String, Object>();
        map.put("code",code);
        log.info("end send ========================================end send");
        if(ms.getCode()==0){
            return new Result(map,"发送成功",0);
        }
        return new Result(map,"发送成功",0);
    }


    @GetMapping("/exists")
    public Result checkPhone(String phone){
        if(phone==null||phone.equals("")){
            return new Result(null,"手机号码为空",1);
        }
        Doctor doctor=doctorService.getByPhone(phone);
        if(doctor!=null){
            return new Result(null,"已注册",0);
        }
        User user=userService.getByPhone(phone);
        if(user!=null){
            return new Result(null,"已注册",0);
        }
        return new Result(null,"未注册",1);

    }

    @GetMapping("/loginByCode")
    public Result loginByCode(String phone,String sendCode,Integer identity,HttpSession session) throws Exception {
        //检查该号码是否已注册
        if(phone==null||sendCode==null||identity==null){
            return new Result(null,"手机号或者验证码为null",1);
        }
        log.info("code:"+code);
        log.info("sendCode:"+sendCode);
        //验证验证码是否正确

        if(!code.equals(sendCode)){
            return new Result(null,"登录失败 验证码错误",1);
        }
        Map<String,Object> data=new HashMap<String,Object>();
        if(identity==0){
            User user=userService.getByPhone(phone);
            if(user.getStatus()==2)
                return new Result(user,"账号被封禁",1);
            session.setAttribute("user",user);
            session.setAttribute("identity",identity);
            return new Result(user,"登录成功",0);

        }else if(identity==1){
            Doctor doctor=doctorService.getByPhone(phone);
            if(doctor.getStatus()==2)
                return new Result(doctor,"账号被封禁",1);
            session.setAttribute("user",doctor);
            session.setAttribute("identity",identity);
            return new Result(doctor,"登录成功",0);
        }
        return new Result(null,"登录失败",1);
    }


    @GetMapping("/registerByCode")
    public Result registerByCode(String phone,String sendCode,String password,String userName,HttpSession session) throws Exception {
        //检查该号码是否已注册
        if(phone==null||sendCode==null||password==null){
            return new Result(null,"手机号或者验证码为null",1);
        }
        log.info("code:"+code);
        log.info("sendCode:"+sendCode);
        //验证验证码是否正确
        /*
        if(!code.equals(sendCode)){
            return new Result(null,"登录失败 验证码错误",1);
        }*/
        User user=new User();
        user.setUserName(userName);
        user.setPhone(phone);
        user.setIdentity(0);
        user.setMoney(0F);
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date= sdf.format(new Date());
        user.setCreateTime(date);
        userService.addUser(user);
        session.setAttribute("user",user);
        return new Result(user,"登录成功",0);
    }


    @PostMapping("/register")
    public Result register(@RequestBody Doctor doctor){
        if(doctor==null){
            return new Result(null,"doctor为null",1);
        }
        String sendCode=doctor.getSendCode();
        log.info("username:"+doctor.getUserName()+"password:"+doctor.getPassword()+"address:"+doctor.getPassword()+"cardId"+doctor.getCardId());
        String phone=doctor.getPhone();
        //验证验证码是否正确
        /*
        if(!code.equals(sendCode)){
            return new Result(null,"登录失败 验证码错误",1);
        }*/
        Doctor exists=doctorService.getByPhone(phone);
        if(exists!=null){
            log.info("手机3号："+doctor.getPhone()+"已注册");
            return new Result(null,"已注册",0);
        }
        doctor.setMoney(0);
        doctor.setCategory(0);
        doctor.setStatus(0);
        doctor.setPassword(DigestUtils.md5DigestAsHex(doctor.getPassword().getBytes()));
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date= sdf.format(new Date());
        doctor.setCreateTime(date);
        doctorService.addDoctor(doctor);
        return new Result(null,"登录成功",0);
    }

}
