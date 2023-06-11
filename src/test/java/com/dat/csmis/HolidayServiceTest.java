package com.dat.csmis;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dat.csmis.entity.HolidaysEntity;
import com.dat.csmis.repository.HolidaysRepository;
import com.dat.csmis.service.HolidayService;

public class HolidayServiceTest {

    @Mock
    private HolidaysRepository holidaysRepository;

    @InjectMocks
    private HolidayService holidayService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        // Prepare test data
        List<HolidaysEntity> holidaysList = new ArrayList<>();
        // Add holiday entities to holidaysList...
        when(holidaysRepository.findAll()).thenReturn(holidaysList);

        // Call the service method
        List<HolidaysEntity> result = holidayService.getAll();

        // Verify the mock behavior
        verify(holidaysRepository, times(1)).findAll();

        // Assert the result
        assertEquals(holidaysList, result);
    }

    @Test
    public void testSaveHoliday() {
        // Prepare test data
        HolidaysEntity holiday = new HolidaysEntity(); // Create a mock HolidaysEntity object

        // Call the service method
        holidayService.saveHoliday(holiday);

        // Verify the mock behavior
        verify(holidaysRepository, times(1)).save(holiday);
    }
    
    @Test
    public void testGetAllHolidays() {
        // Prepare test data
        List<HolidaysEntity> holidaysList = new ArrayList<>();
        // Add holiday entities to holidaysList...
        when(holidaysRepository.findAll()).thenReturn(holidaysList);

        // Call the service method
        List<HolidaysEntity> result = holidayService.getAllHolidays();

        // Verify the mock behavior
        verify(holidaysRepository, times(1)).findAll();

        // Assert the result
        assertEquals(holidaysList, result);
    }


    @Test
    public void testFindById() {
        // Prepare test data
        long id = 1L;
        HolidaysEntity holiday = new HolidaysEntity(); // Create a mock HolidaysEntity object
        when(holidaysRepository.findById(id)).thenReturn(Optional.of(holiday));

        // Call the service method
        Optional<HolidaysEntity> result = holidayService.findById(id);

        // Verify the mock behavior
        verify(holidaysRepository, times(1)).findById(id);

        // Assert the result
        assertEquals(Optional.of(holiday), result);
    }

    @Test
    public void testDeleteById() {
        // Prepare test data
        long id = 1L;

        // Call the service method
        holidayService.deleteById(id);

        // Verify the mock behavior
        verify(holidaysRepository, times(1)).deleteById(id);
    }

    @Test
    public void testGetDate() {
        // Prepare test data
        List<String> dateList = new ArrayList<>();
        // Add dates to dateList...
        when(holidaysRepository.getDate()).thenReturn(dateList);

        // Call the service method
        List<String> result = holidayService.getDate();

        // Verify the mock behavior
        verify(holidaysRepository, times(1)).getDate();

        // Assert the result
        assertEquals(dateList, result);
    }

    @Test
    public void testDuplicateDate() {
        // Prepare test data
        List<String> distinctDateList = new ArrayList<>();
        // Add distinct dates to distinctDateList...
        when(holidaysRepository.getDistinctDates()).thenReturn(distinctDateList);

        // Call the service method
        List<String> result = holidayService.duplicateDate();

        // Verify the mock behavior
        verify(holidaysRepository, times(1)).getDistinctDates();

        // Assert the result
        assertEquals(distinctDateList, result);
    }
}
