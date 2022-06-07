package com.m3s1.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(name = "Inscrição Response")
public class InscricaoRespDTO implements Serializable {

    private Integer id;

    private Integer matriculaAluno;

    private String codigoCurso;


    public InscricaoRespDTO() { }

    public InscricaoRespDTO(Integer id, Integer matriculaAluno, String codigoCurso) {
        this.id = id;
        this.matriculaAluno = matriculaAluno;
        this.codigoCurso = codigoCurso;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMatriculaAluno() {
        return matriculaAluno;
    }

    public void setMatriculaAluno(Integer matriculaAluno) {
        this.matriculaAluno = matriculaAluno;
    }

    public String getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(String codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

}
