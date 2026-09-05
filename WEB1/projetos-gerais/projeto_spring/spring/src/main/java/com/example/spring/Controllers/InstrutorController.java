package com.example.spring.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.Entities.Instrutor;
import com.example.spring.Repository.InstrutorRepository;

@RestController
@RequestMapping("/instrutores")
public class InstrutorController {

    @Autowired
    private InstrutorRepository repository;

    @GetMapping
    public List<Instrutor> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Instrutor salvar(@RequestBody Instrutor instrutor) {
        return repository.save(instrutor);
    }

}