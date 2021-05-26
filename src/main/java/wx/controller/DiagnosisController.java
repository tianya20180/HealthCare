package wx.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wx.poj.Category;
import wx.poj.Diagnosis;
import wx.service.DiagnosisService;
import wx.util.Result;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/diagnosis")
public class DiagnosisController {

    @Resource
    DiagnosisService diagnosisService;

    @PostMapping("/add")
    public wx.util.Result Add(@RequestBody Diagnosis diagnosis){
        diagnosisService.addDiagnosis(diagnosis);
        return new Result(null,"添加成功",0);
    }
    @GetMapping("/user/get")
    public wx.util.Result userGet(Integer userId){
        List<Diagnosis> diagnosisList=diagnosisService.getByUser(userId);
        return new Result(diagnosisList,"获取成功",0);
    }
}
