<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Cadastro de Usuário</title>
</head>
<body>

<h1>Cadastro de Usuário</h1>

<p style="color:red">
    ${erro}
</p>

<form action="usuarios" method="post">

    <label>Nome:</label>
    <input type="text" name="nome">

    <br><br>

    <label>Email:</label>
    <input type="email" name="email">

    <br><br>

    <button type="submit">Cadastrar</button>

</form>

<br>

<a href="usuarios">Listar Usuários</a>

</body>
</html>