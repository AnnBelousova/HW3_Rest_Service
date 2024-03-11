package com.example.hw3_rest_service.servlet;

import com.example.hw3_rest_service.dao.ClaimDao;
import com.example.hw3_rest_service.service.ClaimService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ClaimServlet", value = "/ClaimServlet")
public class ClaimServlet extends HttpServlet {
    ClaimService claimService = new ClaimService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        response.addHeader("Content-Type", "application/json");
        if (email != null) {
            response.getWriter().println(claimService.getClaimListByEmail(email));
        } else {
            response.getWriter().println(claimService.getAllClaims());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addHeader("Content-Type", "application/json");
        response.getWriter().println(claimService.addClaim(request.getReader().lines().reduce("", String::concat)));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addHeader("Content-Type", "application/json");
        response.getWriter().println(claimService.updateClaim(request.getReader().lines().reduce("", String::concat)));
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("claim_id"));
        response.addHeader("Content-Type", "application/json");
        response.getWriter().println(claimService.deleteClaim(id));
    }
}
