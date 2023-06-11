package com.dat.csmis;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dat.csmis.entity.EmployeeEntity;
import com.dat.csmis.repository.EmployeeRepository;
import com.dat.csmis.service.EmployeeNotFoundException;
import com.dat.csmis.service.EmployeeService;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCount() {

    	long expectedCount = 10;
        when(employeeRepository.count()).thenReturn(expectedCount);

        long result = employeeService.count();

        verify(employeeRepository, times(1)).count();

        assertEquals(expectedCount, result);
    }

    @Test
    public void testSave() {

    	EmployeeEntity employee = new EmployeeEntity();

        employeeService.save(employee);

        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void testGetNameByRole() {

    	List<EmployeeEntity> nameList = new ArrayList<>();

        when(employeeRepository.getNameByRole()).thenReturn(nameList);

        List<EmployeeEntity> result = employeeService.NameByRole();

        verify(employeeRepository, times(1)).getNameByRole();

        assertEquals(nameList, result);
    }

    @Test
    public void testSelectOne() {

    	long id = 1L;
        EmployeeEntity employeeEntity = new EmployeeEntity(); // Create a mock EmployeeEntity object
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employeeEntity));

        Optional<EmployeeEntity> result = employeeService.selectOne(id);

        verify(employeeRepository, times(1)).findById(id);

        assertEquals(Optional.of(employeeEntity), result);
    }

    @Test
    public void testSelectBy() {

    	String staffId = "ABC123";
        EmployeeEntity employeeEntity = new EmployeeEntity(); // Create a mock EmployeeEntity object
        when(employeeRepository.findByStaffIdAndPsw(staffId)).thenReturn(Optional.of(employeeEntity));

        Optional<EmployeeEntity> result = employeeService.selectby(staffId);

        verify(employeeRepository, times(1)).findByStaffIdAndPsw(staffId);

        assertEquals(Optional.of(employeeEntity), result);
    }

    @Test
    public void testUpdate() {

    	String staffId = "ABC123";
        String doorLog = "Door Log";
        String email = "test@example.com";
        String team = "Team";
        String role = "Role";
        String dept = "Department";
        String division = "Division";
        String status = "Status";
        long id = 1L;

        employeeService.update(staffId, doorLog, email, team, role, dept, division, status, id);

        verify(employeeRepository, times(1)).update(staffId, doorLog, email, team, role, dept, division, status, id);
    }

    @Test
    public void testUpdateStatus() {

    	long id = 1L;

        employeeService.updateStatus(id);

        verify(employeeRepository, times(1)).updateStatus(id);
    }
    
    @Test
    public void testUpdateResetPasswordToken() throws EmployeeNotFoundException {

    	String token = "reset_token";
        String email = "test@example.com";
        EmployeeEntity employeeEntity = new EmployeeEntity(); // Create a mock EmployeeEntity object
        when(employeeRepository.getByEmail(email)).thenReturn(employeeEntity);

        assertDoesNotThrow(() -> employeeService.updateResetPasswordToken(token, email));

        verify(employeeRepository, times(1)).getByEmail(email);
        verify(employeeRepository, times(1)).save(employeeEntity);
    }

    @Test
    public void testUpdateResetPasswordToken_EmployeeNotFound() {

    	String token = "reset_token";
        String email = "test@example.com";
        when(employeeRepository.getByEmail(email)).thenReturn(null);

        EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.updateResetPasswordToken(token, email));

        verify(employeeRepository, times(1)).getByEmail(email);
        assertEquals("Employee could not be found on " + email, exception.getMessage());
    }

    @Test
    public void testGetByReset() {

    	String token = "reset_token";
        EmployeeEntity employeeEntity = new EmployeeEntity(); // Create a mock EmployeeEntity object
        when(employeeRepository.findByResetPasswordToken(token)).thenReturn(employeeEntity);


       EmployeeEntity result = employeeService.getByReset(token);

        verify(employeeRepository, times(1)).findByResetPasswordToken(token);

        assertEquals(employeeEntity, result);
    }

    @Test
    public void testUpdatePassword() {

    	EmployeeEntity employeeEntity = new EmployeeEntity(); // Create a mock EmployeeEntity object
        String newPassword = "new_password";

        employeeService.updatePassword(employeeEntity, newPassword);

        verify(employeeRepository, times(1)).save(employeeEntity);
        assertNull(employeeEntity.getResetPasswordToken());
    }

    @Test
    public void testUpdateA() {

    	String status = "new_status";
        long id = 1L;

        employeeService.updateA(status, id);

        verify(employeeRepository, times(1)).updateA(status, id);
    }

    @Test
    public void testUpdateB() {

    	String role = "new_role";
        long id = 1L;

        employeeService.updateB(role, id);

        verify(employeeRepository, times(1)).updateB(role, id);
    }
    
    @Test
    public void testGetByMail() {

    	String mail = "test@example.com";
        when(employeeRepository.existsByEmail(mail)).thenReturn(true);

        boolean result = employeeService.getByMail(mail);

        verify(employeeRepository, times(1)).existsByEmail(mail);

        assertTrue(result);
    }

    @Test
    public void testGetOneEmp() {

    	String mail = "test@example.com";
        EmployeeEntity employeeEntity = new EmployeeEntity(); // Create a mock EmployeeEntity object
        when(employeeRepository.getByEmail(mail)).thenReturn(employeeEntity);

        EmployeeEntity result = employeeService.getOneEmp(mail);

        verify(employeeRepository, times(1)).getByEmail(mail);

        assertEquals(employeeEntity, result);
    }

    @Test
    public void testGetOnestaffid() {
        
    	String staffId = "ABC123";
        EmployeeEntity employeeEntity = new EmployeeEntity(); // Create a mock EmployeeEntity object
        when(employeeRepository.getByStaffId(staffId)).thenReturn(employeeEntity);

        EmployeeEntity result = employeeService.getOnestaffid(staffId);

        verify(employeeRepository, times(1)).getByStaffId(staffId);

        assertEquals(employeeEntity, result);
    }

    @Test
    public void testGetAllEmployees() {
        
    	List<EmployeeEntity> empList = new ArrayList<>();
        when(employeeRepository.findAllbutOne()).thenReturn(empList);

        List<EmployeeEntity> result = employeeService.getAllEmployees();

        verify(employeeRepository, times(1)).findAllbutOne();

        assertEquals(empList, result);
    }
}


////////////////////////////////////// J unit Finished //////////////////////////////////////



