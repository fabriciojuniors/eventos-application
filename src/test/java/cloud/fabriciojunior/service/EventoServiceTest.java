package cloud.fabriciojunior.service;

import cloud.fabriciojunior.config.RegraNegocioException;
import cloud.fabriciojunior.dto.EventoDto;
import cloud.fabriciojunior.entity.Evento;
import cloud.fabriciojunior.repository.EventoRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class EventoServiceTest {

    @Inject
    EventoService eventoService;

    @Inject
    EventoRepository repository;

    @Test
    public void shouldFindById() {
        final Evento eventoFind = eventoService.findByIdOrElseThrow(UUID.fromString("e1039dce-1226-4812-ba5c-250ddd8fb505"));
        assertNotNull(eventoFind);
    }

    @Test
    public void shouldThrowWhenFindByIdNotExists() {
        assertThrows(NotFoundException.class, () -> eventoService.findByIdOrElseThrow(UUID.randomUUID()));
    }

    @Test
    @Transactional
    public void shouldCreate() {
        final LocalDateTime dataHoraFim = LocalDateTime.of(LocalDate.of(2024, 8, 23), LocalTime.of(10, 30));
        final LocalDateTime dataHoraInicio = LocalDateTime.of(LocalDate.of(2024, 8, 23), LocalTime.of(9, 30));
        final UUID instituicaoId = UUID.fromString("32758e52-a5b5-4a1e-9726-2a5dede7e448");
        final EventoDto dto = new EventoDto(null, "Evento teste de integração", dataHoraInicio, dataHoraFim, true);

        final Evento eventoCreated = eventoService.create(instituicaoId, dto);

        assertNotNull(eventoCreated);
        assertNotNull(eventoCreated.getId());
    }

    @Test
    public void shouldNotCreateWhenInstituicaoNotFound() {
        final UUID instituicaoId = UUID.randomUUID();
        final EventoDto dto = Mockito.mock(EventoDto.class);
        assertThrows(NotFoundException.class, () ->  eventoService.create(instituicaoId, dto));
    }

    @Test
    public void shouldUpdate() {
        final LocalDateTime dataHoraFim = LocalDateTime.of(LocalDate.of(2024, 8, 23), LocalTime.of(10, 30));
        final LocalDateTime dataHoraInicio = LocalDateTime.of(LocalDate.of(2024, 8, 23), LocalTime.of(9, 30));
        final EventoDto dto = new EventoDto(UUID.fromString("ba36587e-cca3-4fb7-980b-bc9db3c3b319"),"ATUALIZADO", dataHoraInicio,dataHoraFim,true);

        final Evento eventoSaved = eventoService.findByIdOrElseThrow(dto.id());
        assertNotNull(eventoSaved);

        final Evento eventoUpdated = eventoService.update(dto.id(), dto);

        assertNotNull(eventoUpdated);
        assertEquals("ATUALIZADO", eventoUpdated.getNome());
        assertEquals(eventoSaved.getId(), eventoUpdated.getId());
    }

    @Test
    public void shouldNotUpdateEventoEncerrado() {
        final LocalDateTime dataHoraFim = LocalDateTime.of(LocalDate.of(2024, 8, 23), LocalTime.of(8, 30));
        final LocalDateTime dataHoraInicio = LocalDateTime.of(LocalDate.of(2024, 8, 23), LocalTime.of(9, 30));
        final EventoDto dto = new EventoDto(UUID.fromString("d4dc4cbc-c0aa-491a-8ac8-84c4b47c92ea"),"ATUALIZADO", dataHoraInicio,dataHoraFim,true);

        assertThrows(RegraNegocioException.class, () ->  eventoService.update(dto.id(), dto), "Não é permitido alterar um evento encerrado.");

    }

    @Test
    @Transactional
    public void shouldDelete() {
        final UUID id = UUID.fromString("e1039dce-1226-4812-ba5c-250ddd8fb505");
        eventoService.delete(id);

        //Deve não encontrar após a exclusão
        assertThrows(NotFoundException.class, () -> eventoService.findByIdOrElseThrow(id));
    }

    @Test
    public void shouldNotDeleteWhenIdNotExists() {
        final UUID id = UUID.randomUUID();
        assertThrows(NotFoundException.class, () -> eventoService.delete(id));
    }

    @Test
    public void shouldNotDeleteWhenEventoEncerrado() {
        final UUID id = UUID.fromString("a58ac2b8-54d5-475d-9cf9-7eb8f2b01ba3");
        assertThrows(RegraNegocioException.class, () -> eventoService.delete(id));
    }

    @Test
    public void shouldEncerrarEventosPendentes() {
        final int eventosPendentes = repository.findAllPendentesEncerramento().size();
        assertEquals(eventosPendentes, eventoService.processaEncerramentoAutomatico());
    }

    @Test
    public void shuldNaoCriarCasoDataFimMenorDataInicio() {
        final LocalDateTime dataHoraInicio = LocalDateTime.of(LocalDate.of(2024, 8, 23), LocalTime.of(9, 30));
        final EventoDto dto = new EventoDto(UUID.fromString("d4dc4cbc-c0aa-491a-8ac8-84c4b47c92ea"),"ATUALIZADO", dataHoraInicio,dataHoraInicio.minusHours(1),true);

        assertThrows(RegraNegocioException.class, () ->  eventoService.update(dto.id(), dto), "A data fim deve ser maior que a data início");

    }


}