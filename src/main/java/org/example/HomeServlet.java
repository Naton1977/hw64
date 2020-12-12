package org.example;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(urlPatterns = "/")
public class HomeServlet extends HttpServlet {
    private String URL;
    private String UserSQL;
    private String PasswordSQL;
    private int postId = 0;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        URL = servletContext.getInitParameter("URL");
        UserSQL = servletContext.getInitParameter("UserSQL");
        PasswordSQL = servletContext.getInitParameter("PasswordSQL");
        SecurityContext securityContext = SecurityContext.getInstance();
        securityContext.initSQl(URL, UserSQL, PasswordSQL);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            selectPostsFromDataBase(req, resp);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void selectPostsFromDataBase(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        int startPosition = 0;
        int postPosition = 0;
        int countLines = countLines(req, resp);
        if (countLines > 0) {
            postPosition = 3;
        }
        int position = 0;
        if (req.getParameter("postNewer") != null) {
            String postNever = req.getParameter("postNewer");
            try {
                position = Integer.parseInt(postNever);
            } catch (Exception e) {
                e.printStackTrace();
            }
            startPosition += position;
            postPosition += position;
            if (postPosition > countLines) {
                postPosition = countLines;
            }

        }

        if (req.getParameter("postOlder") != null) {
            String postNever = req.getParameter("postOlder");
            try {
                position = Integer.parseInt(postNever);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int temp = position;
            if (temp % 3 == 1) {
                postPosition = position - 1;
                startPosition = position - 4;
            }
            if (temp % 3 == 2) {
                postPosition = position - 2;
                startPosition = position - 5;
            }
            if (temp % 3 == 0) {
                postPosition = position - 3;
                startPosition = position - 6;
            }
        }


        int id = 0;
        String postAuthor;
        String publicationDate;
        String postName;
        String postTheme;
        String extension;
        int count = 1;
        SecurityContext securityContext = SecurityContext.getInstance();
        Connection connection = securityContext.connection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from posts where draft = 'no' limit " + startPosition + ", " + postPosition + ";");
        while (resultSet.next()) {
            id = resultSet.getInt("id");
            postAuthor = resultSet.getString("postAuthor");
            publicationDate = resultSet.getString("publicationDate");
            postName = resultSet.getString("postName");
            postTheme = resultSet.getString("postTheme");
            extension = resultSet.getString("extension");
            req.setAttribute("id" + count + "", id);
            req.setAttribute("postAuthor" + count + "", postAuthor);
            req.setAttribute("publicationDate" + count + "", publicationDate);
            req.setAttribute("postName" + count + "", postName);
            req.setAttribute("postTheme" + count + "", postTheme);
            req.setAttribute("extension" + count + "", extension);
            count++;
        }
        req.setAttribute("startPosition", startPosition);
        req.setAttribute("countLines", countLines);
        req.setAttribute("postPosition", postPosition);
        req.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(req, resp);
    }

    public int countLines(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        int countLines = 0;
        SecurityContext securityContext = SecurityContext.getInstance();
        Connection connection = securityContext.connection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select count(*) from posts where draft = 'no';");
        if (resultSet.next()) {
            countLines = resultSet.getInt(1);
        }
        return countLines;
    }
}















