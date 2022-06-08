package com.m3s1.controller;

import com.m3s1.dto.LoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @InjectMocks
    private LoginController controller;

    @Test
    @DisplayName("Quando credenciais inválidas, Deve retornar status 401 UNAUTHORIZED")
    void autenticar_credenciaisInvalidas() {
        LoginRequest credenciais = new LoginRequest("errado@email.com", "senha_errada");
        Response result = controller.autenticar(credenciais);
        assertNotNull(result);
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), result.getStatus());
        assertNotNull(result.getEntity());
        assertInstanceOf(String.class, result.getEntity());
    }

    @Test
    @DisplayName("Quando credenciais corretas, Deve retornar status 201 CREATED")
    void autenticar_credenciaisValidas() {
        LoginRequest credenciais = new LoginRequest("james@kirk.com", "1234");
        Response result = controller.autenticar(credenciais);
        assertNotNull(result);
        assertEquals(Response.Status.CREATED.getStatusCode(), result.getStatus());
        assertNotNull(result.getEntity());
        assertInstanceOf(String.class, result.getEntity());
    }
}