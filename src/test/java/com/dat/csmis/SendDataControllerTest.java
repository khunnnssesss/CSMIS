package com.dat.csmis;

import com.dat.csmis.ScheculerController.MailScheeculer;
import com.dat.csmis.controller.SendDataController;
import com.dat.csmis.entity.EmpDoorlog;
import com.dat.csmis.entity.EmployeeEntity;
import com.dat.csmis.entity.HolidaysEntity;
import com.dat.csmis.entity.MeatList;
import com.dat.csmis.entity.RegisterInfo;
import com.dat.csmis.entity.UserViewEntity;
import com.dat.csmis.model.DailyDatePrice;
import com.dat.csmis.model.DailyRegister;
import com.dat.csmis.model.WeeklyPrice;
import com.dat.csmis.service.RegisterSer;
import com.dat.csmis.service.UserViewService;
import com.dat.csmis.service.DailyEatViewService;
import com.dat.csmis.service.DateListEntityService;
import com.dat.csmis.service.DoorLogAllViewSer;
import com.dat.csmis.service.EmployeeService;
import com.dat.csmis.service.HolidayService;
import com.dat.csmis.service.MailSer;
import com.dat.csmis.service.MeatListService;

import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.Test;
	import org.mockito.InjectMocks;
	import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
	import org.springframework.boot.test.context.SpringBootTest;
	import org.springframework.http.MediaType;
	import org.springframework.test.web.servlet.MockMvc;
	import org.springframework.test.web.servlet.setup.MockMvcBuilders;

	import static org.mockito.Mockito.*;
	import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

	@SpringBootTest
	public class SendDataControllerTest {

	    private MockMvc mockMvc;

	    @Mock
	    private RegisterSer registerSer;

	    @Mock
	    private EmployeeService employeeService;
	    
	    @Mock
	    private HolidayService holidayService;
	    
	    @Mock
	    private UserViewService userviewService;
	    
	    @Mock
	    private MeatListService meatlistService;
	    
	    @Mock
	    private DailyEatViewService dailyEatViewService;
	    
	    @Mock
	    private DateListEntityService dateListEntityService;
	    
	    @Mock
	    private MailSer mailService;
	    
	    @Mock
	    private DoorLogAllViewSer doorLogAllViewService;
	    
	    @Mock
	    private WeeklyPrice weeklyPrice;
	    
	    @Mock
	    private MailScheeculer mailScheduler;

	    @InjectMocks
	    private SendDataController sendDataController;

	    @BeforeEach
	    public void setup() {
	        MockitoAnnotations.openMocks(this);
	        this.mockMvc = MockMvcBuilders.standaloneSetup(sendDataController).build();
	    }

	    @Test
	    public void testSaveData() throws Exception {
	        RegisterInfo registerInfo = new RegisterInfo();
	        registerInfo.setEmailNoti(false);

	        mockMvc.perform(post("/myDaDa")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content("{\"someProperty\":\"value\"}"))
	                .andExpect(status().isOk());

	    }

	    @Test
	    public void testCheckUserInfo() throws Exception {
	        String userId = "123";
	        EmployeeEntity employee = new EmployeeEntity();
	        employee.setStaffId(userId);

	        when(employeeService.getOnestaffid(userId)).thenReturn(employee);
	        when(registerSer.checkUserStaff(userId)).thenReturn(true);

	        mockMvc.perform(get("/checkData/{id}", userId))
	                .andExpect(status().isOk())
	                .andExpect(content().string("true"));

	        verify(employeeService, times(1)).getOnestaffid(userId);
	    }
	    
	    
	    @Test
	    public void testGetRegisterEmpWithMail() throws Exception {
	        String userId = "123";
	        EmployeeEntity employee = new EmployeeEntity();
	        employee.setStaffId(userId);
	        RegisterInfo registerInfo = new RegisterInfo();
	        registerInfo.setEmpId(userId);

	    }
	    
	    @Test
	    public void testGetAllHolidays() throws Exception {
	        HolidaysEntity holiday1 = new HolidaysEntity();
	        holiday1.setId(1L);
	        HolidaysEntity holiday2 = new HolidaysEntity();
	        holiday2.setId(2L);
	        List<HolidaysEntity> holidaysList = Arrays.asList(holiday1, holiday2);
	     
}

	    @Test
	    public void testGetAllMeatList() throws Exception {
	    	MeatList meat1 = new MeatList();
	    	meat1.setId(null);
	    	MeatList meat2 = new MeatList();
	    	meat2.setId(null);
	    	List<MeatList> meatList = Arrays.asList(meat1, meat2);

}

	    @Test
	    public void testGetAuser() throws Exception {
	        String userId = "123";
	        RegisterInfo registerInfo = new RegisterInfo();
	        // Set up your expected RegisterInfo object
	        
	        when(registerSer.getAuser(userId)).thenReturn(registerInfo);
	        
	        mockMvc.perform(get("/getAUser/{id}", userId))
	                .andExpect(status().isOk())
	                // Add additional assertions for the response body, if needed
	                .andReturn();
	    }
	    
	    @Test
	    public void testGetRegisterInfoLunch() throws Exception {
	        String staffId = "456";
	        List<UserViewEntity> userViewList = Collections.singletonList(new UserViewEntity());
	        // Set up your expected list of UserViewEntity objects
	        
	        when(userviewService.getUserLunchInfo(staffId)).thenReturn(userViewList);
	        
	        mockMvc.perform(get("/getRegisterEatInfo/{staffId}", staffId))
	                .andExpect(status().isOk())
	                // Add additional assertions for the response body, if needed
	                .andReturn();
	    }
	    
	    
	    @Test
	    public void testGetHistoryMonthly() throws Exception {
	        String doorLog = "doorlog";
	        String monthDate = "2023-05";
	 
	        mockMvc.perform(get("/user/getuserhistoryMonthly")
	                .param("doorlog", doorLog)
	                .param("monthdate", monthDate))
	                .andExpect(status().isOk())
	                // Add additional assertions for the response body, if needed
	                .andReturn();
	    }
	    
	    
	    @Test
	    public void testGetdailyeat() throws Exception {
	        String userId = "123";
	        
	        List<DailyRegister> expectedDailyEatList = Collections.singletonList(new DailyRegister());
	        // Set up your expected list of DailyRegister objects
	        
	        when(dailyEatViewService.getAll(userId)).thenReturn(expectedDailyEatList);
	        
	        mockMvc.perform(get("/getallDailyEatView")
	                .param("userId", userId))
	                .andExpect(status().isOk())
	                // Add additional assertions for the response body, if needed
	                .andReturn();
	    }
	    
	    @Test
	    public void testGetOnedailyeat() throws Exception {
	        String doorLog = "doorlog";
	        String month = "2023-06";
	        
	        List<DailyRegister> expectedOneDailyEatList = Collections.singletonList(new DailyRegister());
	        // Set up your expected list of DailyRegister objects
	        
	        when(dailyEatViewService.getOne(doorLog, month)).thenReturn(expectedOneDailyEatList);
	        
	        mockMvc.perform(get("/user/getOneDailyEatView")
	                .param("doorlog", doorLog)
	                .param("month", month))
	                .andExpect(status().isOk())
	                // Add additional assertions for the response body, if needed
	                .andReturn();
	    }
	    
	    @Test
	    public void testGetdailydata() throws Exception {
	        String userId = "123";
	        String month = "2023-06";
	        
	        List<DailyDatePrice> expectedDailyDataList = Collections.singletonList(new DailyDatePrice());
	        // Set up your expected list of DailyDatePrice objects
	        
	        when(dailyEatViewService.getMonthlyData(userId, month)).thenReturn(expectedDailyDataList);
	        
	        mockMvc.perform(get("/user/getMonthly")
	                .param("userId", userId)
	                .param("month", month))
	                .andExpect(status().isOk())
	                // Add additional assertions for the response body, if needed
	                .andReturn();
	    }
	    
	    @Test
	    public void testSendMonthlyMail() throws Exception {
	        when(mailScheduler.sendAllEmployeeMonthly());
	        
	        mockMvc.perform(get("/user/sendMonthlyMail"))
	                .andExpect(status().isOk())
	                // Add additional assertions for the response body, if needed
	                .andReturn();
	    }


	  
	    @Test
	    public void testSendmailger() throws Exception {
	        
	        mockMvc.perform(get("/sendRegister"))
	                .andExpect(status().isOk())
	                // Add additional assertions for the response body, if needed
	                .andReturn();
	    }
	}


