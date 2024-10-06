package com.openlab.tables.web;

import com.openlab.tables.dto.TablesDTO;
import com.openlab.tables.exception.TablesObjectIllegalArgumentException;
import com.openlab.tables.service.TablesService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.lang.module.FindException;
import java.util.List;

@RequestMapping("/api/tables")
@RestController
public class TablesController {
    private final TablesService tablesService;

    public TablesController(TablesService tablesService) {
        this.tablesService = tablesService;
    }

    @PostMapping
    public ResponseEntity<TablesDTO> createTables(@RequestBody TablesDTO dto){
        try{
            return  ResponseEntity.ok(tablesService.createTable(dto));
        }catch (TablesObjectIllegalArgumentException e){
            ErrorResponse errorResponse = new ErrorResponse() {
                @Override
                public HttpStatusCode getStatusCode() {
                    return null;
                }

                @Override
                public ProblemDetail getBody() {
                    return null;
                }
            };

        }


    }

    @GetMapping
    public ResponseEntity<List<TablesDTO>>getAll(){
        return ResponseEntity.ok(tablesService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TablesDTO> getById(@PathVariable long id){
     return ResponseEntity.ok(tablesService.findById(id));
    }


    @PutMapping
    public ResponseEntity<TablesDTO> update(@RequestBody TablesDTO tablesDTO){
        TablesDTO dto = tablesService.update(tablesDTO);
        if(tablesDTO != null){
            return ResponseEntity.ok(tablesDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTable(@PathVariable long id){
        tablesService.delete(id);
        return ResponseEntity.ok("La table avec l'id: " + id + "a été supprimé avec succès");
    }
}
