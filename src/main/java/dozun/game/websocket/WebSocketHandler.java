package dozun.game.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
//    @Autowired
//    private WebSocketSessionManager sessionManager;
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        String userId = extractUserIdFromSession(session);
//        if (userId != null && sessionManager.hasSession(userId)) {
//            session.close();
//        } else {
//            sessionManager.addSession(userId, session);
//        }
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        String userId = extractUserIdFromSession(session);
//        if (userId != null) {
//            sessionManager.removeSession(userId);
//        }
//    }

    private String extractUsernameFromSession(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
        Object userIdAttribute = session.getAttributes().get("username");
        return userIdAttribute != null ? userIdAttribute.toString() : null;
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
        log.info("", message.getPayload());
        session.sendMessage(new TextMessage("Connected"));
    }
}

