package cloud.fabriciojunior.service;

import cloud.fabriciojunior.dto.InstituicaoDto;
import cloud.fabriciojunior.dto.PageDto;
import cloud.fabriciojunior.entity.Instituicao;
import cloud.fabriciojunior.repository.InstituicaoRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class InstituicaoService {

    @Inject
    InstituicaoRepository repository;

    public PageDto<InstituicaoDto> findAll(final int pageNumber, final int pageSize) {
        final PanacheQuery<Instituicao> response = repository.findAll().page(pageNumber, pageSize);
        final List<InstituicaoDto> instituicoes = response.list()
                .stream().map(InstituicaoDto::from)
                .toList();

        return new PageDto<>(instituicoes, response.hasNextPage(), response.count());
    }

    public Instituicao findByIdOrElseThrow(final UUID id) {
        final Optional<Instituicao> instituicaoOpt = repository.findByIdOptional(id);
        return instituicaoOpt.orElseThrow(() -> new NotFoundException(String.format("Instituição id %s não existe", id)));
    }

    public Instituicao save(final InstituicaoDto dto) {
        final Instituicao instituicao = dto.toEntity();
        repository.persist(instituicao);
        return instituicao;
    }

    public Instituicao update(final UUID id, final InstituicaoDto dto) {
        final Instituicao instituicao = findByIdOrElseThrow(id);
        final Instituicao instituicaoUpdate = dto.toEntity(instituicao);
        repository.persist(instituicaoUpdate);
        return instituicaoUpdate;
    }

    public void delete(final UUID id) {
        final Instituicao instituicao = findByIdOrElseThrow(id);
        repository.delete(instituicao);
    }
}
