package com.adso.backend.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.adso.backend.dao.CiudadanoDAO;
import com.adso.backend.dao.CiudadanoDAOImpl;
import com.adso.backend.dao.DocumentoDAO;
import com.adso.backend.dao.DocumentoDAOImpl;
import com.adso.backend.model.Ciudadano;
import com.adso.backend.model.Documento;
import com.adso.backend.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/documentos")
public class DocumentoServlet extends HttpServlet {

    private DocumentoDAO documentoDAO;
    private CiudadanoDAO ciudadanoDAO;

    @Override
    public void init() throws ServletException {
        documentoDAO = new DocumentoDAOImpl();
        ciudadanoDAO = new CiudadanoDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!verificarAdmin(req, resp))
            return;

        String action = req.getParameter("action");
        String idCiudadanoParam = req.getParameter("idCiudadano");

        try {
            if ("nuevo".equals(action) && idCiudadanoParam != null) {
                int idCiudadano = Integer.parseInt(idCiudadanoParam);
                Ciudadano c = ciudadanoDAO.buscarPorId(idCiudadano);
                req.setAttribute("ciudadano", c);
                req.setAttribute("modo", "crear");
                req.getRequestDispatcher("/WEB-INF/vistas/admin/documentos/formulario.jsp").forward(req, resp);

            } else if ("editar".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                Documento d = documentoDAO.buscarPorId(id);
                Ciudadano c = ciudadanoDAO.buscarPorId(d.getIdCiudadano());
                req.setAttribute("documento", d);
                req.setAttribute("ciudadano", c);
                req.setAttribute("modo", "editar");
                req.getRequestDispatcher("/WEB-INF/vistas/admin/documentos/formulario.jsp").forward(req, resp);

            } else if ("listar".equals(action) && idCiudadanoParam != null) {
                int idCiudadano = Integer.parseInt(idCiudadanoParam);
                List<Documento> documentos = documentoDAO.listarPorCiudadano(idCiudadano);
                Ciudadano c = ciudadanoDAO.buscarPorId(idCiudadano);
                req.setAttribute("documentos", documentos);
                req.setAttribute("ciudadano", c);
                req.getRequestDispatcher("/WEB-INF/vistas/admin/documentos/listado.jsp").forward(req, resp);

            } else if ("cambiarEstado".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                String estado = req.getParameter("estado");
                documentoDAO.cambiarEstado(id, estado);
                resp.sendRedirect(req.getContextPath() + "/admin/documentos?action=listar&idCiudadano="
                        + req.getParameter("idCiudadano"));

            } else {
                List<Documento> documentos = documentoDAO.listarTodos();
                req.setAttribute("documentos", documentos);
                req.getRequestDispatcher("/WEB-INF/vistas/admin/documentos/listadoGlobal.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            throw new ServletException("Error en CRUD documentos", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!verificarAdmin(req, resp))
            return;

        String idParam = req.getParameter("id");

        try {
            Documento.Builder builder = new Documento.Builder()
                    .idCiudadano(Integer.parseInt(req.getParameter("idCiudadano")))
                    .tipoDocumento(req.getParameter("tipoDocumento"))
                    .numeroSerie(req.getParameter("numeroSerie"))
                    .fechaExpedicion(LocalDate.parse(req.getParameter("fechaExpedicion")))
                    .estado(req.getParameter("estado"))
                    .observaciones(req.getParameter("observaciones"));

            String fechaVencimiento = req.getParameter("fechaVencimiento");
            if (fechaVencimiento != null && !fechaVencimiento.isEmpty()) {
                builder.fechaVencimiento(LocalDate.parse(fechaVencimiento));
            }

            if (idParam != null && !idParam.isEmpty()) {
                // EDITAR
                builder.id(Integer.parseInt(idParam));
                documentoDAO.actualizar(builder.build());
            } else {
                // CREAR
                Documento d = builder.build();
                documentoDAO.insertar(d);
            }

            resp.sendRedirect(
                    req.getContextPath() + "/admin/documentos?action=listar&idCiudadano="
                            + req.getParameter("idCiudadano"));

        } catch (org.postgresql.util.PSQLException e) {
            // Error de llave duplicada (UNIQUE constraint)
            if (e.getMessage() != null && e.getMessage().contains("violates unique constraint")) {
                String numeroSerie = req.getParameter("numeroSerie");
                req.setAttribute("errorModal",
                        "El número de serie '" + numeroSerie + "' ya está registrado. Use uno diferente.");
                req.setAttribute("tipoDocumento", req.getParameter("tipoDocumento"));
                req.setAttribute("numeroSerie", numeroSerie);
                req.setAttribute("fechaExpedicion", req.getParameter("fechaExpedicion"));
                req.setAttribute("fechaVencimiento", req.getParameter("fechaVencimiento"));
                req.setAttribute("estado", req.getParameter("estado"));
                req.setAttribute("observaciones", req.getParameter("observaciones"));

                // Recargar datos para volver al formulario
                try {
                    Ciudadano c = ciudadanoDAO.buscarPorId(Integer.parseInt(req.getParameter("idCiudadano")));
                    List<Documento> documentos = documentoDAO.listarPorCiudadano(c.getId());
                    req.setAttribute("documentos", documentos);
                    req.setAttribute("ciudadano", c);

                    // Si venía de editar, recargar el documento
                    if (idParam != null && !idParam.isEmpty()) {
                        Documento d = documentoDAO.buscarPorId(Integer.parseInt(idParam));
                        req.setAttribute("documento", d);
                        req.setAttribute("modo", "editar");
                    } else {
                        req.setAttribute("modo", "crear");
                    }
                } catch (Exception ex) {
                    // Si falla recargar, al menos mostramos el error genérico
                }

                req.getRequestDispatcher("/WEB-INF/vistas/admin/documentos/formulario.jsp").forward(req, resp);
            } else {
                throw new ServletException("Error guardando documento", e);
            }

        } catch (Exception e) {
            throw new ServletException("Error guardando documento", e);
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