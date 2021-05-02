package wx.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wx.poj.MessageList;
import wx.service.MessageListService;
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

    @PostMapping("/add")
    public Result addMessage(MessageList messageList){
        if(messageList.getType()==0){
            messageList.setMsgId(messageList.getUserId()+"-"+messageList.getDoctorId());
        }else{
            messageList.setMsgId(+messageList.getDoctorId()+"-"+messageList.getUserId());
        }
        if(messageListService.isExists(messageList.getMsgId())!=null){
            messageList.setUnreadCount(messageList.getUnreadCount()+1);
            messageListService.updateMessage(messageList);
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date= sdf.format(new Date());
        messageList.setUnreadCount(1);
        messageListService.addMessageList(messageList);
        return new Result(null,"添加成功",0);
    }

    @GetMapping("/user/get")
    public Result userGet(Integer userId){
        List<MessageList>messageLists=messageListService.getByUserId(userId);
        return new Result(null,"添加成功",0);
    }

    @GetMapping("/doctor/get")
    public Result doctorGet(Integer doctorId){
        List<MessageList>messageLists=messageListService.getByUserId(doctorId);
        return new Result(null,"添加成功",0);
    }


}
