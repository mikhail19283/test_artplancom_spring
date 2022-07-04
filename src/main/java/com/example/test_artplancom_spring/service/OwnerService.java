package com.example.test_artplancom_spring.service;

import com.example.test_artplancom_spring.dto.OwnerDto;
import com.example.test_artplancom_spring.entity.Owner;
import com.example.test_artplancom_spring.repository.OwnerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;

    public OwnerService(OwnerRepository ownerRepository, PasswordEncoder passwordEncoder) {
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registrationOwner(OwnerDto ownerDto) {
        Owner owner = new Owner(ownerDto.getName(), passwordEncoder.encode(ownerDto.getPassword()));
        Optional<Owner> ownerFromDb = ownerRepository.findByName(ownerDto.getName());
        if (ownerFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Пользователь с таким именем уже существует");
        }
        ownerRepository.save(owner);
    }

}
