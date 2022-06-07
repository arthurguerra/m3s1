package com.m3s1.service;

import com.m3s1.dao.AlunoDao;
import com.m3s1.dao.CursoDao;
import com.m3s1.dao.InscricaoDao;
import com.m3s1.exception.RegistroNaoEncontradoException;
import com.m3s1.model.Aluno;
import com.m3s1.model.Inscricao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class InscricaoService {

    @Inject
    private InscricaoDao inscricaoDao;

    @Inject
    private AlunoDao alunoDao;

    @Inject
    private CursoDao cursoDao;

    public Inscricao inserir(Inscricao inscricao) {
        Optional<Aluno> aluno = alunoDao.obterPorMatricula(inscricao.getAluno().getMatricula());
        if (!aluno.isPresent())
            throw new RegistroNaoEncontradoException("Aluno", inscricao.getAluno().getMatricula().toString());
        cursoDao.obterPorCodigo(inscricao.getCurso().getCodigo());
        inscricaoDao.salvar(inscricao);
        return inscricao;
    }

    public void excluir(Integer id) {
        inscricaoDao.deletar(id);
    }

    public List<Inscricao> obter() {
        return inscricaoDao.obterTodos();
    }
}
