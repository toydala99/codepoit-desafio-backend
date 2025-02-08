package com.hcs.msauth.services;

import com.hcs.msauth.dto.UsersDTO;
import com.hcs.msauth.entities.Users;
import com.hcs.msauth.repository.UsersRepository;
import com.hcs.msauth.services.exceptions.DataBaseException;
import com.hcs.msauth.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class UsersServices {
    @Autowired
    private UsersRepository repository;

    @Autowired
    private AuthorizationService authService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Transactional(readOnly= true)
    public Page<UsersDTO> findAllPaged(PageRequest pageRequest){
        Page<Users> listUsers = repository.findAll(pageRequest);

        return listUsers.map(UsersDTO::new);
    }
    @Transactional(readOnly = true)
    public UsersDTO findByID(String id) {
        authService.validateAdmin(id);//Testando autorização
        Optional<Users> user= repository.findById(id);
        Users entity = user.orElseThrow(()->new ResourceNotFoundException("Usuario Não encontrado"));
        return new UsersDTO(entity);
    }
    @Transactional(readOnly = true)
    public UsersDTO perfil() {
        Users entity = authService.authenticated();
        return new UsersDTO(entity);
    }
    @Transactional
    public UsersDTO insertUsers(UsersDTO user) {
        Users entity = new Users();
        entity.setNome(user.getNome());
        entity.setEmail(user.getEmail());
        entity.setCreatedAt(Instant.now());
        entity=repository.save(entity);
        return new UsersDTO(entity);
    }
    @Transactional
    public UsersDTO update(String id, UsersDTO user) {
        try {
            Users entity = repository.getReferenceById(id);
            entity.setNome(user.getNome());
            entity.setEmail(user.getEmail());
            entity=repository.save(entity);
            return new UsersDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }
    @Transactional
    public void delete(String id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found "+id);
        }
        catch(DataIntegrityViolationException e) {
            throw new DataBaseException("Violação de integridade!");
        }
    }

}
