package com.openlab.menu.services;

import com.openlab.menu.dto.MenuDTO;

import java.util.List;

public interface MenuService {
    MenuDTO createMenu(MenuDTO dto);
    MenuDTO findMenuById(long id);
    List<MenuDTO> getAllMenu();
    MenuDTO updateMenu(MenuDTO dto);
    void deleteMenu(long id);
    MenuDTO findMenuByNom(String nom);

}
