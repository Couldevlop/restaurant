package com.openlab.utilisateurs.web;

import com.openlab.utilisateurs.dto.RoleDTO;
import com.openlab.utilisateurs.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @PostMapping
    public ResponseEntity<RoleDTO> save(@RequestBody RoleDTO dto){
        return ResponseEntity.ok(roleService.addRole(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> findById(@PathVariable long id){
        return  ResponseEntity.ok(roleService.findById(id));
    }


    @PutMapping
    public ResponseEntity<RoleDTO> updateRole(@RequestBody RoleDTO role){
        return  ResponseEntity.ok(roleService.updateRole(role));
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRole(){
        return  ResponseEntity.ok(roleService.getRoleList());
    }
}
