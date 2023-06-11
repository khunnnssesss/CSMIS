package com.dat.csmis;

import com.dat.csmis.entity.WeeklyOrderEntity;
import com.dat.csmis.repository.WeeklyOrderRepository;
import com.dat.csmis.service.WeeklyOrderService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeeklyOrderServiceTest {

    @Mock
    private WeeklyOrderRepository weekOrderRepo;
    
    @Mock
    private WeeklyOrderEntity weeklyOrderEntity;

    @InjectMocks
    private WeeklyOrderService weeklyOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_ShouldReturnSavedWeeklyOrderEntity() {
        // Arrange
        WeeklyOrderEntity weeklyOrder = new WeeklyOrderEntity();
        WeeklyOrderEntity expectedSavedOrder = new WeeklyOrderEntity();
        when(weekOrderRepo.save(weeklyOrder)).thenReturn(expectedSavedOrder);

        // Act
        WeeklyOrderEntity result = weeklyOrderService.save(weeklyOrder);

        // Assert
        assertEquals(expectedSavedOrder, result);
        verify(weekOrderRepo, times(1)).save(weeklyOrder);
    }

    @Test
    void getAllList_ShouldReturnListOfWeeklyOrderEntity() {
        // Arrange
        List<WeeklyOrderEntity> expectedList = new ArrayList<>();
        when(weekOrderRepo.findAll()).thenReturn(expectedList);

        // Act
        List<WeeklyOrderEntity> result = weeklyOrderService.getAllList();

        // Assert
        assertEquals(expectedList, result);
        verify(weekOrderRepo, times(1)).findAll();
    }

    @Test
    void findWeeklyReport_ShouldReturnListOfWeeklyOrderEntity() {
        // Arrange
        List<WeeklyOrderEntity> expectedList = new ArrayList<>();
        when(weekOrderRepo.findWeeklyListReport()).thenReturn(expectedList);

        // Act
        List<WeeklyOrderEntity> result = weeklyOrderService.findWeeklyReport();

        // Assert
        assertEquals(expectedList, result);
        verify(weekOrderRepo, times(1)).findWeeklyListReport();
    }

    @Test
    void searchEngine_ShouldReturnListOfWeeklyOrderEntity() {
        // Arrange
        String start = "2023-05-01";
        String end = "2023-05-07";
        List<WeeklyOrderEntity> expectedList = new ArrayList<>();
        when(weekOrderRepo.searchEngineReport(start, end)).thenReturn(expectedList);

        // Act
        List<WeeklyOrderEntity> result = weeklyOrderService.searchEngine(start, end);

        // Assert
        assertEquals(expectedList, result);
        verify(weekOrderRepo, times(1)).searchEngineReport(start, end);
    }

    // Add more test methods as needed

    @Test
    void findPagination_ShouldReturnPageOfWeeklyOrderEntity() {
        // Arrange
        int pageNo = 1;
        int pageSize = 10;
        String sortField = "fieldName";
        String sortDirection = "ASC";
        String start = "2023-05-01";
        String end = "2023-05-07";
        Page<WeeklyOrderEntity> expectedPage = mock(Page.class);
        Sort sort = Sort.by(Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        when(weekOrderRepo.findAllO(pageable, start, end)).thenReturn(expectedPage);

        // Act
        Page<WeeklyOrderEntity> result = weeklyOrderService.findPagination(pageNo, pageSize, sortField, sortDirection, start, end);

        // Assert
        assertEquals(expectedPage, result);
        verify(weekOrderRepo, times(1)).findAllO(pageable, start, end);
    }

}
