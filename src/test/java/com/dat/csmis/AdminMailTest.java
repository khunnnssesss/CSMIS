package com.dat.csmis;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.dat.csmis.controller.AdminController;
import com.dat.csmis.entity.EmployeeEntity;
import com.dat.csmis.service.EmployeeService;
import com.dat.csmis.service.RegisterService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AdminMailTest{
    @Mock
	private RegisterService serviceRS;

    
    @Mock
	private EmployeeService serviceE;
    
    @Mock
    private EmployeeEntity employeeEntity;
    
    @Mock
    private Model model;

    @InjectMocks
    private AdminController adminController;
     
    
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMailList_EmptyList() {
        // Prepare test data
        List<EmployeeEntity> emptyList = new ArrayList<>();
        when(serviceRS.findJoin()).thenReturn(emptyList);

        // Call the method to be tested
        String result = adminController.mailList(model);

        // Verify the interactions and assertions
        verify(serviceRS, times(1)).findJoin();
        verify(model, times(1)).addAttribute(eq("mailList"), eq(emptyList));
        assertEquals("adminNotiList", result);
    }

    @Test
    void testDeleteMail() {
        // Prepare test data
        long id = 1L;

        // Call the method to be tested
        String result = adminController.deleteMail(id);

        // Verify the interactions and assertions
        verify(serviceE, times(1)).updateStatus(id);
        assertEquals("redirect:/admin/mail", result);
    }
}

