package com.dat.csmis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.dat.csmis.controller.AdminController;
import com.dat.csmis.entity.OrderListEntity;
import com.dat.csmis.entity.RestaurantEntity;
import com.dat.csmis.service.OrderListService;
import com.dat.csmis.service.UserViewService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AdminOrderTest {

    @Mock
    private OrderListService orderListService;
    
    @Mock
    private OrderListEntity orderlistEntity;
    
    @Mock
    private RestaurantEntity restaurantEntity;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    
    
    @Test
    public void testDisplayDeleteOrder() {
        // Mock the necessary dependencies and data
        Integer id = 1;
        Model model = mock(Model.class);
        OrderListEntity orderListEntity = new OrderListEntity();
        orderListEntity.setId(id);
        Optional<OrderListEntity> orderListOptional = Optional.of(orderListEntity);

        // Mock the behavior of the dependencies
        when(orderListService.getFindOne(id)).thenReturn(orderListOptional);
        when(orderListService.save(any(OrderListEntity.class))).thenReturn(orderListEntity);

        // Call the controller method
        String viewName = adminController.displayDeleteOrder(id, model);

        // Assert the expected results
        assertEquals("redirect:/admin/weeklyOrderList", viewName);
        verify(model, never()).addAttribute(any(), any());

        // Verify the orderListService.save() method is called with the expected OrderListEntity object
        verify(orderListService).save(argThat(order -> {
            assertEquals(id, order.getId());
            assertEquals("InActive", order.getIsDelete());
            return true;
        }));
    }

    @Test
    public void testDisplayDeleteOrderWithInvalidId() {
        // Mock the necessary dependencies and data
        Integer id = 1;
        Model model = mock(Model.class);
        Optional<OrderListEntity> orderListOptional = Optional.empty();

        // Mock the behavior of the dependencies
        when(orderListService.getFindOne(id)).thenReturn(orderListOptional);

        // Call the controller method
        String viewName = adminController.displayDeleteOrder(id, model);

        // Assert the expected results
        assertEquals("redirect:/admin/weeklyOrderList", viewName);
        verify(model, never()).addAttribute(any(), any());
        verify(orderListService, never()).save(any(OrderListEntity.class));
    }
    
    @Test
    public void testShowOrderVouncher() {
        // Mock the necessary dependencies and data
        Model model = mock(Model.class);
        Integer id = 1;
        String vid = "ABC123";
        OrderListEntity orderListEntity = new OrderListEntity();
        orderListEntity.setId(id);
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setName("Restaurant Name");
        orderListEntity.setRestaurantEntity(restaurantEntity);
        orderListEntity.setCreatedAt("2023-05-30");
        orderListEntity.setTotalPeople(5);
        orderListEntity.setTotalPrice(100);

        Optional<OrderListEntity> orderDetailOptional = Optional.of(orderListEntity);

        // Mock the behavior of the dependencies
        when(orderListService.getFindOne(id)).thenReturn(orderDetailOptional);

        // Call the controller method
        String viewName = adminController.showOrderVouncher(model, id, vid);

        // Assert the expected results
        assertEquals("OrderVouncher", viewName);
        verify(model).addAttribute("orderDetail", orderListEntity);
        verify(model).addAttribute("createdAt", "2023-05-30");
        verify(model).addAttribute("peopleCount", 5);
        verify(model).addAttribute("totalPrice", 100);
        verify(model).addAttribute("vid", "ABC123");
        verify(model).addAttribute("id", 1);
        verify(model).addAttribute("resName", "Restaurant Name");
    }


    
}
