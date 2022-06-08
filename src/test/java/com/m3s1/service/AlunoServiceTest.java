package com.m3s1.service;

import com.m3s1.dao.AlunoDao;
import com.m3s1.exception.RegistroNaoEncontradoException;
import com.m3s1.model.Aluno;
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
class AlunoServiceTest {

    @Mock
    private AlunoDao alunoDao;

    @InjectMocks
    private AlunoService service;

    @Test
    @DisplayName("Quando requisição com ID não existente, deve retornar status 404 NOT FOUND e lançar exceção")
    void obterPorMatricula_falha() {
        Mockito.when(alunoDao.obterPorMatricula(Mockito.anyInt())).thenReturn(Optional.empty());
        RegistroNaoEncontradoException result = assertThrows(RegistroNaoEncontradoException.class, () -> service.obter(1));
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Quando requisição com ID existente, Deve retornar Aluno instanciado")
    void obterPorMatricula_sucesso() {
        Mockito.when(alunoDao.obterPorMatricula(Mockito.anyInt())).thenReturn(Optional.of(new Aluno()));
        Aluno result = service.obter(1);
        assertNotNull(result);
        assertInstanceOf(Aluno.class, result);
    }
}