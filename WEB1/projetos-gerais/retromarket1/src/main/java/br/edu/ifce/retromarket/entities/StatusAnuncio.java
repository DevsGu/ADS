package br.edu.ifce.retromarket.entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Tabela de domínio com os status possíveis de um anúncio
 * (ex.: ATIVO, RESERVADO, VENDIDO, INATIVO).
 */
@Entity
@Table(name = "status_anuncio")
public class StatusAnuncio {

  @Id
  @Column(name = "codigo", length = 20)
  private String codigo;

  @Column(name = "descricao", length = 60, nullable = false)
  private String descricao;

  public StatusAnuncio() {
  }

  public StatusAnuncio(String codigo, String descricao) {
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
    if (!(o instanceof StatusAnuncio other))
      return false;
    return codigo != null && codigo.equals(other.codigo);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(codigo);
  }
}
