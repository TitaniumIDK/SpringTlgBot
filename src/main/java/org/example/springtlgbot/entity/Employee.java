package org.example.springtlgbot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.example.springtlgbot.enums.Roles;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employees")

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "date_start", nullable = false)
    private LocalDate dateStart;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @Column(name = "salary")
    private Double salary;

//    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Schedule> schedules;
}
