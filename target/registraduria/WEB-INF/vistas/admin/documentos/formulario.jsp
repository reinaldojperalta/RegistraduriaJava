<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <jsp:include page="../../fragments/head.jsp">
            <jsp:param name="title" value="${modo == 'editar' ? 'Editar' : 'Expedir'} Documento"/>
        </jsp:include>
    </head>
    <body>
        <div class="admin-layout">
            <jsp:include page="../../fragments/admin-sidebar.jsp">
                <jsp:param name="active" value="documentos"/>
            </jsp:include>

            <main class="admin-main">
                <div class="container" style="max-width:720px;">
                    <div style="margin-bottom:1.5rem;">
                        <a href="${pageContext.request.contextPath}/admin/documentos?action=listar&idCiudadano=${ciudadano.id}" style="color:var(--azul-acento); text-decoration:none; font-size:0.9rem;">
                            <i class="fas fa-arrow-left"></i> Volver a documentos
                        </a>
                        <h2 style="margin-top:0.5rem; font-size:1.5rem; color:var(--azul-institucional);">
                            ${modo == 'editar' ? 'Editar' : 'Expedir'} Documento
                        </h2>
                        <p style="color:var(--gris-texto); font-size:0.9rem;">
                            Ciudadano: <strong>${ciudadano.nombreCompleto}</strong> · C.C. ${ciudadano.numeroDocumento}
                        </p>
                    </div>

                    <div class="card">
                        <div class="card-body">
                            <!-- ALERTA DE ERROR: solo un form, solo un card-body -->
                            <c:if test="${not empty error}">
                                <div class="alert alert-error" style="margin-bottom:1.5rem;">
                                    <i class="fas fa-circle-exclamation"></i> ${error}
                                </div>
                            </c:if>

                            <form action="${pageContext.request.contextPath}/admin/documentos" method="post">
                                <input type="hidden" name="idCiudadano" value="${ciudadano.id}">
                                <c:if test="${modo == 'editar'}">
                                    <input type="hidden" name="id" value="${documento.id}">
                                </c:if>

                                <div class="form-row">
                                    <div class="form-group">
                                        <label>Tipo de Documento</label>
                                        <select name="tipoDocumento" class="form-control" required>
                                            <option value="">Seleccione...</option>
                                            <option value="Cédula de Ciudadanía" ${documento.tipoDocumento == 'Cédula de Ciudadanía' ? 'selected' : ''}>Cédula de Ciudadanía</option>
                                            <option value="Tarjeta de Identidad" ${documento.tipoDocumento == 'Tarjeta de Identidad' ? 'selected' : ''}>Tarjeta de Identidad</option>
                                            <option value="Registro Civil de Nacimiento" ${documento.tipoDocumento == 'Registro Civil de Nacimiento' ? 'selected' : ''}>Registro Civil de Nacimiento</option>
                                            <option value="Contraseña" ${documento.tipoDocumento == 'Contraseña' ? 'selected' : ''}>Contraseña</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>Número de Serie</label>
                                        <input type="text" name="numeroSerie" class="form-control" required placeholder="Ej: CC-2025-00123"
                                        value="${documento != null ? documento.numeroSerie : ''}">
                                    </div>
                                </div>

                                <div class="form-row">
                                    <div class="form-group">
                                        <label>Fecha Expedición</label>
                                        <input type="date" name="fechaExpedicion" class="form-control" required
                                        value="${documento != null ? documento.fechaExpedicion : ''}">
                                    </div>
                                    <div class="form-group">
                                        <label>Fecha Vencimiento (opcional)</label>
                                        <input type="date" name="fechaVencimiento" class="form-control"
                                        value="${documento != null ? documento.fechaVencimiento : ''}">
                                    </div>
                                </div>

                                <div class="form-row">
                                    <div class="form-group">
                                        <label>Estado</label>
                                        <select name="estado" class="form-control" required>
                                            <option value="vigente" ${documento != null && documento.estado == 'vigente' ? 'selected' : ''}>Vigente</option>
                                            <option value="vencido" ${documento != null && documento.estado == 'vencido' ? 'selected' : ''}>Vencido</option>
                                            <option value="cancelado" ${documento != null && documento.estado == 'cancelado' ? 'selected' : ''}>Cancelado</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Observaciones</label>
                                    <textarea name="observaciones" class="form-control" rows="3">${documento != null ? documento.observaciones : ''}</textarea>
                                </div>

                                <div style="display:flex; gap:0.75rem; justify-content:flex-end; margin-top:1.5rem;">
                                    <a href="${pageContext.request.contextPath}/admin/documentos?action=listar&idCiudadano=${ciudadano.id}" class="btn btn-secundario">Cancelar</a>
                                    <button type="submit" class="btn btn-primario">
                                        <i class="fas fa-save"></i> ${modo == 'editar' ? 'Actualizar' : 'Guardar'} Documento
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