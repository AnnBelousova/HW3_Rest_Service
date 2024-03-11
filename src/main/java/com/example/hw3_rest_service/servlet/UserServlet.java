package com.example.hw3_rest_service.servlet;

import com.example.hw3_rest_service.dao.UserDao;
import com.example.hw3_rest_service.entity.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UserServlet", value = "/UserServlet")
public class UserServlet extends HttpServlet {
    final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String username = request.getParameter("username");
        final String email = request.getParameter("email");
        final User user = new User();
        boolean answer = false;
        try {
            answer = userDao.checkUser(email);
        } catch (SQLException e) {
            System.out.println(e);
        }
        if (answer) {
            response.sendRedirect("/ClaimDao");
        } else {
            user.setName(username);
            user.setEmail(email);
            userDao.insertUser(user);
        }
    }
}
