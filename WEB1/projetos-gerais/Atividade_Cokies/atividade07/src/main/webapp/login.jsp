<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>

<h2>Login</h2>

<form action="login" method="post">

    <label>Usuário:</label>
    <input type="text" name="usuario" required>

    <br><br>

    <label>Senha:</label>
    <input type="password" name="senha" required>

    <br><br>

    <button type="submit">
        Entrar
    </button>

</form>

</body>
</html>