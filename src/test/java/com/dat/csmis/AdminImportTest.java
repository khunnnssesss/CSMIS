package com.dat.csmis;

import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.Test;
	import org.mockito.InjectMocks;
	import org.mockito.Mock;
	import org.mockito.MockitoAnnotations;
	import org.springframework.ui.Model;
	import org.springframework.web.multipart.MultipartFile;

	import com.dat.csmis.controller.AdminController;
	import com.dat.csmis.entity.EmployeeEntity;
	import com.dat.csmis.service.EmployeeService;
	import com.dat.csmis.service.ImportService;
	import com.dat.csmis.service.RegisterService;

	import java.security.Principal;

	import static org.junit.jupiter.api.Assertions.assertEquals;
	import static org.mockito.Mockito.*;

	public class AdminImportTest {

	    @Mock
	    private EmployeeService employeeService;

	    @Mock
	    private RegisterService registerService;
	    
	    @Mock
	    private ImportService importService;
	    
	    @Mock
	    private EmployeeEntity employeeEntity;

	    @InjectMocks
	    private AdminController adminController;

	    @Mock
	    private Model model;
	    
	    @BeforeEach
	    void setup() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    void testImportPage() {
	        // Create a Principal object
	        Principal userObj = mock(Principal.class);

	        // Create an EmployeeEntity object
	        EmployeeEntity emp = new EmployeeEntity();
	        emp.setStaffId("123");
	        emp.setDoorLog("doorLogValue");

	        // Mock the behavior of the employeeService
	        when(employeeService.getOneEmp(userObj.getName())).thenReturn(emp);

	        // Mock the behavior of the registerService
	        when(registerService.checkUserStaff("123")).thenReturn(true);

	        // Call the method to be tested
	        String viewName = adminController.importPage(userObj, model);

	        // Verify the interactions and assertions
	        verify(employeeService, times(1)).getOneEmp(userObj.getName());
	        verify(registerService, times(1)).checkUserStaff("123");
	        verify(model, times(1)).addAttribute("userId", "123");
	        verify(model, times(1)).addAttribute("userDoorlog", "doorLogValue");
	        verify(model, times(1)).addAttribute("registered", true);
	        assertEquals("import", viewName);
	    }
	    
	    @Test
	    void testUserFile_ImportSuccess() {
	        // Create a Mock MultipartFile
	        MultipartFile file = mock(MultipartFile.class);

	        // Mock the behavior of the service.importEmployee() method
	        when(importService.importEmployee(file)).thenReturn(true);

	        // Call the method to be tested
	        String viewName = adminController.userFile(file, model);

	        // Verify the interactions and assertions
	        verify(importService, times(1)).importEmployee(file);
	        verify(model, times(1)).addAttribute("color", "alert alert-success alert-dismissible fade show");
	        verify(model, times(1)).addAttribute("messageEmp", "Successfully Imported..!");
	        assertEquals("import", viewName);
	    }

	    @Test
	    void testUserFile_ImportFailed() {
	        // Create a Mock MultipartFile
	        MultipartFile file = mock(MultipartFile.class);

	        // Mock the behavior of the service.importEmployee() method
	        when(importService.importEmployee(file)).thenReturn(false);

	        // Call the method to be tested
	        String viewName = adminController.userFile(file, model);

	        // Verify the interactions and assertions
	        verify(importService, times(1)).importEmployee(file);
	        verify(model, times(1)).addAttribute("color", "alert alert-danger alert-dismissible fade show");
	        verify(model, times(1)).addAttribute("messageEmp", "Import Failed..!");
	        assertEquals("import", viewName);
	    }
	    
	    @Test
	    void testDisplayAdminRegister() {
	        // Create a Mock Principal object
	        Principal principal = mock(Principal.class);
	        when(principal.getName()).thenReturn("username");

	        // Create a Mock EmployeeEntity object
	        EmployeeEntity emp = mock(EmployeeEntity.class);
	        when(employeeService.getOneEmp("username")).thenReturn(emp);

	        // Mock the behavior of the EmployeeEntity object
	        when(emp.getStaffId()).thenReturn("staffId");
	        when(emp.getDoorLog()).thenReturn("doorLog");
	        when(emp.getRole()).thenReturn("role");

	        // Mock the behavior of the RegisterService
	        when(registerService.checkUserStaff("staffId")).thenReturn(true);

	        // Call the method to be tested
	        String viewName = adminController.displayAdminRegister(principal, model);

	        // Verify the interactions and assertions
	        verify(employeeService, times(1)).getOneEmp("username");
	        verify(model, times(1)).addAttribute("userId", "staffId");
	        verify(model, times(1)).addAttribute("userDoorlog", "doorLog");
	        verify(model, times(1)).addAttribute("registered", true);
	        verify(model, times(1)).addAttribute("userType", "role");
	        assertEquals("adminRegister", viewName);
	    }

	    @Test
	    void testShowHistory() {
	        // Create a Mock Principal object
	        Principal principal = mock(Principal.class);
	        when(principal.getName()).thenReturn("username");

	        // Create a Mock EmployeeEntity object
	        EmployeeEntity emp = mock(EmployeeEntity.class);
	        when(employeeService.getOneEmp("username")).thenReturn(emp);

	        // Mock the behavior of the EmployeeEntity object
	        when(emp.getStaffId()).thenReturn("staffId");
	        when(emp.getDoorLog()).thenReturn("doorLog");

	        // Mock the behavior of the RegisterService
	        when(registerService.checkUserStaff("staffId")).thenReturn(true);

	        // Call the method to be tested
	        String viewName = adminController.showHistory(principal, model);

	        // Verify the interactions and assertions
	        verify(employeeService, times(1)).getOneEmp("username");
	        verify(model, times(1)).addAttribute("userId", "staffId");
	        verify(model, times(1)).addAttribute("userDoorlog", "doorLog");
	        verify(model, times(1)).addAttribute("registered", true);
	        assertEquals("historyDataPage", viewName);
	    }
	    
	    @Test
	    void testDayFile_Success() {
	        // Create a Mock MultipartFile object
	        MultipartFile file = mock(MultipartFile.class);

	        // Mock the behavior of the service method
	        when(importService.importHoliday(file)).thenReturn(true);

	        // Call the method to be tested
	        String viewName = adminController.dayFile(file, model);

	        // Verify the interactions and assertions
	        verify(importService, times(1)).importHoliday(file);
	        verify(model, times(1)).addAttribute("color", "alert alert-success alert-dismissible fade show");
	        verify(model, times(1)).addAttribute("messageHoli", "Successfully Imported..!");
	        assertEquals("import", viewName);
	    }

	    @Test
	    void testDayFile_Failure() {
	        // Create a Mock MultipartFile object
	        MultipartFile file = mock(MultipartFile.class);

	        // Mock the behavior of the service method
	        when(importService.importHoliday(file)).thenReturn(false);

	        // Call the method to be tested
	        String viewName = adminController.dayFile(file, model);

	        // Verify the interactions and assertions
	        verify(importService, times(1)).importHoliday(file);
	        verify(model, times(1)).addAttribute("color", "alert alert-danger alert-dismissible fade show");
	        verify(model, times(1)).addAttribute("messageHoli", "Import Failed..!");
	        assertEquals("import", viewName);
	    }

	    @Test
	    void testDoorFile_Success() {
	        // Create a Mock MultipartFile object
	        MultipartFile file = mock(MultipartFile.class);

	        // Mock the behavior of the service method
	        when(importService.importDoorlog(file)).thenReturn(true);

	        // Call the method to be tested
	        String viewName = adminController.doorFile(file, model);

	        // Verify the interactions and assertions
	        verify(importService, times(1)).importDoorlog(file);
	        verify(model, times(1)).addAttribute("color", "alert alert-success alert-dismissible fade show");
	        verify(model, times(1)).addAttribute("messageDoor", "Successfully Imported..!");
	        assertEquals("import", viewName);
	    }

	    @Test
	    void testDoorFile_Failure() {
	        // Create a Mock MultipartFile object
	        MultipartFile file = mock(MultipartFile.class);

	        // Mock the behavior of the service method
	        when(importService.importDoorlog(file)).thenReturn(false);

	        // Call the method to be tested
	        String viewName = adminController.doorFile(file, model);

	        // Verify the interactions and assertions
	        verify(importService, times(1)).importDoorlog(file);
	        verify(model, times(1)).addAttribute("color", "alert alert-danger alert-dismissible fade show");
	        verify(model, times(1)).addAttribute("messageDoor", "Import Failed..!");
	        assertEquals("import", viewName);
	    }
	    
	    @Test
	    void testDoorPdfFile_Success() {
	        // Create a Mock MultipartFile object
	        MultipartFile file = mock(MultipartFile.class);

	        // Mock the behavior of the service method
	        when(importService.importMenu(file)).thenReturn(true);

	        // Call the method to be tested
	        String viewName = adminController.doorPdfFile(file, model);

	        // Verify the interactions and assertions
	        verify(importService, times(1)).importMenu(file);
	        verify(model, times(1)).addAttribute("color", "alert alert-success alert-dismissible fade show");
	        verify(model, times(1)).addAttribute("messagePdf", "Successfully Imported..!");
	        assertEquals("import", viewName);
	    }

	    @Test
	    void testDoorPdfFile_Failure() {
	        // Create a Mock MultipartFile object
	        MultipartFile file = mock(MultipartFile.class);

	        // Mock the behavior of the service method
	        when(importService.importMenu(file)).thenReturn(false);

	        // Call the method to be tested
	        String viewName = adminController.doorPdfFile(file, model);

	        // Verify the interactions and assertions
	        verify(importService, times(1)).importMenu(file);
	        verify(model, times(1)).addAttribute("color", "alert alert-danger alert-dismissible fade show");
	        verify(model, times(1)).addAttribute("messagePdf", "Import Failed..!");
	        assertEquals("import", viewName);
	    }

	}

