package cloud.fabriciojunior.config;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import org.postgresql.util.PSQLException;

import java.util.Map;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

public class ExceptionMapper {

    @ServerExceptionMapper
    public Response mapException(final NotFoundException exception) {
        return Response.status(NOT_FOUND)
                .entity(Map.of("message", exception.getMessage()))
                .build();
    }

    @ServerExceptionMapper
    public Response mapException(final ConstraintViolationException exception) {
        final PSQLException psqlException = (PSQLException) exception.getCause();
        return Response.status(BAD_REQUEST)
                .entity(Map.of("message", "Restrições cadastrais violadas",
                        "details", psqlException.getServerErrorMessage().getDetail(),
                        "errorCode", psqlException.getSQLState()))
                .build();
    }

    @ServerExceptionMapper
    public Response mapException(final RegraNegocioException exception) {
        return Response.serverError()
                .entity(Map.of("message", exception.getMessage()))
                .build();
    }

}
