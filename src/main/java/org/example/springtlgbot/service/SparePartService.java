package org.example.springtlgbot.service;

import jakarta.transaction.Transactional;
import org.example.springtlgbot.entity.SparePart;
import org.example.springtlgbot.entity.Vehicle;
import org.example.springtlgbot.repository.SparePartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<SparePart> getSpatePartById(Integer id) {
        return sparepartRepository.findById(id);
    }

    @Transactional
    public void reduceStock(Integer sparePartId) {
        int updatedRows = sparepartRepository.decreaseStock(sparePartId);
        if (updatedRows == 0) {
            throw new IllegalArgumentException("Недостаточно запасов или деталь не найдена.");
        }
    }
}
