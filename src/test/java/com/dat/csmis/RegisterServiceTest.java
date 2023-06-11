
package com.dat.csmis;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.dat.csmis.entity.EmployeeEntity;
import com.dat.csmis.entity.RegisterInfo;
import com.dat.csmis.repository.EmployeeRepository;
import com.dat.csmis.repository.RegisterRepository;
import com.dat.csmis.service.RegisterService;

class RegisterServiceTest {

    @Mock
    private RegisterRepository registerRepo;

    @Mock
    private EmployeeRepository employeeRepo;
    

    @InjectMocks
    private RegisterService registerService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getdateList_ShouldReturnRegisterInfo() {
        // Arrange
        String employeeId = "employeeId";
        RegisterInfo expectedRegisterInfo = new RegisterInfo();
        when(registerRepo.finddateList(employeeId)).thenReturn(expectedRegisterInfo);

        // Act
        RegisterInfo result = registerService.getdateList(employeeId);

        // Assert
        assertEquals(expectedRegisterInfo, result);
        verify(registerRepo, times(1)).finddateList(employeeId);
    }

    @Test
    void getAll_ShouldReturnRegisterInfo() {
        // Arrange
        String employeeId = "employeeId";
        RegisterInfo expectedRegisterInfo = new RegisterInfo();
        when(registerRepo.findAll(employeeId)).thenReturn(expectedRegisterInfo);

        // Act
        RegisterInfo result = registerService.getAll(employeeId);

        // Assert
        assertEquals(expectedRegisterInfo, result);
        verify(registerRepo, times(1)).findAll(employeeId);
    }

    @Test
    void checkUserRegi_ExistingUserId_ShouldReturnTrue() {
        // Arrange
        String userId = "existingUserId";
        when(registerRepo.existsById(userId)).thenReturn(true);

        // Act
        boolean result = registerService.checkUserRegi(userId);

        // Assert
        assertTrue(result);
    }

    @Test
    void checkUserRegi_NonExistingUserId_ShouldReturnFalse() {
        // Arrange
        String userId = "nonExistingUserId";
        when(registerRepo.existsById(userId)).thenReturn(false);

        // Act
        boolean result = registerService.checkUserRegi(userId);

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
        RegisterInfo result = registerService.getAuser(empId);

        // Assert
        assertEquals(expectedRegisterInfo, result);
    }

    @Test
    void checkUserStaff_ExistingStaffId_ShouldReturnTrue() {
        // Arrange
        String staffId = "existingStaffId";
        when(registerRepo.existsByEmpId(staffId)).thenReturn(true);

        // Act
        boolean result = registerService.checkUserStaff(staffId);

        // Assert
        assertTrue(result);
    }

    @Test
    void checkUserStaff_NonExistingStaffId_ShouldReturnFalse() {
        // Arrange
        String staffId = "nonExistingStaffId";
        when(registerRepo.existsByEmpId(staffId)).thenReturn(false);

        // Act
        boolean result = registerService.checkUserStaff(staffId);

        // Assert
        assertFalse(result);
    }
    
    @Test
    void getAvoidMeatByist_ShouldReturnList() {
        // Arrange
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String startDate = dateFormat.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        String endDate = dateFormat.format(calendar.getTime());
        List<Object[]> expectedList = new ArrayList<>();
        when(registerRepo.findEmployeeCountByAvoid(startDate, endDate)).thenReturn(expectedList);

        // Act
        List<Object[]> result = registerService.getAvoidMeatByist();

        // Assert
        assertEquals(expectedList, result);
        verify(registerRepo, times(1)).findEmployeeCountByAvoid(startDate, endDate);
    }

    @Test
    void getRowCount_ShouldReturnCount() {
        // Arrange
        long expectedCount = 10L;
        when(registerRepo.count()).thenReturn(expectedCount);

        // Act
        int result = registerService.getRowCount();

        // Assert
        assertEquals((int) expectedCount, result);
        verify(registerRepo, times(1)).count();
    }

    @Test
    void getMailNoti_ShouldReturnCount() {
        // Arrange
        int expectedCount = 5;
        when(registerRepo.countByMailNotiEqualsOne()).thenReturn(expectedCount);

        // Act
        int result = registerService.getMailNoti();

        // Assert
        assertEquals(expectedCount, result);
        verify(registerRepo, times(1)).countByMailNotiEqualsOne();
    }

    @Test
    void getallmailregister_ShouldReturnList() {
        // Arrange
        List<String> expectedList = List.of("email1@example.com", "email2@example.com");
        when(registerRepo.findregisterMailEmployees()).thenReturn(expectedList);

        // Act
        List<String> result = registerService.getallmailregister();

        // Assert
        assertEquals(expectedList, result);
        verify(registerRepo, times(1)).findregisterMailEmployees();
    }
    
    @Test
    void saveData_ShouldSaveRegisterInfo() {
        // Arrange
        RegisterInfo registerInfo = new RegisterInfo();
        when(registerRepo.save(registerInfo)).thenReturn(registerInfo);

        // Act
        registerService.saveData(registerInfo);

        // Assert
        verify(registerRepo, times(1)).save(registerInfo);
    }

    @Test
    void findJoin_ShouldReturnEmployeeEntityList() {
        // Arrange
        List<EmployeeEntity> expectedList = new ArrayList<>();
        when(employeeRepo.findFromTwoTables()).thenReturn(expectedList);

        // Act
        List<EmployeeEntity> result = registerService.findJoin();

        // Assert
        assertEquals(expectedList, result);
        verify(employeeRepo, times(1)).findFromTwoTables();
    }
  
    
}
