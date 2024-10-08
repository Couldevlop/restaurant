package com.openlab.menu.repository;

import com.openlab.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    boolean existsByNom(String nom);
}
