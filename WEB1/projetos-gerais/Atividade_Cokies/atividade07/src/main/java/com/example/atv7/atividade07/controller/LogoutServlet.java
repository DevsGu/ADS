package com.example.atv7.atividade07.controller;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        HttpSession session =
                request.getSession(false);

        if(session != null){

            session.invalidate(); // <- AQUI

        }

        Cookie cookie =
                new Cookie("JSESSIONID", "");

        cookie.setMaxAge(0);

        cookie.setPath(
                request.getContextPath()
        );

        response.addCookie(cookie);

        response.sendRedirect(
                request.getContextPath()
                + "/login.jsp"
        );
    }
}