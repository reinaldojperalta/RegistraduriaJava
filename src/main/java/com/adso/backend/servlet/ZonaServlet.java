package com.adso.backend.servlet;

import java.io.IOException;
import java.util.List;

import com.adso.backend.dao.CiudadDAO;
import com.adso.backend.dao.CiudadDAOImpl;
import com.adso.backend.dao.ZonaDAO;
import com.adso.backend.dao.ZonaDAOImpl;
import com.adso.backend.model.Ciudad;
import com.adso.backend.model.Usuario;
import com.adso.backend.model.Zona;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/zonas")
public class ZonaServlet extends HttpServlet {

    private ZonaDAO zonaDAO;
    private CiudadDAO ciudadDAO;

    @Override
    public void init() throws ServletException {
        zonaDAO = new ZonaDAOImpl();
        ciudadDAO = new CiudadDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!verificarAdmin(req, resp))
            return;

        String action = req.getParameter("action");

        try {
            if ("nuevo".equals(action)) {
                List<Ciudad> ciudades = ciudadDAO.listarTodas();
                req.setAttribute("ciudades", ciudades);
                req.getRequestDispatcher("/WEB-INF/vistas/admin/zonas/formulario.jsp").forward(req, resp);

            } else if ("editar".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                Zona z = zonaDAO.buscarPorId(id);
                List<Ciudad> ciudades = ciudadDAO.listarTodas();
                req.setAttribute("zona", z);
                req.setAttribute("ciudades", ciudades);
                req.getRequestDispatcher("/WEB-INF/vistas/admin/zonas/formulario.jsp").forward(req, resp);

            } else if ("eliminar".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                zonaDAO.eliminar(id);
                resp.sendRedirect(req.getContextPath() + "/admin/zonas");

            } else {
                List<Ciudad> ciudades = ciudadDAO.listarTodas();
                List<Zona> lista = zonaDAO.listarTodas();
                req.setAttribute("zonas", lista);
                req.setAttribute("ciudades", ciudades);
                req.getRequestDispatcher("/WEB-INF/vistas/admin/zonas/listado.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            throw new ServletException("Error en CRUD zonas", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!verificarAdmin(req, resp))
            return;

        try {
            Zona.Builder builder = new Zona.Builder()
                    .idCiudad(Integer.parseInt(req.getParameter("idCiudad")))
                    .nombreZona(req.getParameter("nombreZona"))
                    .puestoVotacion(req.getParameter("puestoVotacion"))
                    .direccion(req.getParameter("direccion"));

            String idParam = req.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                builder.id(Integer.parseInt(idParam));
                zonaDAO.actualizar(builder.build());
            } else {
                zonaDAO.insertar(builder.build());
            }

            resp.sendRedirect(req.getContextPath() + "/admin/zonas");

        } catch (Exception e) {
            throw new ServletException("Error guardando zona", e);
        }
    }

    private boolean verificarAdmin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session = req.getSession(false);
        Usuario admin = (session != null) ? (Usuario) session.getAttribute("admin") : null;

        if (admin == null || !admin.esAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return false;
        }
        return true;
    }
}