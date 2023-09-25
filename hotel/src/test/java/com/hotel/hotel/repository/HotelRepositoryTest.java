package com.hotel.hotel.repository;

import com.hotel.hotel.entity.Hotel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class HotelRepositoryTest {

    @Autowired
    private HotelRepository underTest;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckWhenHotelNameAndAddressExists(){

        //given
        Hotel hotel=new Hotel(
                45,"Amaya","Amaya@gmail.com",
                "Colombo","0765678947","This is a new hotel",
                "https://images.pexels.com/photos/2844474/pexels-photo-2844474.jpeg?auto=compress&cs=tinysrgb&w=600"
        );
        underTest.save(hotel);

        //when
        List<Hotel> exists=underTest.findByAddressAndName("Colombo","Amaya");
        System.out.println("Query Results: " + exists);
        exists.forEach(System.out::println);

        //then
        assertThat(exists).isNotEmpty();
        assertEquals(1, exists.size());
        assertEquals("Amaya", exists.get(0).getHotelName());
        assertEquals("Colombo", exists.get(0).getLocation());

    }

    @Test
    void itShouldCheckWhenHotelNameAndAddressBothNotExists(){

        //given
        Hotel hotel=new Hotel(
                45,"Amaya","Amaya@gmail.com",
                "Colombo","0765678947","This is a new hotel",
                "https://images.pexels.com/photos/2844474/pexels-photo-2844474.jpeg?auto=compress&cs=tinysrgb&w=600"
        );
        underTest.save(hotel);

        //when
        List<Hotel> exists=underTest.findByAddressAndName("Kandy","Sadali");
        System.out.println("Query Results: " + exists);
        exists.forEach(System.out::println);

        //then
        assertThat(exists).isEmpty();

    }





}