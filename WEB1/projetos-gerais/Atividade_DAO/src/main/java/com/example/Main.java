package com.example;

import java.time.LocalDate;
import java.util.List;

import com.example.db.DAOFactory; // Certifique-se de importar a classe DAOFactory corretamente
import com.example.dao.DepartmentDAO; // Certifique-se de importar a interface DepartmentDAO corretamente
import com.example.dao.SellerDAO; // Certifique-se de importar a interface SellerDAO corretamente
import com.example.entities.Department; // Certifique-se de importar a classe Department corretamente
import com.example.entities.Seller; // Certifique-se de importar a classe Seller corretamente

public class Main {
    public static void main(String[] args) {
        DepartmentDAO departmentDAO = DAOFactory.createDepartmentDAO();
        SellerDAO sellerDAO = DAOFactory.createSellerDAO();

        // Teste de inserção de departamento
        // Passamos null para o ID, pois ele será gerado automaticamente pelo banco de
        // dados
        Department newDepartment = new Department(null, "Marketing");
        departmentDAO.insert(newDepartment);

        // Teste de consulta de departamentos
        List<Department> departments = departmentDAO.findAll();

        System.out.println("Departamentos encontrados:");
        for (Department dept : departments) {
            System.out.println(dept.getId() + ": " + dept.getName());
        }

        // Teste de inserção de vendedor
        Seller newSeller = new Seller(null, "Maria Oliveira", "maria.oliveira@example.com",
                LocalDate.parse("1990-05-15"), 5000.0, departments.getLast()); // Associando o vendedor ao último departamento inserido (Marketing)
        sellerDAO.insert(newSeller);

        // Teste de consulta de vendedores
        List<Seller> sellers = sellerDAO.findAll();

        System.out.println("Vendedores encontrados:");
        for (Seller seller : sellers) {
            System.out.println(seller.getId() + ": " + seller.getName());
        }

        if (!sellers.isEmpty()) {
        Seller sellerToUpdate = sellers.get(0);

        sellerToUpdate.setName("Maria Atualizada");
        sellerToUpdate.setEmail("maria.atualizada@example.com");

        sellerDAO.update(sellerToUpdate);

        System.out.println("Seller atualizado com sucesso!");

        if (!sellers.isEmpty()) {
        Integer idToDelete = sellers.get(sellers.size() - 1).getId();

        sellerDAO.deleteById(idToDelete);

        System.out.println("Seller deletado com sucesso!");
    }
    }
    }
}