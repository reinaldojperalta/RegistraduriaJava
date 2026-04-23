package com.adso.backend.filter;

import java.io.IOException;

import com.adso.backend.model.Usuario;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Filtro de autenticación.
 * Protege /admin/* (solo admins) y /consulta/* (solo votantes con sesión).
 */
@WebFilter(urlPatterns = { "/admin/*", "/consulta/*" })
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        HttpSession session = req.getSession(false);

        // Verificar rutas de admin
        if (uri.startsWith(contextPath + "/admin")) {
            if (!esAdmin(session)) {
                resp.sendRedirect(contextPath + "/login");
                return;
            }
        }

        // Verificar rutas de consulta (votante logueado)
        if (uri.startsWith(contextPath + "/consulta")) {
            String action = req.getParameter("action");

            // Si está intentando ver documentos o resultado, requiere sesión
            if ((action != null && action.equals("documentos")) ||
                    (uri.contains("/consulta/resultado"))) {
                if (!esVotante(session)) {
                    resp.sendRedirect(contextPath + "/login");
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    private boolean esAdmin(HttpSession session) {
        if (session == null)
            return false;
        Usuario u = (Usuario) session.getAttribute("admin");
        return u != null && "ADMIN".equals(u.getRol());
    }

    private boolean esVotante(HttpSession session) {
        if (session == null)
            return false;
        Usuario u = (Usuario) session.getAttribute("votante");
        return u != null && "VOTANTE".equals(u.getRol());
    }
}