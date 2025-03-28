package org.example.springtlgbot.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class Users {
    @Id
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
}
