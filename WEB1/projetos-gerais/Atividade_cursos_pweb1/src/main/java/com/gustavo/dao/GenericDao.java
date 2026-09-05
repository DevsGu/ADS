package com.gustavo.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, TypeID> {

  T salvar(T entidade);

  Optional<T> buscarPorId(TypeID id);

  List<T> buscarTodos();

  T atualizar(T entidade);

  void remover(TypeID id);
}
