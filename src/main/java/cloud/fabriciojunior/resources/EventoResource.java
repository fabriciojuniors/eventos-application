package cloud.fabriciojunior.resources;

import cloud.fabriciojunior.dto.EventoDto;
import cloud.fabriciojunior.entity.Evento;
import cloud.fabriciojunior.service.EventoService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.CREATED;

@Path("instituicoes/{idInstituicao}/eventos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EventoResource {

    @Inject
    EventoService service;

    @POST
    @Transactional
    public Response save(@PathParam("idInstituicao") final UUID idInstituicao,
                         EventoDto dto) {
        final Evento evento = service.create(idInstituicao, dto);
        return Response.status(CREATED).entity(EventoDto.from(evento)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response save(@PathParam("idInstituicao") final UUID idInstituicao,
                         @PathParam("id") final UUID id,
                         EventoDto dto) {
        final Evento evento = service.update(id, dto);
        return Response.ok(EventoDto.from(evento)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("idInstituicao") final UUID idInstituicao,
                         @PathParam("id") final UUID id,
                         EventoDto dto) {
        service.delete(id);
        return Response.noContent().build();
    }

}
