package com.example.spring.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Aula {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "titulo", nullable = false, length = 100)
  private String titulo;

  @Column(name = "duracao", nullable = false)
  private Integer duracao;

  @Column(name = "ordem", nullable = false)
  private Integer ordem;

  @ManyToOne
  @JoinColumn(name = "curso_id", nullable = false)
  @JsonBackReference("curso-aula")
  private Curso curso;

  public Aula() {
  }

  public Aula(Long id, String titulo, Integer duracao, Integer ordem, Curso curso) {
    this.id = id;
    this.titulo = titulo;
    this.duracao = duracao;
    this.ordem = ordem;
    this.curso = curso;
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

  public Integer getDuracao() {
    return duracao;
  }

  public void setDuracao(Integer duracao) {
    this.duracao = duracao;
  }

  public Integer getOrdem() {
    return ordem;
  }

  public void setOrdem(Integer ordem) {
    this.ordem = ordem;
  }

  public Curso getCurso() {
    return curso;
  }

  public void setCurso(Curso curso) {
    this.curso = curso;
  }
}