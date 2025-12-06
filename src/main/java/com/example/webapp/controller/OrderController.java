package com.example.webapp.controller;

import com.example.webapp.model.dto.OrderDTO;
import com.example.webapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @GetMapping("/{id}")
    public OrderDTO get(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public OrderDTO create(@RequestBody OrderDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public OrderDTO update(@PathVariable Long id, @RequestBody OrderDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}