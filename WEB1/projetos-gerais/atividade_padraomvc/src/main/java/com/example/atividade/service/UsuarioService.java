package com.example.atividade.service;

import com.example.atividade.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    private static final List<Usuario> usuarios = new ArrayList<>();
    private static Long proximoId = 1L;

    public void cadastrar(String nome, String email) {

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email é obrigatório.");
        }

        boolean emailExiste = usuarios.stream()
                .anyMatch(usuario -> usuario.getEmail().equalsIgnoreCase(email));

        if (emailExiste) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }

        Usuario usuario = new Usuario(proximoId++, nome, email);
        usuarios.add(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarios;
    }
}