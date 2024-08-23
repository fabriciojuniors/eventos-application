package cloud.fabriciojunior.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class EventoTest {

    @Test
    public void shouldBuild() {
        final String nome = "NOME";
        final LocalDateTime dataHoraFim = LocalDateTime.of(LocalDate.of(2024, 8, 23), LocalTime.of(8, 30));
        final LocalDateTime dataHoraInicio = LocalDateTime.of(LocalDate.of(2024, 8, 23), LocalTime.of(9, 30));

        final Evento evento = Evento.builder()
                .nome(nome)
                .ativo(false)
                .dataHoraFim(dataHoraFim)
                .dataHoraInicio(dataHoraInicio)
                .build();

        assertNotNull(evento);
        assertEquals(nome, evento.getNome());
        assertFalse(evento.isAtivo());
        assertEquals(dataHoraFim, evento.getDataHoraFim());
        assertEquals(dataHoraInicio, evento.getDataHoraInicio());
    }

    @Test
    public void shouldEncerrarSeDataHoraFimMenorQueAtual() {
        final LocalDateTime dataHoraFim = LocalDateTime.of(LocalDate.of(2024, 8, 20), LocalTime.of(9, 30));
        final Evento evento = new Evento();
        evento.setDataHoraFim(dataHoraFim);

        evento.encerraSeNecessario();

        assertFalse(evento.isAtivo());
    }

    @Test
    public void shouldNaoEncerrarSeDataHoraFimMaiorQueAtual() {
        final LocalDateTime dataHoraFim = LocalDateTime.now().plusHours(1);
        final Evento evento = new Evento();
        evento.setDataHoraFim(dataHoraFim);

        evento.encerraSeNecessario();

        assertTrue(evento.isAtivo());
    }

}