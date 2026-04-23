package com.adso.backend.servlet;

import java.io.IOException;
import java.util.List;

import com.adso.backend.dao.MesaDAO;
import com.adso.backend.dao.MesaDAOImpl;
import com.adso.backend.dao.ZonaDAO;
import com.adso.backend.dao.ZonaDAOImpl;
import com.adso.backend.model.Mesa;
import com.adso.backend.model.Usuario;
import com.adso.backend.model.Zona;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/mesas")
public class MesaServlet extends HttpServlet {

    private MesaDAO mesaDAO;
    private ZonaDAO zonaDAO;

    @Override
    public void init() throws ServletException {
        mesaDAO = new MesaDAOImpl();
        zonaDAO = new ZonaDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!verificarAdmin(req, resp))
            return;

        String action = req.getParameter("action");

        try {
            if ("nuevo".equals(action)) {
                List<Zona> zonas = zonaDAO.listarTodas();
                req.setAttribute("zonas", zonas);
                req.getRequestDispatcher("/WEB-INF/vistas/admin/mesas/formulario.jsp").forward(req, resp);

            } else if ("editar".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                Mesa m = mesaDAO.buscarPorId(id);
                List<Zona> zonas = zonaDAO.listarTodas();
                req.setAttribute("mesa", m);
                req.setAttribute("zonas", zonas);
                req.getRequestDispatcher("/WEB-INF/vistas/admin/mesas/formulario.jsp").forward(req, resp);

            } else if ("eliminar".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                mesaDAO.eliminar(id);
                resp.sendRedirect(req.getContextPath() + "/admin/mesas");

            } else {
                List<Zona> zonas = zonaDAO.listarTodas();
                List<Mesa> lista = mesaDAO.listarTodas();
                req.setAttribute("mesas", lista);
                req.setAttribute("zonas", zonas);
                req.getRequestDispatcher("/WEB-INF/vistas/admin/mesas/listado.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            throw new ServletException("Error en CRUD mesas", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!verificarAdmin(req, resp))
            return;

        try {
            Mesa.Builder builder = new Mesa.Builder()
                    .idZona(Integer.parseInt(req.getParameter("idZona")))
                    .numeroMesa(Integer.parseInt(req.getParameter("numeroMesa")))
                    .capacidad(Integer.parseInt(req.getParameter("capacidad")));

            String idParam = req.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                builder.id(Integer.parseInt(idParam));
                mesaDAO.actualizar(builder.build());
            } else {
                mesaDAO.insertar(builder.build());
            }

            resp.sendRedirect(req.getContextPath() + "/admin/mesas");

        } catch (Exception e) {
            throw new ServletException("Error guardando mesa", e);
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