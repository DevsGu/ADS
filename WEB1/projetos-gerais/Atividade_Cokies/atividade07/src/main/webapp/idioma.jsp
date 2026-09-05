<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Idioma</title>
</head>
<body>

<h2>Escolha o idioma</h2>

<form action="idioma" method="post">

    <select name="idioma">

        <option value="pt">
            Português
        </option>

        <option value="en">
            Inglês
        </option>

        <option value="es">
            Espanhol
        </option>

    </select>

    <br><br>

    <button type="submit">
        Salvar
    </button>

</form>

</body>
</html>