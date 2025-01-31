package com.relato.ms_books_auth.exceptions;

public class UsuarioException extends RuntimeException {
    public UsuarioException(String mensaje) {
        super(mensaje);
    }
}