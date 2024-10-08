package com.openlab.menu;

import com.openlab.menu.dto.MenuDTO;
import com.openlab.menu.entity.Menu;
import com.openlab.menu.entity.Plat;
import com.openlab.menu.mapper.MenuMapper;
import com.openlab.menu.repository.MenuRepository;
import com.openlab.menu.services.MenuService;
import com.openlab.menu.services.MenuServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MenuServiceTest {
    @Mock
    private MenuRepository menuRepository;
    @Mock
    private MenuMapper menuMapper ;
    @InjectMocks
    private MenuServiceImpl menuService;

    Menu menu;
    MenuDTO menuDTO;
    Plat plat;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        Set<Plat> platSet = new HashSet<>();
        Plat plat = new Plat(1L, "salade au oeufs", 15.0,"salade espagnol + oeuf de volaille élévé en plain air");
        platSet.add(plat);
        menu=new Menu();
        menu.setId(1L);
        menu.setNom("Entrée");
        menu.setPlats(platSet);
        menuDTO = new MenuDTO();
        menuDTO.setId(2L);
        menuDTO.setNom("Entree");
        menuDTO.setPlats(null);
    }

    @Test
    void createMenuTest(){
        when(menuMapper.mapToEntity(any(MenuDTO.class))).thenReturn(menu);
       when(menuRepository.save(any(Menu.class))).thenReturn(menu);
       when(menuMapper.mapToDTO(any(Menu.class))).thenReturn(menuDTO);

        MenuDTO result = menuService.createMenu(menuDTO);

        assertNotNull(result);
        verify(menuRepository, times(1)).save(any(Menu.class));
    }
}
