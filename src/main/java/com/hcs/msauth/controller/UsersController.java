package com.hcs.msauth.controller;

import com.hcs.msauth.dto.UsersDTO;
import com.hcs.msauth.services.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/user")
@RestController
public class UsersController {
    @Autowired
    private UsersServices services;
    @CrossOrigin
    @GetMapping
    public ResponseEntity<Page<UsersDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy
    ){

        PageRequest pageRequest=PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction),orderBy);
        Page<UsersDTO> list = services.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(list);
    }
    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<UsersDTO> findByID(@PathVariable String id) {
        UsersDTO dto = services.findByID(id);
        return ResponseEntity.ok(dto);
    }
    @CrossOrigin
    @GetMapping(value = "/perfil")
    public ResponseEntity<UsersDTO> perfil() {
        UsersDTO dto = services.perfil();
        return ResponseEntity.ok(dto);
    }
    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<UsersDTO> updateUser(@PathVariable String id, @RequestBody UsersDTO dto) {
        dto = services.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }
    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        services.delete(id);
        return ResponseEntity.noContent().build();
    }
}
