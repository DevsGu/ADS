package br.edu.ifce.retromarket.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AnuncioRequestDTO {

  private String titulo;
  private BigDecimal preco;
  private String localizacao;
  private String descricao;
  private List<String> urlsFotos;
  private Long idCategoria;
  private Long idPlataforma;
  private String codigoCompletude;
  private String codigoCondicao;
  private String codigoStatus;
  private Long idUsuario;

  public AnuncioRequestDTO() {
    this.urlsFotos = new ArrayList<>();
  }

  public AnuncioRequestDTO(String titulo, BigDecimal preco, String localizacao, String descricao,
      List<String> urlsFotos, Long idCategoria, Long idPlataforma, String codigoCompletude, String codigoCondicao,
      String codigoStatus, Long idUsuario) {
    this.titulo = titulo;
    this.preco = preco;
    this.localizacao = localizacao;
    this.descricao = descricao;
    this.urlsFotos = urlsFotos;
    this.idCategoria = idCategoria;
    this.idPlataforma = idPlataforma;
    this.codigoCompletude = codigoCompletude;
    this.codigoCondicao = codigoCondicao;
    this.codigoStatus = codigoStatus;
    this.idUsuario = idUsuario;
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

  public List<String> getUrlsFotos() {
    return urlsFotos;
  }

  public void setUrlsFotos(List<String> urlsFotos) {
    this.urlsFotos = urlsFotos;
  }

  public Long getIdCategoria() {
    return idCategoria;
  }

  public void setIdCategoria(Long idCategoria) {
    this.idCategoria = idCategoria;
  }

  public Long getIdPlataforma() {
    return idPlataforma;
  }

  public void setIdPlataforma(Long idPlataforma) {
    this.idPlataforma = idPlataforma;
  }

  public String getCodigoCompletude() {
    return codigoCompletude;
  }

  public void setCodigoCompletude(String codigoCompletude) {
    this.codigoCompletude = codigoCompletude;
  }

  public String getCodigoCondicao() {
    return codigoCondicao;
  }

  public void setCodigoCondicao(String codigoCondicao) {
    this.codigoCondicao = codigoCondicao;
  }

  public String getCodigoStatus() {
    return codigoStatus;
  }

  public void setCodigoStatus(String codigoStatus) {
    this.codigoStatus = codigoStatus;
  }

  public Long getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(Long idUsuario) {
    this.idUsuario = idUsuario;
  }

}
