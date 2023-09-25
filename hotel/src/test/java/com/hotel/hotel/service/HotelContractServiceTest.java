package com.hotel.hotel.service;

import com.hotel.hotel.repository.HotelContractRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HotelContractServiceTest {

    @Mock private HotelContractRepository hotelContractRepository;

    private HotelContractService underTest;

    @Test
    void saveContract() {
    }
}