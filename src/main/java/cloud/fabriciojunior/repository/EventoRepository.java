package cloud.fabriciojunior.repository;

import cloud.fabriciojunior.entity.Evento;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@ApplicationScoped
public class EventoRepository implements PanacheRepositoryBase<Evento, UUID> {

    public Collection<Evento> findAllPendentesEncerramento() {
        final LocalDateTime dataHora = LocalDateTime.now();
        return list("dataHoraFim <= ?1 and ativo = ?2", dataHora, true);
    }

}
