package org.example.springtlgbot.repository;

import org.example.springtlgbot.entity.SparePart;
import org.example.springtlgbot.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SparePartRepository extends JpaRepository<SparePart, Integer> {
    List<SparePart> findByVehicle(Vehicle vehicle);
}
