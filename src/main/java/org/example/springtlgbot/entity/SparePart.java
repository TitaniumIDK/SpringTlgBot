package org.example.springtlgbot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "spareparts")
public class SparePart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

//    @Column(name = "aim_car", nullable = false)
//    private Integer aimCar;

    @Column(name = "price_in")
    private Double priceIn;

    @Column(name = "price_out")
    private Double priceOut;

    //@Column(name = "stock")
    private Integer stock;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
}
