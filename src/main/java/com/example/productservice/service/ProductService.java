package com.example.productservice.service;

import com.example.productservice.entity.Product;
import com.example.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public Flux<Product> getAll() {
        return repo.findAll();
    }

    public Mono<Product> getById(Long id) {
        return repo.findById(id);
    }

    public Mono<Product> create(Product product) {
        return repo.save(product);
    }

    public Mono<Void> delete(Long id) {
        return repo.deleteById(id);
    }
}