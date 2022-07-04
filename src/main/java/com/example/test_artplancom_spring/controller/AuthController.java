package com.example.test_artplancom_spring.controller;

import com.example.test_artplancom_spring.dto.OwnerDto;
import com.example.test_artplancom_spring.service.OwnerDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private final OwnerDetailsService ownerService;

    public AuthController(OwnerDetailsService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping(value = "/registration")
    public HttpStatus registration(@RequestBody OwnerDto ownerDto){
        return ownerService.registrationOwner(ownerDto);
    }

}
