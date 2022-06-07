package com.m3s1.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CursoDTO implements Serializable {

    private String codigo;

    @NotEmpty(message = "Campo obrigatório: Assunto")
    private String assunto;

    @NotNull(message = "Campo obrigatório: Duração")
    private Integer duracao;

    public CursoDTO() {
    }

    public CursoDTO(String assunto, Integer duracao) {
        this.assunto = assunto;
        this.duracao = duracao;
    }

    public CursoDTO(String codigo, String assunto, Integer duracao) {
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
