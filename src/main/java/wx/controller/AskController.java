package wx.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wx.poj.*;
import wx.service.AskService;
import wx.service.DoctorService;
import wx.service.MessageService;
import wx.service.UserService;
import wx.util.Result;
import java.util.List;
import javax.annotation.Resource;

@Slf4j
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/ask")
public class AskController {

    @Resource
    private AskService askService;
    @Resource
    private UserService userService;
    @Resource
    private DoctorService doctorService;
    @Resource
    private MessageService messageService;

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


    @GetMapping("/change/status")
    public Result changeStatus(Integer userId,Integer doctorId){
        askService.deleteByMap(userId,doctorId);
       // messageService.deleteMessageByMap(userId,doctorId);
        return new Result(null,"获取成功",0);
    }


    @GetMapping("/get")
    public Result get(Integer userId,Integer doctorId){
        Ask ask= askService.getAsk(userId,doctorId);
        return new Result(ask,"获取成功",0);
    }

    @GetMapping("/user/get")
    public Result userGet(Integer userId){
        List<Ask>askList=askService.getByUser(userId);
        for(Ask ask:askList){
            Doctor doctor=doctorService.getDoctorById(ask.getDoctorId());
            ask.setDoctorAvatar(doctor.getAvatar());
            List<InMessage>messageList=messageService.getOfflineMessageByUserId(ask.getDoctorId(),userId);
            if(messageList!=null)
                ask.setUserUnread(messageList.size());
            ask.setDoctorName(doctor.getUserName());
        }

        return new Result(askList,"获取成功",0);
    }


    @GetMapping("/doctor/get")
    public Result doctorGet(Integer doctorId){
        List<Ask>askList=askService.getByDoctor(doctorId);
        for(Ask ask:askList){
            User user=userService.getUserById(ask.getUserId());
            ask.setUserAvatar(user.getAvatar());
            ask.setDoctorUnread(messageService.getOffLineMessageByDoctorId(doctorId,ask.getUserId()).size());
            ask.setUserName(user.getUserName());
        }

        return new Result(askList,"获取成功",0);
    }






}
