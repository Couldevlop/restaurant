package com.openlab.utilisateurs;

import com.openlab.utilisateurs.dto.LoginRequest;
import com.openlab.utilisateurs.dto.UserDTO;
import com.openlab.utilisateurs.dto.UserResponseDTO;
import com.openlab.utilisateurs.entities.User;
import com.openlab.utilisateurs.mapper.UserMapper;
import com.openlab.utilisateurs.security.JwtUtil;
import com.openlab.utilisateurs.service.UserService;
import com.openlab.utilisateurs.web.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;


@SpringBootTest
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    private UserDTO userDTO;
    private UserResponseDTO userResponseDTO;
    private LoginRequest loginRequest;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setPassword("password");
        userDTO.setRoles(Set.of("ROLE_USER"));

        userResponseDTO = new UserResponseDTO(1L, "testuser", Set.of("ROLE_USER"));

        loginRequest = new LoginRequest("testuser", "password");

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
    }

    @Test
    public void testRegisterUser() {
        // Mock the behavior of the mapper and service
        when(userMapper.mapToEntity(any(UserDTO.class))).thenReturn(user);
        when(userService.register(any(User.class))).thenReturn(userResponseDTO);

        // Call the controller method
        ResponseEntity<UserResponseDTO> response = userController.registerUser(userDTO);

        // Verify the response
        assertNotNull(response.getBody());
        assertEquals(OK, response.getStatusCode());
        assertEquals("testuser", response.getBody().getUsername());
        assertTrue(response.getBody().getRoles().contains("ROLE_USER"));

        // Verify the interactions
        verify(userMapper, times(1)).mapToEntity(any(UserDTO.class));
        verify(userService, times(1)).register(any(User.class));
    }

    @Test
    public void testLoginSuccess() {
        // Simuler une authentification réussie
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);  // Authentification réussie

        // Simuler la génération d'un token
        when(jwtUtil.generateToken(anyString())).thenReturn("mocked-jwt-token");

        // Appel de la méthode du contrôleur
        ResponseEntity<String> response = userController.login(loginRequest);

        // Vérification que le token est bien retourné
        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals("mocked-jwt-token", response.getBody());

        // Vérification des interactions
        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, times(1)).generateToken(anyString());
    }

    @Test
    public void testLoginFailure() {
        // Simule une BadCredentialsException au lieu de AuthenticationException
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));
        // Call the controller method
        ResponseEntity<String> response = userController.login(loginRequest);

        // Verify the response
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid credentials", response.getBody());

        // Verify the interactions
        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, never()).generateToken(anyString());
    }
}
