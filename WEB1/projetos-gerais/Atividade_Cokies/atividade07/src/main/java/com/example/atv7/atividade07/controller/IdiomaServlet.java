package com.example.atv7.atividade07.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/idioma")
public class IdiomaServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Recebe o idioma selecionado
        String idioma = request.getParameter("idioma");

        // Cria o cookie
        Cookie cookieIdioma = new Cookie(
                "idioma",
                idioma
        );

        // Mantém o cookie por 30 dias
        cookieIdioma.setMaxAge(
                60 * 60 * 24 * 30
        );

        // Define o caminho da aplicação
        cookieIdioma.setPath(
                request.getContextPath()
        );

        // Adiciona o cookie na resposta
        response.addCookie(cookieIdioma);

        // Redireciona para a página de resultado
        response.sendRedirect(
                request.getContextPath()
                + "/resultado.jsp"
        );
    }
}