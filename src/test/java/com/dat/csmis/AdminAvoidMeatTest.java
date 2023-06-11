package com.dat.csmis;
   
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dat.csmis.controller.AdminController;
import com.dat.csmis.entity.MeatList;
import com.dat.csmis.service.AvoidMS;
import com.dat.csmis.service.AvoidMeatService;

@ExtendWith(MockitoExtension.class)
public class AdminAvoidMeatTest {

    @Mock
    private AvoidMeatService avoidMeatService;
    
    @Mock
    private AvoidMS avoidMS;
    
    @Mock
    private MeatList meatList;

    @InjectMocks
    private AdminController adminController;

    @Mock
    private Model model;

    @Mock
    private ModelMap modelMap;

    @Mock
    private RedirectAttributes redirectAttributes;

    private List<MeatList> avoidMeatList;

    @BeforeEach
    public void setup() {
        avoidMeatList = Arrays.asList(new MeatList(), new MeatList());
        meatList = new MeatList();
        meatList.setId(1);
        meatList.setMeat("Chicken");
        meatList.setIsDelete("Active");
    }

    @Test
    public void testShowAvoidMeatAdd() {
        // Arrange
        when(avoidMS.getAllAvoidMeat()).thenReturn(avoidMeatList);

        // Act
        String viewName = adminController.showAvoidMeatAdd(model);

        // Assert
        verify(avoidMS, times(1)).getAllAvoidMeat();
        verify(model, times(1)).addAttribute(eq("AvoidMeatList"), eq(avoidMeatList));
        verify(model, times(1)).addAttribute(eq("avoidMeat"), any(MeatList.class));
        assertEquals("adminAvoidMeat", viewName);
    }

    @Test
    public void testSaveAvoidMeat() {
        // Arrange
        String avoidMeat = "Chicken";
        when(avoidMS.getAllAvoidMeat()).thenReturn(avoidMeatList);
        when(avoidMS.duplicateAvoidMeat()).thenReturn(Arrays.asList("Beef", "Pork"));

        // Act
        String viewName = adminController.saveAvoidMeat(avoidMeat, modelMap, redirectAttributes);

        // Assert
        verify(avoidMS, times(1)).getAllAvoidMeat();
        verify(avoidMS, times(1)).duplicateAvoidMeat();
        verify(avoidMS, times(1)).save(any(MeatList.class));
        verify(modelMap, times(1)).addAttribute(eq("avoidMeat"), eq(avoidMeat));
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("success"), eq("Avoid meat data insert is successfully!"));
        assertEquals("redirect:/admin/AvoidMeat", viewName);
    }
    
    
    @Test
    public void testUpdateAvoidMeat() {
        // Arrange
        Integer id = 1;
        when(avoidMS.findById(id)).thenReturn(Optional.of(meatList));

        // Act
        ResponseEntity<String> response = adminController.updateAvoidMeat(id);

        // Assert
        verify(avoidMS, times(1)).findById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Chicken", response.getBody());
    }

    @Test
    public void testUpdateAvoidMeat_NotFound() {
        // Arrange
        Integer id = 1;
        when(avoidMS.findById(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = adminController.updateAvoidMeat(id);

        // Assert
        verify(avoidMS, times(1)).findById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

  
    @Test
    public void testDeleteAvoidMeat() {
        // Arrange
        Integer id = 1;
        when(avoidMS.findById(id)).thenReturn(Optional.of(meatList));

        // Act
        String viewName = adminController.deleteAvoidMeat(id, model);

        // Assert
        verify(avoidMS, times(1)).findById(id);
        verify(avoidMS, times(1)).save(any(MeatList.class));
        assertEquals("redirect:/admin/AvoidMeat", viewName);
    }

    @Test
    public void testCreateAvoidMeat() {
        // Arrange
        MeatList avoidMeat = new MeatList();
        avoidMeat.setMeat("Pork");
        when(avoidMS.createAvoidMeat(any(MeatList.class))).thenReturn(avoidMeat);

        // Act
        MeatList createdAvoidMeat = adminController.createAvoidMeat(avoidMeat);

        // Assert
        verify(avoidMS, times(1)).createAvoidMeat(avoidMeat);
        assertEquals(avoidMeat, createdAvoidMeat);
    }
    
    
}