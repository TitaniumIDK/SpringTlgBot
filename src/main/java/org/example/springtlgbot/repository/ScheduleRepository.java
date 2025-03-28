package org.example.springtlgbot.repository;

import org.example.springtlgbot.entity.Employee;
import org.example.springtlgbot.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findByWorkDate(LocalDate workDate);
    List<Schedule> findByEmp(Employee emp);
}
