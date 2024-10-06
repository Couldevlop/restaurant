package com.openlab.utilisateurs.dto;

import com.openlab.utilisateurs.entities.Role;
import com.openlab.utilisateurs.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private Long id;
    private ERole name;
}
