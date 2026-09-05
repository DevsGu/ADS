package br.edu.ifce.retromarket.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.ifce.retromarket.dtos.AnuncioDetalhesDTO;
import br.edu.ifce.retromarket.dtos.AnuncioRequestDTO;
import br.edu.ifce.retromarket.dtos.AnuncioResponseDTO;
import br.edu.ifce.retromarket.dtos.CategoriaResumoDTO;
import br.edu.ifce.retromarket.dtos.CompletudeDTO;
import br.edu.ifce.retromarket.dtos.CondicaoDTO;
import br.edu.ifce.retromarket.dtos.PlataformaDTO;
import br.edu.ifce.retromarket.dtos.PlataformaResumoDTO;
import br.edu.ifce.retromarket.dtos.StatusDTO;
import br.edu.ifce.retromarket.entities.Anuncio;
import br.edu.ifce.retromarket.entities.Categoria;
import br.edu.ifce.retromarket.entities.Completude;
import br.edu.ifce.retromarket.entities.Condicao;
import br.edu.ifce.retromarket.entities.FotoAnuncio;
import br.edu.ifce.retromarket.entities.Plataforma;
import br.edu.ifce.retromarket.entities.StatusAnuncio;
import br.edu.ifce.retromarket.entities.Usuario;
import br.edu.ifce.retromarket.repositories.AnuncioRepository;
import br.edu.ifce.retromarket.repositories.CategoriaRepository;
import br.edu.ifce.retromarket.repositories.CompletudeRepository;
import br.edu.ifce.retromarket.repositories.CondicaoRepository;
import br.edu.ifce.retromarket.repositories.FotoAnuncioRepository;
import br.edu.ifce.retromarket.repositories.PlataformaRepository;
import br.edu.ifce.retromarket.repositories.StatusAnuncioRepository;
import br.edu.ifce.retromarket.repositories.UsuarioRepository;

@Service
public class AnuncioService {

  @Autowired
  private AnuncioRepository anuncioRepository;

  @Autowired
  private CompletudeRepository completudeRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private PlataformaRepository plataformaRepository;

  @Autowired
  private CategoriaRepository categoriaRepository;

  @Autowired
  private CondicaoRepository condicaoRepository;

  @Autowired
  private StatusAnuncioRepository statusRepository;

  @Autowired
  private FotoAnuncioRepository fotoAnuncioRepository;

  public Page<AnuncioResponseDTO> listarAnuncios(Pageable pageable) {
    Page<Anuncio> anuncios = anuncioRepository.findAll(pageable);

    Page<AnuncioResponseDTO> pageDTO = anuncios.map(this::toAnuncioReponseDTO);

    return pageDTO;

  }

  public Optional<AnuncioDetalhesDTO> buscarPorId(Long id) {
    Optional<Anuncio> anuncio = anuncioRepository.findById(id);
    return anuncio.map(this::toAnuncioDTO);
  }

  public AnuncioDetalhesDTO criarAnuncio(AnuncioRequestDTO anuncioDTO) {
    Anuncio anuncio = toAnuncioEntity(anuncioDTO);
    Anuncio anuncioCriado = anuncioRepository.save(anuncio);

    return toAnuncioDTO(anuncioCriado);
  }

  public Completude criarCompletude(Completude completude) {
    return completudeRepository.save(completude);
  }

  public List<Completude> buscarCompletudes() {
    return completudeRepository.findAll();
  }

  private AnuncioResponseDTO toAnuncioReponseDTO(Anuncio anuncio) {
    AnuncioResponseDTO anuncioResponseDTO = new AnuncioResponseDTO();

    anuncioResponseDTO.setId(anuncio.getId());
    anuncioResponseDTO.setTitulo(anuncio.getTitulo());
    anuncioResponseDTO.setPreco(anuncio.getPreco());
    anuncioResponseDTO.setLocalizacao(anuncio.getLocalizacao());

    anuncioResponseDTO.setCondicao(anuncio.getCondicao().getDescricao());
    anuncioResponseDTO.setCompletude(anuncio.getCompletude().getDescricao());
    anuncioResponseDTO.setStatus(anuncio.getStatus().getDescricao());

    if (!anuncio.getFotos().isEmpty()) {
      anuncioResponseDTO.setUrlFotoPrincipal(anuncio.getFotos().getFirst().getUrl());
    }

    anuncioResponseDTO
        .setPlataforma(new PlataformaResumoDTO(anuncio.getPlataforma().getId(), anuncio.getPlataforma().getNome()));
    anuncioResponseDTO
        .setCategoria(new CategoriaResumoDTO(anuncio.getCategoria().getId(), anuncio.getCategoria().getNome()));

    return anuncioResponseDTO;

  }

