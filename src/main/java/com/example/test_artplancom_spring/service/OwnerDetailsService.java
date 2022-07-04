package com.example.test_artplancom_spring.service;

import com.example.test_artplancom_spring.dto.OwnerDto;
import com.example.test_artplancom_spring.entity.Owner;
import com.example.test_artplancom_spring.repository.OwnerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class OwnerDetailsService implements UserDetailsService {

    private final OwnerRepository ownerRepository;
    private PasswordEncoder passwordEncoder;

    public OwnerDetailsService(OwnerRepository ownerRepository, PasswordEncoder passwordEncoder) {
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Owner owner = ownerRepository.findByName(username)
                .orElseThrow(()-> new UsernameNotFoundException("Пользователь не найден"));
        return new User(owner.getName(), owner.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("user"));
    }

    public HttpStatus registrationOwner(OwnerDto ownerDto) {
        Owner owner = new Owner(ownerDto.getName(), passwordEncoder.encode(ownerDto.getPassword()));
        Optional<Owner> ownerFromDb = ownerRepository.findByName(ownerDto.getName());
        if (ownerFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Пользователь с таким именем уже существует");
        }
        ownerRepository.save(owner);
        return HttpStatus.CREATED;
    }

}
