package br.edu.ifce.retromarket.entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "foto_anuncio", uniqueConstraints = @UniqueConstraint(columnNames = { "anuncio_id", "ordem" }))
public class FotoAnuncio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "anuncio_id", nullable = false)
  private Anuncio anuncio;

  @Column(name = "url", length = 255, nullable = false)
  private String url;

  @Column(name = "ordem", nullable = false)
  private Integer ordem;

  // Regra de negócio (no máximo uma foto principal por anúncio) é garantida na
  // camada de serviço.
  @Column(name = "principal", nullable = false)
  private boolean principal;

  public FotoAnuncio() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Anuncio getAnuncio() {
    return anuncio;
  }

  public void setAnuncio(Anuncio anuncio) {
    this.anuncio = anuncio;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Integer getOrdem() {
    return ordem;
  }

  public void setOrdem(Integer ordem) {
    this.ordem = ordem;
  }

  public boolean isPrincipal() {
    return principal;
  }

  public void setPrincipal(boolean principal) {
    this.principal = principal;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof FotoAnuncio other))
      return false;
    return id != null && id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
