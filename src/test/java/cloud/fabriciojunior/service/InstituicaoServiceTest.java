package cloud.fabriciojunior.service;

import cloud.fabriciojunior.dto.InstituicaoDto;
import cloud.fabriciojunior.entity.Instituicao;
import cloud.fabriciojunior.entity.enums.TipoInstituicao;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class InstituicaoServiceTest {

    @Inject
    InstituicaoService instituicaoService;

    @Test
    public void shouldFindById() {
        final Instituicao instituicao = instituicaoService.findByIdOrElseThrow(UUID.fromString("32758e52-a5b5-4a1e-9726-2a5dede7e448"));
        assertNotNull(instituicao);
    }

    @Test
    public void shouldThrowWhenFindByIdNotExists() {
        assertThrows(NotFoundException.class, () -> instituicaoService.findByIdOrElseThrow(UUID.randomUUID()));
    }

    @Test
    @Transactional
    public void shouldCreate() {
        final InstituicaoDto dto = new InstituicaoDto(null, "INSTITUICAO TESTE", TipoInstituicao.CENTRAL, Collections.emptyList());
        final Instituicao instituicao = instituicaoService.save(dto);

        assertNotNull(instituicao);
        assertNotNull(instituicao.getId());
    }

    @Test
    public void shouldUpdate() {
        final UUID id = UUID.fromString("32758e52-a5b5-4a1e-9726-2a5dede7e448");
        final InstituicaoDto dto = new InstituicaoDto(id, "INSTITUICAO ATUALIZADO", TipoInstituicao.CENTRAL, Collections.emptyList());
        final Instituicao instituicaoSaved = instituicaoService.findByIdOrElseThrow(dto.id());
        assertNotNull(instituicaoSaved);

        final Instituicao instituicaoUpdated = instituicaoService.update(dto.id(), dto);

        assertNotNull(instituicaoUpdated);
        assertEquals("INSTITUICAO ATUALIZADO", instituicaoUpdated.getNome());
        assertEquals(instituicaoSaved.getId(), instituicaoUpdated.getId());
    }

    @Test
    @Transactional
    public void shouldDelete() {
        final UUID id = UUID.fromString("6f3de683-89ab-45c5-9b3e-16f0b695cbc4");
        instituicaoService.delete(id);

        //Deve não encontrar após a exclusão
        assertThrows(NotFoundException.class, () -> instituicaoService.findByIdOrElseThrow(id));
    }

    @Test
    public void shouldNotDeleteWhenIdNotExists() {
        final UUID id = UUID.randomUUID();
        assertThrows(NotFoundException.class, () -> instituicaoService.delete(id));
    }

}