package org.example.springtlgbot.dto;

import lombok.*;
import org.example.springtlgbot.entity.Employee;
import org.example.springtlgbot.entity.SparePart;
import org.example.springtlgbot.entity.Vehicle;

@Builder
@ToString
@Getter
public class OrderDTO {
    private Vehicle vehicle;
    private SparePart sparePart;
    private Employee master;
}
