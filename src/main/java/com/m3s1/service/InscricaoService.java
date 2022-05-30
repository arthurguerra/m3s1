package com.m3s1.service;

import com.m3s1.dao.AlunoDao;
import com.m3s1.dao.CursoDao;
import com.m3s1.dao.InscricaoDao;
import com.m3s1.model.Inscricao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class InscricaoService {

    @Inject
    private InscricaoDao inscricaoDao;

    public void inserir(Inscricao inscricao) {
        inscricaoDao.salvar(inscricao);
    }

    public void excluir(Integer id) {
        inscricaoDao.deletar(id);
    }

    public List<Inscricao> obter() {
        return inscricaoDao.obterTodos();
    }
}
