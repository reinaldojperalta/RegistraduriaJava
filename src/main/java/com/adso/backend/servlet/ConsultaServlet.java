package com.adso.backend.servlet;

import java.io.IOException;
import java.util.List;

import com.adso.backend.config.RecaptchaConfig;
import com.adso.backend.dao.CiudadanoDAO;
import com.adso.backend.dao.CiudadanoDAOImpl;
import com.adso.backend.dao.ConsultaDAO;
import com.adso.backend.dao.ConsultaDAOImpl;
import com.adso.backend.dao.DocumentoDAO;
import com.adso.backend.dao.DocumentoDAOImpl;
import com.adso.backend.model.Ciudadano;
import com.adso.backend.model.ConsultaDTO;
import com.adso.backend.model.Documento;
import com.adso.backend.model.Usuario;
import com.adso.backend.util.RecaptchaUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/consulta")
public class ConsultaServlet extends HttpServlet {

    private ConsultaDAO consultaDAO;
    private DocumentoDAO documentoDAO;
    private CiudadanoDAO ciudadanoDAO;
    private RecaptchaConfig recaptchaConfig;

    @Override
    public void init() throws ServletException {
        consultaDAO = new ConsultaDAOImpl();
        documentoDAO = new DocumentoDAOImpl();
        ciudadanoDAO = new CiudadanoDAOImpl();
        recaptchaConfig = RecaptchaConfig.getInstancia();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        Usuario votante = (session != null) ? (Usuario) session.getAttribute("votante") : null;

        String action = req.getParameter("action");

        // === 1. VER DOCUMENTOS (requiere sesión de votante) ===
        if ("documentos".equals(action)) {
            if (votante == null) {
                resp.sendRedirect(req.getContextPath() + "/consulta");
                return;
            }
            try {
                Ciudadano c = ciudadanoDAO.buscarPorDocumento(votante.getNumeroDocumento());
                if (c != null) {
                    List<Documento> documentos = documentoDAO.listarPorCiudadano(c.getId());
                    req.setAttribute("documentos", documentos);
                }
                req.getRequestDispatcher("/WEB-INF/vistas/consulta/documentos.jsp").forward(req, resp);
            } catch (Exception e) {
                throw new ServletException("Error cargando documentos", e);
            }
            return;
        }

        // === 2. CARGAR FORMULARIO (cualquier otro GET) ===
        req.setAttribute("siteKey", recaptchaConfig.getSiteKey());
        req.setAttribute("recaptchaEnabled", recaptchaConfig.isEnabled());
        req.getRequestDispatcher("/WEB-INF/vistas/consulta/formulario.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println(">>> doPost START");

        String documento = req.getParameter("documento");
        String recaptchaResponse = req.getParameter("g-recaptcha-response");
        System.out.println(">>> documento param: " + documento);
        System.out.println(">>> g-recaptcha-response param: " + (recaptchaResponse != null ? "presente" : "NULL"));

        // Validar reCAPTCHA
        if (recaptchaConfig.isEnabled()) {
            System.out.println(">>> reCAPTCHA habilitado, validando...");
            try {
                RecaptchaUtil util = new RecaptchaUtil(recaptchaConfig.getSecretKey());
                boolean captchaValido = util.validar(recaptchaResponse);
                System.out.println(">>> reCAPTCHA resultado: " + captchaValido);

                if (!captchaValido) {
                    System.out.println(">>> reCAPTCHA inválido, volviendo al formulario");
                    req.setAttribute("error", "Por favor verifica que no eres un robot.");
                    req.setAttribute("documento", documento);
                    req.setAttribute("siteKey", recaptchaConfig.getSiteKey());
                    req.setAttribute("recaptchaEnabled", recaptchaConfig.isEnabled());
                    req.getRequestDispatcher("/WEB-INF/vistas/consulta/formulario.jsp").forward(req, resp);
                    return;
                }
            } catch (Exception e) {
                System.out.println(">>> EXCEPCIÓN en reCAPTCHA: " + e.getMessage());
                e.printStackTrace();
                throw new ServletException("Error validando reCAPTCHA", e);
            }
        } else {
            System.out.println(">>> reCAPTCHA deshabilitado");
        }

        try {
            System.out.println(">>> Llamando a consultaDAO.consultarMesaPorDocumento...");
            ConsultaDTO resultado = consultaDAO.consultarMesaPorDocumento(documento);
            System.out.println(">>> Resultado DAO: " + (resultado != null ? "ENCONTRADO" : "NULL"));

            if (resultado == null) {
                System.out.println(">>> Resultado null, forwarding a formulario.jsp");
                req.setAttribute("error", "El número de documento no está registrado en el sistema.");
                req.setAttribute("busquedaRealizada", true);
                req.setAttribute("siteKey", recaptchaConfig.getSiteKey());
                req.setAttribute("recaptchaEnabled", recaptchaConfig.isEnabled());
                req.getRequestDispatcher("/WEB-INF/vistas/consulta/formulario.jsp").forward(req, resp);
            } else {
                System.out.println(">>> Resultado encontrado, creando sesión...");
                HttpSession session = req.getSession();
                Usuario votanteTemp = new Usuario.Builder()
                        .numeroDocumento(resultado.getNumeroDocumento())
                        .nombres(resultado.getNombres())
                        .apellidos(resultado.getApellidos())
                        .rol("VOTANTE")
                        .build();
                session.setAttribute("votante", votanteTemp);

                System.out.println(">>> Buscando ciudadano y documentos...");
                Ciudadano c = ciudadanoDAO.buscarPorDocumento(documento);
                if (c != null) {
                    List<Documento> documentos = documentoDAO.listarPorCiudadano(c.getId());
                    req.setAttribute("documentos", documentos);
                    System.out.println(">>> Documentos cargados: " + documentos.size());
                } else {
                    System.out.println(">>> Ciudadano no encontrado para documentos");
                }

                req.setAttribute("resultado", resultado);
                req.setAttribute("busquedaRealizada", true);
                System.out.println(">>> Forwarding a resultado.jsp");
                req.getRequestDispatcher("/WEB-INF/vistas/consulta/resultado.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            System.out.println(">>> EXCEPCIÓN en DAO/Forward: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Error en consulta", e);
        }
    }
}