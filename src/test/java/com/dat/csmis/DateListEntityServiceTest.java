package com.dat.csmis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dat.csmis.repository.DateListEntityRepository;
import com.dat.csmis.service.DateListEntityService;

public class DateListEntityServiceTest {

    @Mock
    private DateListEntityRepository dateListRepository;

    @InjectMocks
    private DateListEntityService dateListEntityService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetNextWeekRegister() {

    	SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String startDate = dateFormat.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        String endDate = dateFormat.format(calendar.getTime());
        
        List<Object[]> expectedList = Arrays.asList(
            new Object[] {  },
            new Object[] {  }
        );
        
        when(dateListRepository.findNextWeekDates(startDate, endDate)).thenReturn(expectedList);

        List<Object[]> resultList = dateListEntityService.getNextWeekRegister();

        verify(dateListRepository).findNextWeekDates(startDate, endDate);

        assertEquals(expectedList, resultList);
    }
}
