package br.edu.ifce.retromarket.dtos;

import java.math.BigDecimal;

public class AnuncioResponseDTO {

  private Long id;
  private String titulo;
  private BigDecimal preco;
  private String condicao;
  private String completude;
  private String status;
  private String localizacao;
  private String urlFotoPrincipal;
  private PlataformaResumoDTO plataforma;
  private CategoriaResumoDTO categoria;

  public AnuncioResponseDTO() {
  }

  public AnuncioResponseDTO(Long id, String titulo, BigDecimal preco, String condicao, String completude, String status,
      String localizacao, String urlFotoPrincipal, PlataformaResumoDTO plataforma, CategoriaResumoDTO categoria) {
    this.titulo = titulo;
    this.preco = preco;
    this.condicao = condicao;
    this.completude = completude;
    this.status = status;
    this.localizacao = localizacao;
    this.urlFotoPrincipal = urlFotoPrincipal;
    this.plataforma = plataforma;
    this.categoria = categoria;
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

  public String getCondicao() {
    return condicao;
  }

  public void setCondicao(String condicao) {
    this.condicao = condicao;
  }

  public String getCompletude() {
    return completude;
  }

  public void setCompletude(String completude) {
    this.completude = completude;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getLocalizacao() {
    return localizacao;
  }

  public void setLocalizacao(String localizacao) {
    this.localizacao = localizacao;
  }

  public String getUrlFotoPrincipal() {
    return urlFotoPrincipal;
  }

  public void setUrlFotoPrincipal(String urlFotoPrincipal) {
    this.urlFotoPrincipal = urlFotoPrincipal;
  }

  public PlataformaResumoDTO getPlataforma() {
    return plataforma;
  }

  public void setPlataforma(PlataformaResumoDTO plataforma) {
    this.plataforma = plataforma;
  }

  public CategoriaResumoDTO getCategoria() {
    return categoria;
  }

  public void setCategoria(CategoriaResumoDTO categoria) {
    this.categoria = categoria;
  }

}
