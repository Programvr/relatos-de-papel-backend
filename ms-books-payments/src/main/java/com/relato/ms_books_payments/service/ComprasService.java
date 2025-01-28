package com.relato.ms_books_payments.service;

import com.relato.ms_books_payments.model.Compras;
import com.relato.ms_books_payments.repository.ComprasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.discovery.DiscoveryClient;


@Service
public class ComprasService {

    @Autowired
    private ComprasRepository compraRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;


    
    private Long stockDisponible;

    public boolean validarCompra(Compras compras) {
        return compras.getLibroId() != null && compras.getCompraFecha() != null;
    }
    
    public boolean verificarStock(Long libroId, Long cantidad) {
        ServiceInstance instance = discoveryClient.getInstances("MS-BOOKS-CATALOGUE").stream().findFirst().orElse(null);
        if (instance == null) {
            throw new RuntimeException("No instances of MS-BOOKS-CATALOGUE found");
        }
        String url = instance.getUri().toString() + "/libros/" + libroId;
        stockDisponible = restTemplate.getForObject(url, Long.class);
        return cantidad <= stockDisponible;
    }
    
    public Compras registrarCompra(Compras compra) {
        ServiceInstance instance = discoveryClient.getInstances("MS-BOOKS-CATALOGUE").stream().findFirst().orElse(null);
        if (instance == null) {
            throw new RuntimeException("No instances of MS-BOOKS-CATALOGUE found");
        }
        Long nuevaCantidad = stockDisponible - compra.getCantidad();
        String url = instance.getUri().toString() + "/libros/" + compra.getLibroId() + "/cantidad?cantidad=" + nuevaCantidad;
        restTemplate.put(url, null);
        return compraRepository.save(compra);
    }

}