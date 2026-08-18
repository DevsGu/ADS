package com.example.dao;

import java.util.List;
import com.example.entities.Seller; // Certifique-se de importar a classe Seller corretamente

public interface SellerDAO {
    void insert(Seller seller);
    void update(Seller seller);
    void deleteById(Integer id);
    Seller findById(Integer id);
    List<Seller> findAll();
}