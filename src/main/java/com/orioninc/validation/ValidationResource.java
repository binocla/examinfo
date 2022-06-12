package com.orioninc.validation;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.annotations.SseElementType;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/validation")
public class ValidationResource {
    private List<Binocla> savedBinoclas = new ArrayList<>();

    /**
     * Retrieves all Binoclas
     *
     * @return Multi of Binocla
     */
    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    public Multi<Binocla> getBinoclas() {
        return Multi.createFrom().items(savedBinoclas.parallelStream())
                .log()
                .cache();
    }

    /**
     * Add new Binocla to the ArrayList
     *
     * @param binocla model itself
     * @return HttpStatus 201 with location & entity
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "201", description = "Binocla saved", headers = @Header(name = "location", description = "Location of the Binocla"))
    @APIResponse(responseCode = "400", description = "Validation for Binocla failed!")
    public Response addBinocla(@NotNull @Valid Binocla binocla) {
        savedBinoclas.add(binocla);
        return Response.created(URI.create(binocla.firstName())).entity(binocla).build();
    }

}
