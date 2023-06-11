package com.dat.csmis;

import com.dat.csmis.entity.MenuEntity;
import com.dat.csmis.repository.MenuRepository;
import com.dat.csmis.service.MenuService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MenuServiceTest {

    @Mock
    private MenuRepository repo;

    @InjectMocks
    private MenuService menuService;

    public MenuServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSelect() {
        MenuEntity mockMenu = new MenuEntity();

        when(repo.select()).thenReturn(mockMenu);

        MenuEntity result = menuService.select();

        assertEquals(mockMenu, result);
    }
}
