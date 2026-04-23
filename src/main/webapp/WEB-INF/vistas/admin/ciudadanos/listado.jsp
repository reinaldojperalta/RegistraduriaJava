<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="../../fragments/head.jsp">
        <jsp:param name="title" value="Gestión de Personas"/>
    </jsp:include>
</head>
<body>
    <div class="admin-layout">
        <jsp:include page="../../fragments/admin-sidebar.jsp">
            <jsp:param name="active" value="ciudadanos"/>
        </jsp:include>

        <main class="admin-main">
            <div class="container">
                <div class="card">
                    <div class="card-header">
                        <h3><i class="fas fa-users"></i> Ciudadanos Registrados</h3>
                        <!-- BOTÓN NUEVO: ahora va a la página completa -->
                        <a href="${pageContext.request.contextPath}/admin/ciudadanos?action=nuevo" class="btn btn-primario btn-sm">
                            <i class="fas fa-plus"></i> Nuevo
                        </a>
                    </div>
                    <div class="card-body" style="padding:0; overflow-x:auto;">
                        <c:if test="${not empty mensaje}">
                            <div style="padding:1rem 1.5rem 0;">
                                <div class="alert alert-success"><i class="fas fa-check-circle"></i> ${mensaje}</div>
                            </div>
                        </c:if>
                        <c:if test="${not empty error}">
                            <div style="padding:1rem 1.5rem 0;">
                                <div class="alert alert-error"><i class="fas fa-circle-exclamation"></i> ${error}</div>
                            </div>
                        </c:if>

                        <table class="tabla">
                            <thead>
                                <tr>
                                    <th>Documento</th>
                                    <th>Nombre Completo</th>
                                    <th>Vereda/Barrio</th>
                                    <th>Teléfono</th>
                                    <th>Mesa</th>
                                    <th style="width:160px;">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="c" items="${ciudadanos}">
                                    <tr>
                                        <td><strong>${c.numeroDocumento}</strong></td>
                                        <td>${c.nombreCompleto}</td>
                                        <td>${c.veredaBarrio}</td>
                                        <td>${c.telefono != null ? c.telefono : '-'}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${c.idMesa != null}">
                                                    <span class="badge badge-vigente">Mesa ${c.idMesa}</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge badge-cancelado">No inscrito</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="acciones">
                                            <a href="?action=editar&id=${c.id}" class="btn btn-secundario btn-sm" title="Editar">
                                                <i class="fas fa-pen"></i>
                                            </a>
                                            <a href="?action=eliminar&id=${c.id}" class="btn btn-peligro btn-sm" title="Eliminar" onclick="return confirm('¿Eliminar a ${c.nombreCompleto}?')">
                                                <i class="fas fa-trash"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/admin/documentos?action=listar&idCiudadano=${c.id}" class="btn btn-primario btn-sm" style="background:#166534;" title="Ver Documentos">
                                                <i class="fas fa-id-card"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </main>
    </div>
</body>
</html>