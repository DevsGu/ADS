package br.edu.ifce.retromarket.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifce.retromarket.entities.Favorito;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

  List<Favorito> findByUsuarioId(Long usuarioId);

  Optional<Favorito> findByUsuarioIdAndAnuncioId(Long usuarioId, Long anuncioId);

  boolean existsByUsuarioIdAndAnuncioId(Long usuarioId, Long anuncioId);
}
