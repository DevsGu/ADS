package com.gustavo.dao;

import java.util.List;
import java.util.Optional;

import com.gustavo.db.DBFactory;
import com.gustavo.entities.Instrutor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

// Importações necessárias omitidas

public class InstrutorDAOImpl implements GenericDao<Instrutor, Long> {

    @Override
public Instrutor salvar(Instrutor entidade) {
    EntityManager em = DBFactory.criarEntityManager(); // Criando uma instância do EntityManager a partir da DBFactory
    EntityTransaction tx = em.getTransaction(); // Pegando a transação do EntityManager

    try {
        // Iniciando a transação, persistindo a entidade e confirmando a transação
        tx.begin();
        em.persist(entidade);
        tx.commit();

        // Retornando a entidade persistida para quem chamou o método
        return entidade;
    } catch (Exception e) { // Em caso de erro, reverter a transação e lançar a exceção
        if (tx.isActive()) {
            tx.rollback();
        }
        throw e;
    } finally { // O bloco finally garante que o EntityManager seja fechado, mesmo em caso de exceção
        em.close();
    }
}
@Override
public Optional<Instrutor> buscarPorId(Long id) {
    EntityManager em = DBFactory.criarEntityManager(); // Criando uma instância do EntityManager a partir da DBFactory

    try {
        // Buscando a entidade Instrutor pelo ID
        Instrutor instrutor = em.find(Instrutor.class, id);
        return Optional.ofNullable(instrutor); // Retornando um Optional contendo a entidade encontrada ou vazio se não encontrada
    } catch (Exception e) {
        throw e;
    } finally { // O bloco finally garante que o EntityManager seja fechado, mesmo em caso de exceção
        em.close();
    }
}

@Override
public List<Instrutor> buscarTodos() {
    EntityManager em = DBFactory.criarEntityManager(); // Criando uma instância do EntityManager a partir da DBFactory

    try {
        // Criando a consulta JPQL para buscar todos os instrutores
        String jpql = "SELECT i FROM Instrutor i";
        TypedQuery<Instrutor> query = em.createQuery(jpql, Instrutor.class);

        // Executando a consulta e retornando os resultados como uma lista
        return query.getResultList();
    } catch (Exception e) {
        throw e;
    } finally { // O bloco finally garante que o EntityManager seja fechado, mesmo em caso de exceção
        em.close();
    }
}

@Override
public Instrutor atualizar(Instrutor entidade) {
    EntityManager em = DBFactory.criarEntityManager(); // Criando uma instância do EntityManager a partir da DBFactory
    EntityTransaction tx = em.getTransaction(); // Pegando a transação do EntityManager

    try {
        // Iniciando a transação, mesclando a entidade e confirmando a transação
        tx.begin();
        Instrutor instrutorAtualizado = em.merge(entidade);
        tx.commit();

        // Retornando a entidade atualizada para quem chamou o método
        return instrutorAtualizado;
    } catch (Exception e) { // Em caso de erro, reverter a transação e lançar a exceção
        if (tx.isActive()) {
            tx.rollback();
        }
        throw e;
    } finally { // O bloco finally garante que o EntityManager seja fechado, mesmo em caso de exceção
        em.close();
    }
}

@Override
public void remover(Long id) {
    EntityManager em = DBFactory.criarEntityManager(); // Criando uma instância do EntityManager a partir da DBFactory
    EntityTransaction tx = em.getTransaction(); // Pegando a transação do EntityManager

    try {
        // Iniciando a transação
        tx.begin();

        // Buscando a entidade Instrutor pelo ID
        Instrutor instrutorParaRemover = em.find(Instrutor.class, id);
        if (instrutorParaRemover != null) {
            // Removendo a entidade
            em.remove(instrutorParaRemover);
        } else {
            System.out.println("Instrutor não encontrado para remoção.");
        }

        // Confirmando a transação
        tx.commit();
    } catch (Exception e) { // Em caso de erro, reverter a transação e lançar a exceção
        if (tx.isActive()) {
            tx.rollback();
        }
        throw e;
    } finally { // O bloco finally garante que o EntityManager seja fechado, mesmo em caso de exceção
        em.close();
    }
}








  
}
