package com.openlab.menu.services;

import com.openlab.menu.dto.MenuDTO;
import com.openlab.menu.entity.Menu;
import com.openlab.menu.entity.Plat;
import com.openlab.menu.exception.MenuAlreadyExistsException;
import com.openlab.menu.exception.MenuNotFoundException;
import com.openlab.menu.exception.MenuObjectIllegalArgumentException;
import com.openlab.menu.mapper.MenuMapper;
import com.openlab.menu.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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
        if(menuRepository.existsByNom(dto.getNom()).isPresent()){
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

       // construire un set de plat
       Set<Plat> updatePlat = dto.getPlats().stream().map(platDTO->{
          return Plat.builder()
                   .id(platDTO.getId())
                   .prix(platDTO.getPrix())
                   .description(platDTO.getDescription())
                   .build();
       }).collect(Collectors.toSet());
        oldMenu.setId(dto.getId());
        oldMenu.setNom(dto.getNom());
        oldMenu.setPlats(updatePlat);
        Menu menuSaved = menuRepository.save(oldMenu);
        return menuMapper.mapToDTO(menuSaved);
    }

    @Override
    public void deleteMenu(long id) {
        if(id == 0){
            throw new MenuObjectIllegalArgumentException("L'id fourni est null");
        }
        if(menuRepository.findById(id).isEmpty()){
            throw new MenuNotFoundException("Aucun menu ne correspond à l'id fourni");
        }
         menuRepository.deleteById(id);
    }

    @Override
    public MenuDTO findMenuByNom(String nom) {
        if(nom == null){
            throw new MenuObjectIllegalArgumentException("le nom renseigné est null " );
        }
        Optional<Menu> menuOptional = menuRepository.existsByNom(nom);
        if(menuOptional.isPresent()){
            Menu menu = menuOptional.get();
            return menuMapper.mapToDTO(menu);
        }else {
            throw new MenuNotFoundException("Aucun menu ne correspon au nom: " + nom);
        }

    }
}
