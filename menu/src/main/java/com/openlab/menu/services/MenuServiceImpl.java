package com.openlab.menu.services;

import com.openlab.menu.dto.MenuDTO;
import com.openlab.menu.entity.Menu;
import com.openlab.menu.exception.MenuAlreadyExistsException;
import com.openlab.menu.exception.MenuNotFoundException;
import com.openlab.menu.exception.MenuObjectIllegalArgumentException;
import com.openlab.menu.mapper.MenuMapper;
import com.openlab.menu.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService{
    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    public MenuServiceImpl(MenuRepository menuRepository, MenuMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
    }

    @Override
    public MenuDTO createMenu(MenuDTO dto) {
        if(dto == null){
            throw new MenuObjectIllegalArgumentException("L'object Menu{} est null");
        }
        if(menuRepository.existsByNom(dto.getNom())){
            throw new MenuAlreadyExistsException("Le nom du menu existe déjà");
        }
        Menu menu = menuRepository.save(menuMapper.mapToEntity(dto));
        return menuMapper.mapToDTO(menu);
    }

    @Override
    public MenuDTO findMenuById(long id) {
        if(id == 0){
            throw new MenuObjectIllegalArgumentException("L'id fourni est null");
        }
    Menu menu = menuRepository.findById(id).orElseThrow(()-> new MenuNotFoundException("Le menu avec l'id " + id + " n'existe pas"));
        return menuMapper.mapToDTO(menu);
    }

    @Override
    public List<MenuDTO> getAllMenu() {
        return menuRepository.findAll().stream().map(menuMapper::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public MenuDTO updateMenu(MenuDTO dto) {
       Optional<Menu> menuDTOOptional = menuRepository.findById(dto.getId());
       if(menuDTOOptional.isEmpty()){
          throw new MenuNotFoundException("Aucun menu n'a été trouvé avec l'id: " + dto.getId());
       }
        Menu oldMenu = menuDTOOptional.get();
        oldMenu.setId(dto.getId());
        oldMenu.setNom(dto.getNom());
        oldMenu.setPlats(dto.getPlats().stream().map(dto1 -> menuMapper.mapPlatToDTO(dto1)));
        Menu menuSaved = menuRepository.save(oldMenu);
        return menuMapper.mapToDTO(menuSaved);
    }

    @Override
    public MenuDTO deleteMenu(long id) {
        return null;
    }

    @Override
    public MenuDTO findMenuByNom(String nom) {
        return null;
    }
}
