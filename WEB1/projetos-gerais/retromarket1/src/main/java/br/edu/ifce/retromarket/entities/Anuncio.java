package br.edu.ifce.retromarket.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "anuncio")
public class Anuncio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "usuario_id", nullable = false)
  private Usuario usuario;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "plataforma_id", nullable = false)
  private Plataforma plataforma;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "categoria_id", nullable = false)
  private Categoria categoria;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "condicao_codigo", nullable = false)
  private Condicao condicao;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "completude_codigo", nullable = false)
  private Completude completude;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "status_codigo", nullable = false)
  private StatusAnuncio status;

  @Column(name = "titulo", length = 150, nullable = false)
  private String titulo;

  @Column(name = "descricao")
  private String descricao;

  @Column(name = "preco", precision = 12, scale = 2, nullable = false)
  private BigDecimal preco;

  @Column(name = "localizacao", length = 120, nullable = false)
  private String localizacao;

  @Column(name = "data_publicacao", nullable = false, updatable = false)
  private LocalDateTime dataPublicacao;

  @Column(name = "data_atualizacao", nullable = false)
  private LocalDateTime dataAtualizacao;

  @OneToMany(mappedBy = "anuncio", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("ordem ASC")
  private List<FotoAnuncio> fotos = new ArrayList<>();

  @OneToMany(mappedBy = "anuncio", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Favorito> favoritos = new ArrayList<>();

  public Anuncio() {
  }

  @PrePersist
  private void prePersist() {
    LocalDateTime now = LocalDateTime.now();
    if (dataPublicacao == null) {
      dataPublicacao = now;
    }
    dataAtualizacao = now;
  }

  @PreUpdate
  private void preUpdate() {
    dataAtualizacao = LocalDateTime.now();
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

  public Plataforma getPlataforma() {
    return plataforma;
  }

  public void setPlataforma(Plataforma plataforma) {
    this.plataforma = plataforma;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
  }

  public Condicao getCondicao() {
    return condicao;
  }

  public void setCondicao(Condicao condicao) {
    this.condicao = condicao;
  }

  public Completude getCompletude() {
    return completude;
  }

  public void setCompletude(Completude completude) {
    this.completude = completude;
  }

  public StatusAnuncio getStatus() {
    return status;
  }

  public void setStatus(StatusAnuncio status) {
    this.status = status;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
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

  public LocalDateTime getDataPublicacao() {
    return dataPublicacao;
  }

  public void setDataPublicacao(LocalDateTime dataPublicacao) {
    this.dataPublicacao = dataPublicacao;
  }

  public LocalDateTime getDataAtualizacao() {
    return dataAtualizacao;
  }

  public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
    this.dataAtualizacao = dataAtualizacao;
  }

  public List<FotoAnuncio> getFotos() {
    return fotos;
  }

  public void setFotos(List<FotoAnuncio> fotos) {
    this.fotos = fotos;
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
    if (!(o instanceof Anuncio other))
      return false;
    return id != null && id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
