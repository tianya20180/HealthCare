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

    @PostMapping("/add")
    public Result addMessage(@RequestBody MessageList messageList){
        if(messageList.getType()==0){
            messageList.setMsgId(messageList.getUserId()+"-"+messageList.getDoctorId());
            User user=userService.getUserById(messageList.getUserId());
            messageList.setUserName(user.getUserName());
        }else{
            messageList.setMsgId(+messageList.getDoctorId()+"-"+messageList.getUserId());
            Doctor doctor=doctorService.getDoctorById(messageList.getDoctorId());
            messageList.setUserName(doctor.getUserName());
        }
        if(messageListService.isExists(messageList.getMsgId())!=null){
            messageList=messageListService.isExists(messageList.getMsgId());
            messageList.setUnreadCount(messageList.getUnreadCount()+1);
            messageListService.changeReadCount(messageList.getId(),messageList.getUnreadCount());
            return new Result(null,"添加成功",0);
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date= sdf.format(new Date());
        messageList.setUnreadCount(1);
        messageList.setCreateTime(date);

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


/*
    @GetMapping("/get")
    public Result getMessage(Integer id){
        List<Message>messageList=messageService.getMessage(id);
        return new Result(messageList,"插入成功",0);
    }
*/

    @GetMapping("/user/get_offline")
    public Result getOfflineMessageByUser(Integer userId){
        List<InMessage>messageLists=messageService.getOfflineMessageByUserId(userId);
        for(InMessage message:messageLists){
            messageService.changeRead(message.getId());
        }
        return new Result(messageLists,"获取成功",0);
    }

    @GetMapping("/doctor/get_offline")
    public Result getOfflineMessage(Integer doctorId){
        List<InMessage>messageLists=messageService.getOffLineMessageByDoctorId(doctorId);
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
        message.setRead(false);
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
    public Result changeMessageStatus(Integer id){
        if(id==null)
            return new Result(null,"id为空",0);
        messageService.changeRead(id);
        return new Result(null,"修改状态成功",1);
    }



}
