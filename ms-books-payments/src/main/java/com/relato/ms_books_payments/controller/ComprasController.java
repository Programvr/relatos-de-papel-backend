package com.relato.ms_books_payments.controller;

import com.relato.ms_books_payments.model.Compras;
import com.relato.ms_books_payments.service.ComprasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compras")
public class ComprasController {

    @Autowired
    private ComprasService compraService;

    @PostMapping
    public ResponseEntity<String> realizarCompra(@RequestBody Compras compras) {
        if (!compraService.validarCompra(compras)) {
            return ResponseEntity.badRequest().body("Compra no válida");
        }

        if (!compraService.verificarStock(compras.getLibroId(),compras.getCantidad())) {
            return ResponseEntity.badRequest().body("Stock no disponible");
        }

        compraService.registrarCompra(compras);
        return ResponseEntity.ok("Compra registrada exitosamente");
    }
}