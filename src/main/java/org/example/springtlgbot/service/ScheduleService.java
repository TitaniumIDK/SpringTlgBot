package org.example.springtlgbot.service;

import org.example.springtlgbot.entity.Employee;
import org.example.springtlgbot.entity.Schedule;
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

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Optional<Schedule> getScheduleById(Integer id) {
        return scheduleRepository.findById(id);
    }

    public List<Schedule> getSchedulesByWorkDate(LocalDate workDate) {
        return scheduleRepository.findByWorkDate(workDate);
    }

    public List<Schedule> getSchedulesByEmployee(Employee emp) {
        return scheduleRepository.findByEmp(emp);
    }

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Integer id) {
        scheduleRepository.deleteById(id);
    }
}
