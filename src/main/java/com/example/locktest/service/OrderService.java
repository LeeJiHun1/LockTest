package com.example.locktest.service;

import com.example.locktest.repository.RedisLockRepository;
import io.lettuce.core.ScriptOutputType;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductService productService;
    private final RedisLockRepository redisLockRepository;
    private final RedissonClient redissonClient;

    public synchronized void decreaseBySynchronized(Long productId) {
        productService.decrease(productId);
    }

    public void decreaseByPessimisticLock(Long productId) {
        productService.decreaseByPessimisticLock(productId);
    }

    public void decreaseByOptimisticLock(Long productId) throws InterruptedException {
        while (true) {
            try {
                productService.decreaseByOptimisticLock(productId);
                break;
            } catch (OptimisticLockingFailureException e) {
                Thread.sleep(50);
            }
        }
    }

    public void decreaseByLettuceLock(Long productId) throws InterruptedException {
        while (!redisLockRepository.lock(productId)) {
            Thread.sleep(50);
        }
        try {
            productService.decrease(productId);
        } finally {
            redisLockRepository.unlock(productId);
        }
    }

    public void decreaseByRedissonLock(Long productId) {

        RLock lock = redissonClient.getLock(productId.toString());
        try {
            lock.tryLock(100, 3, TimeUnit.SECONDS);
            productService.decrease(productId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

}
