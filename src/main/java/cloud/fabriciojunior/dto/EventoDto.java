package cloud.fabriciojunior.dto;

import cloud.fabriciojunior.entity.Evento;
import cloud.fabriciojunior.entity.Instituicao;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventoDto(UUID id,
                        String nome,
                        LocalDateTime dataHoraInicio,
                        LocalDateTime dataHoraFim,
                        boolean ativo) {
    public static EventoDto from(final Evento evento) {
        return new EventoDto(evento.getId(), evento.getNome(), evento.getDataHoraInicio(), evento.getDataHoraFim(), evento.isAtivo());
    }

    public Evento toEntity(final Instituicao instituicao) {
        return Evento.builder()
                .instituicao(instituicao)
                .nome(nome())
                .dataHoraFim(dataHoraFim())
                .dataHoraInicio(dataHoraInicio())
                .ativo(true)
                .build();
    }

    public Evento toEntity(final Evento evento) {
        evento.setNome(nome());
        evento.setDataHoraFim(dataHoraFim());
        evento.setDataHoraInicio(dataHoraInicio());
        return evento;
    }
}
