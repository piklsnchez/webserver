package org.piklsnchez;

import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.websockets.DataFrame;
import org.glassfish.grizzly.websockets.WebSocket;
import org.glassfish.grizzly.websockets.WebSocketAddOn;
import org.glassfish.grizzly.websockets.WebSocketApplication;
import org.glassfish.grizzly.websockets.WebSocketEngine;

public class Server {
    private static final String CLASS = Server.class.getName();
    private static final Logger LOG   = Logger.getLogger(CLASS);
    
    public static void main(String... args){
        HttpServer server = HttpServer.createSimpleServer(); 
        server.getListener("grizzly").registerAddOn(new WebSocketAddOn());
        WebSocketEngine.getEngine().register("/ws", "/event", 
            new WebSocketApplication() {
                private String offer  = "{}";
                private String answer = "{}";
                
                @Override
                public void onConnect(WebSocket ws){
                    LOG.entering(getClass().getName(), "onConnect", ws);
                    super.onConnect(ws);
                    LOG.exiting(CLASS, "onConnect");
                }
                @Override
                public void onClose(WebSocket ws, DataFrame df){
                    LOG.entering(getClass().getName(), "onClose", Stream.of(ws, df).toArray());
                    super.onClose(ws, df);
                    LOG.exiting(CLASS, "onClose");
                }
                @Override
                public void onMessage(WebSocket ws, String data){
                    LOG.entering(getClass().getName(), "onMessage", Stream.of(ws, data).toArray());
                    if(data.contains("\"type\":\"offer\"")){
                        this.offer = data;
                        getWebSockets().parallelStream().filter(w -> !Objects.equals(w, ws)).forEach(w -> w.send(this.offer));
                    } else if(data.contains("\"type\":\"answer\"")){
                        this.answer = data;
                        getWebSockets().parallelStream().filter(w -> !Objects.equals(w, ws)).forEach(w -> w.send(this.answer));
                    }
                    LOG.exiting(CLASS, "onMessage");
                }
                @Override
                public void onPing(WebSocket socket, byte[] bytes) {
                    LOG.entering(getClass().getName(), "onPing", Stream.of(socket, new String(bytes)).toArray());
                    LOG.exiting(getClass().getName(), "onPing");
                }
                @Override
                public void onPong(WebSocket socket, byte[] bytes) {
                    LOG.entering(getClass().getName(), "onPong", Stream.of(socket, new String(bytes)).toArray());
                    LOG.exiting(getClass().getName(), "onPong");
                }
            }
        );
        try {
            server.start();
            System.out.println("Press any key to stop the server...");
            System.in.read();
            server.shutdownNow();
        } catch (Exception e) {
            LOG.severe(e.toString());
        }
    }
}
