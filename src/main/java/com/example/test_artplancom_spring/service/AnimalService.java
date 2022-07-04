package com.example.test_artplancom_spring.service;

import com.example.test_artplancom_spring.dto.AnimalDto;
import com.example.test_artplancom_spring.entity.Animal;
import com.example.test_artplancom_spring.repository.AnimalRepository;
import com.example.test_artplancom_spring.repository.OwnerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final OwnerRepository ownerRepository;

    public AnimalService(AnimalRepository animalRepository, OwnerRepository ownerRepository) {
        this.animalRepository = animalRepository;
        this.ownerRepository = ownerRepository;
    }

    public List<Animal> getAnimals() {
        return animalRepository.findAll();
    }

    public Animal getAnimalById(Integer id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Животное по данному id отсутствует"));
    }

    public List<Animal> getMyAnimals() {
        return animalRepository.findByIdOwner(getIdByName());
    }

    public void addAnimalByOwnerId(AnimalDto animalDto) {
        if (animalRepository.findByAlias(animalDto.getAlias()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Животное с данной кличкой уже существует");
        }
        Animal animal = new Animal(animalDto.getBirthday(), animalDto.getSex(), animalDto.getAlias(), getIdByName());
        animalRepository.save(animal);
    }

    public void putAnimalById(Integer id, AnimalDto animalDto) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Животного с таким id не существует"));
        animal.setAlias(animalDto.getAlias());
        animal.setBirthday(animalDto.getBirthday());
        animal.setSex(animalDto.getSex());
        animalRepository.save(animal);
    }

    public Integer getIdByName() {
        String owner = SecurityContextHolder.getContext().getAuthentication().getName();
        return ownerRepository.findByName(owner).get().getId();
    }

    public void deleteById(Integer id) {
        try {
            animalRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Питомца с таким id не существует");
        }
    }

}
