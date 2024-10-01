package com.openlab.utilisateurs.web;

import com.openlab.utilisateurs.dto.LoginRequest;
import com.openlab.utilisateurs.dto.UserDTO;
import com.openlab.utilisateurs.dto.UserResponseDTO;
import com.openlab.utilisateurs.entities.User;
import com.openlab.utilisateurs.mapper.UserMapper;
import com.openlab.utilisateurs.security.JwtUtil;
import com.openlab.utilisateurs.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;


    public UserController(UserService userService, JwtUtil jwtUtil, UserMapper userMapper, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserDTO dto){
       //Mapper UserDto en User
        User user = userMapper.mapToEntity(dto);
        user.setEnabled(true);

        //Appelle du service pour l'enregistrer l'utilisateur et retourner un UserResponseDto
        UserResponseDTO savedUserResponse = userService.register(user);
        return  ResponseEntity.ok(savedUserResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        }catch (AuthenticationException e){
            return  ResponseEntity.status(401).body("invalid credentials");
        }

        String token = jwtUtil.generateToken(loginRequest.getUsername());
        return ResponseEntity.ok(token);
    }


    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.findAll());
    }
}
