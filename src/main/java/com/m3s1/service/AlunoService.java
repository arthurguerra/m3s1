package com.m3s1.service;

import com.m3s1.dao.AlunoDao;
import com.m3s1.model.Aluno;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class AlunoService {

    @Inject
    private AlunoDao alunoDao;

    public void inserir(Aluno aluno) {
        alunoDao.salvar(aluno);
    }

    public void alterar(Aluno aluno) {
        alunoDao.atualizar(aluno);
    }

    public void excluir(Integer matricula) {
        alunoDao.deletar(matricula);
    }

    public List<Aluno> obter(String nomePesquisa) {
        return alunoDao.obterTodos(nomePesquisa);
    }

    public Aluno obter(Integer matricula) {
        return alunoDao.obterPorMatricula(matricula);
    }

}
