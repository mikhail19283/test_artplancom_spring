package com.example.test_artplancom_spring.controller;

import com.example.test_artplancom_spring.dto.AnimalDto;
import com.example.test_artplancom_spring.entity.Animal;
import com.example.test_artplancom_spring.service.AnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> addAnimalByOwnerId(@RequestBody AnimalDto animalDto){
        animalService.addAnimalByOwnerId(animalDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> putAnimalById(@PathVariable Integer id, @RequestBody AnimalDto animalDto){
        animalService.putAnimalById(id, animalDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable Integer id){
        animalService.deleteById(id);
    }

}
