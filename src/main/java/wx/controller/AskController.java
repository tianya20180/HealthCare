package wx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wx.poj.Ask;
import wx.service.AskService;
import wx.util.Result;

import javax.annotation.Resource;

@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/ask")
public class AskController {

    @Resource
    private AskService askService;

    @GetMapping("/status")
    public Result getStatus(Integer userId,Integer doctorId){
           Ask ask= askService.getAsk(userId,doctorId);
            return new Result(ask.getStatus(),"获取成功",0);
    }
    @GetMapping("/get")
    public Result get(Integer userId,Integer doctorId){
        Ask ask= askService.getAsk(userId,doctorId);
        return new Result(ask,"获取成功",0);
    }

}
