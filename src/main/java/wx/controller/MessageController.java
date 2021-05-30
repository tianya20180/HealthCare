package wx.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wx.poj.*;
import wx.service.DoctorService;
import wx.service.MessageListService;
import wx.service.MessageService;
import wx.service.UserService;
import wx.util.Result;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.print.Doc;

@Slf4j
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/message")
public class MessageController {
    @Resource
    private MessageListService messageListService;
    @Resource
    private MessageService messageService;
    @Resource
    private UserService userService;
    @Resource
    private DoctorService doctorService;





/*
    @GetMapping("/get")
    public Result getMessage(Integer id){
        List<Message>messageList=messageService.getMessage(id);
        return new Result(messageList,"插入成功",0);
    }
*/

    @GetMapping("/user/get_offline")
    public Result getOfflineMessageByUser(Integer doctorId,Integer userId){
        List<InMessage>messageLists=messageService.getOfflineMessageByUserId(doctorId,userId);
        for(InMessage message:messageLists){
            log.info(message.getId().toString());
            messageService.changeRead(message.getId());
        }
        return new Result(messageLists,"获取成功",0);
    }

    @GetMapping("/doctor/get_offline")
    public Result getOfflineMessage(Integer doctorId,Integer userId){
        List<InMessage>messageLists=messageService.getOffLineMessageByDoctorId(doctorId,userId);
        for(InMessage message:messageLists){
            messageService.changeRead(message.getId());
        }
        return new Result(messageLists,"获取成功",0);
    }

    @PostMapping("/add_msg")
    public Result addMessage(Message message){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date= sdf.format(new Date());
        message.setCreateTime(date);
        message.setIsRead(0);
       // messageService.addMessage(InMessage);
        return new Result(null,"获取成功",0);
    }


    @GetMapping("/batch/change_status")
    public Result changeMessageStatus(List<Integer>ids){
        if(ids==null||ids.size()==0)
            return new Result(null,"id为空",0);
        for(Integer id:ids){
            messageService.changeRead(id);
        }
        return new Result(null,"修改状态成功",1);
    }

    @GetMapping("/change_status")
    public Result changeMessageStatus(Integer msgId){
        if(msgId==null)
            return new Result(null,"id为空",0);
        messageService.changeRead(msgId);
        return new Result(null,"修改状态成功",1);
    }



}
