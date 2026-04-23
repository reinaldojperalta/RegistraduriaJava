package com.adso.backend.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.adso.backend.dao.CiudadanoDAO;
import com.adso.backend.dao.CiudadanoDAOImpl;
import com.adso.backend.dao.MesaDAO;
import com.adso.backend.dao.MesaDAOImpl;
import com.adso.backend.dao.UsuarioDAO;
import com.adso.backend.dao.UsuarioDAOImpl;
import com.adso.backend.model.Ciudadano;
import com.adso.backend.model.Mesa;
import com.adso.backend.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/ciudadanos")
public class CiudadanoServlet extends HttpServlet {

    private CiudadanoDAO ciudadanoDAO;
    private MesaDAO mesaDAO;
    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        ciudadanoDAO = new CiudadanoDAOImpl();
        mesaDAO = new MesaDAOImpl();
        usuarioDAO = new UsuarioDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!verificarAdmin(req, resp))
            return;

        String action = req.getParameter("action");

        try {
            if ("nuevo".equals(action)) {
                List<Mesa> mesas = mesaDAO.listarTodas();
                req.setAttribute("mesas", mesas);
                req.getRequestDispatcher("/WEB-INF/vistas/admin/ciudadanos/formulario.jsp").forward(req, resp);

            } else if ("editar".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                Ciudadano c = ciudadanoDAO.buscarPorId(id);
                List<Mesa> mesas = mesaDAO.listarTodas();
                req.setAttribute("ciudadano", c);
                req.setAttribute("mesas", mesas);
                req.getRequestDispatcher("/WEB-INF/vistas/admin/ciudadanos/formulario.jsp").forward(req, resp);

            } else if ("eliminar".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));

                if (ciudadanoDAO.tieneDocumentos(id)) {
                    req.setAttribute("error", "No se puede eliminar: el ciudadano tiene documentos expedidos.");
                    req.setAttribute("mostrarModal", true);
                } else {
                    ciudadanoDAO.eliminar(id);
                    req.setAttribute("mensaje", "Ciudadano eliminado correctamente.");
                }

                listar(req, resp);

            } else {
                listar(req, resp);
            }

        } catch (Exception e) {
            throw new ServletException("Error en CRUD ciudadanos", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!verificarAdmin(req, resp))
            return;

        String idParam = req.getParameter("id");
        String rol = req.getParameter("rol");

        try {
            Ciudadano.Builder ciudadanoBuilder = new Ciudadano.Builder()
                    .numeroDocumento(req.getParameter("numeroDocumento"))
                    .nombres(req.getParameter("nombres"))
                    .apellidos(req.getParameter("apellidos"))
                    .fechaNacimiento(LocalDate.parse(req.getParameter("fechaNacimiento")))
                    .veredaBarrio(req.getParameter("veredaBarrio"))
                    .telefono(req.getParameter("telefono"))
                    .correo(req.getParameter("correo"));

            String idMesaParam = req.getParameter("idMesa");
            if (idMesaParam != null && !idMesaParam.isEmpty()) {
                ciudadanoBuilder.idMesa(Integer.parseInt(idMesaParam));
            } else {
                ciudadanoBuilder.idMesa(null);
            }

            if (idParam != null && !idParam.isEmpty()) {
                // === EDITAR EXISTENTE ===
                ciudadanoBuilder.id(Integer.parseInt(idParam));
                Ciudadano c = ciudadanoBuilder.build();
                ciudadanoDAO.actualizar(c);
                usuarioDAO.actualizarRolPorDocumento(c.getNumeroDocumento(), rol);

            } else {
                // === CREAR NUEVO ===
                Ciudadano c = ciudadanoBuilder.build();
                int idGenerado = ciudadanoDAO.insertarConId(c);
                c.setId(idGenerado);

                // Crear usuario con contraseña = número de documento (hasheada con BCrypt)
                String passwordHasheada = org.mindrot.jbcrypt.BCrypt.hashpw(
                        c.getNumeroDocumento(),
                        org.mindrot.jbcrypt.BCrypt.gensalt());

                Usuario nuevoUsuario = new Usuario.Builder()
                        .numeroDocumento(c.getNumeroDocumento())
                        .nombres(c.getNombres())
                        .apellidos(c.getApellidos())
                        .rol(rol)
                        .passwordHash(passwordHasheada)
                        .activo(true)
                        .build();

                usuarioDAO.insertar(nuevoUsuario);
            }

            resp.sendRedirect(req.getContextPath() + "/admin/ciudadanos");

        } catch (Exception e) {
            throw new ServletException("Error guardando ciudadano", e);
        }
    }

    private void listar(HttpServletRequest req, HttpServletResponse resp)
            throws Exception, ServletException, IOException {
        List<Ciudadano> lista = ciudadanoDAO.listarTodos();
        req.setAttribute("ciudadanos", lista);
        req.getRequestDispatcher("/WEB-INF/vistas/admin/ciudadanos/listado.jsp").forward(req, resp);
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