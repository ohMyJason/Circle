package com.lanqiao.circle.config;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author 刘佳昇
 * @Date 2019/9/19 23:00
 */
@ServerEndpoint(value = "/websocket")
@Component
public class WebSocket {
    private static int onlineCount  = 0;


    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();


    private Session session;


    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSockets.add(this);
        addOnlineCount();
        System.out.println("log:新连接加入，人数为->"+getOnlineCount());
    }

    @OnClose
    public void  onClose(){
        webSockets.remove(this);
        subOnlineCount();
        System.out.println("log:有连接关闭，在线人数为->"+getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message,Session session) throws IOException {
        System.out.println("log:来自客户端的消息"+message);

        WebSocket.sendInfo(message);
    }


    @OnError
    public void onError(Session session,Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    private void sendMessage(String message)throws IOException{
        this.session.getBasicRemote().sendText(message);
    }

    private static void sendInfo(String message)throws IOException{
        for (WebSocket item:webSockets){
            try {
                item.sendMessage(message);
            }catch (IOException e){
                continue;
            }
        }
    }



    private static  synchronized int getOnlineCount(){
        return onlineCount;
    }

    private static  synchronized void addOnlineCount(){
        WebSocket.onlineCount++;
    }

    private static synchronized void subOnlineCount(){
        WebSocket.onlineCount--;
    }


}
