package wx.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wx.poj.Category;
import wx.poj.Diagnosis;
import wx.service.DiagnosisService;
import wx.service.DoctorService;
import wx.util.Result;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/diagnosis")
public class DiagnosisController {

    @Resource
    DiagnosisService diagnosisService;
    @Resource
    private DoctorService doctorService;

    @PostMapping("/add")
    public wx.util.Result Add(@RequestBody Diagnosis diagnosis){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date= sdf.format(new Date());
        diagnosis.setCreateTime(date);
        diagnosisService.addDiagnosis(diagnosis);
        return new Result(null,"添加成功",0);
    }
    @GetMapping("/user/get")
    public wx.util.Result userGet(Integer userId){
        List<Diagnosis> diagnosisList=diagnosisService.getByUser(userId);
        for(Diagnosis diagnosis:diagnosisList){
            diagnosis.setDoctorName(doctorService.getDoctorById(diagnosis.getDoctorId()).getUserName());
        }
        return new Result(diagnosisList,"获取成功",0);
    }
}
