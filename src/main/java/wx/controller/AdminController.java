package wx.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wx.poj.Authentication;
import wx.poj.Doctor;
import wx.service.AuthenticationService;
import wx.service.DoctorService;
import wx.util.Result;
import java.util.List;
import javax.annotation.Resource;

@Slf4j
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/admin")
public class AdminController {

   /*
    @Resource
    private AuthenticationService authenticationService;
    @Resource
    private DoctorService doctorService;



    @GetMapping("/authenticationAgree")
    public Result AuthenticationAgree(Integer doctorId,Integer authenticationId){
          if(doctorId==null||authenticationId==null){
              return new Result(null,"doctorId为null或者authenticationId为null",1);
          }
          Doctor doctor=doctorService.getDoctorById(doctorId);
          doctor.setStatus(1);
          doctorService.updateDoctor(doctor);
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
        doctorService.updateDoctor(doctor);
        authenticationService.setStatus(authenticationId,0);
        return new Result(null,"已成功批准",0);
    }

    @GetMapping("/getAllAuthentiation")
    public Result getAllAuthentication(){
        List<Authentication>authenticationList=authenticationService.getAllAuthentication();
        return new Result(authenticationList,"成功获取",1);
    }*/
/*
    @GetMapping("/getAllUser")
    public Result getAllUser(){

    }
    @GetMapping("/banUser")
    public Result banUser(){

    }
    @GetMapping("/deleteUser")
    public Result deleteUser(){

    }
    @GetMapping("/banDoctor")
    public Result banDoctor(){

    }
    @GetMapping("deleteDoctor")
    public Result deleteDoctor(){

    }

    @GetMapping("/getAllDoctor")
    public Result getAllDoctor(){

    }

    @GetMapping("/getAllOrder")
    public Result getAllOrder(){

    }

*/
}
