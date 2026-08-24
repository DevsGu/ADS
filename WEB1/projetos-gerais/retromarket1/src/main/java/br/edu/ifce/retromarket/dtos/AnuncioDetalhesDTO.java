package br.edu.ifce.retromarket.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AnuncioDetalhesDTO {

  private Long id;
  private String titulo;
  private BigDecimal preco;
  private String localizacao;
  private String descricao;
  private LocalDate dataPublicacao;
  private List<String> urlsFotos;

  private CategoriaResumoDTO categoria;
  private PlataformaDTO plataforma;
  private CondicaoDTO condicao;
  private StatusDTO status;
  private CompletudeDTO completude;

  public AnuncioDetalhesDTO() {
    this.urlsFotos = new ArrayList<>();
  }

  public AnuncioDetalhesDTO(Long id, String titulo, BigDecimal preco, String localizacao, String descricao,
      LocalDate dataPublicacao, List<String> urlsFotos, CategoriaResumoDTO categoria, PlataformaDTO plataforma,
      CondicaoDTO condicao, StatusDTO status, CompletudeDTO completude) {
    this.id = id;
    this.titulo = titulo;
    this.preco = preco;
    this.localizacao = localizacao;
    this.descricao = descricao;
    this.dataPublicacao = dataPublicacao;
    this.urlsFotos = urlsFotos;
    this.categoria = categoria;
    this.plataforma = plataforma;
    this.condicao = condicao;
    this.status = status;
    this.completude = completude;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public BigDecimal getPreco() {
    return preco;
  }

  public void setPreco(BigDecimal preco) {
    this.preco = preco;
  }

  public String getLocalizacao() {
    return localizacao;
  }

  public void setLocalizacao(String localizacao) {
    this.localizacao = localizacao;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public LocalDate getDataPublicacao() {
    return dataPublicacao;
  }

  public void setDataPublicacao(LocalDate dataPublicacao) {
    this.dataPublicacao = dataPublicacao;
  }

  public List<String> getUrlsFotos() {
    return urlsFotos;
  }

  public void setUrlsFotos(List<String> urlsFotos) {
    this.urlsFotos = urlsFotos;
  }

  public void addUrlFoto(String urlFoto) {
    this.urlsFotos.add(urlFoto);
  }

  public CategoriaResumoDTO getCategoria() {
    return categoria;
  }

  public void setCategoria(CategoriaResumoDTO categoria) {
    this.categoria = categoria;
  }

  public PlataformaDTO getPlataforma() {
    return plataforma;
  }

  public void setPlataforma(PlataformaDTO plataforma) {
    this.plataforma = plataforma;
  }

  public CondicaoDTO getCondicao() {
    return condicao;
  }

  public void setCondicao(CondicaoDTO condicao) {
    this.condicao = condicao;
  }

  public StatusDTO getStatus() {
    return status;
  }

  public void setStatus(StatusDTO status) {
    this.status = status;
  }

  public CompletudeDTO getCompletude() {
    return completude;
  }

  public void setCompletude(CompletudeDTO completude) {
    this.completude = completude;
  }

}
