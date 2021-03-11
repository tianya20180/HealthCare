package wx.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import wx.poj.InMessage;
import wx.poj.OutMessage;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;


/**
 *
 * 功能描述：简单消息模板，用来推送消息
 */
@Service
@Slf4j
public class WebSocketService {


    @Autowired
    private SimpMessagingTemplate template;

    /**
     * 简单点对点聊天室
     */
    public void sendChatMessage(InMessage message) {
        log.info("from:"+message.getFromId()+"to:"+message.getToId()+"message:"+message.getContent());
        template.convertAndSend("/chat/single/"+message.getToId(),
                new OutMessage(message.getFromId(), message.getContent(),message.getAvatar(),message.getTime()));
    }


}