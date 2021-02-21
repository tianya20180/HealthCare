package wx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import wx.poj.InMessage;
import wx.poj.OutMessage;

import javax.annotation.Resource;

@Service
public class WebSocketService {

    @Resource
    private SimpMessagingTemplate template;

    public void sendChatMessage(InMessage message){
        template.convertAndSend("/chat/single/"+message.getTo(),
                new OutMessage(message.getFrom()+" 发送:"+ message.getContent()));
    }




}
