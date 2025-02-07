package com.relato.ms_books_payments.controllers;

import com.relato.ms_books_payments.models.Compra;
import com.relato.ms_books_payments.services.CompraService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @PostMapping
    public ResponseEntity<String> realizarCompra(@RequestBody Compra compra) {
        if (!compraService.validarCompra(compra)) {
            return ResponseEntity.badRequest().body("Compra no válida");
        }

        if (!compraService.verificarUsuario(compra.getUsuarioId())) {
            return ResponseEntity.badRequest().body("Usuario no válido");
        }

        if (!compraService.verificarStock(compra.getLibroId(),compra.getCantidad())) {
            return ResponseEntity.badRequest().body("Stock no disponible");
        }

        compraService.registrarCompra(compra);
        return ResponseEntity.ok("Compra registrada exitosamente");
    }
}