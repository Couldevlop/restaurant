package com.openlab.utilisateurs.mapper;

import com.openlab.utilisateurs.dto.RoleDTO;
import com.openlab.utilisateurs.entities.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public  Role mapToEntity(RoleDTO dto){
        Role role = new Role();
        role.setId(dto.getId());
        role.setName(dto.getName());
        return role;
    }



    public  RoleDTO mapToDTO(Role role){
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        return  dto;
    }
}
