<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <jsp:include page="../../fragments/head.jsp">
            <jsp:param name="title" value="Zonas de Votación"/>
        </jsp:include>
    </head>
    <body>
        <div class="admin-layout">
            <jsp:include page="../../fragments/admin-sidebar.jsp">
                <jsp:param name="active" value="zonas"/>
            </jsp:include>

            <main class="admin-main">
                <div class="container">
                    <div style="margin-bottom:1.5rem;">
                        <h2 style="font-size:1.5rem; color:var(--azul-institucional);">Zonas de Votación</h2>
                        <p style="color:var(--gris-texto);">Puestos y zonas electorales del municipio</p>
                    </div>

                    <div class="card">
                        <div class="card-header">
                            <h3><i class="fas fa-map-location-dot"></i> Zonas Registradas</h3>
                            <button class="btn btn-primario btn-sm" onclick="abrirModal('modalZona')">
                                <i class="fas fa-plus"></i> Nueva Zona
                            </button>
                        </div>
                        <div class="card-body" style="padding:0; overflow-x:auto;">
                            <table class="tabla">
                                <thead>
                                    <tr>
                                        <th>Ciudad</th>
                                        <th>Zona</th>
                                        <th>Puesto de Votación</th>
                                        <th>Dirección</th>
                                        <th style="width:120px;">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="z" items="${zonas}">
                                        <tr>
                                            <td><span class="badge badge-vigente">${z.nombreCiudad}</span></td>
                                            <td><strong>${z.nombreZona}</strong></td>
                                            <td>${z.puestoVotacion}</td>
                                            <td style="max-width:280px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;">${z.direccion}</td>
                                            <td class="acciones">
                                                <a href="?action=editar&id=${z.id}" class="btn btn-secundario btn-sm" title="Editar"><i class="fas fa-pen"></i></a>
                                                <a href="?action=eliminar&id=${z.id}" class="btn btn-peligro btn-sm" title="Eliminar" onclick="return confirm('¿Eliminar la zona ${z.nombreZona}?')"><i class="fas fa-trash"></i></a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <c:if test="${empty zonas}">
                                <div class="text-center" style="padding:3rem; color:var(--gris-texto);">
                                    <i class="fas fa-map" style="font-size:2rem; margin-bottom:0.5rem; display:block;"></i>
                                    No hay zonas registradas.
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </main>
        </div>

        <!-- Modal Nueva/Editar Zona -->
        <div id="modalZona" class="modal-overlay">
            <div class="modal">
                <div class="modal-header">
                    <h4><i class="fas fa-map-pin"></i> Registrar Zona de Votación</h4>
                    <button class="modal-close" onclick="cerrarModal('modalZona')"><i class="fas fa-xmark"></i></button>
                </div>
                <form action="${pageContext.request.contextPath}/admin/zonas" method="post">
                    <div class="modal-body">
                        <div class="form-group">
                            <label>Ciudad / Municipio</label>
                            <select name="idCiudad" class="form-control" required>
                                <option value="">Seleccione...</option>
                                <c:forEach var="ciudad" items="${ciudades}">
                                    <option value="${ciudad.id}">${ciudad.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Nombre de la Zona</label>
                            <input type="text" name="nombreZona" class="form-control" required placeholder="Ej: Zona Centro">
                        </div>
                        <div class="form-group">
                            <label>Puesto de Votación</label>
                            <input type="text" name="puestoVotacion" class="form-control" required placeholder="Ej: Institución Educativa de Nobsa">
                        </div>
                        <div class="form-group">
                            <label>Dirección</label>
                            <input type="text" name="direccion" class="form-control" required placeholder="Ej: Calle 5 #4-32, Centro">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secundario" onclick="cerrarModal('modalZona')">Cancelar</button>
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