package com.dat.csmis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dat.csmis.entity.DoorLogEntity;
import com.dat.csmis.entity.DoorlogComposite;
import com.dat.csmis.repository.DoorLogRepository;
import com.dat.csmis.service.DoorLogService;

public class DoorLogServiceTest {

    @Mock
    private DoorLogRepository doorLogRepository;

    @InjectMocks
    private DoorLogService doorLogService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDoorLogCountByCurrentDate() {
       
    	int expectedCount = 5;
        when(doorLogRepository.countEntriesForCurrentDate()).thenReturn(expectedCount);

        int result = doorLogService.doorLogCountByCurrentdate();

        verify(doorLogRepository, times(1)).countEntriesForCurrentDate();

        assertEquals(expectedCount, result);
    }

    @Test
    public void testGetAllData() {
        
    	List<DoorLogEntity> doorLogList = new ArrayList<>();
        DoorLogEntity doorLog1 = new DoorLogEntity();
        DoorlogComposite key1 = new DoorlogComposite();
        key1.setDate("2023-05-24");
        key1.setDoorLog("123");
        doorLog1.setKey(key1);
        doorLog1.setName("John Doe");
        doorLog1.setDept("IT");

        DoorLogEntity doorLog2 = new DoorLogEntity();
        DoorlogComposite key2 = new DoorlogComposite();
        key2.setDate("2023-05-25");
        key2.setDoorLog("456");
        doorLog2.setKey(key2);
        doorLog2.setName("Jane Smith");
        doorLog2.setDept("HR");

        doorLogList.add(doorLog1);
        doorLogList.add(doorLog2);

        when(doorLogRepository.findAll()).thenReturn(doorLogList);

        List<DoorLogEntity> result = doorLogService.getAllData();

        verify(doorLogRepository, times(1)).findAll();

        assertEquals(doorLogList, result);
    }
    
    @Test
    public void testSelectDate() {

    	List<String> dateList = new ArrayList<>();
        dateList.add("2023-05-24");
        dateList.add("2023-05-25");
        dateList.add("2023-05-26");

        when(doorLogRepository.selectDate()).thenReturn(dateList);

        List<String> result = doorLogService.selectDate();

        verify(doorLogRepository, times(1)).selectDate();

        assertEquals(dateList, result);
    }
}
