package com.example.spring2.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring2.dto.UserDTO;
import com.example.spring2.entities.User;
import com.example.spring2.repositories.UserRepository;

@Service
public class UserService {



    // Nessa notação o proprio framework trata de resolver essa dependencia e entregar o objeto 
// q pode usar


    @Autowired
    private UserRepository repository;



// classe service responsavel pelo Id 
// vai no banco de dados de busca esse usuario pelo id
// dps ela retorna o usuario transformado em objeto dto    
    public UserDTO findById(Long id){
        User entity = repository.findById(id).get();
        UserDTO dto = new UserDTO(entity);
        return dto;
    }


}
