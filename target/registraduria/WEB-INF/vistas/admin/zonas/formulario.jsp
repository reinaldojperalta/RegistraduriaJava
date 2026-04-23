<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <jsp:include page="../../fragments/head.jsp">
            <jsp:param name="title" value="${zona != null ? 'Editar' : 'Nueva'} Zona"/>
        </jsp:include>
    </head>
    <body>
        <div class="admin-layout">
            <jsp:include page="../../fragments/admin-sidebar.jsp">
                <jsp:param name="active" value="zonas"/>
            </jsp:include>

            <main class="admin-main">
                <div class="container" style="max-width:720px;">
                    <div style="margin-bottom:1.5rem;">
                        <a href="${pageContext.request.contextPath}/admin/zonas" style="color:var(--azul-acento); text-decoration:none; font-size:0.9rem;">
                            <i class="fas fa-arrow-left"></i> Volver al listado
                        </a>
                        <h2 style="margin-top:0.5rem; font-size:1.5rem; color:var(--azul-institucional);">
                            ${zona != null ? 'Editar' : 'Nueva'} Zona Electoral
                        </h2>
                    </div>

                    <div class="card">
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/admin/zonas" method="post">
                                <c:if test="${zona != null}">
                                    <input type="hidden" name="id" value="${zona.id}">
                                </c:if>

                                <div class="form-group">
                                    <label>Ciudad / Municipio</label>
                                    <select name="idCiudad" class="form-control" required>
                                        <option value="">Seleccione una ciudad...</option>
                                        <c:forEach var="c" items="${ciudades}">
                                            <option value="${c.id}" ${zona != null && zona.idCiudad == c.id ? 'selected' : ''}>
                                                ${c.nombre}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label>Nombre de la Zona</label>
                                    <input type="text" name="nombreZona" class="form-control" required
                                    value="${zona != null ? zona.nombreZona : ''}">
                                </div>

                                <div class="form-group">
                                    <label>Puesto de Votación</label>
                                    <input type="text" name="puestoVotacion" class="form-control" required
                                    value="${zona != null ? zona.puestoVotacion : ''}">
                                </div>

                                <div class="form-group">
                                    <label>Dirección del Puesto</label>
                                    <textarea name="direccion" class="form-control" rows="2" required>${zona != null ? zona.direccion : ''}</textarea>
                                </div>

                                <div style="display:flex; gap:0.75rem; justify-content:flex-end; margin-top:1.5rem;">
                                    <a href="${pageContext.request.contextPath}/admin/zonas" class="btn btn-secundario">Cancelar</a>
                                    <button type="submit" class="btn btn-primario">
                                        <i class="fas fa-save"></i> Guardar Zona
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