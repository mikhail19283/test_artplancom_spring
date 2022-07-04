package com.example.test_artplancom_spring.dto;

public class AnimalDto {

    private final String birthday;
    private final String sex;
    private final String alias;

    public AnimalDto(String birthday, String sex, String alias) {
        this.birthday = birthday;
        this.sex = sex;
        this.alias = alias;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getSex() {
        return sex;
    }

    public String getAlias() {
        return alias;
    }
}
