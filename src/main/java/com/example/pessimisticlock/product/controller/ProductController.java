package com.example.pessimisticlock.product.controller;

import com.example.pessimisticlock.product.entity.Product;
import com.example.pessimisticlock.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{title}")
    public Product getProduct(@PathVariable String title) {
        return productService.findProduct(title);
    }

    @GetMapping
    public List<Product> findAll() {
        return productService.findAll();
    }

    @PatchMapping("/{title}")
    public int decreasePrice(@PathVariable String title) {
        return productService.decreasePrice(title);
    }

    @PostMapping
    public void createProduct() {
        productService.createProduct();
    }

}
