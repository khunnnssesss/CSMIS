package com.dat.csmis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dat.csmis.repository.AvoidMeatRepository;
import com.dat.csmis.service.AvoidMeatService;

public class AvoidMeatServiceTest {

    @Mock
    private AvoidMeatRepository avoidMeatRepository;

    @InjectMocks
    private AvoidMeatService avoidMeatService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAvoidMeatCount() {
        // Prepare test data
        Object[] result1 = { "Chicken", 5 };
        Object[] result2 = { "Beef", 3 };
        List<Object[]> expectedList = Arrays.asList(result1, result2);

        // Set up mock behavior
        when(avoidMeatRepository.getCountByAvoid()).thenReturn(expectedList);

        // Call the service method
        List<Object[]> resultList = avoidMeatService.avoidMeatCount();

        // Verify the mock behavior
        Objects.requireNonNull(resultList).forEach(System.out::println); // Print the result for verification
        // You can also use verify(avoidMeatRepository).getCountByAvoid() to verify the mock behavior

        // Assert the result
        assertEquals(expectedList.size(), resultList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i)[0], resultList.get(i)[0]);
            assertEquals(expectedList.get(i)[1], resultList.get(i)[1]);
        }
    }
}
