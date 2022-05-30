package com.m3s1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CURSO")
public class Curso {

    @Id
    @Column(name = "id_curso")
    private String codigo;

    private String assunto;

    private Integer duracao;

    public Curso() {
    }

    public Curso(String codigo, String assunto, Integer duracao) {
        this.codigo = codigo;
        this.assunto = assunto;
        this.duracao = duracao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }
}
