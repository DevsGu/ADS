package com.gustavo.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

// Substitua pelo pacote correto

// Importações necessárias

@Entity
public class Curso {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "titulo", nullable = false, length = 100)
  private String titulo;

  @Column(name = "descricao", nullable = false, length = 500)
  private String descricao;

  @Column(name = "carga_horaria", nullable = false)
  private Double cargaHoraria;

  @Column(name = "preco", nullable = false)
  private Double preco;

  @Column(name = "nivel", nullable = false, length = 50)
  private String nivel;

  @Column(name = "url", nullable = false, length = 200)
  private String url;

  @Column(name = "status", nullable = false, length = 50)
  private String status;

  @ManyToOne
  @JoinColumn(name = "instrutor_id", nullable = false)
  private Instrutor instrutor;

  // Coleção de aulas associadas ao curso
  @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Aula> aulas = new ArrayList<>();



  
  public Curso(Long id, String titulo, String descricao, Double cargaHoraria, Double preco, String nivel, String url,
        String status, Instrutor instrutor, List<Aula> aulas) {
    this.id = id;
    this.titulo = titulo;
    this.descricao = descricao;
    this.cargaHoraria = cargaHoraria;
    this.preco = preco;
    this.nivel = nivel;
    this.url = url;
    this.status = status;
    this.instrutor = instrutor;
    this.aulas = aulas;
}

  public Curso() {
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

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public Double getCargaHoraria() {
    return cargaHoraria;
  }

  public void setCargaHoraria(Double cargaHoraria) {
    this.cargaHoraria = cargaHoraria;
  }

  public Double getPreco() {
    return preco;
  }

  public void setPreco(Double preco) {
    this.preco = preco;
  }

  public String getNivel() {
    return nivel;
  }

  public void setNivel(String nivel) {
    this.nivel = nivel;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Instrutor getInstrutor() {
    return instrutor;
  }

  public void setInstrutor(Instrutor instrutor) {
    this.instrutor = instrutor;
  }

  public List<Aula> getAulas() {
    return aulas;
  }

  public void setAulas(List<Aula> aulas) {
    this.aulas = aulas;
  }

  

}
