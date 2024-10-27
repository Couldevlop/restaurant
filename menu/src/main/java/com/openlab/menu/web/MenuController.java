package com.openlab.menu.web;

import com.openlab.menu.dto.MenuDTO;
import com.openlab.menu.dto.PlatDTO;
import com.openlab.menu.exception.MenuNotFoundException;
import com.openlab.menu.exception.MenuObjectIllegalArgumentException;
import com.openlab.menu.repository.MenuRepository;
import com.openlab.menu.services.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }


    @PostMapping()
    public ResponseEntity<Object> create(@RequestBody MenuDTO dto){
        try {
            return  ResponseEntity.ok(menuService.createMenu(dto));
        }catch (MenuObjectIllegalArgumentException ex){
            throw new MenuObjectIllegalArgumentException(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<MenuDTO>> getAll(){
        return   ResponseEntity.ok(menuService.getAllMenu());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> findMenuById(@PathVariable long id){
        try {
            return ResponseEntity.ok(menuService.findMenuById(id));
        }catch (MenuNotFoundException ex){
            throw new MenuNotFoundException(ex.getMessage());
        }

    }

    @PutMapping
    public ResponseEntity<MenuDTO> updateMenuDTO(@RequestBody MenuDTO dto){
        return  ResponseEntity.ok(menuService.updateMenu(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMenuDTO(@PathVariable long id) {
        menuService.deleteMenu(id);
        return ResponseEntity.ok("Le Menu avec l'id: " + id);
    }

    // Endpoint pour ajouter un plat à un menu existant
    @PostMapping("/{menuId}/plats")
    public ResponseEntity<PlatDTO> addPlatToMenu(@PathVariable Long menuId, @RequestBody PlatDTO platDTO) {
        return ResponseEntity.ok(menuService.addPlatToMenu(menuId, platDTO));
    }

    // Endpoint pour modifier un plat d'un menu
    @PutMapping("/{menuId}/plats")
    public ResponseEntity<PlatDTO> updatePlatInMenu(@PathVariable Long menuId, @RequestBody PlatDTO platDTO) {
        return ResponseEntity.ok(menuService.updatePlat(menuId, platDTO));
    }

    // Endpoint pour supprimer un plat d'un menu
    @DeleteMapping("/{menuId}/plats/{platId}")
    public ResponseEntity<String> deletePlatFromMenu(@PathVariable Long menuId, @PathVariable Long platId) {
        menuService.deletePlatFromMenu(menuId, platId);
        return ResponseEntity.ok("Le plat avec l'id " + platId + " a été supprimé du menu " + menuId);
    }


    @PutMapping("/{menuId}/plats/{platId}")
    public ResponseEntity<PlatDTO> updatePlatInMenu(@PathVariable long menuId, @PathVariable long platId, @RequestBody PlatDTO platDTO) {
        platDTO.setId(platId); // Assurez-vous que l'ID est correct
        PlatDTO updatedPlat = menuService.updatePlatInMenu(menuId, platDTO);
        return ResponseEntity.ok(updatedPlat);
    }

}
