package com.example.locktest.controller;

import com.example.locktest.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @GetMapping("/")
    @ResponseBody
    public String hi() throws Exception {
        orderService.decreaseByPessimisticLock(21L);
        return "decrease Product1";
    }
    @PostMapping("/Redisson/{id}")
    @ResponseBody
    public String UseRedisson(@PathVariable(value = "id") Long id) throws Exception {
        orderService.decreaseByRedissonLock(id);  // Redisson 락
        return "decrease Product1";
    }

    @PostMapping("/Lettuce/{id}")
    @ResponseBody
    public String UseLettuce(@PathVariable(value = "id") Long id) throws Exception {
        orderService.decreaseByLettuceLock(id);
        return "decrease Product1";
    }

    @PostMapping("/Pessimistic/{id}")
    @ResponseBody
    public String UsePessimistic(@PathVariable(value = "id") Long id) throws Exception {
        orderService.decreaseByPessimisticLock(id); //DB 비관적 락
        return "decrease Product1";
    }
    @PostMapping("/Optimistic/{id}")
    @ResponseBody
    public String UseOptimistic(@PathVariable(value = "id") Long id) throws Exception {
        orderService.decreaseByOptimisticLock(id); //DB 낙관적 락
        return "decrease Product1";
    }


    @PostMapping("/Redisson10")
    @ResponseBody
    public String UseRedisson10() throws Exception {
        for (Long i = 1L; i <= 100; i++) {
            orderService.decreaseByRedissonLock(i);  // Redisson 락
        }
        return "decrease Product1";
    }
    @PostMapping("/Lettuce10")
    @ResponseBody
    public String UseLettuce10() throws Exception {
        for (Long i = 1L; i <= 100; i++) {
            orderService.decreaseByLettuceLock(i);
        }
        return "decrease Product1";
    }
    @PostMapping("/Pessimistic10")
    @ResponseBody
    public String UsePessimistic10() throws Exception {
        for (Long i = 1L; i <= 100; i++) {
            orderService.decreaseByPessimisticLock(i);
        }
        return "decrease Product1";
    }
    @PostMapping("/Optimistic10")
    @ResponseBody
    public String UseOptimistic10() throws Exception {
        for (Long i = 1L; i <= 100; i++) {
            orderService.decreaseByOptimisticLock(i);
        }
        return "decrease Product1";
    }

}
