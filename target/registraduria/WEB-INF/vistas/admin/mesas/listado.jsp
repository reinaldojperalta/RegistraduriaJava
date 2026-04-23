<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <jsp:include page="../../fragments/head.jsp">
            <jsp:param name="title" value="Mesas de Votación"/>
        </jsp:include>
    </head>
    <body>
        <div class="admin-layout">
            <jsp:include page="../../fragments/admin-sidebar.jsp">
                <jsp:param name="active" value="mesas"/>
            </jsp:include>

            <main class="admin-main">
                <div class="container">
                    <div style="margin-bottom:1.5rem;">
                        <h2 style="font-size:1.5rem; color:var(--azul-institucional);">Mesas de Votación</h2>
                        <p style="color:var(--gris-texto);">Mesas asignadas por zona electoral</p>
                    </div>

                    <div class="card">
                        <div class="card-header">
                            <h3><i class="fas fa-check-to-slot"></i> Mesas Registradas</h3>
                            <button class="btn btn-primario btn-sm" onclick="abrirModal('modalMesa')">
                                <i class="fas fa-plus"></i> Nueva Mesa
                            </button>
                        </div>
                        <div class="card-body" style="padding:0; overflow-x:auto;">
                            <table class="tabla">
                                <thead>
                                    <tr>
                                        <th>Ciudad</th>
                                        <th>Zona</th>
                                        <th>Puesto</th>
                                        <th>Mesa No.</th>
                                        <th>Capacidad</th>
                                        <th style="width:120px;">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="m" items="${mesas}">
                                        <tr>
                                            <td>${m.nombreCiudad}</td>
                                            <td><strong>${m.nombreZona}</strong></td>
                                            <td style="max-width:220px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;">${m.puestoVotacion}</td>
                                            <td><span style="font-size:1.25rem; font-weight:700; color:var(--azul-institucional);">${m.numeroMesa}</span></td>
                                            <td>${m.capacidad} votantes</td>
                                            <td class="acciones">
                                                <a href="?action=editar&id=${m.id}" class="btn btn-secundario btn-sm" title="Editar"><i class="fas fa-pen"></i></a>
                                                <a href="?action=eliminar&id=${m.id}" class="btn btn-peligro btn-sm" title="Eliminar" onclick="return confirm('¿Eliminar la Mesa ${m.numeroMesa} de ${m.nombreZona}?')"><i class="fas fa-trash"></i></a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <c:if test="${empty mesas}">
                                <div class="text-center" style="padding:3rem; color:var(--gris-texto);">
                                    <i class="fas fa-chair" style="font-size:2rem; margin-bottom:0.5rem; display:block;"></i>
                                    No hay mesas registradas.
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </main>
        </div>

        <!-- Modal Nueva/Editar Mesa -->
        <div id="modalMesa" class="modal-overlay">
            <div class="modal">
                <div class="modal-header">
                    <h4><i class="fas fa-square-plus"></i> Registrar Mesa de Votación</h4>
                    <button class="modal-close" onclick="cerrarModal('modalMesa')"><i class="fas fa-xmark"></i></button>
                </div>
                <form action="${pageContext.request.contextPath}/admin/mesas" method="post">
                    <div class="modal-body">
                        <div class="form-group">
                            <label>Zona Electoral</label>
                            <select name="idZona" class="form-control" required>
                                <option value="">Seleccione zona...</option>
                                <c:forEach var="zona" items="${zonas}">
                                    <option value="${zona.id}">${zona.nombreZona} - ${zona.nombreCiudad}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-row">
                            <div class="form-group">
                                <label>Número de Mesa</label>
                                <input type="number" name="numeroMesa" class="form-control" required min="1" placeholder="Ej: 1">
                            </div>
                            <div class="form-group">
                                <label>Capacidad (votantes)</label>
                                <input type="number" name="capacidad" class="form-control" required min="1" value="200">
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secundario" onclick="cerrarModal('modalMesa')">Cancelar</button>
                        <button type="submit" class="btn btn-primario"><i class="fas fa-save"></i> Guardar</button>
                    </div>
                </form>
            </div>
        </div>

        <script>
            function abrirModal(id){ document.getElementById(id).classList.add('active'); }
            function cerrarModal(id){ document.getElementById(id).classList.remove('active'); }
            document.querySelectorAll('.modal-overlay').forEach(m => m.addEventListener('click', e => { if(e.target === m) m.classList.remove('active'); }));
        </script>
    </body>
</html>