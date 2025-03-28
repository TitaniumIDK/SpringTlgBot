package org.example.springtlgbot.repository;

import org.example.springtlgbot.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    Vehicle findByBrandAndModelAndGeneration(String brand, String model, Integer generation);
}
