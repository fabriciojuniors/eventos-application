package cloud.fabriciojunior.service;

import cloud.fabriciojunior.config.RegraNegocioException;
import cloud.fabriciojunior.dto.EventoDto;
import cloud.fabriciojunior.entity.Evento;
import cloud.fabriciojunior.entity.Instituicao;
import cloud.fabriciojunior.repository.EventoRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class EventoService {

    @Inject
    InstituicaoService instituicaoService;

    @Inject
    EventoRepository repository;

    public Evento findByIdOrElseThrow(final UUID id) {
        final Optional<Evento> eventoOpt = repository.findByIdOptional(id);
        return eventoOpt.orElseThrow(() -> new NotFoundException(String.format("Evento id %s não existe", id)));
    }

    public Evento create(final UUID idInstituicao, final EventoDto dto) {
        final Instituicao instituicao = instituicaoService.findByIdOrElseThrow(idInstituicao);
        final Evento evento = dto.toEntity(instituicao);
        repository.persist(evento);
        return  evento;
    }

    public Evento update(final UUID id, final EventoDto dto) {
        final Evento evento = findByIdOrElseThrow(id);

        validarEventoEncerrado(evento);

        final Evento eventoUpdated = dto.toEntity(evento);
        eventoUpdated.encerraSeNecessario();
        repository.persist(eventoUpdated);
        return eventoUpdated;
    }

    public void delete(final UUID id) {
        final Evento evento = findByIdOrElseThrow(id);
        validarEventoEncerrado(evento);
        repository.delete(evento);
    }

    private void validarEventoEncerrado(Evento evento) {
        if (!evento.isAtivo()) {
            throw new RegraNegocioException("Não é permitido alterar um evento encerrado.");
        }
    }

    @Transactional
    public int processaEncerramentoAutomatico() {
        final Collection<Evento> eventosParaEncerrar = repository.findAllPendentesEncerramento();
        eventosParaEncerrar.forEach(evento -> {
            evento.encerraSeNecessario();
            repository.persist(evento);
        });

        return eventosParaEncerrar.size();
    }
}
