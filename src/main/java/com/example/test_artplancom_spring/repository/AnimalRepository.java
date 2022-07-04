package com.example.test_artplancom_spring.repository;

import com.example.test_artplancom_spring.entity.Animal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepository extends CrudRepository<Animal, Integer> {

    @Override
    List<Animal> findAll();

    List<Animal> findByIdOwner(Integer id);

    Optional<Animal> findByAlias(String alias);
}
