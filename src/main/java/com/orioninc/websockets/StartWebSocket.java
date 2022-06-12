package com.orioninc.websockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/binocla/{gonnaLit}") // URI WebSocket сервера
@ApplicationScoped
public class StartWebSocket {
    private static final Logger LOGGER = LoggerFactory.getLogger(StartWebSocket.class);

    private Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("gonnaLit") String gonnaLit) {
        LOGGER.info("К нам присоединился {}!", gonnaLit);
        // Сохраняем сессию в общем словаре
        sessions.put(gonnaLit, session);
        // Отправляем конкретной сессии текстовое сообщение
        session.getAsyncRemote().sendText(String.format("Привет, %s!", gonnaLit)); // Можно и .getBasicRemote()
    }

    @OnClose
    public void onClose(Session session, @PathParam("gonnaLit") String gonnaLit) {
        LOGGER.info("{} отключился :(", gonnaLit);
        sessions.values()
                .forEach(action -> {
                    action.getAsyncRemote()
                            .sendText(String.format("%s отвалился(", gonnaLit), sendResult -> {
                                if (Objects.nonNull(sendResult.getException())) {
                                    LOGGER.error("Не получилось отправить сообщение: ", sendResult.getException());
                                }
                            });
                }); // Рассылка всем нашим участникам (открытым соединениям)

    }

    @OnError
    public void onError(Session session, @PathParam("gonnaLit") String gonnaLit, Throwable throwable) {
        LOGGER.error("Что-то пошло не так у {}: {}", gonnaLit, throwable);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("gonnaLit") String gonnaLit) {
        LOGGER.info("""
                {} нам что-то прислал!
                Само сообщение:
                {}
                """, gonnaLit, message);
    }
}
