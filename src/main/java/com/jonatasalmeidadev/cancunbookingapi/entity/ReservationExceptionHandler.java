package com.jonatasalmeidadev.cancunbookingapi.entity;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ReservationExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ReservationBusinessException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String reservationHandler(ReservationBusinessException ex){
        return ex.getMessage();
    }
}
