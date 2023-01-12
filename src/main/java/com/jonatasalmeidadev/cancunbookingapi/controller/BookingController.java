package com.jonatasalmeidadev.cancunbookingapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {
    @GetMapping(value = "/book")
    public String book(){
        return "booked";
    }
}
