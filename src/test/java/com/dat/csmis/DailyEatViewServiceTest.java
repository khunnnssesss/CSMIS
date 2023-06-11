package com.dat.csmis;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dat.csmis.model.DailyDatePrice;
import com.dat.csmis.model.DailyRegister;
import com.dat.csmis.repository.DailyEatViewRepo;
import com.dat.csmis.service.DailyEatViewService;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DailyEatViewServiceTest {

    @Mock
    private DailyEatViewRepo dailyEatViewRepo;
    
    @Mock
    private DailyRegister dailyRegister;
    
    @Mock
    private DailyDatePrice dailyDatePrice;

    @InjectMocks
    private DailyEatViewService dailyEatViewService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
       
    	String userId = "123";
        List<DailyRegister> expectedRegisters = new ArrayList<>();
        expectedRegisters.add(new DailyRegister());
        expectedRegisters.add(new DailyRegister());

        when(dailyEatViewRepo.getAllOne(userId)).thenReturn(expectedRegisters);

        List<DailyRegister> actualRegisters = dailyEatViewService.getAll(userId);

        assertEquals(expectedRegisters, actualRegisters);
        
        verify(dailyEatViewRepo).getAllOne(userId);
    }
    
    @Test
    public void testGetOne() {
        
    	String doorlog = "door1";
        String month = "June";
        List<DailyRegister> expectedRegisters = new ArrayList<>();
        expectedRegisters.add(new DailyRegister());
        expectedRegisters.add(new DailyRegister());

        when(dailyEatViewRepo.getUser(doorlog, month)).thenReturn(expectedRegisters);

        List<DailyRegister> actualRegisters = dailyEatViewService.getOne(doorlog, month);

        assertEquals(expectedRegisters, actualRegisters);
        
        verify(dailyEatViewRepo).getUser(doorlog, month);
    }

    @Test
    public void testGetMonthlyData() {

    	String userId = "123";
        String month = "June";
        List<DailyDatePrice> expectedData = new ArrayList<>();
        expectedData.add(new DailyDatePrice());
        expectedData.add(new DailyDatePrice());

        when(dailyEatViewRepo.getmonthlydates(userId, month)).thenReturn(expectedData);

        List<DailyDatePrice> actualData = dailyEatViewService.getMonthlyData(userId, month);

        assertEquals(expectedData, actualData);
        
        verify(dailyEatViewRepo).getmonthlydates(userId, month);
    }
}

