package com.openlab.tables.service;

import com.openlab.tables.dto.TablesDTO;

import java.util.List;

public interface TablesService {
    TablesDTO createTable(TablesDTO dto);
    List<TablesDTO> getAll();
    TablesDTO findById(long id);
    TablesDTO update(TablesDTO dto);
    void delete(long id);
    boolean numberIsAlredyUsed(long id);
}
