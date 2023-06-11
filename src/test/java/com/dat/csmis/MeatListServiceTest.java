package com.dat.csmis;

import com.dat.csmis.entity.MeatList;
import com.dat.csmis.repository.MeatListRepo;
import com.dat.csmis.service.MeatListService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MeatListServiceTest {

    @Mock
    private MeatListRepo meatrepo;

    @InjectMocks
    private MeatListService meatListService;

    public MeatListServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMeats() {
        List<MeatList> mockMeatList = new ArrayList<>();

        when(meatrepo.findAll()).thenReturn(mockMeatList);

        List<MeatList> result = meatListService.getmeats();

        assertEquals(mockMeatList, result);
    }
}
