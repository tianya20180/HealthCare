package wx.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wx.poj.Ask;
import wx.service.AskService;
import wx.util.Result;

import javax.annotation.Resource;

@Slf4j
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/ask")
public class AskController {

    @Resource
    private AskService askService;

    @GetMapping("/status")
    public Result getStatus(Integer userId,Integer doctorId){
           log.info(userId.toString());
           log.info(doctorId.toString());
           Ask ask= askService.getAsk(userId,doctorId);
           log.info(ask.toString());
           if(ask!=null)
            return new Result(ask.getStatus(),"获取成功",0);
           else
               return new Result(0,"获取成功",0);
    }
    @GetMapping("/get")
    public Result get(Integer userId,Integer doctorId){
        Ask ask= askService.getAsk(userId,doctorId);
        return new Result(ask,"获取成功",0);
    }

    @GetMapping("/change/status")
    public Result changeStatus(Integer userId,Integer doctorId){
        askService.changeStatus(userId,doctorId,0);
        return new Result(null,"获取成功",0);
    }


}
