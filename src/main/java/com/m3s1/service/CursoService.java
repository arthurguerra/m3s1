package com.m3s1.service;

import com.m3s1.dao.CursoDao;
import com.m3s1.model.Curso;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@RequestScoped
public class CursoService {

    @Inject
    private CursoDao cursoDao;

    public void inserir(Curso curso) {
        curso.setCodigo(UUID.randomUUID().toString());
        cursoDao.salvar(curso);
    }

    public void alterar(Curso curso) {
        cursoDao.atualizar(curso);
    }

    public void excluir(String codigo) {
        cursoDao.deletar(codigo);
    }

    public List<Curso> obter(String sort, Integer limit) {
        return cursoDao.obterTodos(sort, limit);
    }

    public Curso obter(String codigo) {
        return cursoDao.obterPorCodigo(codigo);
    }
}
