package com.m3s1.dao;

import com.m3s1.exception.RegistroNaoEncontradoException;
import com.m3s1.model.Aluno;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
@Transactional
public class AlunoDao implements Serializable {

    @PersistenceContext(unitName = "m3s1")
    EntityManager em;

    public void salvar(Aluno aluno) {
        em.persist(aluno);
    }

    public List<Aluno> obterTodos(String nomePesquisa) {
        String sql = montaSqlObterAlunos(nomePesquisa);
        Query query = montaQueryObterAlunos(sql, nomePesquisa);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    public Aluno obterPorMatricula(Integer matricula) {
        Aluno aluno = em.find(Aluno.class, matricula);
        if (aluno == null)
            throw new RegistroNaoEncontradoException("Aluno", matricula.toString());

        return aluno;
    }

    public void deletar(Integer matricula) {
        Aluno aluno = obterPorMatricula(matricula);
        em.remove(aluno);
    }

    public void atualizar(Aluno alterado) {
        Aluno aluno = obterPorMatricula(alterado.getMatricula());
        aluno.setNome(alterado.getNome());
        em.merge(aluno);
    }

    private String montaSqlObterAlunos(String nomePesquisa) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT a FROM Aluno a ");
        if (nomePesquisa != null) {
            builder.append("WHERE LOWER(a.nome) LIKE LOWER(:nome)");
        }
        return builder.toString();
    }

    private Query montaQueryObterAlunos(String sql, String nomePesquisa) {
        Query query = em.createQuery(sql, Aluno.class);
        if (nomePesquisa != null) {
            query.setParameter("nome", "%" + nomePesquisa + "%");
        }
        return query;
    }
}