  private AnuncioDetalhesDTO toAnuncioDTO(Anuncio anuncio) {
    AnuncioDetalhesDTO anuncioDTO = new AnuncioDetalhesDTO();

    anuncioDTO.setId(anuncio.getId());
    anuncioDTO.setTitulo(anuncio.getTitulo());
    anuncioDTO.setDescricao(anuncio.getDescricao());
    anuncioDTO.setPreco(anuncio.getPreco());
    anuncioDTO.setLocalizacao(anuncio.getLocalizacao());
    anuncioDTO.setDataPublicacao(anuncio.getDataPublicacao().toLocalDate());

    anuncioDTO.setUrlsFotos(anuncio.getFotos()
        .stream()
        .map(fontoAnuncio -> fontoAnuncio.getUrl())
        .toList()); // Stream API, Expressões Lambda

    anuncioDTO.setCategoria(new CategoriaResumoDTO(
        anuncio.getCategoria().getId(),
        anuncio.getCategoria().getNome()));

    anuncioDTO.setPlataforma(new PlataformaDTO(
        anuncio.getPlataforma().getId(),
        anuncio.getPlataforma().getNome(),
        anuncio.getPlataforma().getFabricante(),
        anuncio.getPlataforma().getGeracao()));

    anuncioDTO.setCondicao(new CondicaoDTO(
        anuncio.getCondicao().getCodigo(),
        anuncio.getCondicao().getDescricao()));

    anuncioDTO.setStatus(new StatusDTO(
        anuncio.getStatus().getCodigo(),
        anuncio.getStatus().getDescricao()));

    anuncioDTO.setCompletude(new CompletudeDTO(
        anuncio.getCompletude().getCodigo(),
        anuncio.getCompletude().getDescricao()));

    return anuncioDTO;
  }

  private Anuncio toAnuncioEntity(AnuncioRequestDTO anuncioDTO) {
    Usuario usuario = usuarioRepository
        .findById(anuncioDTO.getIdUsuario())
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

    Categoria categoria = categoriaRepository
        .findById(anuncioDTO.getIdCategoria())
        .orElseThrow(() -> new RuntimeException("Categoria não encontrado."));

    Plataforma plataforma = plataformaRepository
        .findById(anuncioDTO.getIdPlataforma())
        .orElseThrow(() -> new RuntimeException("Plataforma não encontrado."));

    Completude completude = completudeRepository
        .findById(anuncioDTO.getCodigoCompletude())
        .orElseThrow(() -> new RuntimeException("Completude não encontrado."));

    Condicao condicao = condicaoRepository
        .findById(anuncioDTO.getCodigoCondicao())
        .orElseThrow(() -> new RuntimeException("Condição não encontrado."));

    StatusAnuncio status = statusRepository
        .findById(anuncioDTO.getCodigoStatus())
        .orElseThrow(() -> new RuntimeException("Status não encontrado."));

    Anuncio anuncio = new Anuncio();

    anuncio.setUsuario(usuario);
    anuncio.setCategoria(categoria);
    anuncio.setPlataforma(plataforma);
    anuncio.setCompletude(completude);
    anuncio.setCondicao(condicao);
    anuncio.setStatus(status);

    anuncio.setTitulo(anuncioDTO.getTitulo());
    anuncio.setDescricao(anuncioDTO.getDescricao());
    anuncio.setLocalizacao(anuncioDTO.getLocalizacao());
    anuncio.setPreco(anuncioDTO.getPreco());

    List<FotoAnuncio> fotos = new ArrayList<>();

    for (int i = 0; i > anuncioDTO.getUrlsFotos().size(); i++) {
      FotoAnuncio foto = new FotoAnuncio();

      foto.setUrl(anuncioDTO.getUrlsFotos().get(i));
      foto.setOrdem(i + 1);
      foto.setPrincipal(i == 0);
      foto.setAnuncio(anuncio);

      fotos.add(foto);
    }

    anuncio.setFotos(fotos);

    return anuncio;

  }

}
