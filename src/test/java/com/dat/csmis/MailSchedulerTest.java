package com.dat.csmis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dat.csmis.ScheculerController.MailScheeculer;
import com.dat.csmis.entity.EmployeeEntity;
import com.dat.csmis.model.WeeklyLists;
import com.dat.csmis.service.DailyEatViewService;
import com.dat.csmis.service.DateListEntityService;
import com.dat.csmis.service.EmployeeService;
import com.dat.csmis.service.MailSer;
import com.dat.csmis.service.RegisterService;

public class MailSchedulerTest {

    @Mock
    private RegisterService registerService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private DateListEntityService dateListEntityService;

    @Mock
    private MailSer mailService;

    @Mock
    private DailyEatViewService dailyEatViewService;

    @InjectMocks
    private MailScheeculer mailScheeculer;

    public MailSchedulerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendRegisterDayMailAll() throws Exception {
        // Mock data
        List<String> listdate = new ArrayList<>();
        listdate.add("empId1");
        listdate.add("empId2");

        EmployeeEntity emp1 = new EmployeeEntity();
        emp1.setStaffId("empId1");
        emp1.setEmail("emp1@example.com");

        EmployeeEntity emp2 = new EmployeeEntity();
        emp2.setStaffId("empId2");
        emp2.setEmail("emp2@example.com");

        List<String> emp1Dates = new ArrayList<>();
        emp1Dates.add("2023-05-08");
        emp1Dates.add("2023-05-10");

        List<String> emp2Dates = new ArrayList<>();
        emp2Dates.add("2023-05-09");
        emp2Dates.add("2023-05-11");

        // Mock methods
        when(registerService.getallmailregister()).thenReturn(listdate);
        when(employeeService.getOnestaffid("empId1")).thenReturn(emp1);
        when(employeeService.getOnestaffid("empId2")).thenReturn(emp2);
        when(dateListEntityService.getnext("empId1", "2023-05-08", "2023-05-12")).thenReturn(emp1Dates);
        when(dateListEntityService.getnext("empId2", "2023-05-08", "2023-05-12")).thenReturn(emp2Dates);

        // Call the method
        mailScheeculer.sendRegisterDayMailAll();

        // Verify the interactions
        verify(mailService, times(1)).sendWeeklyEmail(anyList(), eq("emp1@example.com"));
        verify(mailService, times(1)).sendWeeklyEmail(anyList(), eq("emp2@example.com"));
    }
    
    @Test
    void testSendAllEmployeeMonthly() {
        // Set up mock data
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat monthformat = new SimpleDateFormat("yyyy-MM");
        String curmonthdate = monthformat.format(cal.getTime());

        List<EmployeeEntity> employeeList = Arrays.asList( );
       

        when(employeeService.getAllEmployees()).thenReturn(employeeList);

        

        String result = mailScheeculer.sendAllEmployeeMonthly();

        // Verify that the sendMonthlyEmail method is called for each employee
        

        // Assert the result
        assertEquals("email Send success !!!", result);
    }

}