package org.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(urlPatterns = "/post")
public class PostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            addFullPost(req, resp);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addFullPost(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        SecurityContext securityContext = SecurityContext.getInstance();
        String id = "";
        String postAuthor = "";
        String publicationDate = "";
        String postName = "";
        String postTheme = "";
        String post = "";
        int idInt = 0;;
        if (req.getParameter("fullPost") != null) {
            id = req.getParameter("fullPost");
        }
        try {
            idInt = Integer.parseInt(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Connection connection = securityContext.connection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from posts where id = '" + idInt + "';");
        if (resultSet.next()) {
            postAuthor = resultSet.getString("postAuthor");
            publicationDate = resultSet.getString("publicationDate");
            postName = resultSet.getString("postName");
            postTheme = resultSet.getString("postTheme");
            post = resultSet.getString("post");
            req.setAttribute("postAuthor", postAuthor);
            req.setAttribute("publicationDate", publicationDate);
            req.setAttribute("postName", postName);
            req.setAttribute("postTheme", postTheme);
            req.setAttribute("post", post);
            req.getRequestDispatcher("/WEB-INF/pages/post.jsp").forward(req, resp);
        }
    }
}
