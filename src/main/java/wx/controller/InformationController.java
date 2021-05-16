package wx.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import wx.poj.Information;
import wx.service.InformationService;
import wx.util.Result;

import javax.annotation.Resource;
import javax.sound.sampled.Line;

@Slf4j
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/info")
public class InformationController {


    @Resource
    private InformationService informationService;

    @PostMapping("/add")
    public Result addInformation(@RequestBody  Information information){
        log.info(information.toString());
        informationService.addInformation(information);
        return new Result(null,"新增成功",0);
    }

    @GetMapping("/get")
    public Result getInformation(String orderId){
        Information info =informationService.getInformation(orderId);
        return new Result(info,"获取成功",0);
    }
}
