package com.hotel.hotel.repository;

import com.hotel.hotel.entity.Reservation;
import com.hotel.hotel.entity.RoomType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class HotelContractRepositoryTest {

    @Autowired
    private HotelContractRepository underTest;

    @Autowired
    private TestEntityManager entityManager;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void findMaxAdultsForRoomType() {

        RoomType roomType=new RoomType();
        roomType.setRoomType("Single");
        roomType.setNoOfRooms(12);
        roomType.setMaxAdults(4);
        entityManager.persist(roomType);
        entityManager.flush();

        int roomTypeId = roomType.getRoomTypeID();
        int result = underTest.findMaxAdultsForRoomType(roomTypeId);

        assertEquals(4, result);
    }

    @Test
    void findRoomCountForRoomType() {

        RoomType roomType=new RoomType();
        roomType.setRoomType("Single");
        roomType.setNoOfRooms(12);
        roomType.setMaxAdults(4);
        entityManager.persist(roomType);
        entityManager.flush();

        int roomTypeId = roomType.getRoomTypeID();
        int result = underTest.findRoomCountForRoomType(roomTypeId);

        assertEquals(12, result);
    }

    @Test
    void countBookedRooms() throws ParseException {
        RoomType roomType = new RoomType();
        roomType.setRoomType("Single");
        roomType.setNoOfRooms(12);
        roomType.setMaxAdults(4);
        entityManager.persist(roomType);
        entityManager.flush();

        int roomTypeId = roomType.getRoomTypeID();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date checkInDate = dateFormat.parse("2023-10-01"); // Replace with checkIN date
        Date checkOutDate = dateFormat.parse("2023-10-05"); // Replace with checkOUT date

        Reservation reservation1 = new Reservation(new Date(),checkInDate,checkOutDate,3);
        reservation1.setRoomType(roomType);
        reservation1.setBookedRooms(3); // Add the booked rooms count
        entityManager.persist(reservation1);

        Reservation reservation2 = new Reservation(new Date(),checkInDate,checkOutDate,2);
        reservation2.setRoomType(roomType);
        reservation2.setBookedRooms(2); // Add the booked rooms count
        entityManager.persist(reservation2);

        // Call the repository method to count booked rooms
        Integer result = underTest.countBookedRooms(roomTypeId);

        assertEquals(5, result);
    }
}