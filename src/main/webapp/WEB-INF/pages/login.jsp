<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        body {
            display: flex;
            align-items: center;
            justify-content: center;
            width: 100%;
            height: 100%;
            background-color: gainsboro;
        }

        div {
            width: 350px;
            height: 300px;
            border: 1px solid black;
            text-align: center;
            background-color: #f7f7f7;
        }

        form {
            margin-top: 70px;
        }

        input {
            margin-top: 7px;
        }

        input:nth-of-type(1) {
            margin-bottom: 15px;
        }


    </style>
</head>
<body>
<div>
    <form method="post">
        <label for="login">Enter Admin login : </label><br>
        <input id="login" name="login"/>
        <br/>
        <label for="password">Enter password :</label><br>
        <input id="password" name="password" type="password"/><br>
        <br/>
        <input type="submit"/>
    </form>
    <%
        if (request.getAttribute("admin") != null) {
    %>
    <p style="color: red">Вы ввели не правильный логин или пароль</p>
    <% }%>
</div>
</body>
</html>
