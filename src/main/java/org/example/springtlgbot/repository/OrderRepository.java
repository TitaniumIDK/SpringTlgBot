package org.example.springtlgbot.repository;

import org.example.springtlgbot.entity.Employee;
import org.example.springtlgbot.entity.Order;
import org.example.springtlgbot.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByVehicle(Vehicle vehicle);
    List<Order> findByManager(Employee manager);
    List<Order> findByMaster(Employee master);
}
