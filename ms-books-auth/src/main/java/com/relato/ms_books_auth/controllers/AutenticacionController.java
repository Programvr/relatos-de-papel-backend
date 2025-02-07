package com.relato.ms_books_auth.controllers;

import com.relato.ms_books_auth.exceptions.UsuarioException;
import com.relato.ms_books_auth.models.Usuario;
import com.relato.ms_books_auth.services.AutenticacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/autenticacion")
public class AutenticacionController {

    @Autowired
    private AutenticacionService autenticacionesService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        try {
            return ResponseEntity.ok(autenticacionesService.registrarUsuario(usuario));
        } catch (UsuarioException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String correo, @RequestParam String contrasena) {
        try {
            Optional<Usuario> usuario = autenticacionesService.loginUsuario(correo, contrasena);
            return usuario.isPresent() ? ResponseEntity.ok(usuario.get()) : ResponseEntity.status(401).body("Credenciales invalidas");
        } catch (UsuarioException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Boolean> getUsuarioById(@PathVariable Long id) {
        boolean exists = autenticacionesService.getUsuarioById(id).isPresent();
        return ResponseEntity.ok(exists);
    }
}