<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <jsp:include page="../fragments/head.jsp">
            <jsp:param name="title" value="Mis Documentos"/>
        </jsp:include>
    </head>
    <body class="pagina-publica">
        <header class="header-gov">
            <div class="container">
                <h1><i class="fas fa-landmark"></i> Registraduría Municipal de Nobsa</h1>
                <a href="${pageContext.request.contextPath}/consulta"><i class="fas fa-arrow-left"></i> Volver</a>
            </div>
        </header>

        <main style="flex:1; padding:2rem 1rem;">
            <div class="container" style="max-width:900px;">
                <div class="card">
                    <div class="card-header">
                        <h3><i class="fas fa-id-card"></i> Documentos Expedidos</h3>
                    </div>
                    <div class="card-body" style="padding:0; overflow-x:auto;">
                        <table class="tabla">
                            <thead>
                                <tr>
                                    <th>Tipo</th>
                                    <th>Número de Serie</th>
                                    <th>Fecha Expedición</th>
                                    <th>Vencimiento</th>
                                    <th>Estado</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="d" items="${documentos}">
                                    <tr>
                                        <td><strong>${d.tipoDocumento}</strong></td>
                                        <td>${d.numeroSerie}</td>
                                        <td>${d.fechaExpedicion}</td>
                                        <td>${d.fechaVencimiento != null ? d.fechaVencimiento : 'N/A'}</td>
                                        <td>
                                            <span class="badge badge-${d.estado}">
                                                ${d.estado.toUpperCase()}
                                            </span>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <c:if test="${empty documentos}">
                            <div class="text-center" style="padding:3rem; color:var(--gris-texto);">
                                <i class="fas fa-folder-open" style="font-size:2rem; margin-bottom:0.5rem; display:block;"></i>
                                No se encontraron documentos registrados.
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </main>

        <footer class="footer-gov">
            <div class="container"><p>Registraduría Municipal de Nobsa</p></div>
        </footer>
    </body>
</html>