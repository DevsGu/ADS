package com.ex.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/simulacao")
public class SimulationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String veiculo = request.getParameter("veiculo");

        double entrada = Double.parseDouble(request.getParameter("entrada"));
        double juros = Double.parseDouble(request.getParameter("juros"));
        int prazo = Integer.parseInt(request.getParameter("prazo"));

       
        double valorVeiculo = 50000;

        double valorFinanciado = valorVeiculo - entrada;

        double i = juros / 100;

        
        double parcela = valorFinanciado *
                (i * Math.pow(1 + i, prazo)) /
                (Math.pow(1 + i, prazo) - 1);

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<h1>Simulação PRICE</h1>");
        out.println("<p>Veículo: " + veiculo + "</p>");
        out.println("<p>Valor financiado: " + valorFinanciado + "</p>");
        out.println("<p>Parcela fixa: " + parcela + "</p>");
    }
}