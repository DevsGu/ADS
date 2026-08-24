package br.edu.ifce.retromarket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifce.retromarket.entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

  Optional<Categoria> findByNome(String nome);

  boolean existsByNome(String nome);
}
