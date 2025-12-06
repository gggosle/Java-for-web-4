package com.example.webapp.service.impl;

import com.example.webapp.model.Order;
import com.example.webapp.model.Product;
import com.example.webapp.model.dto.OrderDTO;
import com.example.webapp.model.mapper.OrderMapper;
import com.example.webapp.repository.CustomerRepository;
import com.example.webapp.repository.OrderRepository;
import com.example.webapp.repository.ProductRepository;
import com.example.webapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;
    private final CustomerRepository customerRepo;
    private final ProductRepository productRepo;
    private final OrderMapper mapper;

    @Override
    public OrderDTO getById(Long id) {
        return mapper.toDto(orderRepo.findById(id).orElseThrow());
    }

    @Override
    public OrderDTO create(OrderDTO dto) {
        Order order = mapper.toEntity(dto);

        order.setCustomer(customerRepo.findById(dto.customerId()).orElseThrow());

        Set<Product> products = new HashSet<>(productRepo.findAllById(dto.productIds()));
        order.setProducts(products);

        Order saved = orderRepo.save(order);
        return mapper.toDto(saved);
    }

    @Override
    public OrderDTO update(Long id, OrderDTO dto) {
        Order order = orderRepo.findById(id).orElseThrow();

        order.setStatus(dto.status());
        order.setCustomer(customerRepo.findById(dto.customerId()).orElseThrow());

        Set<Product> products = new HashSet<>(productRepo.findAllById(dto.productIds()));
        order.setProducts(products);

        return mapper.toDto(orderRepo.save(order));
    }

    @Override
    public void delete(Long id) {
        orderRepo.deleteById(id);
    }
}