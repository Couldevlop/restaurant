package com.openlab.menu.mapper;

import com.openlab.menu.dto.MenuDTO;
import com.openlab.menu.dto.PlatDTO;
import com.openlab.menu.entity.Menu;
import com.openlab.menu.entity.Plat;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MenuMapper {

    public MenuDTO mapToDTO(Menu menu){
        return MenuDTO.builder()
                .id(menu.getId())
                .nom(menu.getNom())
                .plats(menu.getPlats().stream().map(this::mapPlatToDTO).collect(Collectors.toSet()))
                .build();
    }


   public Menu mapToEntity(MenuDTO dto){
        return Menu.builder()
                .id(dto.getId())
                .nom(dto.getNom())
                .plats(dto.getPlats().stream().map(this::mapPlatDtoToEntity).collect(Collectors.toSet()))
                .build();
    }



   public  PlatDTO mapPlatToDTO(Plat plat){
        return PlatDTO.builder()
                .id(plat.getId())
                .nom(plat.getNom())
                .prix(plat.getPrix())
                .description(plat.getDescription())
                .build();
    }


   public Plat mapPlatDtoToEntity(PlatDTO dto){
        return Plat.builder()
                .id(dto.getId())
                .nom(dto.getNom())
                .prix(dto.getPrix())
                .description(dto.getDescription())
                .build();
    }


}
