package com.ex.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/login")
public class loginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String login = request.getParameter("name");
        String senha = request.getParameter("senha");

        if ("admin123".equals(login) && "123".equals(senha)) {
            response.getWriter().println("Acesso Autorizado , seja muito bem vindo " + nome);
        } else {
            response.getWriter().println("Acesso Negado");
        }
    }
}