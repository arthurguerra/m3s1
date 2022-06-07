package com.m3s1.dao;

import com.m3s1.exception.RegistroExistenteException;
import com.m3s1.exception.RegistroNaoEncontradoException;
import com.m3s1.model.Inscricao;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@RequestScoped
@Transactional
public class InscricaoDao implements Serializable {

    @PersistenceContext(unitName = "m3s1")
    EntityManager em;

    public void salvar(Inscricao inscricao) {
        verificaInscricaoDuplicada(inscricao);
        em.persist(inscricao);
    }

    public List<Inscricao> obterTodos() {
        return em.createQuery("SELECT I FROM Inscricao i", Inscricao.class).getResultList();
    }

    public Inscricao obter(Integer id) {
        Inscricao inscricao = em.find(Inscricao.class, id);
        if (inscricao == null)
            throw new RegistroNaoEncontradoException("Inscrição", id.toString());

        return inscricao;
    }

    public void deletar(Integer id) {
        Inscricao inscricao = obter(id);
        em.remove(inscricao);
    }
    
    private void verificaInscricaoDuplicada(Inscricao inscricao) {
        List<Inscricao> inscricoes = obterTodos();
        boolean matriculaDuplicada = inscricoes.stream().anyMatch(i -> i.equals(inscricao));
        if (matriculaDuplicada)
            throw new RegistroExistenteException("Inscrição", inscricao.getAluno().getMatricula().toString() + " - " + inscricao.getCurso().getCodigo());
    }
}
