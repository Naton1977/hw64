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
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/delete")
public class DeletePost extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id;
        if (req.getParameter("delete") != null) {
            id = req.getParameter("delete");
            System.out.println(id);
            try {
                int idInt = Integer.parseInt(id);
                deletePost(idInt);
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/admin");
        }


        if (req.getParameter("deletePost") != null) {
            try {
                selectPostsFromDataBase(req, resp);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    public void selectPostsFromDataBase(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Posts> postsList = new ArrayList<>();
        String postAuthor = "";
        String publicationDate = "";
        String postName = "";
        int id = 0;
        SecurityContext securityContext = SecurityContext.getInstance();
        Connection connection = securityContext.connection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from posts;");
        while (resultSet.next()) {
            postAuthor = resultSet.getString("postAuthor");
            publicationDate = resultSet.getString("publicationDate");
            postName = resultSet.getString("postName");
            id = resultSet.getInt("id");
            postsList.add(new Posts(id, postAuthor, publicationDate, postName));
        }
        req.setAttribute("Posts", postsList);
        req.getRequestDispatcher("WEB-INF/pages/deletepost.jsp").forward(req, resp);
    }

    public void deletePost(int id) throws SQLException {
        SecurityContext securityContext = SecurityContext.getInstance();
        Connection connection = securityContext.connection();
        Statement statement = connection.createStatement();
        connection.setAutoCommit(false);
        try {
            statement.executeUpdate("delete from posts where id= '" + id + "';");
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
        }
    }
}
