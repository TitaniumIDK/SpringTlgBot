package org.example.springtlgbot.service;
import org.example.springtlgbot.entity.Employee;
import org.example.springtlgbot.entity.Order;
import org.example.springtlgbot.entity.Vehicle;
import org.example.springtlgbot.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByVehicle(Vehicle vehicle) {
        return orderRepository.findByVehicle(vehicle);
    }

    public List<Order> getOrdersByManager(Employee manager) {
        return orderRepository.findByManager(manager);
    }

    public List<Order> getOrdersByMaster(Employee master) {
        return orderRepository.findByMaster(master);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }
}