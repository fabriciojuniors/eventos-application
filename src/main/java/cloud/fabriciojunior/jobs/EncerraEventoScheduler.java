package cloud.fabriciojunior.jobs;

import cloud.fabriciojunior.service.EventoService;
import cloud.fabriciojunior.ws.EncerraEventoWs;
import io.quarkus.scheduler.Scheduled;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.Map;

public class EncerraEventoScheduler {

    @Inject
    Logger logger;

    @Inject
    EventoService eventoService;

    @Inject
    EncerraEventoWs ws;

    @Scheduled(every = "{encerra-eventos-scheduler.time}")
    public void encerraEventoScheduler() {
        logger.info("Iniciando processo de encerramento de eventos");

        final int eventosEncerrados = eventoService.processaEncerramentoAutomatico();
        if (eventosEncerrados > 0) {
            ws.broadcast(Map.of(
                    "titulo", "Encerramento de eventos",
                    "mensagem", String.format("Eventos encerrados: %s", eventosEncerrados)
            ));
        }

        logger.info("Encerramento de eventos finalizado. Eventos encerrados:" + eventosEncerrados);
    }

}
