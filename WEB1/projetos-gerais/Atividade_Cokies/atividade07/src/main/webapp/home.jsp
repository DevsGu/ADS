<%@ page contentType="text/html;charset=UTF-8" %>

<%
    String usuario =
            (String) session.getAttribute("usuario");

    if(usuario == null){

        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
</head>
<body>

<h2>Sessão Ativa</h2>

<p>
    Usuário Logado:
    <strong><%= usuario %></strong>
</p>

<p>
    A sessão foi criada com sucesso.
</p>

<form action="logout" method="post">

    <button type="submit">
        Logout
    </button>

</form>
</body>
</html>