package org.example;


import com.mysql.cj.xdevapi.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@MultipartConfig
@WebServlet(urlPatterns = "/admin")
public class AdminServlet extends HttpServlet {
    public static final String UPLOADS = "resources/uploads";
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
        try {
            saveFile(req, resp);
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
                statement.executeUpdate("insert into posts (postAuthor, postName, postTheme, post, draft) value ('" + postAuthor + "', '" + postName + "', '" + postTheme + "', '" + post + "', '" + draft + "');");
                connection.setAutoCommit(true);
            } catch (Exception exception) {
                connection.rollback();
                exception.printStackTrace();
            }
        } else {
            try {
                statement.executeUpdate("insert into posts (postAuthor, publicationDate ,postName, postTheme, post, draft) value ('" + postAuthor + "', '" + publicationDate + "','" + postName + "', '" + postTheme + "', '" + post + "', '" + draft + "');");
                connection.setAutoCommit(true);
            } catch (Exception exception) {
                connection.rollback();
                exception.printStackTrace();
            }
        }
    }

    public void saveFile(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {
        Part part = req.getPart("image");
        SecurityContext securityContext = SecurityContext.getInstance();
        Connection connection = securityContext.connection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select id from posts order by id desc limit 1;");
        int id = 0;
        while (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        String contentDisposition = part.getHeader("Content-Disposition");
        int start = contentDisposition.indexOf("filename=");
        start += "filename=".length();
        int end = contentDisposition.lastIndexOf("\"");
        String filename = contentDisposition.substring(start + 1, end);
        int indexExpansion = filename.indexOf(".");
        String extension = filename.substring(indexExpansion + 1, filename.length());
        String uploadsDirUrl = getServletContext().getRealPath(UPLOADS);
        String absolutePathToFile = uploadsDirUrl + "/" + id + "." + extension;
        String contentType = contentType(part);
        if (contentType.equals("image")) {
            part.write(absolutePathToFile);
            connection.setAutoCommit(false);
            try {
                statement.executeUpdate("update posts set extension = '" + extension + "' where id = " + id + ";");
                connection.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
                connection.rollback();
            }
        } else {
            connection.setAutoCommit(false);
            try {
                statement.executeUpdate("delete from posts where id = " + id + ";");
                connection.setAutoCommit(true);
            } catch (Exception e) {
                connection.rollback();
                e.printStackTrace();
            }
        }
    }

    private String contentType(Part part) {
        if (part == null) return null;
        String contentDisposition = part.getContentType();
        int end = contentDisposition.lastIndexOf("/");
        return contentDisposition.substring(0, end);
    }
}
