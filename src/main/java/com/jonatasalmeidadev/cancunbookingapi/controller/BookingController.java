package com.jonatasalmeidadev.cancunbookingapi.controller;

import com.jonatasalmeidadev.cancunbookingapi.entity.Reservation;
import com.jonatasalmeidadev.cancunbookingapi.entity.Room;
import com.jonatasalmeidadev.cancunbookingapi.entity.RoomNotFoundException;
import com.jonatasalmeidadev.cancunbookingapi.service.ReservationService;
import com.jonatasalmeidadev.cancunbookingapi.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/cancun")
public class BookingController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private ReservationService reservationService;

    @GetMapping(value = "/rooms")
    ResponseEntity<List<Room>> allRooms(@RequestParam LocalDate checkIn, @RequestParam LocalDate checkOut){
        System.out.println("in, out " +  checkIn + " / " + checkOut);
        return new ResponseEntity<>(this.roomService.listAvailableRooms(checkIn, checkOut), HttpStatus.OK);
    }
    @PostMapping(value = "/book")
    public ResponseEntity<?> book(@RequestBody(required = true) Reservation reservation){
        return new ResponseEntity<>(this.reservationService.book(reservation), HttpStatus.OK);
    }
}
