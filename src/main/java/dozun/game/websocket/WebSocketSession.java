//package dozun.game.websocket;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import dozun.game.payloads.responses.GameResponse;
//import jakarta.websocket.OnClose;
//import jakarta.websocket.OnError;
//import jakarta.websocket.OnOpen;
//import jakarta.websocket.Session;
//import jakarta.websocket.server.ServerEndpoint;
//import org.springframework.stereotype.Component;
//import java.io.IOException;
//
//
//@ServerEndpoint("/topic/game")
//@Component
//public class WebSocketSession {
//
//    private Session session;
//
//    @OnOpen
//    public void onOpen(Session session) {
//        this.session = session;
//        System.out.println("WebSocket opened: " + session.getId());
//    }
//
//    @OnClose
//    public void onClose() {
//        this.session = null;
//        System.out.println("WebSocket closed");
//    }
//
//    @OnError
//    public void onError(Throwable error) {
//        error.printStackTrace();
//    }
//
//    public void sendMessage(GameResponse gameResponse) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            if (session != null && session.isOpen()) {
//                String json = objectMapper.writeValueAsString(gameResponse);
//                session.getBasicRemote().sendText(json);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
