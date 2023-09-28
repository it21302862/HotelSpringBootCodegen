package com.hotel.hotel.repository;

import com.hotel.hotel.entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class SupplementRepositoryTest {

    @Autowired
    private SupplementRepository underTest;

    @Autowired
    private TestEntityManager entityManager;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @BeforeEach
    void setUp() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = dateFormat.parse("2023-07-01");
        Date endDate = dateFormat.parse("2023-12-05");

        HotelContract hotelContract = new HotelContract();
        hotelContract.setStartDate(startDate);
        hotelContract.setEndDate(endDate);
        hotelContract.setTermsAndConditions("sample conditions");

        Supplement supplement1 = new Supplement();
        supplement1.setSupplementName("Supplement 1");
        entityManager.persist(supplement1);

        Supplement supplement2 = new Supplement();
        supplement2.setSupplementName("Supplement 2");
        entityManager.persist(supplement2);


        Date checkInDate = dateFormat.parse("2023-10-01"); // Replace with  checkIN date
        Date checkOutDate = dateFormat.parse("2023-10-05"); // Replace with  checkOUT date

        Season season=new Season();
        season.setSeasonName("Peak");
        season.setStartDate(startDate);
        season.setEndDate(endDate);
        season.setHotelContract(hotelContract);
        entityManager.persist(season);

        SupplementPrice.SupplementPriceId supplementPriceId1 = new SupplementPrice.SupplementPriceId(season.getSeasonID(),supplement1.getSupplementID(),hotelContract.getContractID());


        SupplementPrice supplementPrice1 = new SupplementPrice();
        supplementPrice1.setHotelContract(hotelContract);
        supplementPrice1.setSeason(season);
        supplementPrice1.setSupplement(supplement1);
        supplementPrice1.setSupplementPriceId(supplementPriceId1);
        entityManager.persist(supplementPrice1);

        SupplementPrice.SupplementPriceId supplementPriceId2 = new SupplementPrice.SupplementPriceId(season.getSeasonID(),supplement1.getSupplementID(),hotelContract.getContractID());


        SupplementPrice supplementPrice2 = new SupplementPrice();
        supplementPrice2.setHotelContract(hotelContract);
        supplementPrice2.setSeason(season);
        supplementPrice2.setSupplement(supplement2);
        supplementPrice2.setSupplementPriceId(supplementPriceId2);
        entityManager.persist(supplementPrice2);

        Reservation reservation1 = new Reservation(new Date(),checkInDate,checkOutDate,2);
        reservation1.setReservationID(1);
        reservation1.setSeasonId(1);
        reservation1.setContract(hotelContract.getContractID());



    }
    @Test
    void getSupplementsBySeason() {

        int reservationId = 1;
        List<Supplement> supplements = underTest.getSupplementsBySeason(reservationId);
        assertNotNull(supplements);

    }
}