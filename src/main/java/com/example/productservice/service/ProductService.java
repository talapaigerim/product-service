package com.example.productservice.service;

import com.example.productservice.dto.DeliveryRequest;
import com.example.productservice.entity.Product;
import com.example.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.scheduling.annotation.Async;
import java.util.concurrent.CompletableFuture;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;
    private final RestTemplate restTemplate;

    public ProductService(ProductRepository repo, RestTemplate restTemplate) {
        this.repo = repo;
        this.restTemplate = restTemplate;
    }

    public Product create(Product product) {
        // 1) сохраняем продукт
        Product saved = repo.save(product);

        // 2) формируем запрос в delivery-service
        DeliveryRequest request = new DeliveryRequest(saved.getId(), saved.getAddress());

        // 3) вызываем delivery-service
        String url = "http://localhost:8082/delivery";
        restTemplate.postForObject(url, request, String.class);

        return saved;
    }


    @Async("productExecutor")
    public CompletableFuture<List<Product>> getAllAsync() {
        System.out.println("Thread: " + Thread.currentThread().getName());
        return CompletableFuture.completedFuture(repo.findAll());
    }

    public Product getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Product not found: " + id));
    }

    public Product update(Long id, Product updated) {
        Product p = getById(id);
        p.setName(updated.getName());
        p.setPrice(updated.getPrice());
        p.setAddress(updated.getAddress());
        return repo.save(p);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
