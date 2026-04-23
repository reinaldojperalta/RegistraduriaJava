[README.md](https://github.com/user-attachments/files/27014055/README.md)
# Registraduría Municipal de Nobsa

Aplicación web de gestión electoral para la Registraduría Municipal de Nobsa, Boyacá — Colombia.

## Descripción General

Sistema modular que permite:

- **Consulta pública**: Los ciudadanos pueden verificar su mesa de votación ingresando su número de cédula.
- **Panel administrativo**: Gestión completa de ciudadanos, documentos expedidos, zonas electorales y mesas de votación.
- **Expedición documental**: Registro y control de cédulas, tarjetas de identidad, registros civiles y contraseñas.
- **Asignación electoral**: Inscripción de ciudadanos en mesas y zonas de votación.

## Tecnologías

| Capa | Tecnología |
|------|-----------|
| Servidor | Apache Tomcat 10.1 |
| Compilación/Deploy | Maven + Cargo Plugin |
| Backend | Jakarta EE (Servlets, JSP, JSTL) |
| Base de datos | PostgreSQL |
| Seguridad | BCrypt (hash de contraseñas), reCAPTCHA v2 |
| Frontend | CSS vanilla + Font Awesome |

## Requisitos Previos

- JDK 17+
- Apache Maven 3.9+
- PostgreSQL 14+
- Apache Tomcat 10.1 (gestionado automáticamente por Cargo)

## Configuración

### Base de datos

Crear la base de datos `dbregistraduria` en PostgreSQL y ejecutar el script SQL de estructura incluido en `src/main/resources/sql/`.

Configurar credenciales en:
```
src/main/resources/db.properties
```

Ejemplo:
```properties
db.url=jdbc:postgresql://localhost:5432/dbregistraduria
db.user=postgres
db.password=tu_password
db.driver=org.postgresql.Driver
```

### reCAPTCHA (opcional)

Para habilitar la protección del formulario de consulta pública:

```properties
# src/main/resources/recaptcha.properties
recaptcha.site.key=tu_site_key
recaptcha.secret.key=tu_secret_key
recaptcha.enabled=true
```

Para deshabilitar: `recaptcha.enabled=false`

## Compilación y Ejecución

### Desarrollo local

```bash
mvn clean package cargo:run
```

Cargo descarga y configura Tomcat 10 automáticamente. La aplicación estará disponible en:

```
http://localhost:8080/
```

### Solo compilar WAR

```bash
mvn clean package
```

El archivo `target/registraduria.war` se despliega manualmente en cualquier Tomcat 10.

## Estructura de Módulos

```
├── Consulta Pública (/consulta)
│   └── Formulario de consulta por cédula
│   └── Resultado con mesa asignada
│   └── Documentos expedidos del ciudadano
│
├── Panel Administrativo (/admin/*)
│   ├── Ciudadanos (CRUD + asignación a mesas)
│   ├── Documentos (expedición, edición, cambio de estado)
│   ├── Zonas Electorales (CRUD)
│   └── Mesas de Votación (CRUD + capacidad)
│
└── Autenticación (/login)
    ├── Administradores: usuario + contraseña (BCrypt)
    └── Votantes: número de documento únicamente
```

## Flujo de Datos Clave

### Crear ciudadano + usuario

1. El administrador registra un ciudadano en `/admin/ciudadanos`
2. El sistema crea automáticamente un usuario vinculado
3. La contraseña inicial es el número de documento (hasheado con BCrypt)
4. El rol puede ser `VOTANTE` o `ADMIN`

### Consulta pública

1. El ciudadano ingresa su cédula en `/consulta`
2. Valida reCAPTCHA (si está habilitado)
3. El sistema busca en `ciudadanos`, `mesas_votacion`, `zonas_votacion` y `ciudades`
4. Muestra: número de mesa, puesto, dirección, ciudad y capacidad

## Convenciones Importantes

- **JSPs**: Todas las vistas están en `WEB-INF/vistas/` (inaccesibles directamente)
- **Filtros**: `AuthFilter` protege `/admin/*`; `NoDirectJspFilter` bloquea acceso directo a `.jsp`
- **Encoding**: `EncodingFilter` fuerza UTF-8 en todas las peticiones/respuestas
- **DAOs**: Patrón DAO con `ConexionDB` singleton para pool de conexiones

## Licencia

Proyecto académico — ADSO (Análisis y Desarrollo de Software).
