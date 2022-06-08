package com.m3s1.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

@Schema(name = "Login Requisição")
public class LoginRequest {

    @NotNull(message = "Campo Obrigatório: email")
    private String email;

    @NotNull(message = "Campo Obrigatório: senha")
    private String senha;


    public LoginRequest() { }

    public LoginRequest(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
