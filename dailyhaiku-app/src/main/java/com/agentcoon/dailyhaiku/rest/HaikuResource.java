package com.agentcoon.dailyhaiku.rest;

import com.agentcoon.dailyhaiku.api.HaikuDto;
import com.agentcoon.dailyhaiku.domain.Haiku;
import com.agentcoon.dailyhaiku.domain.HaikuService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Singleton
@Path("/v1/haiku")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HaikuResource {

    private final HaikuService haikuService;
    private final HaikuDtoMapper haikuDtoMapper;

    @Inject
    public HaikuResource(HaikuService haikuService, HaikuDtoMapper haikuDtoMapper) {
        this.haikuService = haikuService;
        this.haikuDtoMapper = haikuDtoMapper;
    }

    @POST
    public Response create(HaikuDto dto, @Context UriInfo uriInfo) {

        Haiku haiku = haikuDtoMapper.from(dto);

        Long id = haikuService.save(haiku);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(String.valueOf(id));

        return Response.created(builder.build()).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, HaikuDto dto) {

        Haiku haiku = haikuService.findById(id);

        if(haiku == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Haiku not found for ID: " + id)
                    .type(MediaType.TEXT_PLAIN).build();
        }

        haikuService.update(haikuDtoMapper.from(id, dto));

        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {

        haikuService.delete(id);

        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {

        Haiku haiku = haikuService.findById(id);

        if(haiku == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Haiku not found for ID: " + id)
                    .type(MediaType.TEXT_PLAIN).build();
        }

        return Response.ok(haikuDtoMapper.from(haiku)).build();
    }

    @GET
    public Response getRandom() {

        Haiku haiku = haikuService.getRandom();

        if(haiku == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Haiku not found")
                    .type(MediaType.TEXT_PLAIN).build();
        }

        return Response.ok(haikuDtoMapper.from(haiku)).build();
    }
}
