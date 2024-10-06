package com.openlab.tables.service;

import com.openlab.tables.dto.TablesDTO;
import com.openlab.tables.entity.Tables;
import com.openlab.tables.exception.TablesNotFoundException;
import com.openlab.tables.exception.TablesObjectIllegalArgumentException;
import com.openlab.tables.mapper.TablesMapper;
import com.openlab.tables.repository.TablesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TablesServiceImpl implements TablesService{
    private final TablesRepository tablesRepository;
    private final TablesMapper tablesMapper;

    public TablesServiceImpl(TablesRepository tablesRepository, TablesMapper tablesMapper) {
        this.tablesRepository = tablesRepository;
        this.tablesMapper = tablesMapper;
    }

    @Override
    public TablesDTO createTable(TablesDTO dto) {
        if(dto != null){
            Tables tables = tablesRepository.save(tablesMapper.mapToEntity(dto));
            return tablesMapper.mapToDTO(tables);
        }else {
          throw   new  TablesObjectIllegalArgumentException(" L'objet Tables {} fourni est null ou le numéro de la table existe déjà");
        }

    }

    @Override
    public List<TablesDTO> getAll() {
        return tablesRepository.findAll().stream().map(tablesMapper::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public TablesDTO findById(long id) {
        if(id != 0){
            Tables tables = tablesRepository.findById(id).orElseThrow(()-> new TablesNotFoundException("Aucune table n'a été trouvée"));
            return tablesMapper.mapToDTO(tables);
        }else {
            throw new TablesObjectIllegalArgumentException("L'id est null");
        }

    }

    @Override
    public TablesDTO update(TablesDTO dto) {
        if(dto != null){
            TablesDTO tablesDTO = findById(dto.getId());
            return createTable(tablesDTO);
        }else {
            throw new TablesObjectIllegalArgumentException("L'objet Tables {} fourni est null");
        }

    }

    @Override
    public void delete(long id) {
      if(id != 0){
          TablesDTO tablesDTO = findById(id);
          tablesRepository.deleteById(tablesDTO.getId());
      }else {

      }
    }

    @Override
    public boolean numberIsAlredyUsed(long numero) {
        if(tablesRepository.existsByNumero(numero)){
            return false;
        }
        return true;
    }
}
