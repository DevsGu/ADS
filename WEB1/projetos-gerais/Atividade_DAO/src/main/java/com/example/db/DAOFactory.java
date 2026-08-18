package com.example.db;

import com.example.dao.*;;

public class DAOFactory {
    public static DepartmentDAO createDepartmentDAO() {
        return new DepartmentDAOImpl(ConnectionFactory.getConnection());
    }

    public static SellerDAO createSellerDAO() {
        return new SellerDAOImpl(ConnectionFactory.getConnection());
    }
}