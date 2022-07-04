package com.example.test_artplancom_spring.dto;

public class OwnerDto {

    private String name;
    private String password;

    public OwnerDto(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

}
