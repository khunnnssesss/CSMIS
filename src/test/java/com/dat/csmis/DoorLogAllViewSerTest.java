package com.dat.csmis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.dat.csmis.entity.DoorLogAllViewEntity;
import com.dat.csmis.entity.EmpDoorlog;
import com.dat.csmis.entity.YourEntityKey;
import com.dat.csmis.repository.DoorLogRepository;
import com.dat.csmis.repository.DoorlogAllViewRepo;
import com.dat.csmis.repository.DoorlogViewRepository;
import com.dat.csmis.service.DailyEatViewService;
import com.dat.csmis.service.DoorLogAllViewSer;

public class DoorLogAllViewSerTest {

    @Mock
    private DoorlogAllViewRepo doorlogAllViewRepo;
    
    @Mock
    private DoorLogRepository doorlogRepository;
    
    @Mock
    private DoorlogViewRepository doorlogviewRepository;

    @InjectMocks
    private DoorLogAllViewSer doorLogAllViewSer;
    
    @InjectMocks
    private DailyEatViewService dailyEatViewService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testFindLunchAllPagination() {

    	int pageNo = 1;
        int pageSize = 10;
        String sortField = "name";
        String sortDirection = "ASC";
        String start = "2023-05-01";
        String end = "2023-05-31";
        String status = "active";

        List<DoorLogAllViewEntity> doorLogList = new ArrayList<>();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortDirection.equalsIgnoreCase("ASC") ? Sort.Order.asc(sortField) : Sort.Order.desc(sortField)));

        when(doorlogAllViewRepo.findAllDoor(pageable, start, end, status)).thenReturn(null);

        Page<DoorLogAllViewEntity> result = doorLogAllViewSer.findLunchAllPagination(pageNo, pageSize, sortField,
                sortDirection, start, end, status);

        verify(doorlogAllViewRepo, times(1)).findAllDoor(pageable, start, end, status);

        assertNull(result);
    }

    
    @Test
    public void testGetDoorAllView() {
       
    	List<DoorLogAllViewEntity> doorLogList = new ArrayList<>();

        when(doorlogAllViewRepo.getDoorLogAllView()).thenReturn(doorLogList);

        List<DoorLogAllViewEntity> result = doorLogAllViewSer.getDoorAllView();

        verify(doorlogAllViewRepo, times(1)).getDoorLogAllView();

        assertEquals(doorLogList, result);
    }
    
    
    @Test
    public void testGetDoorAllSearch() {
        
    	String start = "2022-01-01";
        String end = "2022-01-31";
        String status = "open";
        List<DoorLogAllViewEntity> expectedList = new ArrayList<>();
        expectedList.add(new DoorLogAllViewEntity());
        expectedList.add(new DoorLogAllViewEntity());

        when(doorlogAllViewRepo.getDoorLogSearch(start, end, status)).thenReturn(expectedList);

        List<DoorLogAllViewEntity> actualList = doorlogAllViewRepo.getDoorLogSearch(start, end, status);

        assertEquals(expectedList, actualList);

        verify(doorlogAllViewRepo).getDoorLogSearch(start, end, status);
    }

    @Test
    public void testGetDoorAllSearchByStartEndreport() {

    	String start = "2022-01-01";
        String end = "2022-01-31";
        List<DoorLogAllViewEntity> expectedList = new ArrayList<>();
        expectedList.add(new DoorLogAllViewEntity());
        expectedList.add(new DoorLogAllViewEntity());

        when(doorlogAllViewRepo.findAllDoorByStartEndreport(start, end)).thenReturn(expectedList);

        List<DoorLogAllViewEntity> actualList = doorlogAllViewRepo.findAllDoorByStartEndreport(start, end);

        assertEquals(expectedList, actualList);

        verify(doorlogAllViewRepo).findAllDoorByStartEndreport(start, end);
    }

    @Test
    public void testGetDoorAllSearchByStatusreport() {

    	String status = "open";
        List<DoorLogAllViewEntity> expectedList = new ArrayList<>();
        expectedList.add(new DoorLogAllViewEntity());
        expectedList.add(new DoorLogAllViewEntity());

        when(doorlogAllViewRepo.findAllDoorByStatusreport(status)).thenReturn(expectedList);

        List<DoorLogAllViewEntity> actualList =  doorlogAllViewRepo.findAllDoorByStatusreport(status);

        assertEquals(expectedList, actualList);

        verify(doorlogAllViewRepo).findAllDoorByStatusreport(status);
    }
    
    
    @Test
    public void testGetCountRegisterByMonth() {

    	List<Object[]> expectedData = new ArrayList<>();
        expectedData.add(new Object[]{"January", 10});

        when(doorlogAllViewRepo.getCountsByMonth()).thenReturn(expectedData);

        List<Object[]> actualData = doorlogAllViewRepo.getCountsByMonth();

        assertEquals(expectedData, actualData);

        verify(doorlogAllViewRepo).getCountsByMonth();
    }

    @Test
    public void testGetEmpRegisterList() {

    	String doorlog = "door1";
        String datestart = "2022-01-01";
        String dateend = "2022-01-31";
        List<EmpDoorlog> expectedList = new ArrayList<>();
       

        when(doorlogviewRepository.getDoorlogWeeklyLists(doorlog, datestart, dateend)).thenReturn(expectedList);

        List<EmpDoorlog> actualList = doorLogAllViewSer.getEmpRegisterList(doorlog, datestart, dateend);

        assertEquals(expectedList, actualList);

        verify(doorlogviewRepository).getDoorlogWeeklyLists(doorlog, datestart, dateend);
    }


    @Test
    public void testGetUserMonthlyHistory() {

    	String doorLogNo = "door1";
        String month = "January";
        List<EmpDoorlog> expectedList = new ArrayList<>();
        expectedList.add(null);
        expectedList.add(null);

        when(doorlogviewRepository.getDoorlogMonthlyLists(doorLogNo, month)).thenReturn(expectedList);

        List<EmpDoorlog> actualList = doorLogAllViewSer.getUserMonthlyHistory(doorLogNo, month);

        assertEquals(expectedList, actualList);

        verify(doorlogviewRepository).getDoorlogMonthlyLists(doorLogNo, month);
    }
}
