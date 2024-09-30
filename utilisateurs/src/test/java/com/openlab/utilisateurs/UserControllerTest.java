package com.openlab.utilisateurs;

import com.openlab.utilisateurs.dto.UserResponseDTO;
import com.openlab.utilisateurs.entities.Role;
import com.openlab.utilisateurs.entities.User;
import com.openlab.utilisateurs.enums.ERole;
import com.openlab.utilisateurs.mapper.UserMapper;
import com.openlab.utilisateurs.repository.RoleRepository;
import com.openlab.utilisateurs.repository.UserRepository;
import com.openlab.utilisateurs.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
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
    private PasswordEncoder passwordEncoder;

    private User user;
    private UserResponseDTO userResponseDTO;
    @Mock
    private RoleRepository roleRepository;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Créer des rôles et les associer à l'utilisateur
        Set<Role> listeRole = new HashSet<>();
        Role roleClient = new Role();
        roleClient.setId(1L);
        roleClient.setName(ERole.ROLE_CLIENT);
        roleRepository.save(roleClient);
        listeRole.add(roleClient);

        Role roleServer = new Role();
        roleServer.setId(2L);
        roleServer.setName(ERole.ROLE_SERVER);
        roleRepository.save(roleServer);
        listeRole.add(roleServer);

        Role roleSupervisor = new Role();
        roleSupervisor.setId(3L);
        roleSupervisor.setName(ERole.ROLE_SUPERVISOR);
        roleRepository.save(roleSupervisor);
        listeRole.add(roleSupervisor);

        // Initialiser un utilisateur
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("coulwao@gmail.com");
        user.setRoles(listeRole);

        // Créer un UserResponseDTO correspondant
        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUsername("testuser");
    }

    @Test
    public void testRegisterUser() {
        // Simuler l'encodage du mot de passe
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Simuler l'enregistrement de l'utilisateur
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Simuler le mapping de l'utilisateur vers un DTO
        when(userMapper.mapToDTO(any(User.class))).thenReturn(userResponseDTO);

        // Appel de la méthode à tester
        UserResponseDTO response = userService.register(user);

        // Vérification des résultats
        assertNotNull(response);  // Vérifie que la réponse n'est pas nulle
        assertEquals("testuser", response.getUsername());  // Vérifie que le nom d'utilisateur est correct

        // Vérifier que les mocks ont été appelés
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).mapToDTO(any(User.class));
    }
}
