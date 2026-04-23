package com.adso.backend.servlet;

import java.io.IOException;

import com.adso.backend.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        Usuario admin = (session != null) ? (Usuario) session.getAttribute("admin") : null;

        if (admin == null || !admin.esAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.setAttribute("admin", admin);
        req.getRequestDispatcher("/WEB-INF/vistas/admin/dashboard.jsp").forward(req, resp);
    }
}