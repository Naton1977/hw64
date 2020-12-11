<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p>Admin page</p>
<form method="post">
    <label for="postAuthor">Введите имя и фамилию автора поста</label>
    <p></p>
    <input id="postAuthor" name="postAuthor" size="70"/>
    <p></p>
    <label for="postName">Введите название поста :</label>
    <p></p>
    <input id="postName" name="postName" size="70"/>
    <p></p>
    <label for="publicationDate">Введите дату публикации (по умолчанию будет установленна текущая дата) :</label>
    <p></p>
    <input id="publicationDate" name="publicationDate" size="70"/>
    <p></p>
    <label for="postTheme">Введите зоголовок поста :</label>
    <p></p>
    <textarea id="postTheme" name="postTheme" cols="100" rows="10"></textarea>
    <p></p>
    <br/>
    <label for="post">Введите пост :</label>
    <p></p>
    <textarea id="post" name="post" cols="100" rows="18"></textarea>
    <p></p>
    <label>Черновик ?
        &nbsp; Да<input type="radio" name="draft" value="yes" checked>
        &nbsp; Нет<input type="radio" name="draft" value="no">
    </label>
    <p></p>
    <input type="submit"/>
    <c:url value="/delete" var="deletePost">
        <c:param name="deletePost" value="delete"/>
    </c:url>
    &nbsp; <a href="${deletePost}">Удалить пост</a>
    <p></p>
</form>

</body>
</html>
