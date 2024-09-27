package com.openlab.utilisateurs.service;

import com.openlab.utilisateurs.dto.UserResponseDTO;
import com.openlab.utilisateurs.entities.User;
import com.openlab.utilisateurs.mapper.UserMapper;
import com.openlab.utilisateurs.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public UserResponseDTO register(User user){
        String password = user.getPassword();
        // Vérifiez que l'utilisateur n'est pas null
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("L'utilisateur ou ses informations obligatoires sont null");
        }
        String passwordEncoded = passwordEncoder.encode(password);
        user.setPassword(password);
        System.out.println("Mot de passe après encodage : " + user.getPassword());
        User userSaved = userRepository.save(user);

        //Vérifier si l'utilisateur est valide
        if(userSaved == null){
            throw new IllegalStateException("Utilisateur sauvegardé invalide" + user.getPassword());
        }
        return  userMapper.mapToDTO(userSaved);
    }

    public UserResponseDTO findByUsername(String username){
        return userRepository.findByUsername(username)
                .map(userMapper::mapToDTO)
                .orElse(null);
    }

    public User findEntityByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }
}
