package com.example.atv7.atividade07.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String usuario =
                request.getParameter("usuario");

        String senha =
                request.getParameter("senha");

        if(usuario.equals("admin")
                && senha.equals("123")) {

            HttpSession session =
                    request.getSession();

            session.setAttribute(
                    "usuario",
                    usuario
            );

            response.sendRedirect(
                    "home.jsp"
            );

        } else {

            response.sendRedirect(
                    "login.jsp"
            );

        }
    }
}