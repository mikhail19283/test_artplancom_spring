package com.example.test_artplancom_spring.controller;

import com.example.test_artplancom_spring.dto.AnimalDto;
import com.example.test_artplancom_spring.entity.Animal;
import com.example.test_artplancom_spring.service.AnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/animals", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping
    public List<Animal> getAnimals(){
        return animalService.getAnimals();
    }

    @GetMapping(value = "/{id}")
    public Animal getAnimalById(@PathVariable Integer id){
        return animalService.getAnimalById(id);
    }

    @GetMapping(value = "/my")
    public List<Animal> getMyAnimals(){
        return animalService.getMyAnimals();
    }

    @PostMapping
    public HttpStatus addAnimalByOwnerId(@RequestBody AnimalDto animalDto){
        return animalService.addAnimalByOwnerId(animalDto);
    }

}
