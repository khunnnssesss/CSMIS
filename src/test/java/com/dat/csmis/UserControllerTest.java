package com.dat.csmis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dat.csmis.controller.UserController;
import com.dat.csmis.entity.EmployeeEntity;
import com.dat.csmis.entity.FeedbackEntity;
import com.dat.csmis.entity.MenuEntity;
import com.dat.csmis.entity.RegisterInfo;
import com.dat.csmis.entity.UserViewEntity;
import com.dat.csmis.repository.EmployeeRepository;
import com.dat.csmis.repository.UserViewRepository;
import com.dat.csmis.service.EmployeeService;
import com.dat.csmis.service.FeedbackService;
import com.dat.csmis.service.MenuService;
import com.dat.csmis.service.RegisterService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.test.web.servlet.MockMvc;


public class UserControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @Mock
    private MenuService menuService;

    @InjectMocks
    private UserController userController;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private RegisterService registerService;
    
    @Mock
    private FeedbackService feedbackService;

    @Mock
	UserViewRepository uvRepo;
    
    @Mock
	private EmployeeRepository emp;
    
    @Mock
	EmployeeService serviceE;
    
    @Mock
	RegisterService registerSer;
    
    @Mock
	MenuService serviceM;
    
    @Mock
    private EmployeeEntity employeeEntity;

    @Mock
    private FeedbackEntity feedbackEntity;
    
    @Mock
    private RegisterInfo registerInfo;

    @Mock
    private UserViewRepository userViewRepository;

    @Mock
    private Model model;
    
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    
    
    
    
    @Test
    public void testRegisterPage() {
        // Prepare test data
        String username = "testuser";
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setDoorLog("doorlog");
        employeeEntity.setStaffId("staffId");
        employeeEntity.setRole("role");

        RegisterInfo registerInfo = new RegisterInfo();
        List<UserViewEntity> allUserViewEntities = new ArrayList<>();
        List<UserViewEntity> reDateUserViewEntities = new ArrayList<>();
        List<UserViewEntity> rneDateUserViewEntities = new ArrayList<>();
        List<UserViewEntity> ueDateUserViewEntities = new ArrayList<>();

        Principal principal = new Principal() {
            @Override
            public String getName() {
                return username;
            }
        };

        Model model = new ConcurrentModel();

        when(emp.getByEmail(username)).thenReturn(employeeEntity);
        when(serviceE.getOneEmp(username)).thenReturn(employeeEntity);
        when(registerSer.checkUserStaff(employeeEntity.getStaffId())).thenReturn(true);
        when(registerService.getdateList("26-99917")).thenReturn(registerInfo);
        when(uvRepo.findAll()).thenReturn(allUserViewEntities);
        when(uvRepo.findREdate(employeeEntity.getDoorLog())).thenReturn(reDateUserViewEntities);
        when(uvRepo.findRNEdate(employeeEntity.getDoorLog())).thenReturn(rneDateUserViewEntities);
        when(uvRepo.findUEdate(employeeEntity.getDoorLog())).thenReturn(ueDateUserViewEntities);

        // Perform the request
        String result = userController.registerPage(principal, model);

        // Verify the interactions and assertions
        assertEquals("userRegister", result);
        assertEquals(employeeEntity.getDoorLog(), model.getAttribute("doorlog"));
        assertEquals(employeeEntity.getStaffId(), model.getAttribute("empID"));
        assertEquals(employeeEntity.getStaffId(), model.getAttribute("userId"));
        assertEquals(employeeEntity.getDoorLog(), model.getAttribute("userDoorlog"));
        assertTrue((boolean) model.getAttribute("registered"));
        assertEquals(employeeEntity.getRole(), model.getAttribute("userType"));
        assertEquals(allUserViewEntities, model.getAttribute("doorLogNo"));
        assertEquals(reDateUserViewEntities, model.getAttribute("RElists"));
        assertEquals(rneDateUserViewEntities, model.getAttribute("RNElists"));
        assertEquals(ueDateUserViewEntities, model.getAttribute("UElists"));

        verify(emp).getByEmail(username);
        verify(serviceE).getOneEmp(username);
        verify(registerSer).checkUserStaff(employeeEntity.getStaffId());
        verify(registerService).getdateList("26-99917");
        verify(uvRepo).findAll();
        verify(uvRepo).findREdate(employeeEntity.getDoorLog());
        verify(uvRepo).findRNEdate(employeeEntity.getDoorLog());
        verify(uvRepo).findUEdate(employeeEntity.getDoorLog());
    }


   
    @Test
    public void testShowAboutUs() {
        String result = userController.showAboutUs(model);
        assertEquals("userAboutUs", result);
        verifyNoInteractions(model);
    }
    
    
    @Test
    public void testDisplayFeedbackPage() {
        String result = userController.displayFeedbackPage(model);
        assertEquals("userFeedBack", result);
        verify(model).addAttribute(eq("feedback"), any(FeedbackEntity.class));
    }

    @Test
    public void testDisplayFeedbackSubmit() {
        String feedback = "This is a test feedback";
        FeedbackEntity savedFeedback = new FeedbackEntity();
        savedFeedback.setFeedback(feedback);


        String result = userController.displayFeedbackSubmit(feedback, model);
        assertEquals("userFeedBack", result);
        verify(feedbackService).save(any(FeedbackEntity.class));
        verify(model).addAttribute("message", "true");
    }
    
   

}

