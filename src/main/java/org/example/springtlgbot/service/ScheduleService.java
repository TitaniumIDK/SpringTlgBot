package org.example.springtlgbot.service;

import jakarta.transaction.Transactional;
import org.example.springtlgbot.entity.Employee;
import org.example.springtlgbot.entity.Schedule;
import org.example.springtlgbot.enums.BusynessType;
import org.example.springtlgbot.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> getSchedulesByEmployee(Employee emp) {
        return scheduleRepository.findByEmp(emp);
    }

    @Transactional
    public void updateBusynessType(Employee emp, LocalDate date, BusynessType busynessType, Integer thirdPart) {
        int updatedRows = scheduleRepository.updateBusynessType(emp, date, busynessType, thirdPart);
        if (updatedRows == 0) {
            throw new IllegalArgumentException("Расписание не найдено или неверное значение для thirdPart.");
        }
    }
}
