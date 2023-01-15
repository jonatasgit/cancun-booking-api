package com.jonatasalmeidadev.cancunbookingapi.repository;

import com.jonatasalmeidadev.cancunbookingapi.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository  extends JpaRepository<Room, Long> {
}
