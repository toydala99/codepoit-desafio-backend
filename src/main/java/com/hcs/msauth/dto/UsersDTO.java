package com.hcs.msauth.dto;

import com.hcs.msauth.entities.Users;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UsersDTO {
    private String id;
    private String nome;
    private String email;

    public UsersDTO() {
    }

    public UsersDTO(String id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public UsersDTO(Users users) {
        this.id = users.getId();
        this.nome = users.getNome();
        this.email = users.getEmail();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
