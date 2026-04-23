<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <jsp:include page="../fragments/head.jsp">
            <jsp:param name="title" value="Resultado Consulta"/>
        </jsp:include>
    </head>
    <body class="pagina-publica">
        <header class="header-gov">
            <div class="container">
                <h1><i class="fas fa-landmark"></i> Registraduría Municipal de Nobsa</h1>
                <a href="${pageContext.request.contextPath}/logout"><i class="fas fa-right-from-bracket"></i> Salir</a>
            </div>
        </header>

        <main style="flex:1; padding:0 1rem;">
            <div class="resultado-mesa" style="animation: modalIn 0.4s ease-out;">
                <c:choose>
                    <c:when test="${resultado != null and resultado.tieneMesaAsignada}">
                        <div class="etiqueta-inst">Mesa de Votación Asignada</div>
                        <div class="nombre-ciudadano">${resultado.nombreCompleto}</div>
                        <div style="color:var(--gris-texto); font-size:0.95rem; margin-bottom:1rem;">
                            C.C. ${resultado.numeroDocumento}
                        </div>

                        <div class="caja-mesa">
                            <div style="font-size:1rem; color:var(--gris-texto); margin-bottom:0.5rem;">Mesa Número</div>
                            <div class="numero-mesa-display">${resultado.numeroMesa}</div>
                        </div>

                        <div class="info-secundaria">
                            <div class="info-item">
                                <label>Ciudad</label>
                                <span>${resultado.ciudad}</span>
                            </div>
                            <div class="info-item">
                                <label>Zona</label>
                                <span>${resultado.nombreZona}</span>
                            </div>
                            <div class="info-item">
                                <label>Puesto</label>
                                <span>${resultado.puestoVotacion}</span>
                            </div>
                            <div class="info-item">
                                <label>Dirección</label>
                                <span>${resultado.direccion}</span>
                            </div>
                            <div class="info-item">
                                <label>Capacidad</label>
                                <span>${resultado.capacidad} votantes</span>
                            </div>
                            <div class="info-item">
                                <label>Código DANE</label>
                                <span>${resultado.codigoDane}</span>
                            </div>
                        </div>

                        <div style="margin-top:2rem; display:flex; gap:1rem; justify-content:center; flex-wrap:wrap;">
                            <button onclick="window.print()" class="btn btn-primario">
                                <i class="fas fa-print"></i> Imprimir Comprobante
                            </button>
                            <a href="${pageContext.request.contextPath}/consulta?action=documentos" class="btn btn-secundario">
                                <i class="fas fa-id-card"></i> Ver Mis Documentos
                            </a>
                        </div>
                    </c:when>

                    <c:otherwise>
                        <div class="no-inscrito text-center">
                            <i class="fas fa-circle-info"></i>
                            <h3 style="font-size:1.25rem; margin-bottom:0.5rem;">Ciudadano no inscrito</h3>
                            <p>El ciudadano con documento <strong>${resultado.numeroDocumento}</strong> no tiene una mesa de votación asignada en este momento.</p>
                            <p style="margin-top:1rem; font-size:0.875rem;">Por favor acérquese a la Registraduría Municipal para mayor información.</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>

        <footer class="footer-gov">
            <div class="container">
                <p>Registraduría Municipal de Nobsa · Boyacá · Colombia</p>
            </div>
        </footer>
    </body>
</html>