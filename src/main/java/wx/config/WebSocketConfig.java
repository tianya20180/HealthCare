package wx.config;

import inteceptor.HttpHandShakeIntecepter;
import inteceptor.SocketChannelIntecepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;


import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**
 * @author xub
 * @Description: 注解用于开启使用STOMP协议来传输基于代理（MessageBroker）的消息，这时候控制器（controller）
 * 开始支持@MessageMapping,就像是使用@requestMapping一样。
 */
@Configuration
@EnableWebSocketMessageBroker
@EnableScheduling

public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


        @Override
        public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
            registration.setSendTimeLimit(15 * 1000)
                    .setSendBufferSizeLimit(10* 1024)
                    // max message size 2GB (2048 bytes) : default is 64KB
                    .setMessageSizeLimit(2 * 1024 * 1024);
        }

        /**
     * 配置基站
     */
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/endpoint-websocket").
                addInterceptors(new HttpHandShakeIntecepter()).
                setAllowedOrigins("*").withSockJS().
                setStreamBytesLimit(15 * 1024)
                .setHttpMessageCacheSize(15 * 1024);;

    }

    /**
     * 配置消息代理(中介)
     * enableSimpleBroker 服务端推送给客户端的路径前缀
     * setApplicationDestinationPrefixes  客户端发送数据给服务器端的一个前缀
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        registry.enableSimpleBroker("/topic", "/chat");
        registry.setApplicationDestinationPrefixes("/app");

    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new SocketChannelIntecepter());
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.interceptors(new SocketChannelIntecepter());
    }

}