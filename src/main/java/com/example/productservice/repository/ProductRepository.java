package com.example.productservice.repository;

import com.example.productservice.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {

    // опционально, если нужно
    Flux<Product> findByName(String name);
}