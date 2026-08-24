package br.edu.ifce.retromarket.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "favorito", uniqueConstraints = @UniqueConstraint(columnNames = { "usuario_id", "anuncio_id" }))
public class Favorito {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "usuario_id", nullable = false)
  private Usuario usuario;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "anuncio_id", nullable = false)
  private Anuncio anuncio;

  @Column(name = "data_favorito", nullable = false, updatable = false)
  private LocalDateTime dataFavorito;

  public Favorito() {
  }

  @PrePersist
  private void prePersist() {
    if (dataFavorito == null) {
      dataFavorito = LocalDateTime.now();
    }
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public Anuncio getAnuncio() {
    return anuncio;
  }

  public void setAnuncio(Anuncio anuncio) {
    this.anuncio = anuncio;
  }

  public LocalDateTime getDataFavorito() {
    return dataFavorito;
  }

  public void setDataFavorito(LocalDateTime dataFavorito) {
    this.dataFavorito = dataFavorito;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Favorito other))
      return false;
    return id != null && id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
