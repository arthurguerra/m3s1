package com.m3s1.controller;

import com.m3s1.dto.AlunoDTO;
import com.m3s1.mapper.AlunoMapper;
import com.m3s1.model.Aluno;
import com.m3s1.security.Authorize;
import com.m3s1.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/alunos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AlunoController {

    @Inject
    private AlunoService service;

    @Operation( summary = "Criar Aluno", description = "Criação de Aluno",
            responses = {
                    @ApiResponse( responseCode = "201", description = "Aluno cadastrado",
                            content = {@Content( schema = @Schema(implementation = AlunoDTO.class) ) }),
                    @ApiResponse( responseCode = "400", description = "Request inválida" )
            })
    @Authorize
    @POST
    public Response inserir(@Valid AlunoDTO alunoDTO) {
        Aluno aluno = AlunoMapper.INSTANCE.toModel(alunoDTO);
        service.inserir(aluno);
        alunoDTO = AlunoMapper.INSTANCE.toDTO(aluno);
        return Response
                .created(URI.create(aluno.getMatricula().toString()))
                .entity(alunoDTO)
                .build();
    }

    @Authorize
    @PUT
    @Path("/{matricula}")
    public Response alterar(@PathParam("matricula") Integer matricula, @Valid AlunoDTO alunoDTO) {
        alunoDTO.setMatricula(matricula);
        Aluno aluno = AlunoMapper.INSTANCE.toModel(alunoDTO);
        service.alterar(aluno);
        return Response.ok(aluno).build();
    }

    @Authorize
    @DELETE
    @Path("/{matricula}")
    public Response remover(@PathParam("matricula") Integer matricula) {
        service.excluir(matricula);
        return Response.noContent().build();
    }

    @GET
    public Response obterAlunos(@QueryParam("nome") String nome) {
        List<Aluno> alunos = service.obter(nome);
        List<AlunoDTO> resp = alunos.stream()
                .map(AlunoMapper.INSTANCE::toDTO).collect(Collectors.toList());
        return Response.ok(resp).build();
    }

    @GET
    @Path("/{matricula}")
    public Response obter(@PathParam("matricula") Integer matricula) {
        Aluno aluno = service.obter(matricula);
        AlunoDTO resp = AlunoMapper.INSTANCE.toDTO(aluno);
        return Response.ok(resp).build();
    }
}

