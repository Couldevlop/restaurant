package com.openlab.utilisateurs;

import com.openlab.utilisateurs.dto.UserDTO;
import com.openlab.utilisateurs.dto.UserResponseDTO;
import com.openlab.utilisateurs.entities.Role;
import com.openlab.utilisateurs.entities.User;
import com.openlab.utilisateurs.enums.ERole;
import com.openlab.utilisateurs.mapper.UserMapper;
import com.openlab.utilisateurs.repository.UserRepository;
import com.openlab.utilisateurs.service.UserService;
import com.openlab.utilisateurs.web.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;  // Mock du PasswordEncoder

    private User user;
    private UserDTO userDTO;
    private UserResponseDTO userResponseDTO;



    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        Set<Role> listeRole = new HashSet<>();
        Role role = new Role();
        role.setId(1L);
        role.setName(ERole.ROLE_CLIENT);
        listeRole.add(role);

        Role role1 = new Role();
        role1.setId(1L);
        role1.setName(ERole.ROLE_SERVER);
        listeRole.add(role1);


        Role role2 = new Role();
        role2.setId(1L);
        role2.setName(ERole.ROLE_SUPERVISOR);
        listeRole.add(role2);

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("coulwao@gmail.com");
        user.setRoles(listeRole);


        /*userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setPassword("password");*/

        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUsername("testuser");

    }


    @Test
    public void testRegisterUser(){
        // Simuler l'encodage du mot de passe
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Simuler l'enregistrement de l'utilisateur
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Simuler le mapping de l'utilisateur vers un DTO
        when(userMapper.mapToDTO(any(User.class))).thenReturn(userResponseDTO);

        // Appel de la méthode à tester
        UserResponseDTO response;
        response = userService.register(user);

        // Vérification des résultats
       assertNotNull(response);  // Assure que la réponse n'est pas null
        assertEquals("testuser", response.getUsername());  // Vérifie que le username est correct

        // Vérifier que les mocks ont été appelés
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).mapToDTO(any(User.class));
    }
}
