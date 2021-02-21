package wx.websocket;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ServerEndpoint(value = "/test/one")
@Component
public class TestSocket {
    private static AtomicInteger onlineCount=new AtomicInteger(0);


    //连接建立成功
    @OnOpen
    public void onOpen(Session session){
        onlineCount.incrementAndGet();
        log.info("有新连接加入:{},当前在线人数：{}",session.getId(),onlineCount.get());
    }

    @OnClose
    public void onClose(Session session){
        onlineCount.decrementAndGet();
        log.info("连接关闭：{}，当前在线人数为：{}",session.getId(),onlineCount.get());
    }
    @OnMessage
    public void onMessage(String message,Session session){
        log.info("服务端收到客户端[{}]的消息:{}",session.getId(),message);
    }
    private void sendMessage(String message,Session toSession){
        try{
            log.info("服务端给客户端[{}]发送消息",toSession.getId(),message);
            toSession.getBasicRemote().sendText(message);
        }catch (Exception e) {
            log.error("失败:{}", e);
        }
    }

    @OnError
    public void onError(Session session,Throwable error){
        log.error("发送错误");
        error.printStackTrace();
    }

}
