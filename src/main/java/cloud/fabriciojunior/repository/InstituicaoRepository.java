package cloud.fabriciojunior.repository;

import cloud.fabriciojunior.entity.Instituicao;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class InstituicaoRepository implements PanacheRepositoryBase<Instituicao, UUID> {
}
