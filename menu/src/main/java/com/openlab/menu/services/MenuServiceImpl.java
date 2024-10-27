package com.openlab.menu.services;

import com.openlab.menu.dto.MenuDTO;
import com.openlab.menu.dto.PlatDTO;
import com.openlab.menu.entity.Menu;
import com.openlab.menu.entity.Plat;
import com.openlab.menu.exception.MenuAlreadyExistsException;
import com.openlab.menu.exception.MenuNotFoundException;
import com.openlab.menu.exception.MenuObjectIllegalArgumentException;
import com.openlab.menu.exception.PlatNotFoundException;
import com.openlab.menu.mapper.MenuMapper;
import com.openlab.menu.repository.MenuRepository;
import com.openlab.menu.utils.PlatComparator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService{
    private final MenuRepository menuRepository;
    private final PlatComparator platComparator;
    private final MenuMapper menuMapper;

    public MenuServiceImpl(MenuRepository menuRepository, PlatComparator platComparator, MenuMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.platComparator = platComparator;
        this.menuMapper = menuMapper;
    }

    @Override
    public MenuDTO createMenu(MenuDTO dto) {
        if(dto == null){
            throw new MenuObjectIllegalArgumentException("L'object Menu{} est null");
        }
        if(menuRepository.existsByNom(dto.getNom()).isEmpty()){
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
        Optional<Menu> menuOptional = menuRepository.findById(dto.getId());
        if (menuOptional.isEmpty()) {
            throw new MenuNotFoundException("Aucun menu n'a été trouvé avec l'id: " + dto.getId());
        }

        Menu existingMenu = menuOptional.get();

        // Mise à jour des attributs de Menu uniquement
        existingMenu.setNom(dto.getNom());

        // Comparer l'état actuel des plats avec ceux dans le DTO
        if (dto.getPlats() != null && !dto.getPlats().isEmpty()) {
            boolean platsChanged = platComparator.havePlatsChanged(existingMenu.getPlats(), dto.getPlats());

            // Si la collection a changé, la mettre à jour
            if (platsChanged) {
                updatePlats(existingMenu, dto);
            }
        }

        // Sauvegarder le menu avec ou sans les plats mis à jour
        Menu savedMenu = menuRepository.save(existingMenu);

        return menuMapper.mapToDTO(savedMenu);
    }


    // Mise à jour de la collection de plats si nécessaire
    private void updatePlats(Menu existingMenu, MenuDTO dto) {
        if (dto.getPlats() != null && !dto.getPlats().isEmpty()) {
            Set<Plat> updatedPlats = dto.getPlats().stream().map(platDTO -> {
                Plat plat = existingMenu.getPlats().stream()
                        .filter(p -> p.getId().equals(platDTO.getId()))
                        .findFirst()
                        .orElse(null); // Ne crée pas un nouveau plat si l'ID n'existe pas

                if (plat == null) {
                    // Si le plat n'existe pas, en créer un nouveau
                    plat = new Plat();
                }

                plat.setNom(platDTO.getNom());
                plat.setPrix(platDTO.getPrix());
                plat.setDescription(platDTO.getDescription());
                plat.setMenu(existingMenu); // Associer le plat au menu

                return plat;
            }).collect(Collectors.toSet());

            // Mettre à jour la collection des plats sans la vider complètement
            existingMenu.getPlats().retainAll(updatedPlats);
            existingMenu.getPlats().addAll(updatedPlats);
        }
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


    // Méthode pour ajouter un plat à un menu
    @Override
    public PlatDTO addPlatToMenu(Long menuId, PlatDTO platDTO) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new MenuNotFoundException("Le menu avec l'id " + menuId + " n'existe pas"));
        Plat plat = menuMapper.mapPlatDtoToEntity(platDTO);
        plat.setMenu(menu);
        menu.getPlats().add(plat);
        menuRepository.save(menu);
        return menuMapper.mapPlatToDTO(plat);
    }

    // Méthode pour modifier un plat existant
    @Override
    public PlatDTO updatePlat(Long menuId, PlatDTO platDTO) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new MenuNotFoundException("Le menu avec l'id " + menuId + " n'existe pas"));

        Plat plat = menu.getPlats().stream()
                .filter(p -> p.getId().equals(platDTO.getId()))
                .findFirst()
                .orElseThrow(() -> new MenuNotFoundException("Le plat avec l'id " + platDTO.getId() + " n'existe pas dans ce menu"));

        plat.setNom(platDTO.getNom());
        plat.setPrix(platDTO.getPrix());
        plat.setDescription(platDTO.getDescription());

        menuRepository.save(menu);
        return menuMapper.mapPlatToDTO(plat);
    }

    // Mise à jour de la collection de plats si nécessaire

    // Méthode pour supprimer un plat d'un menu
    @Override
    public void deletePlatFromMenu(Long menuId, Long platId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new MenuNotFoundException("Le menu avec l'id " + menuId + " n'existe pas"));

        Plat platToRemove = menu.getPlats().stream()
                .filter(p -> p.getId().equals(platId))
                .findFirst()
                .orElseThrow(() -> new MenuNotFoundException("Le plat avec l'id " + platId + " n'existe pas dans ce menu"));

        menu.getPlats().remove(platToRemove);
        menuRepository.save(menu);
    }


    @Override
    public PlatDTO updatePlatInMenu(long menuId, PlatDTO platDTO) {
        Optional<Menu> menuOptional = menuRepository.findById(menuId);
        if (menuOptional.isEmpty()) {
            throw new MenuNotFoundException("Aucun menu n'a été trouvé avec l'id: " + menuId);
        }

        Menu menu = menuOptional.get();
        Optional<Plat> platOptional = menu.getPlats().stream()
                .filter(plat -> plat.getId().equals(platDTO.getId()))
                .findFirst();

        if (platOptional.isEmpty()) {
            throw new PlatNotFoundException("Le plat n'existe pas pour le menu spécifié.");
        }

        Plat platToUpdate = platOptional.get();
        platToUpdate.setNom(platDTO.getNom());
        platToUpdate.setPrix(platDTO.getPrix());
        platToUpdate.setDescription(platDTO.getDescription());

        // Mettre à jour le plat dans la collection du menu
        menuRepository.save(menu);

        return menuMapper.mapPlatToDTO(platToUpdate);
    }

}
