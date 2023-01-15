package com.jonatasalmeidadev.cancunbookingapi.entity;

public class ReservationBusinessException extends RuntimeException{
    public ReservationBusinessException(String message){
        super(message);
    }
}
