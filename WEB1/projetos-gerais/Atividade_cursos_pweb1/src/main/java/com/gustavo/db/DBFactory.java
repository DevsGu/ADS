package com.gustavo.db;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class DBFactory {

  private static final String PERSISTENCE_UNIT = "cursosPU"; // Nome do persistence unit definido no persistence.xml
  private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
      Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

  private DBFactory() {
  }

  public static EntityManager criarEntityManager() {
    return ENTITY_MANAGER_FACTORY.createEntityManager();
  }

  public static void fechar() {
    if (ENTITY_MANAGER_FACTORY.isOpen()) {
      ENTITY_MANAGER_FACTORY.close();
    }
  }
}
