package com.m3s1.service;

import com.m3s1.dao.AlunoDao;
import com.m3s1.dao.CursoDao;
import com.m3s1.dao.InscricaoDao;
import com.m3s1.exception.RegistroExistenteException;
import com.m3s1.exception.RegistroNaoEncontradoException;
import com.m3s1.model.Aluno;
import com.m3s1.model.Curso;
import com.m3s1.model.Inscricao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InscricaoServiceTest {

    @Mock
    private InscricaoDao inscricaoDao;

    @Mock
    private AlunoDao alunoDao;

    @Mock
    private CursoDao cursoDao;

    @InjectMocks
    private InscricaoService service;

    @Test
    @DisplayName("Quando requisição com aluno e curso duplicados, Deve retornar status 409 CONFLICT e lançar exceção")
    void inserir_falha() {
        Inscricao inscricao = gerarInscricao();
        Mockito.when(alunoDao.obterPorMatricula(Mockito.anyInt())).thenReturn(Optional.of(inscricao.getAluno()));
        Mockito.when(cursoDao.obterPorCodigo(Mockito.anyString())).thenReturn(inscricao.getCurso());
        Mockito.doThrow(new RegistroExistenteException("Inscrição", "1")).when(inscricaoDao).salvar(inscricao);
        RegistroExistenteException result = assertThrows(RegistroExistenteException.class, () -> service.inserir(inscricao));
        assertEquals(Response.Status.CONFLICT.getStatusCode(), result.getResponse().getStatus());
        assertNull(result.getResponse().getEntity());
    }

    @Test
    @DisplayName("Quando requisição válida, Deve retonar status 201 CREATED e entidade Inscrição instanciada")
    void inserir_sucesso() {
        Inscricao inscricao = gerarInscricao();
        Mockito.when(alunoDao.obterPorMatricula(Mockito.anyInt())).thenReturn(Optional.of(inscricao.getAluno()));
        Mockito.when(cursoDao.obterPorCodigo(Mockito.anyString())).thenReturn(inscricao.getCurso());
//        Mockito.doReturn(inscricao).when(inscricaoDao).salvar(inscricao);
        Inscricao result = service.inserir(inscricao);
        assertNotNull(result);
        assertInstanceOf(Inscricao.class, result);
    }

    @Test
    @DisplayName("Quando requisição com ID não existente, Deve retornar status 404 NOT FOUND e lançar exceção")
    void excluir_falha() {
        Mockito.doThrow(new RegistroNaoEncontradoException("Inscrição", "1")).when(inscricaoDao).deletar(Mockito.anyInt());
        RegistroNaoEncontradoException result = assertThrows(RegistroNaoEncontradoException.class, () -> service.excluir(1));
        assertNotNull(result);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Quando requisição com ID existente, Deve retornar status 204 NO CONTENT")
    void excluir_sucesso() {
        Mockito.doNothing().when(inscricaoDao).deletar(Mockito.anyInt());
        assertDoesNotThrow(() -> service.excluir(1));
    }

    private Inscricao gerarInscricao() {
        return new Inscricao(1, new Aluno(1, "Aluno"), new Curso("id", "WEB java", 60));
    }

}