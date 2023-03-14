package com.example.dioclass.apirest.controller;


import com.example.dioclass.apirest.repository.PersonRepository;
import com.example.dioclass.apirest.model.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("persons")
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public List<Person> consultAllPersons(){
        return personRepository.findAll();
    }

    @GetMapping("/{id}")
    public Person concultById(@PathVariable Long id){
        return personRepository.findById(id).orElseThrow(() -> new RuntimeException("Erro ao buscar pessoa por id"));
    }
}
