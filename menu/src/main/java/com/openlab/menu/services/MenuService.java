package com.openlab.menu.services;

import com.openlab.menu.dto.MenuDTO;
import com.openlab.menu.dto.PlatDTO;

import java.util.List;

public interface MenuService {
    MenuDTO createMenu(MenuDTO dto);
    MenuDTO findMenuById(long id);
    List<MenuDTO> getAllMenu();
    MenuDTO updateMenu(MenuDTO dto);
    void deleteMenu(long id);
    MenuDTO findMenuByNom(String nom);

    // Méthodes pour gerer les plats
    PlatDTO addPlatToMenu(Long menuId, PlatDTO platDTO);
    PlatDTO updatePlat(Long menuId, PlatDTO platDTO);
    void deletePlatFromMenu(Long menuId, Long platId);

}
