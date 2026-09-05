package br.edu.ifce.retromarket.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "plataforma")
public class Plataforma {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nome", length = 80, nullable = false, unique = true)
  private String nome;

  @Column(name = "fabricante", length = 80)
  private String fabricante;

  @Column(name = "geracao")
  private Short geracao;

  @OneToMany(mappedBy = "plataforma")
  private List<Anuncio> anuncios = new ArrayList<>();

  public Plataforma() {
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

  public List<Anuncio> getAnuncios() {
    return anuncios;
  }

  public void setAnuncios(List<Anuncio> anuncios) {
    this.anuncios = anuncios;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Plataforma other))
      return false;
    return id != null && id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
