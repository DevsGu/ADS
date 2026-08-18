<%@ page import="java.util.List" %>
<%@ page import="com.example.atividade.model.Usuario" %>

<%
List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuarios");
%>

<!DOCTYPE html>
<html>
<body>

<h1>Usuários</h1>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Nome</th>
        <th>Email</th>
    </tr>

    <% for (Usuario usuario : usuarios) { %>
        <tr>
            <td><%= usuario.getId() %></td>
            <td><%= usuario.getNome() %></td>
            <td><%= usuario.getEmail() %></td>
        </tr>
    <% } %>

</table>

</body>
</html>