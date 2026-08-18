package com.gustavo.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Instrutor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nome", nullable = false, length = 100)
  private String nome;

  @Column(name = "email", nullable = false, length = 100, unique = true)
  private String email;

  @Column(name = "biografia", length = 500)
  private String biografia;

  @OneToMany(mappedBy = "instrutor")
  private List<Curso> cursos = new ArrayList<>();

  public Instrutor() {
  }



  public Instrutor(Long id, String nome, String email, String biografia, List<Curso> cursos) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.biografia = biografia;
    this.cursos = cursos;
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



  public String getBiografia() {
    return biografia;
  }



  public void setBiografia(String biografia) {
    this.biografia = biografia;
  }



  public void setCursos(List<Curso> cursos) {
    this.cursos = cursos;
  }



  public List<Curso> getCursos() {
    return cursos;
  }

}

