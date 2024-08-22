package cloud.fabriciojunior.resources;

import cloud.fabriciojunior.dto.InstituicaoDto;
import cloud.fabriciojunior.entity.Instituicao;
import cloud.fabriciojunior.service.InstituicaoService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.CREATED;

@Path("instituicoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InstituicaoResource {

    @Inject
    InstituicaoService service;

    @GET
    public Response findAll(@DefaultValue("0") @QueryParam("page") final int page,
                            @DefaultValue("20") @QueryParam("size") final int size) {
        return Response.ok(service.findAll(page, size)).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") final UUID id) {
        return Response.ok(InstituicaoDto.from(service.findByIdOrElseThrow(id))).build();
    }

    @POST
    @Transactional
    public Response save(final InstituicaoDto dto) {
        final Instituicao instituicao = service.save(dto);
        return Response.status(CREATED).entity(InstituicaoDto.from(instituicao)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") final UUID id,
                           final InstituicaoDto dto) {
        final Instituicao instituicao = service.update(id, dto);
        return Response.ok(InstituicaoDto.from(instituicao)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") final UUID id) {
        service.delete(id);
        return Response.noContent().build();
    }

}
