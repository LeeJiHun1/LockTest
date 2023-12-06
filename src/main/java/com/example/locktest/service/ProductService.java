package com.example.locktest.service;

import com.example.locktest.entity.Product;
import com.example.locktest.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final RedissonClient redissonClient;

    @Transactional
    public void decrease(Long productId) {
        Product product = productRepository.findById(productId).get();
        product.decrease(1L);
    }

    @Transactional
    public void decreaseByPessimisticLock(Long productId) {
        Product product = productRepository.findPessimisticProductById(productId);
        product.decrease(1L);
    }

    @Transactional
    public void decreaseByOptimisticLock(Long productId) {
        Product product = productRepository.findOptimisticProductById(productId);
        product.decrease(1L);
    }
}
