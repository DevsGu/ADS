package br.edu.ifce.retromarket.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nome", length = 100, nullable = false)
  private String nome;

  @Column(name = "email", length = 100, nullable = false, unique = true)
  private String email;

  @Column(name = "senha_hash", length = 255, nullable = false)
  private String senhaHash;

  @Column(name = "data_cadastro", nullable = false, updatable = false)
  private LocalDateTime dataCadastro;

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Anuncio> anuncios = new ArrayList<>();

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Favorito> favoritos = new ArrayList<>();

  public Usuario() {
  }

  @PrePersist
  private void prePersist() {
    if (dataCadastro == null) {
      dataCadastro = LocalDateTime.now();
    }
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSenhaHash() {
    return senhaHash;
  }

  public void setSenhaHash(String senhaHash) {
    this.senhaHash = senhaHash;
  }

  public LocalDateTime getDataCadastro() {
    return dataCadastro;
  }

  public void setDataCadastro(LocalDateTime dataCadastro) {
    this.dataCadastro = dataCadastro;
  }

  public List<Anuncio> getAnuncios() {
    return anuncios;
  }

  public void setAnuncios(List<Anuncio> anuncios) {
    this.anuncios = anuncios;
  }

  public List<Favorito> getFavoritos() {
    return favoritos;
  }

  public void setFavoritos(List<Favorito> favoritos) {
    this.favoritos = favoritos;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Usuario other))
      return false;
    return id != null && id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
