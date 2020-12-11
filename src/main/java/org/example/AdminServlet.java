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

@WebServlet(urlPatterns = "/admin")
public class AdminServlet extends HttpServlet {
    private String postAuthor;
    private String publicationDate;
    private String postName;
    private String postTheme;
    private String post;
    private String draft;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/pages/adminpage.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("postAuthor") != null) {
            postAuthor = req.getParameter("postAuthor");
        }
        if (req.getParameter("postName") != null) {
            postName = req.getParameter("postName");
        }
        if (req.getParameter("postTheme") != null) {
            postTheme = req.getParameter("postTheme");
        }
        if (req.getParameter("post") != null) {
            post = req.getParameter("post");
        }

        if (req.getParameter("draft") != null) {
            draft = req.getParameter("draft");
        }
        if (req.getParameter("publicationDate") != null) {
            publicationDate = req.getParameter("publicationDate");
            if (publicationDate.length() < 3) {
                publicationDate = null;
            }
        }
        try {
            getPostDataBase(postAuthor, postName, postTheme, post, publicationDate, draft);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void getPostDataBase(String... strings) throws SQLException {
        SecurityContext securityContext = SecurityContext.getInstance();
        Connection connection = securityContext.connection();
        Statement statement = connection.createStatement();
        connection.setAutoCommit(false);
        if (publicationDate == null) {
            try {
                statement.executeUpdate("insert into posts (postAuthor, postName, postTheme, post, draft) value ('"+postAuthor+"', '"+postName+"', '"+postTheme+"', '"+post+"', '"+draft+"');");
                connection.setAutoCommit(true);
            } catch (Exception exception) {
                connection.rollback();
                exception.printStackTrace();
            }
        } else {
            try {
                statement.executeUpdate("insert into posts (postAuthor, publicationDate ,postName, postTheme, post, draft) value ('"+postAuthor+"', '"+publicationDate+"','"+postName+"', '"+postTheme+"', '"+post+"', '"+draft+"');");
                connection.setAutoCommit(true);
            } catch (Exception exception) {
                connection.rollback();
                exception.printStackTrace();
            }
        }
    }


}
