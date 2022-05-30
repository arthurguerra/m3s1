package com.m3s1.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class AlunoDTO implements Serializable {

    private Integer matricula;

    @NotEmpty(message = "Campo obrigatório: Nome")
    private String nome;

    public AlunoDTO() {
    }

    public AlunoDTO(Integer matricula, String nome) {
        this.matricula = matricula;
        this.nome = nome;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
