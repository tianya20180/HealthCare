package wx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wx.poj.Doctor;
import wx.service.DoctorService;
import wx.util.Result;
import java.util.*;
import javax.annotation.Resource;

@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/doctor")
public class DoctorController {

    @Resource
    private DoctorService doctorService;

    @GetMapping("/search")
    public Result searchDoctor(String userName){
        if(userName==null||userName.equals("")){
            return new Result(null,"医生名为空",1);
        }
        List<Doctor> doctorList= doctorService.getDoctorList(userName);
        return new Result(doctorList,"获取成功",0);
    }
    @GetMapping("/profile")
    public Result profile(){
        return new Result(null,"",0);
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
}
