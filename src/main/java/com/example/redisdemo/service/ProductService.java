package com.example.redisdemo.service;

import com.example.redisdemo.entity.Product;
import com.example.redisdemo.repository.ProductRepository;
import org.springframework.cache.annotation.CachePut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Cacheable(value = "products",key = "#id") // có nhiều cache khác nhau nên là đặt tên value để đánh dấu
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Cacheable(value = "products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @CacheEvict(value = "products", allEntries = true)
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @CacheEvict(value = "products", key = "#product.id")
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }
}
