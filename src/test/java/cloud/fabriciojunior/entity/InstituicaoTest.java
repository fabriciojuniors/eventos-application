package cloud.fabriciojunior.entity;

import cloud.fabriciojunior.entity.enums.TipoInstituicao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InstituicaoTest {

    @Test
    public void shouldBuild() {
        final String nome = "NOME";
        final Instituicao instituicao = Instituicao.builder()
                .nome(nome)
                .tipo(TipoInstituicao.CENTRAL)
                .build();

        assertNotNull(instituicao);
        assertEquals(nome, instituicao.getNome());
        assertEquals(TipoInstituicao.CENTRAL, instituicao.getTipo());
    }

}