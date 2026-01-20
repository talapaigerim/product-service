package com.example.productservice.service;

import com.example.productservice.dto.DeliveryRequest;
import com.example.productservice.entity.Product;
import com.example.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final ProductRepository repo;
    private final WebClient deliveryWebClient;

    public ProductService(ProductRepository repo, WebClient deliveryWebClient) {
        this.repo = repo;
        this.deliveryWebClient = deliveryWebClient;
    }


    public Flux<Product> getAll() {
        return repo.findAll();
    }


    public Mono<Product> getById(Long id) {
        return repo.findById(id);
    }

    //  CREATE + неблокирующий вызов delivery-service
    public Mono<Product> create(Product product) {
        return repo.save(product)
                .flatMap(savedProduct ->
                        deliveryWebClient.post()
                                .uri("/deliveries")
                                .bodyValue(
                                        new DeliveryRequest(
                                                savedProduct.getId(),
                                                "Almaty"
                                        )
                                )
                                .retrieve()
                                .bodyToMono(Void.class)
                                .thenReturn(savedProduct)
                );
    }
}