package com.hotel.hotel.service;

import com.hotel.hotel.repository.SupplementRepository;
import com.hotel.hotel.util.VarList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupplementServiceTest {

    @Mock
    private SupplementRepository supplementRepository;

    private SupplementService underTest;

    @BeforeEach
    void setUp(){
        underTest=new SupplementService(supplementRepository,new ModelMapper());
    }


    @Test
    void canGetAllSupplement() {
        //test
        underTest.getAllSupplement();
       //then
        verify(supplementRepository).findAll();
    }

    @Test
    void testDeleteSupplement_Exists() {
        int supplementID = 123; // Replace with a valid supplementID
        when(supplementRepository.existsById(supplementID)).thenReturn(true);

        String result = underTest.deleteSupplement(supplementID);

        verify(supplementRepository).deleteById(supplementID);
        assertEquals(VarList.RSP_SUCCESS, result);
    }

    @Test
    void testDeleteSupplement_NotExists() {
        int supplementID = 456; // Replace with a non-existent supplementID
        when(supplementRepository.existsById(supplementID)).thenReturn(false);

        String result = underTest.deleteSupplement(supplementID);

        verify(supplementRepository, never()).deleteById(anyInt()); //deleteById is never called
        assertEquals(VarList.RSP_NO_DATA_FOUND, result);
    }
}