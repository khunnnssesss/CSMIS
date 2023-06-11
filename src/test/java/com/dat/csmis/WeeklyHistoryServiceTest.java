package com.dat.csmis;

import com.dat.csmis.model.WeeklyPrice;
import com.dat.csmis.repository.WeekLyHistoryRepo;
import com.dat.csmis.service.WeeklyHistoryService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeeklyHistoryServiceTest {

    @Mock
    private WeekLyHistoryRepo weekLyHistoryRepo;

    @InjectMocks
    private WeeklyHistoryService weeklyHistoryService;

    @Test
    void getWeekPrice_ShouldReturnWeeklyPrice() {
        // Arrange
        String startDate = "2023-05-01";
        String endDate = "2023-05-07";
        WeeklyPrice expectedPrice = new WeeklyPrice();
        // Set up any necessary expectations on the mock repository
        when(weekLyHistoryRepo.empPP(startDate, endDate)).thenReturn(expectedPrice);

        // Act
        WeeklyPrice result = weeklyHistoryService.getWeekPrice(startDate, endDate);

        // Assert
        assertEquals(expectedPrice, result);
    }

    // Add more test methods as needed

}
