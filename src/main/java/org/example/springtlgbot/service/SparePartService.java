package org.example.springtlgbot.service;

import org.example.springtlgbot.entity.SparePart;
import org.example.springtlgbot.entity.Vehicle;
import org.example.springtlgbot.repository.SparePartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SparePartService {

    private final SparePartRepository sparepartRepository;

    @Autowired
    public SparePartService(SparePartRepository sparePartRepository) {
        this.sparepartRepository = sparePartRepository;
    }

    public List<SparePart> getSparePartsForVehicle(Vehicle vehicle) {
        return sparepartRepository.findByVehicle(vehicle);
    }
}
