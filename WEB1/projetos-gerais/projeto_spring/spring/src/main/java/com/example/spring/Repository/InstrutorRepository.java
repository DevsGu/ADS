package com.example.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring.Entities.Instrutor;

@Repository
public interface InstrutorRepository
        extends JpaRepository<Instrutor, Long> {

}