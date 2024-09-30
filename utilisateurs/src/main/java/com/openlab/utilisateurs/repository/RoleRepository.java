package com.openlab.utilisateurs.repository;

import com.openlab.utilisateurs.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
