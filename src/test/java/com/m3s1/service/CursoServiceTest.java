package com.m3s1.service;

import com.m3s1.dao.CursoDao;
import com.m3s1.exception.RegistroNaoEncontradoException;
import com.m3s1.model.Aluno;
import com.m3s1.model.Curso;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CursoServiceTest {

    @Mock
    private CursoDao dao;

    @InjectMocks
    private CursoService service;

    @Test
    @DisplayName("")
    void obter_falha() {
    }

    @Test
    @DisplayName("")
    void obter_sucesso() {
    }

    @Test
    @DisplayName("Quando requisição com código não existente, deve retornar status 404 NOT FOUND e lançar exceção")
    void obterPorCodigo_falha() {
        Mockito.when(dao.obterPorCodigo(Mockito.anyString())).thenThrow(new RegistroNaoEncontradoException("Curso", "código"));
        assertThrows(RegistroNaoEncontradoException.class, () -> service.obter("código"));
    }

    @Test
    @DisplayName("Quando requisição com código existente, Deve retornar Curso instanciado")
    void obterPorCodigo_sucesso() {
        Mockito.when(dao.obterPorCodigo(Mockito.anyString())).thenReturn(new Curso());
        Curso result = service.obter("codigo");
        assertNotNull(result);
        assertInstanceOf(Curso.class, result);
    }
}