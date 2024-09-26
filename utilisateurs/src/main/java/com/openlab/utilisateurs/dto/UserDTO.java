package com.openlab.utilisateurs.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
        private String username;
        private String password;
        private Set<String> roles;
}
