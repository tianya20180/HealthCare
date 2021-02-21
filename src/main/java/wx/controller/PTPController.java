package wx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import wx.poj.InMessage;
import wx.service.WebSocketService;

import javax.annotation.Resource;

@Controller
public class PTPController {

    @Autowired
    private WebSocketService ws;

    @MessageMapping("/ptp/single/chat")
    public void singleChat(InMessage message){
        ws.sendChatMessage(message);
    }

}
