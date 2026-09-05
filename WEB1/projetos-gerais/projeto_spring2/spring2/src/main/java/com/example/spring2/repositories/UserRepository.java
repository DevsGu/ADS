package com.example.spring2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring2.entities.User;

@Repository
public interface UserRepository extends JpaRepository< User , Long>{

    
} 