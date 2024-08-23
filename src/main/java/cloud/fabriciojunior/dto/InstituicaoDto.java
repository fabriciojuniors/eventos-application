package cloud.fabriciojunior.dto;

import cloud.fabriciojunior.entity.Instituicao;
import cloud.fabriciojunior.entity.enums.TipoInstituicao;

import java.util.Collection;
import java.util.UUID;

public record InstituicaoDto(UUID id,
                             String nome,
                             TipoInstituicao tipo,
                             Collection<EventoDto> eventos) {

    public static InstituicaoDto from(final Instituicao instituicao) {
        final Collection<EventoDto> eventos = instituicao.getEventos().stream()
                .map(EventoDto::from)
                .toList();
        return new InstituicaoDto(instituicao.getId(),
                instituicao.getNome(),
                instituicao.getTipo(),
                eventos);
    }

    public Instituicao toEntity() {
        return Instituicao.builder()
                .id(id())
                .nome(nome())
                .tipo(tipo())
                .build();
    }

    public Instituicao toEntity(final Instituicao instituicao) {
        instituicao.setNome(nome());
        instituicao.setTipo(tipo());
        return instituicao;
    }
}
