package com.hcs.msauth.dto;

import com.hcs.msauth.entities.UserRole;
import jakarta.validation.constraints.Email;

public record RegisterDTO(String id, String nome,
                          @Email(message="Por favor insira um email válido")
                          String email,
                          String password,
                          UserRole role) {
}
