<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<aside class="sidebar">
    <div class="sidebar-brand">
        <h2><i class="fas fa-shield-halved"></i> Registraduría</h2>
        <small>Panel Administrativo</small>
    </div>
    <ul class="sidebar-menu">
        <li><a href="${pageContext.request.contextPath}/admin" class="${param.active eq 'dashboard' ? 'active' : ''}"><i class="fas fa-chart-pie"></i> Dashboard</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/ciudadanos" class="${param.active eq 'ciudadanos' ? 'active' : ''}"><i class="fas fa-users"></i> Personas</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/documentos" class="${param.active eq 'documentos' ? 'active' : ''}"><i class="fas fa-id-card"></i> Documentos</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/zonas" class="${param.active eq 'zonas' ? 'active' : ''}"><i class="fas fa-map-location-dot"></i> Zonas</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/mesas" class="${param.active eq 'mesas' ? 'active' : ''}"><i class="fas fa-check-to-slot"></i> Mesas</a></li>
    </ul>
    <div class="sidebar-footer">
        <a href="${pageContext.request.contextPath}/logout" style="color:#fff; text-decoration:none;"><i class="fas fa-right-from-bracket"></i> Cerrar sesión</a>
    </div>
</aside>