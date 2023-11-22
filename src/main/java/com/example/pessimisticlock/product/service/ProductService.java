package com.example.pessimisticlock.product.service;

import com.example.pessimisticlock.product.entity.Product;
import com.example.pessimisticlock.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public void createProduct() {
        productRepository.save(Product.builder()
                .title("상품").price(20000)
                .build());
    }

    @Transactional(readOnly = true)
    public Product findProduct(String title) {
        return getProductByTitle(title);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional
    public int decreasePrice(String title) {
        Product product = getProductByTitle(title);
        product.decreasePrice();
        return product.getPrice();
    }

    private Product getProductByTitle(String title) {
        return productRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException());
    }

}
