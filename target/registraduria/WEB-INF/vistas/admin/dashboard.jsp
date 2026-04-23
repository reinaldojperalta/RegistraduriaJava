<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <jsp:include page="../fragments/head.jsp">
            <jsp:param name="title" value="Panel Administrativo"/>
        </jsp:include>
    </head>
    <body>
        <div class="admin-layout">
            <jsp:include page="../fragments/admin-sidebar.jsp">
                <jsp:param name="active" value="dashboard"/>
            </jsp:include>

            <main class="admin-main">
                <div class="container">
                    <div style="margin-bottom:2rem;">
                        <h2 style="font-size:1.5rem; color:var(--azul-institucional); font-weight:600;">Bienvenido, ${admin.nombreCompleto}</h2>
                        <p style="color:var(--gris-texto);">Panel de gestión de la Registraduría Municipal de Nobsa</p>
                    </div>

                    <div style="display:grid; grid-template-columns:repeat(auto-fit, minmax(240px, 1fr)); gap:1.5rem; margin-bottom:2rem;">
                        <div class="card" style="padding:1.5rem; display:flex; align-items:center; gap:1rem;">
                            <div style="width:48px; height:48px; background:#dbeafe; color:var(--azul-institucional); border-radius:8px; display:flex; align-items:center; justify-content:center; font-size:1.25rem;">
                                <i class="fas fa-users"></i>
                            </div>
                            <div>
                                <div style="font-size:1.5rem; font-weight:700; color:#1f2937;">Ciudadanos</div>
                                <div style="font-size:0.875rem; color:var(--gris-texto);">Gestión de personas</div>
                            </div>
                        </div>
                        <div class="card" style="padding:1.5rem; display:flex; align-items:center; gap:1rem;">
                            <div style="width:48px; height:48px; background:#dcfce7; color:#166534; border-radius:8px; display:flex; align-items:center; justify-content:center; font-size:1.25rem;">
                                <i class="fas fa-id-card"></i>
                            </div>
                            <div>
                                <div style="font-size:1.5rem; font-weight:700; color:#1f2937;">Documentos</div>
                                <div style="font-size:0.875rem; color:var(--gris-texto);">Expedición y estados</div>
                            </div>
                        </div>
                        <div class="card" style="padding:1.5rem; display:flex; align-items:center; gap:1rem;">
                            <div style="width:48px; height:48px; background:#fef3c7; color:#92400e; border-radius:8px; display:flex; align-items:center; justify-content:center; font-size:1.25rem;">
                                <i class="fas fa-map-location-dot"></i>
                            </div>
                            <div>
                                <div style="font-size:1.5rem; font-weight:700; color:#1f2937;">Infraestructura</div>
                                <div style="font-size:0.875rem; color:var(--gris-texto);">Zonas y mesas</div>
                            </div>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-header">
                            <h3>Accesos rápidos</h3>
                        </div>
                        <div class="card-body" style="display:flex; gap:1rem; flex-wrap:wrap;">
                            <a href="${pageContext.request.contextPath}/admin/ciudadanos?action=nuevo" class="btn btn-primario"><i class="fas fa-plus"></i> Nueva Persona</a>
                            <a href="${pageContext.request.contextPath}/admin/documentos" class="btn btn-primario" style="background:#166534;"><i class="fas fa-file-signature"></i> Ver Documentos</a>
                            <a href="${pageContext.request.contextPath}/admin/zonas" class="btn btn-primario" style="background:#92400e;"><i class="fas fa-plus"></i> Nueva Zona</a>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </body>
</html>