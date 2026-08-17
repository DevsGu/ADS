package com.example.spring.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring.Entities.Aula;
import com.example.spring.Entities.Instrutor;

@Repository
public interface AulaRepository
        extends JpaRepository<Aula, Long> {

            List<Instrutor> findByNomeContaining(String nome);


}
