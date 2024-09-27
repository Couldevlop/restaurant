package com.openlab.utilisateurs.web;

import com.openlab.utilisateurs.dto.UserDTO;
import com.openlab.utilisateurs.dto.UserResponseDTO;
import com.openlab.utilisateurs.entities.User;
import com.openlab.utilisateurs.mapper.UserMapper;
import com.openlab.utilisateurs.security.JwtUtil;
import com.openlab.utilisateurs.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    public UserController(UserService userService, JwtUtil jwtUtil, UserMapper userMapper, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userMapper = userMapper;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserDTO dto){
       //Mapper UserDto en User
        User user = userMapper.mapToEntity(dto);
        user.setEnabled(true);

        //Appelle du service pour l'enregistrer l'utilisateur et retourner un UserResponseDto
        UserResponseDTO savedUserResponse = userService.register(user);
        return  ResponseEntity.ok(savedUserResponse);
    }
}
