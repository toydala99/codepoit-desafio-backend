package com.hcs.msauth.controller;

import com.hcs.msauth.dto.AuthenticationDTO;
import com.hcs.msauth.dto.LoginResponseDTO;
import com.hcs.msauth.dto.RegisterDTO;
import com.hcs.msauth.entities.UserRole;
import com.hcs.msauth.entities.Users;
import com.hcs.msauth.repository.UsersRepository;
import com.hcs.msauth.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsersRepository repository;
    @Autowired
    private TokenService tokenService;

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var emailPassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(emailPassword);

        var token = tokenService.generateToken((Users)auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Users newUser = new Users(data.id(),data.nome(),data.email(),encryptedPassword, UserRole.USER);

        this.repository.save(newUser);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(uri).body(newUser);
    }

}