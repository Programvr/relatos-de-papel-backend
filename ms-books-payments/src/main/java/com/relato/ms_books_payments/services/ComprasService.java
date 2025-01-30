package com.relato.ms_books_payments.services;

import com.relato.ms_books_payments.models.Compras;
import com.relato.ms_books_payments.repositorys.ComprasRepository;

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
        ServiceInstance instance = discoveryClient.getInstances("CLOUD-GATEWAY").stream().findFirst().orElse(null);
        if (instance == null) {
            throw new RuntimeException("No instances of CLOUD-GATEWAY found");
        }
        String url = instance.getUri().toString() + "/libros/" + libroId + "/cantidad";
        stockDisponible = restTemplate.getForObject(url, Long.class);
        System.out.println("Stock disponible: " + stockDisponible);
        return cantidad <= stockDisponible;
    }
    
    public Compras registrarCompra(Compras compra) {
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