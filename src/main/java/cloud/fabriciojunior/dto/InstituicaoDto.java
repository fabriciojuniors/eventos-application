package cloud.fabriciojunior.dto;

import cloud.fabriciojunior.entity.Instituicao;
import cloud.fabriciojunior.entity.enums.TipoInstituicao;

import java.util.UUID;

public record InstituicaoDto(UUID id,
                             String nome,
                             TipoInstituicao tipo) {

    public static InstituicaoDto from(final Instituicao instituicao) {
        return new InstituicaoDto(instituicao.getId(), instituicao.getNome(), instituicao.getTipo());
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
