<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <script src="https://cdn.tiny.cloud/1/5visbkrzsmef4spvcdb2hatgn4e60j4uxqoh6hqabnbihb9x/tinymce/5/tinymce.min.js"
            referrerpolicy="origin"></script>
</head>
<body>
<p>Admin page</p>
<c:url value="/" var="link1"></c:url>
<a href="${link1}">Вернуться на главную страницу</a>
<p></p>
<p></p>

<form method="post" enctype="multipart/form-data">
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
    &nbsp;<label>Загрузить изображение &nbsp;<input type="file" name="image"></label>
    <c:url value="/delete" var="deletePost">
        <c:param name="deletePost" value="delete"/>
    </c:url>
    &nbsp; <a href="${deletePost}">Удалить пост</a>
    <p></p>
</form>
<script>
    tinymce.init({
        selector: '#postTheme',
        plugins: 'a11ychecker advcode casechange formatpainter linkchecker autolink lists checklist media mediaembed pageembed permanentpen powerpaste table advtable tinycomments tinymcespellchecker',
        toolbar: 'a11ycheck addcomment showcomments casechange checklist code formatpainter pageembed permanentpen table',
        toolbar_mode: 'floating',
        tinycomments_mode: 'embedded',
        tinycomments_author: 'Author name',
    });
    tinymce.init({
        selector: '#post',
        plugins: 'a11ychecker advcode casechange formatpainter linkchecker autolink lists checklist media mediaembed pageembed permanentpen powerpaste table advtable tinycomments tinymcespellchecker',
        toolbar: 'a11ycheck addcomment showcomments casechange checklist code formatpainter pageembed permanentpen table',
        toolbar_mode: 'floating',
        tinycomments_mode: 'embedded',
        tinycomments_author: 'Author name',
    });
</script>
</body>
</html>
