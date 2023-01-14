package com.jonatasalmeidadev.cancunbookingapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Hotel hotel;
    @Column(name = "room_name")
    private String name;
    @Column
    private int capacity;
    @Column
    private double dailyPrice;
    @Column
    private String facilities;

}
