package br.edu.ifce.retromarket.dtos;

public class PlataformaDTO {

  private Long id;
  private String nome;
  private String fabricante;
  private Short geracao;

  public PlataformaDTO() {
  }

  public PlataformaDTO(Long id, String nome, String fabricante, Short geracao) {
    this.id = id;
    this.nome = nome;
    this.fabricante = fabricante;
    this.geracao = geracao;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getFabricante() {
    return fabricante;
  }

  public void setFabricante(String fabricante) {
    this.fabricante = fabricante;
  }

  public Short getGeracao() {
    return geracao;
  }

  public void setGeracao(Short geracao) {
    this.geracao = geracao;
  }

}
