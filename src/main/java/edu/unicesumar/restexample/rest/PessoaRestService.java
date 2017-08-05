package edu.unicesumar.restexample.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/pessoas")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class PessoaRestService {

    private static List<Pessoa> pessoas = new ArrayList<>();

    static {
        pessoas.add(new Pessoa(1, "Jose", "Silva"));
        pessoas.add(new Pessoa(2, "Joao", "Santos"));
    }

    @GET
    // http://localhost:8080/v1/pessoas ou http://localhost:8080/v1/pessoas?nome=xx
    public List<Pessoa> getAll(@QueryParam("nome") String nome) {
        if (nome == null) return pessoas;
        return pessoas.stream().filter(p -> p.getNome().toUpperCase().contains(nome.toUpperCase())).collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    // http://localhost:8080/v1/pessoas/1
    public Pessoa getOne(@PathParam("id") Integer id) {
        return pessoas.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response insert(Pessoa pessoa, @Context UriInfo uriInfo) throws URISyntaxException {
        pessoas.add(pessoa);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(pessoa.getId()));
        return Response.created(builder.build()).build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{id}")
    public Response update(Pessoa pessoa, @PathParam("id") Integer id) {
        return Response.notModified().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        return Response.notModified().build();
    }

    //@GET
    //public Response getAll(@HeaderParam("accept") String contentType) {
     //   if (contentType.isEmpty() || contentType.contains("*/*") || contentType.contains(MediaType.APPLICATION_JSON)) {
     //       final CacheControl cc = new CacheControl();
     //       cc.setMaxAge(3600);

    //        final Response.ResponseBuilder builder = Response.ok(pessoas, MediaType.APPLICATION_JSON);
    //        builder.cacheControl(cc);

    //        return builder.build();
    //    }

    //    return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    //}

}
