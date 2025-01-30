package com.relato.ms_books_auth.controllers;

import com.relato.ms_books_auth.models.Usuarios;
import com.relato.ms_books_auth.services.AutenticacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/autenticacion")
public class AutenticacionesController {

    @Autowired
    private AutenticacionesService autenticacionesService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody Usuarios usuarios) {
        return ResponseEntity.ok(autenticacionesService.registrarUsuario(usuarios));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String correo, @RequestParam String contrasena) {
        Optional<Usuarios> usuarios = autenticacionesService.loginUsuario(correo, contrasena);
        return usuarios.isPresent() ? ResponseEntity.ok(usuarios.get()) : ResponseEntity.status(401).body("Invalid credentials");
    }
}