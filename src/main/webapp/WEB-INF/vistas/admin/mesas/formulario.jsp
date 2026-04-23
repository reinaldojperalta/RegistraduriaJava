<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <jsp:include page="../../fragments/head.jsp">
            <jsp:param name="title" value="${mesa != null ? 'Editar' : 'Nueva'} Mesa"/>
        </jsp:include>
    </head>
    <body>
        <div class="admin-layout">
            <jsp:include page="../../fragments/admin-sidebar.jsp">
                <jsp:param name="active" value="mesas"/>
            </jsp:include>

            <main class="admin-main">
                <div class="container" style="max-width:600px;">
                    <div style="margin-bottom:1.5rem;">
                        <a href="${pageContext.request.contextPath}/admin/mesas" style="color:var(--azul-acento); text-decoration:none; font-size:0.9rem;">
                            <i class="fas fa-arrow-left"></i> Volver al listado
                        </a>
                        <h2 style="margin-top:0.5rem; font-size:1.5rem; color:var(--azul-institucional);">
                            ${mesa != null ? 'Editar' : 'Nueva'} Mesa de Votación
                        </h2>
                    </div>

                    <div class="card">
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/admin/mesas" method="post">
                                <c:if test="${mesa != null}">
                                    <input type="hidden" name="id" value="${mesa.id}">
                                </c:if>

                                <div class="form-group">
                                    <label>Zona Electoral</label>
                                    <select name="idZona" class="form-control" required>
                                        <option value="">Seleccione una zona...</option>
                                        <c:forEach var="z" items="${zonas}">
                                            <option value="${z.id}" ${mesa != null && mesa.idZona == z.id ? 'selected' : ''}>
                                                ${z.nombreZona} - ${z.puestoVotacion}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="form-row">
                                    <div class="form-group">
                                        <label>Número de Mesa</label>
                                        <input type="number" name="numeroMesa" class="form-control" required min="1"
                                        value="${mesa != null ? mesa.numeroMesa : ''}">
                                    </div>
                                    <div class="form-group">
                                        <label>Capacidad (votantes)</label>
                                        <input type="number" name="capacidad" class="form-control" required min="1"
                                        value="${mesa != null ? mesa.capacidad : ''}">
                                    </div>
                                </div>

                                <div style="display:flex; gap:0.75rem; justify-content:flex-end; margin-top:1.5rem;">
                                    <a href="${pageContext.request.contextPath}/admin/mesas" class="btn btn-secundario">Cancelar</a>
                                    <button type="submit" class="btn btn-primario">
                                        <i class="fas fa-save"></i> Guardar Mesa
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </body>
</html>