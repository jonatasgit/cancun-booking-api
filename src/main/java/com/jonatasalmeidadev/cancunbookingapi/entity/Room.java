package com.jonatasalmeidadev.cancunbookingapi.entity;

import com.jonatasalmeidadev.cancunbookingapi.repository.ReservationRepository;
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
public class Room implements Serializable, Cloneable {
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
    private double price;
    @Column
    private String facilities;

    @Override
    public Room clone(){
        Room clonedRoom = null;
        try {
            clonedRoom = (Room) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return clonedRoom;
    }
}
