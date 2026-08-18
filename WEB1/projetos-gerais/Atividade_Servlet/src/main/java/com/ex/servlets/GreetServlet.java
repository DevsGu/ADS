 package com.ex.servlets;

 import jakarta.servlet.ServletException;
 import jakarta.servlet.annotation.WebServlet;
 import jakarta.servlet.http.HttpServlet;
 import jakarta.servlet.http.HttpServletRequest;
 import jakarta.servlet.http.HttpServletResponse;

 import java.io.IOException;

 @WebServlet("/greet")
 public class GreetServlet extends HttpServlet {
     @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         String name = request.getParameter("name");

         if (name == null || name.isEmpty()) {
             name = "Visitante";
         }

         response.setContentType("text/html; charset=UTF-8");
         response.getWriter().print("<h1>Olá, " + name + "! Bem-vindo ao tutorial de Servlets!</h1>");
     }
    }
   