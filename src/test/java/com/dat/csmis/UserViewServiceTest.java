package com.dat.csmis;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dat.csmis.entity.UserViewEntity;
import com.dat.csmis.repository.UserViewRepository;
import com.dat.csmis.service.UserViewService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserViewServiceTest {

    @Mock
    private UserViewRepository uvRepo;
    
    @Mock
    private UserViewEntity userViewEntity;

    @InjectMocks
    private UserViewService userViewService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ShouldReturnUserViewEntityList() {
        // Arrange
        List<UserViewEntity> expectedList = new ArrayList<>();
        when(uvRepo.findAll()).thenReturn(expectedList);

        // Act
        List<UserViewEntity> result = userViewService.findAll();

        // Assert
        assertEquals(expectedList, result);
    }

    @Test
    void getUserLunchInfo_ShouldReturnUserViewEntityList() {
        // Arrange
        String staffId = "123";
        List<UserViewEntity> expectedList = new ArrayList<>();
        when(uvRepo.findUserLuncInfo(staffId)).thenReturn(expectedList);

        // Act
        List<UserViewEntity> result = userViewService.getUserLunchInfo(staffId);

        // Assert
        assertEquals(expectedList, result);
    }

    // Additional tests for other scenarios can be added here

}
