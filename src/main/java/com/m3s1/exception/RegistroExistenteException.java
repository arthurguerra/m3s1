package com.m3s1.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class RegistroExistenteException extends WebApplicationException {

    public RegistroExistenteException(String tipo, String identificador) {
        super(String.format("%s: Registro existente com identificador: %s", tipo, identificador), Response.Status.CONFLICT);
    }

}
