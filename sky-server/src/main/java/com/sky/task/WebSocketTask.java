package com.sky.task;

import com.sky.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class WebSocketTask {
    @Autowired
    private WebSocketServer webSocketServer;

//    /**
//     * 通过WebSocket每隔5秒向客户端发送消息
//     */
//    @Scheduled(cron = "0/1 * * * * ?")
//    public void sendMessageToClient() {
//        for(int i = 0; i < (int)(Math.random()*1000); i++ ) {
//            webSocketServer.sendToAllClient(i+"这是来自服务端的消息：" + DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
//        }
//    }
}
