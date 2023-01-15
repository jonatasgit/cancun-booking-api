package com.jonatasalmeidadev.cancunbookingapi.entity;

public class RoomNotFoundException extends RuntimeException{
    public RoomNotFoundException(String message){
        super(message);
    }
}
