package com.dat.csmis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dat.csmis.entity.MeatList;
import com.dat.csmis.repository.AvoidMSP;
import com.dat.csmis.service.AvoidMS;

public class AvoidMSServiceTest {

    @Mock
    private AvoidMSP avoidMeatRepository;

    @InjectMocks
    private AvoidMS avoidMSService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testFindById() {
       
    	int id = 1;
        MeatList expectedMeat = new MeatList();
        expectedMeat.setId(id);
        when(avoidMeatRepository.findById(id)).thenReturn(Optional.of(expectedMeat));

        Optional<MeatList> actualMeat = avoidMSService.findById(id);

        assertTrue(actualMeat.isPresent());
        assertEquals(expectedMeat, actualMeat.get());
    }

    
    @Test
    public void testCreateAvoidMeat() {
       
    	MeatList avoidMeat = new MeatList();
        MeatList savedAvoidMeat = new MeatList();

        when(avoidMeatRepository.save(avoidMeat)).thenReturn(savedAvoidMeat);

        MeatList result = avoidMSService.createAvoidMeat(avoidMeat);

        verify(avoidMeatRepository).save(avoidMeat);

        assertEquals(savedAvoidMeat, result);
    }
    
    
    @Test
    public void testSave() {
       
    	MeatList meat = new MeatList();
        when(avoidMeatRepository.save(meat)).thenReturn(meat);

        avoidMSService.save(meat);

    }
    

    @Test
    public void testGetAllAvoidMeat() {
        List<MeatList> expectedList = new ArrayList<>();
        when(avoidMeatRepository.allList()).thenReturn(expectedList);

        List<MeatList> actualList = avoidMSService.getAllAvoidMeat();

        assertEquals(expectedList, actualList);
    }

    @Test
    public void testDeleteAvoidMeat() {
        int id = 1;

        avoidMSService.deleteAvoidMeat(id);

        verify(avoidMeatRepository).deleteById(id);
    }

    @Test
    public void testUpdate() {
        
    	MeatList avoidMeat = new MeatList();
        int id = 1;
        MeatList updatedAvoidMeat = new MeatList();

        when(avoidMeatRepository.save(avoidMeat)).thenReturn(updatedAvoidMeat);

        int result = avoidMSService.update(avoidMeat, id);

        verify(avoidMeatRepository).save(avoidMeat);

        assertEquals(1, result);
    }
    
    
    @Test
    public void testUpdateWithUserAndInteger() {
        
    	MeatList avoidMeat = new MeatList();
        when(avoidMeatRepository.save(avoidMeat)).thenReturn(avoidMeat);

        int result = avoidMSService.update(avoidMeat, 1);

        assertEquals(1, result);
    }

    @Test
    public void testUpdateWithIdAndMeat() {
       
    	Integer id = 1;
        MeatList avoidMeat = new MeatList();
        when(avoidMeatRepository.save(avoidMeat)).thenReturn(avoidMeat);

        int result = avoidMSService.update(id, avoidMeat);
        
        assertEquals(1,result);
}
    
    @Test
    public void testDuplicateAvoidMeat() {

    	List<String> expectedAvoidMeat = new ArrayList<>();
        expectedAvoidMeat.add("meat1");
        expectedAvoidMeat.add("meat2");
        when(avoidMeatRepository.getAvoidMeatDuplicate()).thenReturn(expectedAvoidMeat);

        List<String> actualAvoidMeat = avoidMSService.duplicateAvoidMeat();

        assertEquals(expectedAvoidMeat, actualAvoidMeat);
    }
}

