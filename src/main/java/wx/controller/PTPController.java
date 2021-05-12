package wx.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import wx.poj.InMessage;
import wx.poj.Message;
import wx.service.MessageService;
import wx.service.WebSocketService;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Controller
public class PTPController {

    @Resource
    private WebSocketService ws;
    @Resource
    private MessageService messageService;

    @MessageMapping("/ptp/single/chat")
    public void singleChat(InMessage inMsg){
        log.info("sending");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date= sdf.format(new Date());
        inMsg.setCreateTime(date);
        inMsg.setRead(false);
        Integer id=messageService.addMessage(inMsg);
        inMsg.setId(id);
        ws.sendChatMessage(inMsg);
    }

}
