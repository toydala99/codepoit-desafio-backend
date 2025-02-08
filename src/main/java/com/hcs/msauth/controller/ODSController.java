package com.hcs.msauth.controller;

import com.hcs.msauth.dto.ODSDTO;
import com.hcs.msauth.entities.ODS;
import com.hcs.msauth.services.ODSService;
import com.hcs.msauth.services.UsersServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping(value = "/ods")
@RestController
public class ODSController {
    @Autowired
    private ODSService ODSServices;

    @Autowired
    private UsersServices userSevice;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<ODSDTO>> findAll(
    ){
        List<ODSDTO> list = ODSServices.findAll();
        for (ODSDTO ods: list){
            String id = ods.getId();
            ods.add(linkTo(methodOn(ODSController.class).findById(id)).withSelfRel());
        }
        return ResponseEntity.ok().body(list);
    }
    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<ODSDTO> findById(@PathVariable String id){
        ODSDTO dto = ODSServices.findById(id);
        dto.add(linkTo(methodOn(ODSController.class).findAll()).withRel("Lista dos ODS feitos"));
        return ResponseEntity.ok().body(dto);
    }
    @CrossOrigin
    @GetMapping(value = "/pontos")
    public Integer findPontos(){
        return ODSServices.findPontos();
    }
    @CrossOrigin
    @PostMapping
    public ResponseEntity<ODSDTO> insert(@Valid @RequestBody ODSDTO dto){
        dto = ODSServices.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<ODSDTO> update(@PathVariable String id, @Valid @RequestBody ODSDTO dto){
        dto = ODSServices.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }
    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteODS(@PathVariable String id) {
        ODSServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
