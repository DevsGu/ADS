package br.edu.ifce.retromarket.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifce.retromarket.entities.Anuncio;
import br.edu.ifce.retromarket.entities.Usuario;

public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {

  List<Anuncio> findByUsuario(Usuario usuario);

  List<Anuncio> findByUsuarioId(Long usuarioId);

  List<Anuncio> findByStatusCodigo(String statusCodigo);

  List<Anuncio> findByPlataformaId(Long plataformaId);

  List<Anuncio> findByCategoriaId(Long categoriaId);
}
