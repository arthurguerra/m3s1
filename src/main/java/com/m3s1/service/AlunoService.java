package com.m3s1.service;

import com.m3s1.dao.AlunoDao;
import com.m3s1.exception.RegistroNaoEncontradoException;
import com.m3s1.model.Aluno;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class AlunoService {

    @Inject
    private AlunoDao alunoDao;

    public void inserir(Aluno aluno) {
        alunoDao.salvar(aluno);
    }

    public void alterar(Aluno aluno) {
        Aluno alunoDB = obter(aluno.getMatricula());
        alunoDao.atualizar(aluno, alunoDB);
    }

    public void excluir(Integer matricula) {
        obter(matricula);
        alunoDao.deletar(matricula);
    }

    public List<Aluno> obter(String nomePesquisa) {
        return alunoDao.obterTodos(nomePesquisa);
    }

    public Aluno obter(Integer matricula) {
        Optional<Aluno> aluno = alunoDao.obterPorMatricula(matricula);
        return aluno.orElseThrow(() -> new RegistroNaoEncontradoException("Aluno", matricula.toString()));
    }

}
