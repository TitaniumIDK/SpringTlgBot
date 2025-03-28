package org.example.springtlgbot.service;

import org.example.springtlgbot.entity.Vehicle;
import org.example.springtlgbot.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

//    @Autowired
//    private VehicleDAO vehicleDAO;

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle getVehicle(String brand, String model, Integer generation) {
        return vehicleRepository.findByBrandAndModelAndGeneration(brand, model, generation);
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    public Vehicle checkAndAddVehicle(Vehicle vehicle) {
        // Проверяем, существует ли автомобиль
        Vehicle existingVehicle = vehicleRepository.findByBrandAndModelAndGeneration(
                vehicle.getBrand(), vehicle.getModel(), vehicle.getGeneration());

        // Если автомобиль не найден, добавляем его
        if (existingVehicle == null) {
            addVehicle(vehicle);
            return vehicle;
        } else {
            return existingVehicle; // Возвращаем существующий автомобиль
        }
    }
}
