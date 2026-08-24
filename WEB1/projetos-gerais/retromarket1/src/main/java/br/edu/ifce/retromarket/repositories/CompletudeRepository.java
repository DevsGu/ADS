package br.edu.ifce.retromarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifce.retromarket.entities.Completude;

public interface CompletudeRepository extends JpaRepository<Completude, String> {
}
