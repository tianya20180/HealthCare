package wx.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import wx.poj.InMessage;
import wx.poj.OutMessage;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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
    @Autowired
    private MessageService messageService;

    /**
     * 简单点对点聊天室
     */
    public void sendChatMessage(InMessage message) {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date= sdf.format(new Date());
        message.setCreateTime(date);
        message.setIsRead(0);
        Integer id=0;
        if(message.getContentType()==0){
            messageService.addMessage(message);
        }
        message.setId(id);
        log.info("from:"+message.getFromId()+"to:"+message.getToId()+"message:"+message.getContent());
        template.convertAndSend("/chat/single/"+message.getToId(),
                new OutMessage(0,String.valueOf(message.getFromId()), message.getContent(),message.getAvatar(),message.getCreateTime(),message.getOrderId(),message.getContentType()));
    }


}