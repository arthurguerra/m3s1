package com.m3s1.controller;

import com.m3s1.dto.CursoDTO;
import com.m3s1.exception.RegistroNaoEncontradoException;
import com.m3s1.model.Curso;
import com.m3s1.service.CursoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CursoControllerTest {

    @Mock
    private CursoService service;

    @InjectMocks
    private CursoController controller;

    @Test
    @DisplayName("Quando inserir curso, Deve retornar status 201 CREATED e objeto Curso com ID preenchido")
    void inserir_sucesso() {
        CursoDTO cursoDTO = gerarCursoDTO();
        cursoDTO.setCodigo(UUID.randomUUID().toString());
        Response result = controller.inserir(cursoDTO);
        assertNotNull(result);
        CursoDTO curso = (CursoDTO) result.getEntity();
        assertNotNull(curso);
        assertNotNull(curso.getCodigo());
        assertEquals(Response.Status.CREATED.getStatusCode(), result.getStatus());
    }

    @Test
    @DisplayName("Quando requisição com ID não existente, Deve retornar status NOT FOUND 404")
    void alterar_falha() {
        CursoDTO cursoDTO = gerarCursoDTO();
        String codigo = UUID.randomUUID().toString();
        Mockito.doThrow(new RegistroNaoEncontradoException("Curso", codigo)).when(service).alterar(Mockito.any(Curso.class));
        RegistroNaoEncontradoException result = assertThrows(RegistroNaoEncontradoException.class, () -> controller.alterar(codigo, cursoDTO));
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Quando requisição com ID existente, Deve retornar status 200 e objeto Curso")
    void alterar_sucesso() {
        CursoDTO cursoDTO = gerarCursoDTO();
        Response result = controller.alterar(UUID.randomUUID().toString(), cursoDTO);
        assertNotNull(result);
        assertNotNull(result.getEntity());
        assertInstanceOf(Curso.class, result.getEntity());
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
    }

    @Test
    @DisplayName("Quando ID não existente, Deve retornar status NOT FOUND")
    void remover_falha() {
        Mockito.doThrow(new RegistroNaoEncontradoException("Aluno", "id")).when(service).excluir(Mockito.anyString());
        assertThrows(RegistroNaoEncontradoException.class, () -> controller.remover(UUID.randomUUID().toString()));
    }

    @Test
    @DisplayName("Quando ID existente, Deve retornar status 204 (NO CONTENT) e remover vídeo")
    void remover_sucesso() {
        Response result = controller.remover(UUID.randomUUID().toString());
        assertNotNull(result);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), result.getStatus());
        assertNull(result.getEntity(), "Não deveria conter objeto no response (decisao de projeto)");
    }
    @Test
    @DisplayName("Quando sem cursos cadastrados, Deve retornar status 200 com lista vazia")
    void obterTodos_falha() {
        Mockito.when(service.obter("", 10)).thenReturn(new ArrayList<>());
        // when
        Response result = controller.obter("", 10);
        // then
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertNotNull(result.getEntity(), "Deveria retornar objeto não nulo no response");
        List<CursoDTO> lista = (List<CursoDTO>) result.getEntity();
        assertTrue(lista.isEmpty(), "Deveria retornar lista vazia");
    }

    @Test
    @DisplayName("Quando existem cursos cadastrados, Deve retornar status 200 (OK) e lista preenchida")
    void obterTodos_sucesso() {
        List<Curso> cursos = Arrays.asList(gerarCurso(), gerarCurso());
        Mockito.when(service.obter(Mockito.anyString(), Mockito.anyInt())).thenReturn(cursos);
        Response result = controller.obter("", 10);
        assertNotNull(result);
        List<Curso> lista = (List<Curso>) result.getEntity();
        assertNotNull(lista);
        assertFalse(lista.isEmpty());
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
    }

    @Test
    @DisplayName("Quando requisição com ID não existe, Deve retornar status 404 NOT FOUND e lançar exceção")
    void obter_falha() {
        Mockito.doThrow(new RegistroNaoEncontradoException("Curso", "id")).when(service).excluir(Mockito.anyString());
        RegistroNaoEncontradoException result = assertThrows(RegistroNaoEncontradoException.class, () -> controller.remover("id"));
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Quando requisição com ID existente, Deve retornar STATUS OK 200 e entidade Curso")
    void obter_sucesso() {
        Curso curso = gerarCurso();
        curso.setCodigo("codigo");
        Mockito.when(service.obter(Mockito.anyString())).thenReturn(curso);
        Response result = controller.obter("id");
        assertNotNull(result);
        CursoDTO cursoDTO = (CursoDTO) result.getEntity();
        assertNotNull(curso);
        assertEquals(cursoDTO.getCodigo(), "codigo");
        assertEquals(cursoDTO.getAssunto(), "Assunto");
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
    }

    private Curso gerarCurso() {
        return new Curso("Assunto", 90);
    }

    private CursoDTO gerarCursoDTO() {
        return new CursoDTO("Assunto", 100);
    }
}