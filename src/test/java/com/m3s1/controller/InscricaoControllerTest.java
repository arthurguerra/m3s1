package com.m3s1.controller;

import com.m3s1.dto.CursoDTO;
import com.m3s1.dto.InscricaoReqDTO;
import com.m3s1.dto.InscricaoRespDTO;
import com.m3s1.exception.RegistroNaoEncontradoException;
import com.m3s1.mapper.InscricaoMapper;
import com.m3s1.model.Aluno;
import com.m3s1.model.Curso;
import com.m3s1.model.Inscricao;
import com.m3s1.service.InscricaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InscricaoControllerTest {

    @Mock
    private InscricaoService service;

    @InjectMocks
    private InscricaoController controller;

    @Test
    @DisplayName("Quando requisição com matrícula de aluno ou código de curso inexistente, Deve retornar status 404 NOT FOUND e lançar exceção")
    void inserir_falha() {
        InscricaoReqDTO inscricaoReqDTO = gerarInscricaoReq();
        Mockito.when(service.inserir(Mockito.any(Inscricao.class))).thenThrow(new RegistroNaoEncontradoException("Curso", "id"));
        RegistroNaoEncontradoException result = assertThrows(RegistroNaoEncontradoException.class, () -> controller.inserir(inscricaoReqDTO));
        assertNotNull(result);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Quando requisição com matrícula e código de curso válidos, Deve retornar status 201 CREATED e atributo ID preenchido")
    void inserir_sucesso() {
        InscricaoReqDTO request = gerarInscricaoReq();
        Inscricao inscricao = InscricaoMapper.INSTANCE.toModel(request);
        inscricao.setId(1);
        Mockito.when(service.inserir(Mockito.any(Inscricao.class))).thenReturn(inscricao);
        Response result = controller.inserir(request);
        assertNotNull(result);
        InscricaoRespDTO inscricaoResponse = (InscricaoRespDTO) result.getEntity();
        assertNotNull(inscricaoResponse);
        assertNotNull(inscricaoResponse.getId());
        assertEquals(inscricao.getId(), inscricaoResponse.getId());
        assertEquals(Response.Status.CREATED.getStatusCode(), result.getStatus());
    }

    @Test
    @DisplayName("Quando requisição com ID inválido, Deve retornar status 404 NOT FOUND e lançar exceção")
    void remover_falha() {
        Mockito.doThrow(new RegistroNaoEncontradoException("Inscrição", "1")).when(service).excluir(Mockito.anyInt());
        RegistroNaoEncontradoException result = assertThrows(RegistroNaoEncontradoException.class, () -> controller.remover(1));
        assertNotNull(result);
        assertNotNull(result.getResponse());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), result.getResponse().getStatus());
        assertNull(result.getResponse().getEntity());
    }

    @DisplayName("Quando requisição com ID válido, Deve retornar status 204 NO CONTENT")
    @Test
    void remover_sucesso() {
        Response result = controller.remover(1);
        assertNotNull(result);
        assertNull(result.getEntity());
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), result.getStatus());
    }

    @Test
    @DisplayName("Quando sem inscrições cadastradas, Deve retornar status 200 com lista vazia")
    void obter_falha() {
//        Mockito.when(service.obter()).thenReturn(new ArrayList<>());
        Response result = controller.obter();
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertNotNull(result.getEntity());
        List<Inscricao> lista = (List<Inscricao>) result.getEntity();
        assertTrue(lista.isEmpty());
    }

    @DisplayName("Quando existem inscrições cadastradas, Deve retornar status 200 com lista preenchida")
    @Test
    void obter_sucesso() {
        List<Inscricao> inscricoes= Arrays.asList(gerarInscricao(), gerarInscricao());
        Mockito.when(service.obter()).thenReturn(inscricoes);
        Response result = controller.obter();
        assertNotNull(result);
        List<Inscricao> resp = (List<Inscricao>) result.getEntity();
        assertNotNull(resp);
        assertFalse(resp.isEmpty());
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
    }

    private InscricaoReqDTO gerarInscricaoReq() {
        return new InscricaoReqDTO(1, "id");
    }

    private Inscricao gerarInscricao() {
        return new Inscricao(1, new Aluno(), new Curso());
    }
}