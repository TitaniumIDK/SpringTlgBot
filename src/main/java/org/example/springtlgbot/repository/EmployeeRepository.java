package org.example.springtlgbot.repository;

import org.example.springtlgbot.entity.Employee;
import org.example.springtlgbot.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByRole(Roles role);
}
