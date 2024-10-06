package com.openlab.utilisateurs.service;

import com.openlab.utilisateurs.dto.RoleDTO;
import com.openlab.utilisateurs.entities.Role;
import com.openlab.utilisateurs.mapper.RoleMapper;
import com.openlab.utilisateurs.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private  final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    //Méthodepour ajouter un role
    public RoleDTO addRole(RoleDTO dto){
        if(dto != null){
            Role role = roleMapper.mapToEntity(dto);
            return roleMapper.mapToDTO(roleRepository.save(role)) ;
        }else {
            throw new IllegalArgumentException(" L'objet Role{} est null");
        }
    }

    //Méthode qui retourne la liste de tous les roles
    public List<RoleDTO> getRoleList(){
        return roleRepository.findAll().stream().map(roleMapper::mapToDTO).collect(Collectors.toList());
    }

// Méthode pour rechercher un role par son id
    public RoleDTO findById(long id){
        if(id == 0){
            throw  new IllegalArgumentException("L'id fournit est null");
        }

        Role role = roleRepository.findById(id).orElseThrow(() -> new FindException("Aucun élément ne correspond à votre recher"));
        return  roleMapper.mapToDTO(role);
    }


    // Méthode pour mettre à jour le role
    public RoleDTO updateRole(RoleDTO dto){
       Role lastRole = roleMapper.mapToEntity(dto);
       lastRole.setId(dto.getId());
       lastRole.setName(dto.getName());
       return addRole(roleMapper.mapToDTO(lastRole));
    }
}
