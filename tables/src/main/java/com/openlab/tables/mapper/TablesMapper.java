package com.openlab.tables.mapper;

import com.openlab.tables.dto.TablesDTO;
import com.openlab.tables.entity.Tables;
import org.springframework.stereotype.Component;

@Component
public class TablesMapper {
    public TablesDTO mapToDTO(Tables tables){
        return TablesDTO.builder()
                .id(tables.getId())
                .numero(tables.getNumero())
                .build();

    }


    public Tables mapToEntity(TablesDTO dto){
        return Tables.builder()
                .id(dto.getId())
                .numero(dto.getNumero())
                .build();
    }
}
