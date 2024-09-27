package com.openlab.utilisateurs.mapper;

import com.openlab.utilisateurs.dto.UserDTO;
import com.openlab.utilisateurs.dto.UserResponseDTO;
import com.openlab.utilisateurs.entities.Role;
import com.openlab.utilisateurs.entities.User;
import com.openlab.utilisateurs.enums.ERole;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    // Mapper de userDto vers User
    public User mapToEntity(UserDTO dto){
        User user = new User();
        user.setPassword(dto.getPassword());
        user.setUsername(dto.getUsername());
        user.setRoles(dto.getRoles().stream()
                .map(roleName -> new Role(null, ERole.valueOf(roleName) ))
                .collect(Collectors.toSet()));
        return user;
    }

// mapper de User vers UserResponseDTO
    public UserResponseDTO mapToDTO( User user){
        if(user == null){
            return null;
        }
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        if(user.getRoles() != null){
            dto.setRoles(user.getRoles().stream()
                    .map(role -> role.getName().name())
                    .collect(Collectors.toSet()));
        }else {
            dto.setRoles(Collections.emptySet());
        }


        return dto;
    }
}
