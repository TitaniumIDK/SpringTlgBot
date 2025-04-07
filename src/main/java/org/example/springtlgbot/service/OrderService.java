package org.example.springtlgbot.service;
import org.example.springtlgbot.dto.OrderDTO;
import org.example.springtlgbot.entity.Employee;
import org.example.springtlgbot.entity.Order;
import org.example.springtlgbot.entity.SparePart;
import org.example.springtlgbot.entity.Vehicle;
import org.example.springtlgbot.enums.Status;
import org.example.springtlgbot.mappers.OrderMapper;
import org.example.springtlgbot.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public void createOrderForMechanic(OrderDTO orderDTO) {
        Order mappedOrder = orderMapper.toOrder(orderDTO);
        orderRepository.save(Order.builder()
                        .vehicle(mappedOrder.getVehicle())
                        .sparePart(mappedOrder.getSparePart())
                        .master(mappedOrder.getMaster())
                        .orderDate(LocalDate.now())
                        .status(Status.NEW)
                .build());
    }

}