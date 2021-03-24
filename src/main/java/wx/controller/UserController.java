package wx.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wx.poj.Doctor;
import wx.poj.User;
import wx.service.UserService;
import wx.util.Result;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
@Slf4j
@Controller
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/profile")
    public Result profile(HttpSession session){
        User sessionUser= (User) session.getAttribute("user");
        Integer id=sessionUser.getId();
        User user=userService.getUserById(id);
        return new Result(user,"找到信息",0);
    }


    @PostMapping("/changePassword")
    public Result changePassword(String oldPassword, String newPassword,String phone){
        log.info("start change password");
        log.info("old:"+oldPassword+"new"+newPassword+"phone"+phone);
        if(oldPassword==null||oldPassword.equals("")||newPassword==null||newPassword.equals("")){
            return new Result(null,"密码为空",1);
        }

        oldPassword= DigestUtils.md5DigestAsHex(oldPassword.getBytes());
        newPassword=DigestUtils.md5DigestAsHex(newPassword.getBytes());
        User exists=userService.checkUser(phone,oldPassword);
        if(exists==null){
            return new Result(null,"密码错误",1);
        }
        userService.changePassword(phone,newPassword);
        return new Result(null,"修改密码成功",0);
    }
    @PostMapping("/upload")
    public Result uploadAvatar(@RequestParam(value = "file")MultipartFile file, @RequestParam Integer id, HttpServletRequest request) throws IOException {
      //  String dirPath=request.getServletContext().getRealPath("upload");
        String dirPath="C:\\Users\\wangxi\\Desktop\\谷芽平台\\HealthyCare-Web\\static\\image\\avatar";
        log.info("dir:"+dirPath);
        File dir=new File(dirPath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        String orginalFileName=file.getOriginalFilename();
        int beginIndex=orginalFileName.lastIndexOf(".");
        String suffix="";
        if(beginIndex!=-1){
            suffix=orginalFileName.substring(beginIndex);
        }
        String filename= UUID.randomUUID().toString()+suffix;
        File dest=new File(dir,filename);
        file.transferTo(dest);
        String avatar=filename;
        userService.changeAvatar(id,avatar);
        String path=dest.getPath();
        log.info("end change");
        return new Result(path,"更新头像成功",0);
    }
}
