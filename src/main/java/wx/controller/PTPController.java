package wx.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import wx.poj.InMessage;
import wx.service.WebSocketService;

import javax.annotation.Resource;

@Slf4j
@Controller
public class PTPController {

    @Resource
    private WebSocketService ws;

    @MessageMapping("/ptp/single/chat")
    public void singleChat(InMessage message){
        log.info("sending");
        ws.sendChatMessage(message);
    }

}
