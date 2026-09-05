<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="jakarta.servlet.http.Cookie" %>

<%
    String idioma = "pt";

    Cookie[] cookies = request.getCookies();

    if(cookies != null){

        for(Cookie cookie : cookies){

            if(cookie.getName().equals("idioma")){

                idioma = cookie.getValue();
                break;
            }
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Idioma Selecionado</title>
</head>
<body>

<%
    if("pt".equals(idioma)){
%>

    <h1>Bem-vindo ao curso!</h1>

<%
    } else if("en".equals(idioma)){
%>

    <h1>Welcome to the course!</h1>

<%
    } else if("es".equals(idioma)){
%>

    <h1>¡Bienvenido al curso!</h1>

<%
    }
%>

<br>

<a href="idioma.jsp">
    Alterar idioma
</a>

</body>
</html>