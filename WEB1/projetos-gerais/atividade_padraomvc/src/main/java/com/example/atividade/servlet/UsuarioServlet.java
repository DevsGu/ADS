package com.example.atividade.servlet;

import com.example.atividade.service.UsuarioService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("usuarios", usuarioService.listarTodos());

        req.getRequestDispatcher("/listar.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String nome = req.getParameter("nome");
        String email = req.getParameter("email");

        try {

            usuarioService.cadastrar(nome, email);

            resp.sendRedirect(req.getContextPath() + "/usuarios");

        } catch (IllegalArgumentException e) {

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            req.setAttribute("erro", e.getMessage());

            req.getRequestDispatcher("/index.jsp")
                    .forward(req, resp);
        }
    }
}