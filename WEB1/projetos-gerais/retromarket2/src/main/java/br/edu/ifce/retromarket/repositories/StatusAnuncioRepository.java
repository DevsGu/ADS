package br.edu.ifce.retromarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifce.retromarket.entities.StatusAnuncio;

public interface StatusAnuncioRepository extends JpaRepository<StatusAnuncio, String> {
}
