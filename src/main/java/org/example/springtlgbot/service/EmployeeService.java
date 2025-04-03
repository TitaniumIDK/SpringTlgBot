package org.example.springtlgbot.service;

import org.checkerframework.checker.units.qual.A;
import org.example.springtlgbot.entity.Employee;
import org.example.springtlgbot.enums.Roles;
import org.example.springtlgbot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getMechanics() {
        return employeeRepository.findByRole(Roles.MECHANIC);
    }
}
