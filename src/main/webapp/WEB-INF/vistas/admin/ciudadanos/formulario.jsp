<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <jsp:include page="../../fragments/head.jsp">
            <jsp:param name="title" value="${ciudadano != null ? 'Editar' : 'Nuevo'} Ciudadano"/>
        </jsp:include>
    </head>
    <body>
        <div class="admin-layout">
            <jsp:include page="../../fragments/admin-sidebar.jsp">
                <jsp:param name="active" value="ciudadanos"/>
            </jsp:include>

            <main class="admin-main">
                <div class="container" style="max-width:720px;">
                    <div style="margin-bottom:1.5rem;">
                        <a href="${pageContext.request.contextPath}/admin/ciudadanos" style="color:var(--azul-acento); text-decoration:none; font-size:0.9rem;">
                            <i class="fas fa-arrow-left"></i> Volver al listado
                        </a>
                        <h2 style="margin-top:0.5rem; font-size:1.5rem; color:var(--azul-institucional);">
                            ${ciudadano != null ? 'Editar' : 'Registrar'} Ciudadano
                        </h2>
                    </div>

                    <div class="card">
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/admin/ciudadanos" method="post">
                                <c:if test="${ciudadano != null}">
                                    <input type="hidden" name="id" value="${ciudadano.id}">
                                </c:if>

                                <div class="form-row">
                                    <div class="form-group">
                                        <label>Número de Documento</label>
                                        <input type="text" name="numeroDocumento" class="form-control" required
                                        value="${ciudadano != null ? ciudadano.numeroDocumento : ''}">
                                    </div>
                                    <div class="form-group">
                                        <label>Fecha de Nacimiento</label>
                                        <input type="date" name="fechaNacimiento" class="form-control" required
                                        value="${ciudadano != null ? ciudadano.fechaNacimiento : ''}">
                                    </div>
                                </div>

                                <div class="form-row">
                                    <div class="form-group">
                                        <label>Nombres</label>
                                        <input type="text" name="nombres" class="form-control" required
                                        value="${ciudadano != null ? ciudadano.nombres : ''}">
                                    </div>
                                    <div class="form-group">
                                        <label>Apellidos</label>
                                        <input type="text" name="apellidos" class="form-control" required
                                        value="${ciudadano != null ? ciudadano.apellidos : ''}">
                                    </div>
                                </div>

                                <div class="form-row">
                                    <div class="form-group">
                                        <label>Teléfono</label>
                                        <input type="text" name="telefono" class="form-control"
                                        value="${ciudadano != null ? ciudadano.telefono : ''}">
                                    </div>
                                    <div class="form-group">
                                        <label>Correo Electrónico</label>
                                        <input type="email" name="correo" class="form-control"
                                        value="${ciudadano != null ? ciudadano.correo : ''}">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Vereda / Barrio</label>
                                    <input type="text" name="veredaBarrio" class="form-control"
                                    value="${ciudadano != null ? ciudadano.veredaBarrio : ''}">
                                </div>

                                <div class="form-group">
                                    <label>Mesa de Votación Asignada</label>
                                    <select name="idMesa" class="form-control">
                                        <option value="">Sin asignar</option>
                                        <c:forEach var="m" items="${mesas}">
                                            <option value="${m.id}" ${ciudadano != null && ciudadano.idMesa == m.id ? 'selected' : ''}>
                                                ${m.identificacionCompleta} (Cap: ${m.capacidad})
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Rol en el Sistema</label>
                                    <select name="rol" class="form-control" required>
                                        <option value="VOTANTE" ${rol == 'VOTANTE' ? 'selected' : ''}>Votante</option>
                                        <option value="ADMIN" ${rol == 'ADMIN' ? 'selected' : ''}>Administrador</option>
                                    </select>
                                    <small style="color:var(--gris-texto); font-size:0.8rem;">
                                        <i class="fas fa-circle-info"></i> La contraseña inicial será el número de documento.
                                    </small>
                                </div>
                                <div style="display:flex; gap:0.75rem; justify-content:flex-end; margin-top:1.5rem;">
                                    <a href="${pageContext.request.contextPath}/admin/ciudadanos" class="btn btn-secundario">Cancelar</a>
                                    <button type="submit" class="btn btn-primario">
                                        <i class="fas fa-save"></i> Guardar Ciudadano
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