package wx.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wx.poj.Authentication;
import wx.poj.Doctor;
import wx.poj.User;
import wx.service.AuthenticationService;
import wx.service.DoctorService;
import wx.util.Result;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.annotation.Resource;
import javax.print.Doc;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/doctor")
public class DoctorController {

    @Resource
    private DoctorService doctorService;
    @Resource
    private AuthenticationService authenticationService;
    @GetMapping("/search")
    public Result searchDoctor(String userName){
        if(userName==null||userName.equals("")){
            return new Result(null,"医生名为空",1);
        }
        List<Doctor> doctorList= doctorService.getDoctorList(userName);
        return new Result(doctorList,"获取成功",0);
    }

    @GetMapping("/getDoctorByTime")
    public Result getDoctors(){
        List<Doctor> doctorList= doctorService.getDoctorListByTime();
        return new Result(doctorList,"获取成功",0);
    }
    @GetMapping("/getDoctorByCategory")
    public Result getDoctorByCategory(Integer category){
        if(category==null) {
            return new Result(null, "类别为空", 1);
        }
        List<Doctor> doctorList= doctorService.getDoctorListByCategory(category);
        return new Result(doctorList,"获取成功",0);
    }
    @GetMapping("/changePassword")
    public Result changePassword(String oldPassword, String newPassword, HttpSession session){
        if(oldPassword==null||oldPassword.equals("")||newPassword==null||newPassword.equals("")){
            return new Result(null,"密码为空",1);
        }
        Doctor doctor= (Doctor) session.getAttribute("doctor");
        String phone=doctor.getPhone();
        oldPassword=DigestUtils.md5DigestAsHex(oldPassword.getBytes());
        newPassword=DigestUtils.md5DigestAsHex(newPassword.getBytes());
        Doctor exists=doctorService.checkDoctor(phone,oldPassword);
        if(exists==null){
            return new Result(null,"密码错误",1);
        }
        doctorService.changePassword(phone,newPassword);
        return new Result(null,"修改密码成功",0);
    }
    @GetMapping("/profile")
    public Result profile(Integer id){
        Doctor doctor=doctorService.getDoctorById(id);
        return new Result(doctor,"找到信息",0);
    }


    @PostMapping("/upload")
    public Result uploadAvatar(@RequestParam(value = "file") MultipartFile file,@RequestParam Integer id, HttpServletRequest request) throws IOException {
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
        doctorService.changeAvatar(id,avatar);
        String path=dest.getPath();
        log.info("end change");
        return new Result(path,"更新头像成功",0);
    }

    @PostMapping("/authentication")
    public Result authentication(@RequestParam(value = "card") MultipartFile card,
                                 @RequestParam(value = "hospital") MultipartFile hospital,
                                 @RequestParam(value = "doctor") MultipartFile doctor,Integer doctorId) throws IOException {
        String cardPath="C:\\Users\\wangxi\\Desktop\\谷芽平台\\HealthyCare-Web\\static\\authentication\\card";
        String hospitalPath="C:\\Users\\wangxi\\Desktop\\谷芽平台\\HealthyCare-Web\\static\\authentication\\hospital";
        String doctorPath="C:\\Users\\wangxi\\Desktop\\谷芽平台\\HealthyCare-Web\\static\\authentication\\doctor";
        File cardDir=new File(cardPath);
        File hospitalDir=new File(hospitalPath);
        File doctorDir=new File(doctorPath);
        if(!cardDir.exists()){
            cardDir.mkdirs();
        }
        if(!hospitalDir.exists()){
            hospitalDir.mkdirs();
        }
        if(!doctorDir.exists()){
            doctorDir.mkdirs();
        }
        String cardFileName=makeFile(card,cardDir);
        String hospitalFileName=makeFile(hospital,hospitalDir);
        String doctorFileName=makeFile(doctor,doctorDir);

        Authentication authentication=new Authentication();
        authentication.setCardPath(cardFileName);
        authentication.setDoctorPath(hospitalFileName);
        authentication.setHospitalPath(doctorFileName);
        authentication.setStatus(0);
        authentication.setDoctorId(doctorId);
        authenticationService.addAuthentication(authentication);
        return new Result(null,"上传成功",0);
    }

    public String makeFile(MultipartFile file,File dir) throws IOException {
        String orginalFileName=file.getOriginalFilename();
        int beginIndex=orginalFileName.lastIndexOf(".");
        String suffix="";
        if(beginIndex!=-1){
            suffix=orginalFileName.substring(beginIndex);
        }
        String Filename= UUID.randomUUID().toString()+suffix;
        File dest=new File(dir,Filename);
        file.transferTo(dest);
        return dir+Filename;
    }
    @GetMapping("/get")
    public Result getUser(Integer id){
        Doctor doctor=doctorService.getDoctorById(id);
        return new Result(doctor,"成功",0);
    }
}

