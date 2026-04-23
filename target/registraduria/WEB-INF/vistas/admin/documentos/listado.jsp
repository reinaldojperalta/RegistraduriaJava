<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <jsp:include page="../../fragments/head.jsp">
            <jsp:param name="title" value="Documentos del Ciudadano"/>
        </jsp:include>
    </head>
    <body>
        <div class="admin-layout">
            <jsp:include page="../../fragments/admin-sidebar.jsp">
                <jsp:param name="active" value="documentos"/>
            </jsp:include>

            <main class="admin-main">
                <div class="container">
                    <div style="margin-bottom:1.5rem;">
                        <a href="${pageContext.request.contextPath}/admin/ciudadanos" style="color:var(--azul-acento); text-decoration:none; font-size:0.9rem; font-weight:500;">
                            <i class="fas fa-arrow-left"></i> Volver a personas
                        </a>
                        <h2 style="margin-top:0.5rem; font-size:1.5rem; color:var(--azul-institucional);">
                            Documentos de ${ciudadano.nombreCompleto}
                        </h2>
                        <p style="color:var(--gris-texto); font-size:0.9rem;">C.C. ${ciudadano.numeroDocumento}</p>
                    </div>

                    <div class="card">
                        <div class="card-header">
                            <h3><i class="fas fa-id-card"></i> Historial Documental</h3>
                            <button class="btn btn-primario btn-sm" onclick="abrirModal('modalDocumento')">
                                <i class="fas fa-plus"></i> Expedir Documento
                            </button>
                        </div>
                        <div class="card-body" style="padding:0; overflow-x:auto;">
                            <table class="tabla">
                                <thead>
                                    <tr>
                                        <th>Tipo</th>
                                        <th>Número de Serie</th>
                                        <th>Expedición</th>
                                        <th>Vencimiento</th>
                                        <th>Estado</th>
                                        <th>Observaciones</th>
                                        <th style="width:120px;">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="d" items="${documentos}">
                                        <tr>
                                            <td><strong>${d.tipoDocumento}</strong></td>
                                            <td>${d.numeroSerie}</td>
                                            <td>${d.fechaExpedicion}</td>
                                            <td>${d.fechaVencimiento != null ? d.fechaVencimiento : 'Sin vencimiento'}</td>
                                            <td>
                                                <span class="badge badge-${d.estado}">${d.estado.toUpperCase()}</span>
                                            </td>
                                            <td style="max-width:200px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;">${d.observaciones != null ? d.observaciones : '-'}</td>
                                            <td class="acciones">
                                                <a href="${pageContext.request.contextPath}/admin/documentos?action=editar&id=${d.id}" class="btn btn-primario btn-sm" title="Editar">
                                                    <i class="fas fa-pen"></i>
                                                </a>
                                                <c:if test="${d.estaVigente}">
                                                    <a href="?action=cambiarEstado&id=${d.id}&estado=vencido&idCiudadano=${ciudadano.id}" class="btn btn-secundario btn-sm" title="Marcar vencido"><i class="fas fa-clock"></i></a>
                                                    <a href="?action=cambiarEstado&id=${d.id}&estado=cancelado&idCiudadano=${ciudadano.id}" class="btn btn-peligro btn-sm" title="Cancelar"><i class="fas fa-ban"></i></a>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <c:if test="${empty documentos}">
                                <div class="text-center" style="padding:3rem; color:var(--gris-texto);">
                                    <i class="fas fa-folder-open" style="font-size:2rem; margin-bottom:0.5rem; display:block;"></i>
                                    Este ciudadano no tiene documentos expedidos.
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </main>
        </div>

        <!-- Modal Nuevo Documento -->
        <!-- Modal Nuevo Documento -->
        <div id="modalDocumento" class="modal-overlay ${not empty errorModal ? 'active' : ''}">
            <div class="modal">
                <div class="modal-header">
                    <h4><i class="fas fa-file-signature"></i> Expedir Nuevo Documento</h4>
                    <button class="modal-close" onclick="cerrarModal('modalDocumento')"><i class="fas fa-xmark"></i></button>
                </div>
                <form action="${pageContext.request.contextPath}/admin/documentos" method="post">
                    <input type="hidden" name="idCiudadano" value="${ciudadano.id}">
                    <div class="modal-body">
                        <!-- ALERTA DE ERROR DENTRO DEL MODAL -->
                        <c:if test="${not empty errorModal}">
                            <div class="alert alert-error" style="margin-bottom:1rem;">
                                <i class="fas fa-circle-exclamation"></i> ${errorModal}
                            </div>
                        </c:if>

                        <div class="form-row">
                            <div class="form-group">
                                <label>Tipo de Documento</label>
                                <select name="tipoDocumento" class="form-control" required>
                                    <option value="">Seleccione...</option>
                                    <option value="Cédula de Ciudadanía" ${tipoDocumento == 'Cédula de Ciudadanía' ? 'selected' : ''}>Cédula de Ciudadanía</option>
                                    <option value="Tarjeta de Identidad" ${tipoDocumento == 'Tarjeta de Identidad' ? 'selected' : ''}>Tarjeta de Identidad</option>
                                    <option value="Registro Civil de Nacimiento" ${tipoDocumento == 'Registro Civil de Nacimiento' ? 'selected' : ''}>Registro Civil de Nacimiento</option>
                                    <option value="Contraseña" ${tipoDocumento == 'Contraseña' ? 'selected' : ''}>Contraseña</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Número de Serie</label>
                                <input type="text" name="numeroSerie" class="form-control" required placeholder="Ej: CC-2025-00123"
                                value="${numeroSerie != null ? numeroSerie : ''}">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group">
                                <label>Fecha Expedición</label>
                                <input type="date" name="fechaExpedicion" class="form-control" required
                                value="${fechaExpedicion != null ? fechaExpedicion : ''}">
                            </div>
                            <div class="form-group">
                                <label>Fecha Vencimiento (opcional)</label>
                                <input type="date" name="fechaVencimiento" class="form-control"
                                value="${fechaVencimiento != null ? fechaVencimiento : ''}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Estado</label>
                            <select name="estado" class="form-control" required>
                                <option value="vigente" ${estado == 'vigente' ? 'selected' : ''}>Vigente</option>
                                <option value="vencido" ${estado == 'vencido' ? 'selected' : ''}>Vencido</option>
                                <option value="cancelado" ${estado == 'cancelado' ? 'selected' : ''}>Cancelado</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Observaciones</label>
                            <textarea name="observaciones" class="form-control" rows="2">${observaciones != null ? observaciones : ''}</textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secundario" onclick="cerrarModal('modalDocumento')">Cancelar</button>
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