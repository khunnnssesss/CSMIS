package com.dat.csmis;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dat.csmis.controller.AdminController;
import com.dat.csmis.controller.MailController;
import com.dat.csmis.entity.HolidaysEntity;
import com.dat.csmis.repository.HolidaysRepository;
import com.dat.csmis.service.HolidayService;


import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import javax.servlet.http.HttpSession;





class AdminHolidayTest {

    @Mock
    private HolidayService holidaysService;
    
    @Mock
    private HolidaysRepository hdRepo;
    
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private HolidaysEntity holidayEntity;

    @InjectMocks
    private AdminController adminController;
    
    @Mock
    private RedirectAttributes redirectAttributes;

    private MailController mailController;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mailController = new MailController();
    }

    @Test
    void testShowHolidaysAdd() {
        // Arrange
        Model model = mock(Model.class);

        // Act
        String viewName = adminController.showHolidaysAdd(model);

        // Assert
        verify(model, times(1)).addAttribute(eq("holidays"), any(HolidaysEntity.class));
        assertEquals("adminHolidayAdd", viewName);
    }

    @Test
    void testSaveHoliday() throws Exception {
        // Arrange
        MockHttpSession session = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        ModelMap model = new ModelMap();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Act
        String viewName = adminController.saveHoliday("2023-06-01", "Holiday 1", model, redirectAttributes);

        // Assert
        verify(holidaysService, times(1)).duplicateDate();
        verify(holidaysService, times(1)).deletedate("2023-06-01");
        verify(hdRepo, times(1)).save(any(HolidaysEntity.class));
        assertEquals("redirect:/admin/HolidayAdd", viewName);
    }
   
    @Test
    void testSetHolidaysList() {
    	 // Arrange
        Model model = mock(Model.class);
        List<HolidaysEntity> holidaysList = new ArrayList<>();

        when(holidaysService.getAllHolidays()).thenReturn(holidaysList);

        // Act
        String viewName = adminController.setHolidaysList(model);

        // Assert
        verify(holidaysService, times(1)).getAllHolidays();
        verify(model, times(1)).addAttribute("hlist", holidaysList);
        assertEquals("adminHolidayList", viewName);
    }
    
    

    @Test
    void testDoUpdateHolidays() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(mock(HttpSession.class));
        HolidaysEntity holidayEntity = new HolidaysEntity();
        holidayEntity.setId(1);
        when(holidaysService.findById(1)).thenReturn(Optional.of(holidayEntity));
        when(holidaysService.getDate()).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<String> response = adminController.doUpdateHolidays(request, 1, "2023-06-01", "Holiday 1");

        // Assert
        verify(holidaysService, times(1)).findById(1);
        verify(holidaysService, times(1)).saveHoliday(any(HolidaysEntity.class));
        verify(request.getSession(), times(1)).setAttribute(eq("listOfHolidays"), any(List.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully updated !", response.getBody());
    }
    
    @Test
    void testDeleteHoliday() {
        // Arrange
        Model model = mock(Model.class);

        // Act
        String viewName = adminController.deleteHoliday(1, model);
    
        verify(holidaysService, times(1)).deleteById(1);
        assertEquals("redirect:/admin/HolidaysList", viewName);
    }
}