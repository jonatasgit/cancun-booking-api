package com.jonatasalmeidadev.cancunbookingapi.service;

import com.jonatasalmeidadev.cancunbookingapi.entity.Reservation;
import com.jonatasalmeidadev.cancunbookingapi.entity.ReservationBusinessException;
import com.jonatasalmeidadev.cancunbookingapi.entity.Room;
import com.jonatasalmeidadev.cancunbookingapi.entity.RoomNotFoundException;
import com.jonatasalmeidadev.cancunbookingapi.repository.ReservationRepository;
import com.jonatasalmeidadev.cancunbookingapi.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    public Reservation reserve(Reservation reservation){
        var checkIn = reservation.getCheckIn();
        var checkOut = reservation.getCheckOut();
        var today = LocalDateTime.now();

        Optional<Room> roomOpt = this.roomRepository.findById(reservation.getRoom().getId());
        reservation.setRoom(roomOpt.orElseThrow(() -> new RoomNotFoundException("Room not found for id: " + reservation.getRoom().getId())));

        if(checkIn.isAfter(checkOut)) {
            throw new ReservationBusinessException("The check-in date can't be after check-out.");
        }

        if(checkIn.isBefore(today)){
            throw new ReservationBusinessException("The check-in date can't be before today.");
        } else if (checkIn.isBefore(today.plusDays(1))) {
            throw new ReservationBusinessException("All reservations start at least the next day of booking.");
        }

        var daysOfStay = Duration.between(checkIn, checkOut).toDays();
        var daysInAdvance = Duration.between(today, checkIn).toDays();

        if(daysOfStay > 3){
            throw new ReservationBusinessException("The stay can’t be longer than 3 days.");
        } else if (daysInAdvance > 30){
            throw new ReservationBusinessException("The stay can’t be reserved more than 30 days in advance.");
        }
        var placedReservations = this.reservationRepository.findAll();
        ArrayList<Room> availableRooms = (ArrayList<Room>) this.roomRepository.findAll();
        ArrayList<Room> allRooms = (ArrayList<Room>) availableRooms.clone();

        for(Reservation placedReservation : placedReservations){
            if(reservation.getRoom().getId().equals(placedReservation.getRoom().getId())){
                var rCheckIn = placedReservation.getCheckIn();
                var rCheckOut = placedReservation.getCheckOut();

                if((rCheckIn.isAfter(checkIn) || rCheckIn.isEqual(checkIn)) && (rCheckIn.isBefore(checkOut) || rCheckIn.isEqual(checkOut))){
                        throw new RoomNotFoundException("This room isn't available is this period.");
                } else if ((rCheckOut.isAfter(checkIn) || rCheckOut.isEqual(checkIn)) && (rCheckOut.isBefore(checkOut) || rCheckOut.isEqual(checkOut))) {
                        throw new RoomNotFoundException("This room isn't available is this period.");
                }
            }
        }

        setBalanceAmount(reservation, daysOfStay);
        return reservationRepository.save(reservation);
    }

    static void setBalanceAmount(Reservation reservation, Long daysOfStay){
        var pricePerDay = reservation.getRoom().getPrice();
        var amount = daysOfStay * pricePerDay;
        reservation.setBalanceAmount(amount);
    }

    public ResponseEntity<?> deleteReserve(Long id){
        try {
            this.reservationRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<?> updateReserve(Long id, Reservation updatedReservation){
        try {
            var reservation = this.reservationRepository.findById(id);

            if(reservation.isPresent()){
                Reservation _reservation = reservation.get();
                _reservation.setRoom(roomRepository.findById(updatedReservation.getRoom().getId()).orElseThrow(() ->
                        new RoomNotFoundException("Room not found for id: " + updatedReservation.getRoom().getId())
                ));
                _reservation.setGuests(updatedReservation.getGuests());
                if(Objects.nonNull(updatedReservation.getCheckIn()) || Objects.nonNull(updatedReservation.getCheckOut())){
                    _reservation.setCheckIn(updatedReservation.getCheckIn());
                    _reservation.setCheckOut(updatedReservation.getCheckOut());
                    setBalanceAmount(_reservation, Duration.between(_reservation.getCheckIn(), _reservation.getCheckOut()).toDays());
                }
                return new ResponseEntity<>(this.reservationRepository.save(_reservation), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
