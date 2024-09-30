package com.openlab.utilisateurs;

import com.openlab.utilisateurs.dto.UserResponseDTO;
import com.openlab.utilisateurs.entities.Role;
import com.openlab.utilisateurs.entities.User;
import com.openlab.utilisateurs.enums.ERole;
import com.openlab.utilisateurs.mapper.UserMapper;
import com.openlab.utilisateurs.repository.RoleRepository;
import com.openlab.utilisateurs.repository.UserRepository;
import com.openlab.utilisateurs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class UtilisateursApplication implements CommandLineRunner {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserService userService;


	private User user;
	private UserResponseDTO userResponseDTO;
    @Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(UtilisateursApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
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
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setId(1L);
		user.setUsername("testuser");
		user.setPassword("password");
		user.setEmail("coulwao@gmail.com");
		user.setRoles(listeRole);

		//userRepository.save(user);
	UserResponseDTO userSaved = userService.register(user);
		System.out.println("+++++++++++" + userSaved.getUsername());

	}
}
