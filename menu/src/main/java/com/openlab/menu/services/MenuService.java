package com.openlab.menu.services;

import com.openlab.menu.dto.MenuDTO;

import java.util.List;

public interface MenuService {
    MenuDTO createMenu(MenuDTO dto);
    MenuDTO findMenuById(long id);
    List<MenuDTO> getAllMenu();
    MenuDTO updateMenu(MenuDTO dto);
    MenuDTO deleteMenu(long id);
    MenuDTO findMenuByNom(String nom);

}
