package com.dat.csmis;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dat.csmis.controller.AdminController;
import com.dat.csmis.entity.PriceEntity;
import com.dat.csmis.entity.RestaurantEntity;
import com.dat.csmis.model.RestaurantModel;
import com.dat.csmis.repository.RestaurantRepository;
import com.dat.csmis.service.RestaurantService;




	public class AdminRestaurantTest {
	
    private MockMvc mockMvc;
    
    @Mock
    private Model model;
    
    @Mock
    private RestaurantRepository restaurantRepository;
    
    @Mock
    private RestaurantModel restaurantModel;
    
    @Mock
    private RestaurantEntity restaurantEntity;
    
    @Mock
    private RestaurantService serviceR;
    
    @Mock
    private PriceEntity priceEntity;

	@InjectMocks
    private AdminController adminController;
	
	@Mock
    private RedirectAttributes redirectAttributes;
    
    @Captor
    private ArgumentCaptor<String> stringCaptor;
    
    @Captor
    private ArgumentCaptor<RestaurantEntity> restaurantEntityCaptor;
    
    @Captor
    private ArgumentCaptor<Double> doubleCaptor;
	
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }
    
    @Test
    public void testResturant_ShouldReturnAdminRestaurantViewWithRestaurantModel() {
        // Act
        ModelAndView modelAndView = adminController.resturant();
        
        // Assert
        assertEquals("adminRestaurant", modelAndView.getViewName());
        assertEquals(RestaurantModel.class, modelAndView.getModel().get("res").getClass());
    }
    
    @Test
    public void testDoRestaurant() {
        // Mocking data
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setName("Test Restaurant");
        restaurantModel.setEmail("test@example.com");
        restaurantModel.setPhone("1234567890");
        restaurantModel.setReceiveBy("John Doe");
        restaurantModel.setCost(100);
        restaurantModel.setAddress("123 Main St");
        
        Double datPrice = 10.0;
        Double empPrice = 20.0;
        
        RestaurantEntity savedRestaurantEntity = new RestaurantEntity();
        savedRestaurantEntity.setId(1);
        savedRestaurantEntity.setName("Test Restaurant");
        savedRestaurantEntity.setEmail("test@example.com");
        savedRestaurantEntity.setPhone("1234567890");
        savedRestaurantEntity.setReceiveBy("John Doe");
        savedRestaurantEntity.setTotalPrice(100);;
        savedRestaurantEntity.setAddress("123 Main St");
        savedRestaurantEntity.setStatus("InActive");
        
        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setDatPrice(datPrice);
        priceEntity.setEmpPrice(empPrice);
        savedRestaurantEntity.setPrice(priceEntity);
        
        // Mocking service methods
       
        Mockito.when(serviceR.count()).thenReturn(1);
        
        // Perform the method invocation
        String viewName = adminController.doRestaurant(restaurantModel, datPrice, empPrice, redirectAttributes, model);
        
        // Assertions
       
        
        Mockito.verify(model).addAttribute("datPrice", datPrice);
        Mockito.verify(model).addAttribute("empPrice", empPrice);
        Mockito.verify(serviceR).save(restaurantEntityCaptor.capture());
        RestaurantEntity capturedRestaurantEntity = restaurantEntityCaptor.getValue();
        
        assertEquals("redirect:/admin/res", viewName);
        
        assertEquals("Test Restaurant", capturedRestaurantEntity.getName());
        assertEquals("test@example.com", capturedRestaurantEntity.getEmail());
        assertEquals("1234567890", capturedRestaurantEntity.getPhone());
        assertEquals("John Doe", capturedRestaurantEntity.getReceiveBy());
        assertEquals(100, capturedRestaurantEntity.getTotalPrice());
        assertEquals("123 Main St", capturedRestaurantEntity.getAddress());
        assertEquals("InActive", capturedRestaurantEntity.getStatus());
        assertEquals(datPrice, capturedRestaurantEntity.getPrice().getDatPrice());
        assertEquals(empPrice, capturedRestaurantEntity.getPrice().getEmpPrice());
        
        
        Mockito.verify(redirectAttributes).addAttribute("success", "true");
    }
    
   
    
    @Test
    public void testResList() {
        // Mocking data
        List<RestaurantEntity> restaurantList = new ArrayList<>();
        restaurantList.add(new RestaurantEntity());
        restaurantList.add(new RestaurantEntity());
        
        // Mocking service method
        Mockito.when(serviceR.selectAll()).thenReturn(restaurantList);

        // Perform the method invocation
        String viewName = adminController.resList(model);

        // Assertions
        assertEquals("adminRestaurantList", viewName);
        Mockito.verify(model).addAttribute("resList", restaurantList);
    }

	}
