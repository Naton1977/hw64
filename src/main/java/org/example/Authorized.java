package org.example;


import javax.servlet.ServletConfig;
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

@WebServlet(urlPatterns = "/authorized")
public class Authorized extends HttpServlet {
    private String login;
    private String password;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            authorized(req, resp);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void authorized(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        addAdminData();
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login != null && password != null) {
            if (this.login.equals(login) && this.password.equals(password)) {
                User user = new User(login, password);
                HttpSession session = req.getSession();
                if (session.isNew() || session.getAttribute("User") == null) {
                    session.setAttribute("User", user);
                    resp.sendRedirect(req.getContextPath() + "/admin");
                }
            } else {
                req.setAttribute("admin", false);
                req.getRequestDispatcher(req.getContextPath() + "/login").forward(req, resp);
            }
        }
    }


    public void addAdminData() throws SQLException {
        SecurityContext securityContext = SecurityContext.getInstance();
        Connection connection = securityContext.connection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select  * from admintable");
        while (resultSet.next()) {
            login = resultSet.getString("AdminLogin");
            password = resultSet.getString("AdminPassword");
        }
    }
}
