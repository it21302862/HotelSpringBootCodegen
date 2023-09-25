package com.hotel.hotel.service;

import com.hotel.hotel.repository.SeasonRepository;
import com.hotel.hotel.util.VarList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeasonServiceTest {

    @Mock private SeasonRepository seasonRepository;
//    private AutoCloseable autoCloseable;
    private SeasonService underTest;



    @BeforeEach
    void setUp(){
//        autoCloseable=MockitoAnnotations.openMocks(this);
        underTest = new SeasonService(seasonRepository, new ModelMapper());
    }

//    @AfterEach
//    void tearDown() throws Exception {
//        autoCloseable.close();
//    }

    @Test
    void canGetAllSeasons() {
        //test
        underTest.getAllSeasons();

        //then
        verify(seasonRepository).findAll();

    }

    @Test
    void testDeleteSeason_Exists() {
        int seasonID = 123; // Replace with a valid seasonID
        when(seasonRepository.existsById(seasonID)).thenReturn(true);

        String result = underTest.deleteSeason(seasonID);

        verify(seasonRepository).deleteById(seasonID);
        assertEquals(VarList.RSP_SUCCESS, result);
    }

    @Test
    void testDeleteSeason_NotExists() {
        int seasonID = 456; // Replace with a non-existent seasonID
        when(seasonRepository.existsById(seasonID)).thenReturn(false);

        String result = underTest.deleteSeason(seasonID);

        verify(seasonRepository, never()).deleteById(anyInt()); //deleteById is never called
        assertEquals(VarList.RSP_NO_DATA_FOUND, result);
    }


}