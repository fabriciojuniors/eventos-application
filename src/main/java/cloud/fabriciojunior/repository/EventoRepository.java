package cloud.fabriciojunior.repository;

import cloud.fabriciojunior.entity.Evento;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class EventoRepository implements PanacheRepositoryBase<Evento, UUID> {
}
