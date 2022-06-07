package com.m3s1.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Schema(name = "Aluno DTO")
public class AlunoDTO implements Serializable {

    private Integer matricula;

    @NotEmpty(message = "Campo obrigatório: Nome")
    private String nome;

    public AlunoDTO() {
    }

    public AlunoDTO(String nome) {
        this.nome = nome;
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
