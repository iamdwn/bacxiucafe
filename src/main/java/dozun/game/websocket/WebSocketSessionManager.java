//package dozun.game.websocket;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.WebSocketSession;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//public class WebSocketSessionManager {
//    private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
//
//    public void addSession(String userId, WebSocketSession session) {
//        userSessions.put(userId, session);
//    }
//
//    public void removeSession(String userId) {
//        userSessions.remove(userId);
//    }
//
//    public boolean hasSession(String userId) {
//        return userSessions.containsKey(userId);
//    }
//
//    public WebSocketSession getSession(String userId) {
//        return userSessions.get(userId);
//    }
//}
