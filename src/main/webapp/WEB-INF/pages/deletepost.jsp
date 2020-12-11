
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <style>
        table{
            border-collapse: collapse;
        }

        table td {
            border: 1px solid black;
            width: 250px;
            text-align: center;
        }
    </style>
</head>
<body>
<p>Удалить пост</p>

    <table>
        <thead>
        <tr>
            <td>Автор поста</td>
            <td>Дата поста</td>
            <td>Название поста</td>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${Posts}" var="post">

            <tr>
                <td>${post.postAuthor}</td>
                <td>${post.publicationDate}</td>
                <td>${post.postAuthor}</td>
                <c:url value="/delete" var="deletePost">
                    <c:param name="delete" value="${post.id}"/>
                </c:url>

                <td><a href="${deletePost}">Удалить этот пост</a> </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
