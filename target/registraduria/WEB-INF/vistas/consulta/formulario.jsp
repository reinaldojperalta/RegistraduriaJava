<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <jsp:include page="../fragments/head.jsp">
            <jsp:param name="title" value="Consulta Mesa de Votación"/>
        </jsp:include>
        <c:if test="${recaptchaEnabled}">
            <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        </c:if>
    </head>
    <body class="pagina-publica">
        <header class="header-gov">
            <div class="container">
                <h1><i class="fas fa-landmark"></i> Registraduría Municipal de Nobsa</h1>
                <a href="${pageContext.request.contextPath}/login"><i class="fas fa-lock"></i> Acceso funcionarios</a>
            </div>
        </header>

        <div class="hero-publico">
            <div class="container">
                <h2><i class="fas fa-check-to-slot"></i> Consulta tu Mesa de Votación</h2>
                <p>Ingrese su número de cédula para conocer su puesto y mesa de votación</p>
            </div>
        </div>

        <main style="flex:1; display:flex; align-items:flex-start; justify-content:center; padding:0 1rem;">
            <div style="width:100%; max-width:480px; margin-top:-2rem; position:relative; z-index:10;">
                <div class="card" style="padding:2rem;">
                    <c:if test="${not empty error}">
                        <div class="alert alert-error mb-2"><i class="fas fa-circle-exclamation"></i> ${error}</div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/consulta" method="post">
                        <div class="form-group">
                            <label for="documento" style="font-size:1rem; font-weight:600; color:var(--azul-institucional);">
                                Número de Cédula
                            </label>
                            <input type="text" id="documento" name="documento" class="form-control"
                            style="font-size:1.1rem; padding:0.875rem;"
                            placeholder="Ej: 1052345678" value="${documento}" required autofocus>
                        </div>

                        <c:if test="${recaptchaEnabled}">
                            <div class="form-group" style="display:flex; justify-content:center;">
                                <div class="g-recaptcha" data-sitekey="${siteKey}"></div>
                            </div>
                        </c:if>

                        <button type="submit" class="btn btn-primario" style="width:100%; justify-content:center; padding:0.875rem; font-size:1rem;">
                            <i class="fas fa-magnifying-glass"></i> Consultar
                        </button>
                    </form>

                    <div style="margin-top:1.5rem; padding-top:1.5rem; border-top:1px solid #e5e7eb; font-size:0.8rem; color:var(--gris-texto); text-align:center;">
                        <i class="fas fa-circle-info"></i> Si no encuentra su información, acérquese a la Registraduría Municipal.
                    </div>
                </div>
            </div>
        </main>

        <footer class="footer-gov">
            <div class="container">
                <p><i class="fas fa-shield-halved"></i> Registraduría Municipal de Nobsa · Boyacá · Colombia</p>
            </div>
        </footer>
    </body>
</html>