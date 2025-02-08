package com.hcs.msauth.services;

import com.hcs.msauth.dto.ODSDTO;
import com.hcs.msauth.entities.ODS;
import com.hcs.msauth.repository.ODSRepository;
import com.hcs.msauth.repository.UsersRepository;
import com.hcs.msauth.services.exceptions.DataBaseException;
import com.hcs.msauth.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ODSService {
    @Autowired
    private ODSRepository odsRepository;

    @Autowired
    private AuthorizationService authService;

    @Autowired
    private UsersRepository userRepository;

    @Transactional(readOnly = true)
    public List<ODSDTO> findAll(){
        List<ODS> list = odsRepository.findAll();
        return list.stream().map(x -> new ODSDTO(x)).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public Integer findPontos(){
        return odsRepository.totalPoints();
    }
    @Transactional(readOnly = true)
    public ODSDTO findById(String id){
        Optional<ODS> obj = odsRepository.findById(id);
        ODS entity = obj.orElseThrow(()->new ResourceNotFoundException("Entity not found!"));
        return new ODSDTO(entity, entity.getUser());
    }
    @Transactional
    public ODSDTO insert(ODSDTO dto) {
        ODS entity = new ODS();
        entity.setDescription(dto.getDescription());
        entity.setTitle(dto.getTitle());
        entity.setCategory(dto.getCategory());
        entity.setPoints(dto.getPoints());
        entity.setCreatedAt(Instant.now());
        entity.setUser(authService.authenticated());
        entity = odsRepository.save(entity);
        return new ODSDTO(entity);
    }
    @Transactional
    public ODSDTO update(String id, ODSDTO dto) {
        try{
            ODS entity = odsRepository.getReferenceById(id);
            entity.setDescription(dto.getDescription());
            entity.setTitle(dto.getTitle());
            entity.setCategory(dto.getCategory());
            entity.setPoints(dto.getPoints());
            entity.setUser(authService.authenticated());
            entity = odsRepository.save(entity);
            return new ODSDTO(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found "+ id);
        }
    }
    @Transactional
    public void delete(String id) {
        try {
            odsRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found "+id);
        }
        catch(DataIntegrityViolationException e) {
            throw new DataBaseException("Violação de integridade!");
        }
    }
}
