package com.example.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring.Entities.Curso;

@Repository
public interface CursoRepository
        extends JpaRepository<Curso, Long> {

}