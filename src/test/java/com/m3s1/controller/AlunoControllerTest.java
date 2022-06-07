package com.m3s1.controller;

import com.m3s1.dto.AlunoDTO;
import com.m3s1.exception.RegistroNaoEncontradoException;
import com.m3s1.mapper.AlunoMapper;
import com.m3s1.model.Aluno;
import com.m3s1.service.AlunoService;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AlunoControllerTest {

    @Mock
    private AlunoService service;

    @InjectMocks
    private AlunoController controller;

    @Test
    @DisplayName("Quando inserir novo aluno, Deve retornar atributo ID preenchido")
    void inserir_sucesso() {
        // given
        AlunoDTO alunoDTO = new AlunoDTO(1, "nome");
        // when
        Response result = controller.inserir(alunoDTO);
        // then
        assertNotNull(result);
        assertNotNull(result.getEntity());
        AlunoDTO resp = (AlunoDTO) result.getEntity();
        assertInstanceOf(AlunoDTO.class, resp);
        assertEquals(Response.Status.CREATED.getStatusCode(), result.getStatus());
        assertNotNull(resp.getMatricula());
    }

    @Test
    @DisplayName("Quando requisição com ID não existente, Deve retornar status NOT FOUND")
    void alterar_falha() {
        AlunoDTO request = new AlunoDTO();
        Mockito.doThrow(new RegistroNaoEncontradoException("Aluno", "id")).when(service).alterar(Mockito.any(Aluno.class));
        RegistroNaoEncontradoException result = assertThrows(RegistroNaoEncontradoException.class, () -> controller.alterar(1, request));
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Quando requisição com ID existente, Deve retornar status 200 e objeto Aluno")
    void alterar_sucesso() {
        AlunoDTO request = new AlunoDTO();
        Response result = controller.alterar(1, request);
        assertNotNull(result);
        assertNotNull(result.getEntity());
        assertInstanceOf(Aluno.class, result.getEntity());
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
    }

    @Test
    @DisplayName("Quando ID não existente, Deve retornar status NOT FOUND")
    void remover_falha() {
        Mockito.doThrow(new RegistroNaoEncontradoException("Aluno", "id")).when(service).excluir(Mockito.anyInt());
        assertThrows(RegistroNaoEncontradoException.class, () -> controller.remover(1));
    }

    @Test
    @DisplayName("Quando ID existente, Deve retornar status 204 (NO CONTENT) e remover vídeo")
    void remover_sucesso() {
        Response result = controller.remover(1);
        assertNotNull(result);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), result.getStatus());
        assertNull(result.getEntity(), "Não deveria conter objeto no response (decisao de projeto)");
    }

    @Test
    @DisplayName("Quando sem alunos cadastrados, Deve retornar status 200 com lista vazia")
    void obterAlunos_falha() {
        Mockito.when(service.obter("")).thenReturn(new ArrayList<>());
        // when
        Response result = controller.obterAlunos("");
        // then
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertNotNull(result.getEntity(), "Deveria retornar objeto não nulo no response");
        List<AlunoDTO> lista = (List<AlunoDTO>) result.getEntity();
        assertTrue(lista.isEmpty(), "Deveria retornar lista vazia");
    }

    @Test
    @DisplayName("Quando existem alunos cadastrados, Deve retornar status 200 (OK) e lista preenchida")
    void obterAlunos_sucesso() {
        List<Aluno> alunos = Arrays.asList(gerarAluno(), gerarAluno());
        Mockito.when(service.obter("")).thenReturn(alunos);
        Response result = controller.obterAlunos("");
        assertNotNull(result);
        List<AlunoDTO> lista = (List<AlunoDTO>) result.getEntity();
        assertNotNull(lista);
        assertFalse(lista.isEmpty());
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
    }

    @Test
    @DisplayName("Quando requisição com ID não existente, Deve lançar exceção")
    void obter_falha() {
        Mockito.doThrow(new RegistroNaoEncontradoException("Aluno", "id")).when(service).obter(Mockito.anyInt());
        assertThrows(RegistroNaoEncontradoException.class, () -> controller.obter(1));
    }

    @Test
    @DisplayName("Quando requisição com ID válida e existente, Deve retornar status OK e entidade aluno")
    void obter_sucesso() {
        Mockito.when(service.obter(Mockito.anyInt())).thenReturn(gerarAluno());
        Response result = controller.obter(1);
        assertNotNull(result);
        AlunoDTO aluno = (AlunoDTO) result.getEntity();
        assertNotNull(aluno);
        assertEquals(aluno.getMatricula(), 1);
        assertEquals(aluno.getNome(), "Aluno");
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
    }


    private Aluno gerarAluno() {
        return new Aluno(1, "Aluno");
    }

    private AlunoDTO gerarAlunoDTO() {
        return new AlunoDTO("Nome");
    }

}