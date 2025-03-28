package org.example.springtlgbot.service;

import org.example.springtlgbot.entity.Vehicle;
import org.example.springtlgbot.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle getVehicle(String brand, String model, Integer generation) {
        return vehicleRepository.findByBrandAndModelAndGeneration(brand, model, generation);
    }
    public Optional<Vehicle> getVehicle(Integer id) {
        return vehicleRepository.findById(id);
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    public Vehicle checkAndAddVehicle(Vehicle vehicle) {
        Vehicle existingVehicle = vehicleRepository.findByBrandAndModelAndGeneration(
                vehicle.getBrand(), vehicle.getModel(), vehicle.getGeneration());

        if (existingVehicle == null) {
            addVehicle(vehicle);
            return vehicle;
        } else {
            return existingVehicle;
        }
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
}
