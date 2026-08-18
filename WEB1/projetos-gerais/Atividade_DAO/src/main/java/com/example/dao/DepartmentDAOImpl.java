package com.example.dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import com.example.db.ConnectionFactory;
import com.example.entities.Department; // Certifique-se de importar a classe Department corretamente

public class DepartmentDAOImpl implements DepartmentDAO {
    private Connection conn;

    public DepartmentDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>(); // Lista para armazenar os departamentos encontrados

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM department")) {

            // next() avança para o próximo registro e retorna true enquanto houver registros para ler no ResultSet
            while (rs.next()) {
                // Cria um novo objeto Department para cada registro encontrado
                Department department = new Department();
                department.setId(rs.getInt("id"));
                department.setName(rs.getString("name"));

                // Adiciona o departamento encontrado à lista
                departments.add(department);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar departamentos:");
            e.printStackTrace();
        }

        return departments; // Retorna a lista de departamentos encontrados
    }

    @Override
        public Department findById(Integer id) {
        String sql = "SELECT * FROM department WHERE id = ?";

        try (
             Connection conn = ConnectionFactory.getConnection();
            PreparedStatement st = conn.prepareStatement(sql)
        )  {
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

        if (rs.next()) {
            Department dep = new Department();
            dep.setId(rs.getInt("id"));
            dep.setName(rs.getString("name"));

            return dep;
        }

        return null;

    } catch (SQLException e) {
        throw new RuntimeException("Erro ao buscar departamento: " + e.getMessage());
    }
}

    @Override
    public void insert(Department department) {

        try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO department (name) VALUES (?)")) {
            // Define o valor do parâmetro para o nome do departamento
            // O 1 indica a posição do parâmetro na consulta SQL (o primeiro '?')
            pstmt.setString(1, department.getName());

            // Executa a consulta de inserção e retorna o número de linhas afetadas
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Departamento inserido com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir departamento:");
            e.printStackTrace();
        }

    }

    @Override
    public void update(Department department) {
        String sql = "UPDATE department SET name = ? WHERE id = ?";

    try (
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement st = conn.prepareStatement(sql)
    ) {
        st.setString(1, department.getName());
        st.setInt(2, department.getId());

        st.executeUpdate();

    } catch (SQLException e) {
        throw new RuntimeException("Erro ao atualizar departamento: " + e.getMessage());
    }
}

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM department WHERE id = ?";

    try (
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement st = conn.prepareStatement(sql)
    ) {
        st.setInt(1, id);

        st.executeUpdate();

    } catch (SQLException e) {
        throw new RuntimeException("Erro ao deletar departamento: " + e.getMessage());
    }
}
}