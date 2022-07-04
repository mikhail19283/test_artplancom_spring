package com.example.test_artplancom_spring.entity;

import javax.persistence.*;

@Entity
@Table(name = "animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String birthday;
    private String sex;
    private String alias;
    private Integer idOwner;

    public Animal() {
    }

    public Animal(String birthday, String sex, String alias, Integer idOwner) {
        this.birthday = birthday;
        this.sex = sex;
        this.alias = alias;
        this.idOwner = idOwner;
    }

    public Integer getId() {
        return id;
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
