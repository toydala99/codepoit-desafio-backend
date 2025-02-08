package com.hcs.msauth.services;

import com.hcs.msauth.entities.Users;
import com.hcs.msauth.repository.UsersRepository;
import com.hcs.msauth.services.exceptions.ForbiddenException;
import com.hcs.msauth.services.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UsersRepository repository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Users authenticated() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return (Users) repository.findByEmail(username);
        }
        catch (Exception e) {
            throw new UnauthorizedException("Invalid user");
        }
    }

    public void validateAdmin(String userId) {
        Users user = authenticated();
        if (!user.hasHole("admin")) {
            throw new ForbiddenException("Access denied");
        }
    }
    public void validateSelfOrAdmin(String userId) {
        Users user = authenticated();
        if (!user.getId().equals(userId) && !user.hasHole("admin")) {
            throw new ForbiddenException("Access denied");
        }
    }
}

