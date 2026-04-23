<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <jsp:include page="fragments/head.jsp">
            <jsp:param name="title" value="Acceso - Registraduría Municipal"/>
        </jsp:include>
    </head>
    <body class="pagina-publica">
        <header class="header-gov">
            <div class="container">
                <h1><i class="fas fa-landmark"></i> Registraduría Municipal de Nobsa</h1>
                <span>Boyacá, Colombia</span>
            </div>
        </header>

        <main style="flex:1; display:flex; align-items:center; justify-content:center; padding:3rem 1rem;">
            <div style="width:100%; max-width:420px;">
                <div class="card" style="padding:2.5rem;">
                    <div class="text-center" style="margin-bottom:1.5rem;">
                        <div style="width:64px; height:64px; background:var(--azul-institucional); color:#fff; border-radius:50%; display:inline-flex; align-items:center; justify-content:center; font-size:1.5rem; margin-bottom:1rem;">
                            <i class="fas fa-user-lock"></i>
                        </div>
                        <h2 style="font-size:1.25rem; color:var(--azul-institucional);">Ingreso al Sistema</h2>
                        <p style="font-size:0.875rem; color:var(--gris-texto); margin-top:0.25rem;">Digite su número de cédula para continuar</p>
                    </div>

                    <c:if test="${not empty error}">
                        <div class="alert alert-error">
                            <i class="fas fa-circle-exclamation"></i> ${error}
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/login" method="post">
                        <div class="form-group">
                            <label for="documento">Número de Documento</label>
                            <input type="text" id="documento" name="documento" class="form-control" placeholder="Ej: 1052345678" value="${documento}" required autofocus>
                        </div>

                        <c:if test="${requierePassword}">
                            <div class="form-group" id="campoPassword">
                                <label for="password">Contraseña de Administrador</label>
                                <input type="password" id="password" name="password" class="form-control" placeholder="••••••" required>
                            </div>
                        </c:if>

                        <button type="submit" class="btn btn-primario" style="width:100%; justify-content:center; padding:0.75rem;">
                            <i class="fas fa-arrow-right"></i> Ingresar
                        </button>
                    </form>

                    <div class="text-center" style="margin-top:1.5rem; padding-top:1.5rem; border-top:1px solid #e5e7eb;">
                        <a href="${pageContext.request.contextPath}/consulta" style="color:var(--azul-acento); text-decoration:none; font-size:0.9rem; font-weight:500;">
                            <i class="fas fa-magnifying-glass"></i> Soy ciudadano: consultar mesa de votación
                        </a>
                    </div>
                </div>
            </div>
        </main>

        <footer class="footer-gov">
            <div class="container">
                <p> Servicio Nacional de Aprendizaje SENA · ADSO · Centro Industrial CIMM</p>
            </div>
        </footer>
    </body>
</html>