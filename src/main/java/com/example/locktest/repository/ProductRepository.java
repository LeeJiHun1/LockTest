package com.example.locktest.repository;

import com.example.locktest.entity.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Product findPessimisticProductById(Long id);

    @Lock(LockModeType.OPTIMISTIC)
//    @Query
    Product findOptimisticProductById(Long id);
}
