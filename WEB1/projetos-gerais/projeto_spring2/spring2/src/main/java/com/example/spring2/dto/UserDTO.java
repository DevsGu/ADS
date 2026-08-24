package com.example.spring2.dto;

import com.example.spring2.entities.User;

public class UserDTO {

    private long id;
    private String name;


        
    public UserDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }




    //COMO VOU COPIAR OS DADOS DE UM USER PARA UM USERDTO???? SEGUE ABAIXO A RESP]
    //posso tirar o this dai , pois não precisar referenciar pois ja sabe q e da classe e nao 
    // do parametro

        public UserDTO(User user) {
        id = user.getId();
        name = user.getName();
    }



    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    



    
}
