package com.jonatasalmeidadev.cancunbookingapi.service;

import com.jonatasalmeidadev.cancunbookingapi.entity.Reservation;
import com.jonatasalmeidadev.cancunbookingapi.entity.ReservationBusinessException;
import com.jonatasalmeidadev.cancunbookingapi.entity.Room;
import com.jonatasalmeidadev.cancunbookingapi.entity.RoomNotFoundException;
import com.jonatasalmeidadev.cancunbookingapi.repository.ReservationRepository;
import com.jonatasalmeidadev.cancunbookingapi.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public Room save(Room room){
        return roomRepository.save(room);
    }

    public List<Room> listAvailableRooms(LocalDate checkIn, LocalDate checkOut){
        var today = LocalDate.now();

        if(checkIn.isBefore(today)){
            throw new RoomNotFoundException("The check-in date can't be before today.");
        } else if (checkIn.isBefore(today.plusDays(1))) {
            throw new RoomNotFoundException("All reservations start at least the next day of booking.");
        }

        var daysOfStay = DAYS.between(checkIn, checkOut);

        if(daysOfStay > 3){
            throw new RoomNotFoundException("The stay can’t be longer than 3 days.");
        }

        var placedReservations = this.reservationRepository.findAll();
        ArrayList<Room> availableRooms = (ArrayList<Room>) this.roomRepository.findAll();
        ArrayList<Room> allRooms = (ArrayList<Room>) availableRooms.clone();

        for(Room room : allRooms){
            for(Reservation placedReservation : placedReservations){
                if(room.getId().equals(placedReservation.getRoom().getId())){
                    var rCheckIn = placedReservation.getCheckIn().toLocalDate();
                    var rCheckOut = placedReservation.getCheckOut().toLocalDate();

                    if((rCheckIn.isAfter(checkIn) || rCheckIn.isEqual(checkIn)) && (rCheckIn.isBefore(checkOut) || rCheckIn.isEqual(checkOut))){
                        availableRooms.remove(room);
                        if(availableRooms.isEmpty()){
                            throw new RoomNotFoundException("Could not find any room available is this period.");
                        }
                    } else if ((rCheckOut.isAfter(checkIn) || rCheckOut.isEqual(checkIn)) && (rCheckOut.isBefore(checkOut) || rCheckOut.isEqual(checkOut))) {
                        availableRooms.remove(room);
                        if(availableRooms.isEmpty()){
                            throw new RoomNotFoundException("Could not find any room available is this period.");
                        }
                    }
                }
            }
        }

        if(availableRooms.isEmpty()){
            throw new RoomNotFoundException("Could not find any rooms.");
        }

        return availableRooms;
    }

    public Optional<Room> searchById(Long roomId){
         return roomRepository.findById(roomId);
    }

    public void removeById(Long roomId){
        roomRepository.deleteById(roomId);
    }
}
