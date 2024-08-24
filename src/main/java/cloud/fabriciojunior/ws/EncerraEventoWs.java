package cloud.fabriciojunior.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ServerEndpoint("/encerra-evento-ws")
@ApplicationScoped
public class EncerraEventoWs {

    @Inject
    Logger logger;

    private final List<Session> sessions = new ArrayList<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    public void broadcast(final Map<String, String> message) {
        try {
            final ObjectMapper om = new ObjectMapper();
            final String msg = om.writeValueAsString(message);
            sessions.forEach(s -> {
                s.getAsyncRemote().sendObject(msg, result ->  {
                    if (result.getException() != null) {
                        logger.error(String.format("Erro ao notificar ouvintes. Motivo: %s", result.getException().getMessage()));
                    }
                });
            });
        } catch (Exception ex) {
            logger.error(String.format("Erro ao processar mensagem do websocket. Motivo: %s", ex.getMessage()));
        }
    }

}
