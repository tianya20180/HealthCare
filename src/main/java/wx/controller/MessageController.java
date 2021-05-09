package wx.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wx.poj.Message;
import wx.poj.MessageList;
import wx.service.MessageListService;
import wx.service.MessageService;
import wx.util.Result;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

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

    @PostMapping("/add")
    public Result addMessage(@RequestBody MessageList messageList){
        if(messageList.getType()==0){
            messageList.setMsgId(messageList.getUserId()+"-"+messageList.getDoctorId());
        }else{
            messageList.setMsgId(+messageList.getDoctorId()+"-"+messageList.getUserId());
        }
        if(messageListService.isExists(messageList.getMsgId())!=null){
            messageList=messageListService.isExists(messageList.getMsgId());
            messageList.setUnreadCount(messageList.getUnreadCount()+1);
            messageListService.updateMessage(messageList);
            return new Result(null,"添加成功",0);
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date= sdf.format(new Date());
        messageList.setUnreadCount(1);
        messageList.setCreateTime(date);
        messageList.setUserName("wangxi");
        messageListService.addMessageList(messageList);
        return new Result(null,"添加成功",0);
    }

    @GetMapping("/user/get")
    public Result userGet(Integer userId){
        log.info(String.valueOf(userId));
        List<MessageList>messageLists=messageListService.getByUserId(userId);
        return new Result(messageLists,"获取成功",0);
    }

    @GetMapping("/doctor/get")
    public Result doctorGet(Integer doctorId){
        log.info(String.valueOf(doctorId));
        List<MessageList>messageLists=messageListService.getByDoctorId(doctorId);
        return new Result(messageLists,"获取成功",0);
    }



    @GetMapping("/get")
    public Result getMessage(String id){
        List<Message>messageList=messageService.getMessage(id);
        return new Result(messageList,"插入成功",0);
    }


    @GetMapping("/get_offline")
    public Result getOfflineMessage(String id){
        List<Message>messageLists=messageService.getOffLineMessage(id);
        return new Result(messageLists,"获取成功",0);
    }

    @PostMapping("/add_msg")
    public Result addMessage(Message message){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date= sdf.format(new Date());
        message.setCreateTime(date);
        messageService.addMessage(message);
        return new Result(null,"获取成功",0);
    }



}
