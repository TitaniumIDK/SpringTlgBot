package org.example.springtlgbot.repository;

import org.example.springtlgbot.entity.SparePart;
import org.example.springtlgbot.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SparePartRepository extends JpaRepository<SparePart, Integer> {
    List<SparePart> findByVehicle(Vehicle vehicle);

    @Modifying
    @Query("UPDATE SparePart sp SET sp.stock = sp.stock - 1 WHERE sp.id = :id")
    int decreaseStock(@Param("id") Integer id);
}
