package org.example.springtlgbot.service;
import org.example.springtlgbot.entity.Employee;
import org.example.springtlgbot.entity.Order;
import org.example.springtlgbot.entity.SparePart;
import org.example.springtlgbot.entity.Vehicle;
import org.example.springtlgbot.enums.Status;
import org.example.springtlgbot.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrderForMechanic(Vehicle vehicle, SparePart sparePart, Employee employee) {
        Order order = Order.builder()
                .orderDate(LocalDate.now())
                .vehicle(vehicle)
                .sparePart(sparePart)
                .master(employee)
                .status(Status.NEW)
                .build();
        orderRepository.save(order);
    }

}