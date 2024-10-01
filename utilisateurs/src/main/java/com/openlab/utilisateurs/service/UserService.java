package com.openlab.utilisateurs.service;

import com.openlab.utilisateurs.dto.UserResponseDTO;
import com.openlab.utilisateurs.entities.User;
import com.openlab.utilisateurs.mapper.UserMapper;
import com.openlab.utilisateurs.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final  PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserResponseDTO register(User user) {

        PasswordEncoder passEncod = new BCryptPasswordEncoder();
        // Encoder le mot de passe de l'utilisateur
        user.setPassword(passEncod.encode(user.getPassword()));

        // Enregistrer l'utilisateur dans la base de données
        User savedUser = userRepository.save(user);

        // Mapper l'utilisateur vers le DTO de réponse
        return userMapper.mapToDTO(savedUser);
    }

    public UserResponseDTO findByUsername(String username){
        return userRepository.findByUsername(username)
                .map(userMapper::mapToDTO)
                .orElse(null);
    }

    public User findEntityByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }

    public List<UserResponseDTO> findAll(){
       return userRepository.findAll().stream().map(userMapper::mapToDTO).collect(Collectors.toList());
    }
}
