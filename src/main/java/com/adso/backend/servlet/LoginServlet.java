package com.adso.backend.servlet;

import java.io.IOException;

import com.adso.backend.dao.UsuarioDAO;
import com.adso.backend.dao.UsuarioDAOImpl;
import com.adso.backend.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(value = { "/login", "" })
public class LoginServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/vistas/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String numeroDocumento = req.getParameter("documento");
        String password = req.getParameter("password");

        try {
            Usuario usuario = usuarioDAO.buscarPorDocumento(numeroDocumento);

            if (usuario == null) {
                req.setAttribute("error", "El número de documento no está registrado en el sistema.");
                req.getRequestDispatcher("/WEB-INF/vistas/login.jsp").forward(req, resp);
                return;
            }

            if ("VOTANTE".equals(usuario.getRol())) {
                HttpSession session = req.getSession();
                session.setAttribute("votante", usuario);
                resp.sendRedirect(req.getContextPath() + "/consulta");
                return;
            }

            if ("ADMIN".equals(usuario.getRol())) {
                if (password == null || password.isEmpty()) {
                    req.setAttribute("documento", numeroDocumento);
                    req.setAttribute("requierePassword", true);
                    req.getRequestDispatcher("/WEB-INF/vistas/login.jsp").forward(req, resp);
                    return;
                }

                boolean valido = usuarioDAO.validarCredenciales(numeroDocumento, password);

                if (valido) {
                    HttpSession session = req.getSession();
                    session.setAttribute("admin", usuario);
                    resp.sendRedirect(req.getContextPath() + "/admin");
                } else {
                    req.setAttribute("error", "Contraseña incorrecta.");
                    req.setAttribute("documento", numeroDocumento);
                    req.setAttribute("requierePassword", true);
                    req.getRequestDispatcher("/WEB-INF/vistas/login.jsp").forward(req, resp);
                }
            }

        } catch (Exception e) {
            throw new ServletException("Error en autenticación", e);
        }
    }
}