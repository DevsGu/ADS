package br.edu.ifce.retromarket.entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Tabela de domínio com as classificações de completude de um anúncio
 * (ex.: COMPLETO, LOOSE, INCOMPLETO).
 */
@Entity
@Table(name = "completude")
public class Completude {

  @Id
  @Column(name = "codigo", length = 20)
  private String codigo;

  @Column(name = "descricao", length = 60, nullable = false)
  private String descricao;

  public Completude() {
  }

  public Completude(String codigo, String descricao) {
    this.codigo = codigo;
    this.descricao = descricao;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Completude other))
      return false;
    return codigo != null && codigo.equals(other.codigo);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(codigo);
  }
}
