package com.dat.csmis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dat.csmis.entity.RegisterInfo;
import com.dat.csmis.repository.RegisterRepo;
import com.dat.csmis.service.RegisterSer;

class RegisterSerTest {

    @Mock
    private RegisterRepo registerRepo;

    @InjectMocks
    private RegisterSer registerSer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveData_ShouldSaveRegisterInfo() {
        // Arrange
        RegisterInfo registerInfo = new RegisterInfo();

        // Act
        registerSer.saveData(registerInfo);

        // Assert
        verify(registerRepo, times(1)).save(registerInfo);
    }

    @Test
    void checkUserRegi_ExistingUserId_ShouldReturnTrue() {
        // Arrange
        String userId = "existingUserId";
        when(registerRepo.existsById(userId)).thenReturn(true);

        // Act
        boolean result = registerSer.checkUserRegi(userId);

        // Assert
        assertTrue(result);
    }

    @Test
    void checkUserRegi_NonExistingUserId_ShouldReturnFalse() {
        // Arrange
        String userId = "nonExistingUserId";
        when(registerRepo.existsById(userId)).thenReturn(false);

        // Act
        boolean result = registerSer.checkUserRegi(userId);

        // Assert
        assertFalse(result);
    }

    @Test
    void getAuser_ExistingEmpId_ShouldReturnRegisterInfo() {
        // Arrange
        String empId = "existingEmpId";
        RegisterInfo expectedRegisterInfo = new RegisterInfo();
        when(registerRepo.findById(empId)).thenReturn(Optional.of(expectedRegisterInfo));

        // Act
        RegisterInfo result = registerSer.getAuser(empId);

        // Assert
        assertEquals(expectedRegisterInfo, result);
    }


    @Test
    void checkUserStaff_ExistingStaffId_ShouldReturnTrue() {
        // Arrange
        String staffId = "existingStaffId";
        when(registerRepo.existsByEmpId(staffId)).thenReturn(true);

        // Act
        boolean result = registerSer.checkUserStaff(staffId);

        // Assert
        assertTrue(result);
    }

    @Test
    void checkUserStaff_NonExistingStaffId_ShouldReturnFalse() {
        // Arrange
        String staffId = "nonExistingStaffId";
        when(registerRepo.existsByEmpId(staffId)).thenReturn(false);

        // Act
        boolean result = registerSer.checkUserStaff(staffId);

        // Assert
        assertFalse(result);
    }

}