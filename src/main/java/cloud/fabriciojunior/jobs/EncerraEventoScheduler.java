package cloud.fabriciojunior.jobs;

import cloud.fabriciojunior.service.EventoService;
import io.quarkus.scheduler.Scheduled;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

public class EncerraEventoScheduler {

    @Inject
    Logger logger;

    @Inject
    EventoService eventoService;

    @Scheduled(every = "{encerra-eventos-scheduler.time}")
    public void encerraEventoScheduler() {
        logger.info("Iniciando processo de encerramento de eventos");

        final int eventosEncerrados = eventoService.processaEncerramentoAutomatico();

        logger.info("Encerramento de eventos finalizado. Eventos encerrados:" + eventosEncerrados);
    }

}
