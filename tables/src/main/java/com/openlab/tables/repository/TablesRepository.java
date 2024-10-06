package com.openlab.tables.repository;

import com.openlab.tables.entity.Tables;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TablesRepository extends JpaRepository<Tables, Long> {

    boolean existsByNumero(long numero);
}
