package com.relato.ms_books_payments.services;

import com.relato.ms_books_payments.models.Compra;
import com.relato.ms_books_payments.repositorys.CompraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.discovery.DiscoveryClient;


@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;


    
    private Long stockDisponible;

    public boolean validarCompra(Compra compra) {
        return compra.getUsuarioId() != null && compra.getLibroId() != null && compra.getCantidad() != null && compra.getCompraFecha() != null;
    }
    
    public boolean verificarUsuario(Long usuarioId) {
        ServiceInstance instance = discoveryClient.getInstances("CLOUD-GATEWAY").stream().findFirst().orElse(null);
        if (instance == null) {
            throw new RuntimeException("No instances of CLOUD-GATEWAY found");
        }
        String url = instance.getUri().toString() + "/autenticacion/" + usuarioId;
        return restTemplate.getForObject(url, Boolean.class);
    }
    
    public boolean verificarStock(Long libroId, Long cantidad) {
        ServiceInstance instance = discoveryClient.getInstances("CLOUD-GATEWAY").stream().findFirst().orElse(null);
        if (instance == null) {
            throw new RuntimeException("No instances of CLOUD-GATEWAY found");
        }
        String url = instance.getUri().toString() + "/libros/" + libroId + "/cantidad";
        stockDisponible = restTemplate.getForObject(url, Long.class);
        return cantidad <= stockDisponible;
    }
    
    public Compra registrarCompra(Compra compra) {
        ServiceInstance instance = discoveryClient.getInstances("CLOUD-GATEWAY").stream().findFirst().orElse(null);
        if (instance == null) {
            throw new RuntimeException("No instances of CLOUD-GATEWAY found");
        }
        Long nuevaCantidad = stockDisponible - compra.getCantidad();
        String url = instance.getUri().toString() + "/libros/" + compra.getLibroId() + "/cantidad?cantidad=" + nuevaCantidad;
        restTemplate.put(url, null);
        return compraRepository.save(compra);
    }

    


}