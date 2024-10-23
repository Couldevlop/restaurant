package com.openlab.menu.services;

import com.openlab.menu.dto.MenuDTO;
import com.openlab.menu.dto.PlatDTO;
import com.openlab.menu.entity.Menu;
import com.openlab.menu.entity.Plat;
import com.openlab.menu.exception.MenuAlreadyExistsException;
import com.openlab.menu.exception.MenuNotFoundException;
import com.openlab.menu.exception.MenuObjectIllegalArgumentException;
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

        // Vérifier si la collection de plats doit être modifiée
        if (dto.getPlats() != null && !dto.getPlats().isEmpty()) {
            boolean platsChanged = platComparator.havePlatsChanged(existingMenu.getPlats(), dto.getPlats());

            if (platsChanged) {
                // Mettre à jour la collection des plats seulement si nécessaire
                updatePlats(existingMenu, dto);
            }
        }

        // Sauvegarder le menu sans toucher aux plats s'ils n'ont pas changé
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
                        .orElse(null); // Retourner null si le plat n'existe pas

                if (plat == null) {
                    // Si le plat n'existe pas, créer un nouveau plat
                    plat = new Plat();
                    plat.setMenu(existingMenu);
                }

                // Mettre à jour les attributs du plat seulement si l'objet existe ou est créé
                plat.setNom(platDTO.getNom());
                plat.setPrix(platDTO.getPrix());
                plat.setDescription(platDTO.getDescription());

                return plat;
            }).collect(Collectors.toSet());

            // Remplacer la collection des plats uniquement si elle a changé
            existingMenu.getPlats().clear();
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
}
