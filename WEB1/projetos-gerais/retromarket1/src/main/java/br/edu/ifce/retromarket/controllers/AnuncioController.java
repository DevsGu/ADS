package br.edu.ifce.retromarket.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifce.retromarket.dtos.AnuncioDetalhesDTO;
import br.edu.ifce.retromarket.dtos.AnuncioRequestDTO;
import br.edu.ifce.retromarket.dtos.AnuncioResponseDTO;
import br.edu.ifce.retromarket.entities.Anuncio;
import br.edu.ifce.retromarket.entities.Completude;
import br.edu.ifce.retromarket.services.AnuncioService;

@RestController
@RequestMapping(value = "/anuncios")
public class AnuncioController {

  @Autowired
  private AnuncioService service;

  // Método para retornar a lista de
  @GetMapping("/completudes")
  public List<Completude> buscarCompletudes() {
    return service.buscarCompletudes();
  }

  @PostMapping("/completudes")
  public Completude criarCompletude(@RequestBody Completude completude) {
    return service.criarCompletude(completude);
  }

  @GetMapping
  public Page<AnuncioResponseDTO> listarAnuncios(Pageable pageable) {
    return service.listarAnuncios(pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AnuncioDetalhesDTO> buscarPorId(@PathVariable Long id) {
    Optional<AnuncioDetalhesDTO> anuncio = service.buscarPorId(id);
    return anuncio.map(a -> ResponseEntity.ok(a)).orElse(ResponseEntity.status(404).build());
  }

  @PostMapping
  public ResponseEntity<AnuncioDetalhesDTO> criarAnuncio(@RequestBody AnuncioRequestDTO anuncioDTO) {
    AnuncioDetalhesDTO anuncioCriado = service.criarAnuncio(anuncioDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(anuncioCriado);
  }

}
