<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <jsp:include page="../../fragments/head.jsp">
            <jsp:param name="title" value="Todos los Documentos"/>
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
                        <h2 style="font-size:1.5rem; color:var(--azul-institucional);">Registro de Documentos Expedidos</h2>
                        <p style="color:var(--gris-texto);">Vista global con información del ciudadano</p>
                    </div>

                    <div class="card">
                        <div class="card-header">
                            <h3><i class="fas fa-file-invoice"></i> Documentos en el Sistema</h3>
                            <div style="display:flex; gap:0.5rem;">
                                <input type="text" id="buscarDoc" placeholder="Buscar documento o cédula..." class="form-control" style="width:260px;" onkeyup="filtrarTabla()">
                            </div>
                        </div>
                        <div class="card-body" style="padding:0; overflow-x:auto;">
                            <table class="tabla" id="tablaDocumentos">
                                <thead>
                                    <tr>
                                        <th>Ciudadano</th>
                                        <th>Cédula</th>
                                        <th>Tipo</th>
                                        <th>Serie</th>
                                        <th>Expedición</th>
                                        <th>Vencimiento</th>
                                        <th>Estado</th>
                                        <th style="width:80px;">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="d" items="${documentos}">
                                        <tr>
                                            <td><strong>${d.nombreCompletoCiudadano}</strong></td>
                                            <td>${d.numeroDocumentoCiudadano}</td>
                                            <td>${d.tipoDocumento}</td>
                                            <td>${d.numeroSerie}</td>
                                            <td>${d.fechaExpedicion}</td>
                                            <td>${d.fechaVencimiento != null ? d.fechaVencimiento : 'N/A'}</td>
                                            <td><span class="badge badge-${d.estado}">${d.estado.toUpperCase()}</span></td>
                                            <td class="acciones">
                                                <a href="${pageContext.request.contextPath}/admin/documentos?action=editar&id=${d.id}" class="btn btn-primario btn-sm" title="Editar">
                                                    <i class="fas fa-pen"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <c:if test="${empty documentos}">
                                <div class="text-center" style="padding:3rem; color:var(--gris-texto);">
                                    <i class="fas fa-folder-open" style="font-size:2rem; margin-bottom:0.5rem; display:block;"></i>
                                    No hay documentos registrados.
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </main>
        </div>

        <script>
            function filtrarTabla() {
                const input = document.getElementById('buscarDoc').value.toLowerCase();
                const filas = document.querySelectorAll('#tablaDocumentos tbody tr');
                filas.forEach(fila => {
                    const texto = fila.innerText.toLowerCase();
                    fila.style.display = texto.includes(input) ? '' : 'none';
                });
            }
        </script>
    </body>
</html>