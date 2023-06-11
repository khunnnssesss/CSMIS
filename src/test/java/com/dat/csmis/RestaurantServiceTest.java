
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

import com.dat.csmis.entity.PriceEntity;
import com.dat.csmis.entity.RestaurantEntity;
import com.dat.csmis.repository.PriceRepository;
import com.dat.csmis.repository.RestaurantRepository;
import com.dat.csmis.service.RestaurantService;

class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepo;

    @Mock
    private PriceRepository priceRepo;

    @InjectMocks
    private RestaurantService restaurantService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_ShouldSaveRestaurantEntity() {
        // Arrange
        RestaurantEntity restaurantEntity = new RestaurantEntity();

        // Act
        restaurantService.save(restaurantEntity);

        // Assert
        verify(restaurantRepo, times(1)).save(restaurantEntity);
    }

    @Test
    void count_ShouldReturnCount() {
        // Arrange
        long expectedCount = 10L;
        when(restaurantRepo.count()).thenReturn(expectedCount);

        // Act
        int result = restaurantService.count();

        // Assert
        assertEquals((int) expectedCount, result);
        verify(restaurantRepo, times(1)).count();
    }

    @Test
    void updateA_ShouldUpdateRestaurantEntity() {
        // Arrange
        String active = "active";
        int id = 1;

        // Act
        restaurantService.updateA(active, id);

        // Assert
        verify(restaurantRepo, times(1)).updateA(active, id);
    }

    @Test
    void saveP_ShouldSavePriceEntity() {
        // Arrange
        PriceEntity priceEntity = new PriceEntity();

        // Act
        restaurantService.saveP(priceEntity);

        // Assert
        verify(priceRepo, times(1)).save(priceEntity);
    }

    @Test
    void updateB_ShouldUpdateRestaurantEntity() {
        // Arrange
        String inactive = "inactive";
        int id = 1;

        // Act
        restaurantService.updateB(inactive, id);

        // Assert
        verify(restaurantRepo, times(1)).updateB(inactive, id);
    }

    @Test
    void updateC_ShouldUpdateRestaurantEntity() {
        // Arrange
        String inactive = "inactive";
        int id = 1;

        // Act
        restaurantService.updateC(inactive, id);

        // Assert
        verify(restaurantRepo, times(1)).updateB(inactive, id);
    }

    @Test
    void getPriceByRestaurant_ShouldReturnRestaurantEntity() {
        // Arrange
        RestaurantEntity expectedRestaurant = new RestaurantEntity();
        when(restaurantRepo.priceByRestaurant()).thenReturn(expectedRestaurant);

        // Act
        RestaurantEntity result = restaurantService.getPriceByRestaurant();

        // Assert
        assertEquals(expectedRestaurant, result);
        verify(restaurantRepo, times(1)).priceByRestaurant();
    }

    @Test
    void selectAll_ShouldReturnRestaurantEntityList() {
        // Arrange
        List<RestaurantEntity> expectedList = new ArrayList<>();
        when(restaurantRepo.selectAll()).thenReturn(expectedList);

        // Act
        List<RestaurantEntity> result = restaurantService.selectAll();

        // Assert
        assertEquals(expectedList, result);
        verify(restaurantRepo, times(1)).selectAll();
}
    
    @Test
    void findById_ShouldReturnOptionalRestaurantEntity() {
        // Arrange
        int id = 1;
        RestaurantEntity expectedEntity = new RestaurantEntity();
        when(restaurantRepo.findById(id)).thenReturn(Optional.of(expectedEntity));

        // Act
        Optional<RestaurantEntity> result = restaurantService.findById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedEntity, result.get());
        verify(restaurantRepo, times(1)).findById(id);
    }

    @Test
    void findByIdForPrice_ShouldReturnOptionalPriceEntity() {
        // Arrange
        int id = 1;
        PriceEntity expectedEntity = new PriceEntity();
        when(priceRepo.findById(id)).thenReturn(Optional.of(expectedEntity));

        // Act
        Optional<PriceEntity> result = restaurantService.findByIdForPrice(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedEntity, result.get());
        verify(priceRepo, times(1)).findById(id);
    }

    @Test
    void save_ShouldSaveRestaurantEntityWithId() {
        // Arrange
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        int id = 1;

        // Act
        restaurantService.save(restaurantEntity, id);

        // Assert
        verify(restaurantRepo, times(1)).save(restaurantEntity);
    }

}