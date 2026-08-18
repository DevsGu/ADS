package com.gustavo;

import java.util.Optional;

import com.gustavo.dao.InstrutorDAOImpl;
import com.gustavo.db.DBFactory;
import com.gustavo.entities.Instrutor;

public class Main {
    public static void main(String[] args) {
        InstrutorDAOImpl instrutorDAO = new InstrutorDAOImpl();

        // Criando um novo instrutor
        Instrutor novoInstrutor = new Instrutor();
        novoInstrutor.setNome("Carlos Eduardo");
        novoInstrutor.setEmail("carlos.eduardo@example.com");
        novoInstrutor.setBiografia("Especialista em desenvolvimento de software e arquitetura de sistemas.");

        // Persistindo o novo instrutor
        instrutorDAO.salvar(novoInstrutor);

        // Buscando o instrutor pelo ID
        Optional<Instrutor> instrutorEncontrado = instrutorDAO.buscarPorId(novoInstrutor.getId());
        if (instrutorEncontrado.isPresent()) {
            System.out.println("Instrutor encontrado: " + instrutorEncontrado.get().getNome());
        } else {
            System.out.println("Instrutor não encontrado.");
        }

        // Atualizando o instrutor
        novoInstrutor.setNome("Carlos E. Silva");
        instrutorDAO.atualizar(novoInstrutor);

        // Removendo o instrutor
        //instrutorDAO.remover(novoInstrutor.getId());

        // Fechando a fábrica de EntityManager
        DBFactory.fechar();
    }
}

        
