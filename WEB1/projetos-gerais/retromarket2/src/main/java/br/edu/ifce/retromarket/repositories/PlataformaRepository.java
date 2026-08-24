package br.edu.ifce.retromarket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifce.retromarket.entities.Plataforma;

public interface PlataformaRepository extends JpaRepository<Plataforma, Long> {

  Optional<Plataforma> findByNome(String nome);

  boolean existsByNome(String nome);
}
