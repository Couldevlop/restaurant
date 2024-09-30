package com.openlab.utilisateurs.security;

import com.openlab.utilisateurs.entities.User;
import com.openlab.utilisateurs.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new CustomUserDetails (user); // Retourne un CustomUserDetails pour Spring Security
    }


    public User registerUser(User user){
        return userRepository.save(user);
    }
}
