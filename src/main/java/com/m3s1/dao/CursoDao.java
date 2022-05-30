package com.m3s1.dao;

import com.m3s1.exception.RegistroNaoEncontradoException;
import com.m3s1.model.Curso;

import javax.enterprise.context.RequestScoped;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
@Transactional
public class CursoDao implements Serializable {

    @PersistenceContext(unitName = "m3s1")
    EntityManager em;

    public void salvar(Curso curso) {
        em.persist(curso);
    }

    public List<Curso> obterTodos(String sort, Integer limit) {
        String hql = montaSQLObterCursos(sort, limit);
        Query query = montaQueryObterCursos(hql, limit);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    public Curso obterPorCodigo(String codigo) {
        Curso curso = em.find(Curso.class, codigo);
        if (curso == null)
            throw new RegistroNaoEncontradoException("Curso", codigo);
        return curso;
    }

    public void deletar(String codigo) {
        Curso curso = obterPorCodigo(codigo);
        em.remove(curso);
    }

    public void atualizar(Curso alterado) {
        Curso curso = obterPorCodigo(alterado.getCodigo());
        curso.setAssunto(alterado.getAssunto());
        curso.setDuracao(alterado.getDuracao());
        em.merge(curso);
    }

    private String montaSQLObterCursos(String sort, Integer limit) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT c FROM Curso c ");
        if (sort != null) {
            if (sort.equals("assunto"))
                builder.append("ORDER BY LOWER(c.assunto) ");
            if (sort.equals("duracao"))
                builder.append("ORDER BY LOWER(c.duracao) ");
        }
//        if (limit != null) {
//            builder.append("LIMIT ").append(limit);
//        }
        return builder.toString();
    }

    private Query montaQueryObterCursos(String sql, Integer limit) {
        TypedQuery<Curso> query = em.createQuery(sql, Curso.class);
        if (limit != null)
            query.setMaxResults(limit);
        return query;
    }
}
