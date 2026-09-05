package br.edu.ifce.retromarket.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifce.retromarket.entities.FotoAnuncio;

public interface FotoAnuncioRepository extends JpaRepository<FotoAnuncio, Long> {

  List<FotoAnuncio> findByAnuncioIdOrderByOrdemAsc(Long anuncioId);
}
