package com.example.test_artplancom_spring.service;

import com.example.test_artplancom_spring.entity.Owner;
import com.example.test_artplancom_spring.repository.OwnerRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class CustomUserDetailService implements UserDetailsService {

    private final OwnerRepository ownerRepository;

    public CustomUserDetailService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Owner owner = ownerRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        return new User(owner.getName(), owner.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("user"));
    }

}
