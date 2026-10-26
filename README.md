# demsecurityone — entrega formativa Semana 1 (PBY2202)

## Qué incluye
- Spring Boot con `spring-boot-starter-security`.
- Autenticación con nombre de usuario y contraseña mediante HTTP Basic.
- Usuarios de demostración en memoria con roles separados: CLIENTE, EMPLEADO y ADMIN.
- `PasswordEncoder` delegado de Spring Security; las contraseñas no se guardan como texto plano.
- Endpoints públicos, autenticados y restringidos por rol.
- Denegación por defecto para las rutas no declaradas.

## Requisitos
- JDK 17
- Maven Wrapper incluido

## Ejecutar
En macOS/Linux:
```bash
./mvnw spring-boot:run
```
En Windows:
```bat
mvnw.cmd spring-boot:run
```

## Endpoints
| Método | Ruta | Acceso esperado |
|---|---|---|
| GET | `/api/publico` | Público |
| GET | `/api/saludo` | Público |
| GET | `/api/perfil` | Usuario autenticado |
| GET | `/api/empleados` | EMPLEADO o ADMIN |
| GET | `/api/admin` | Solo ADMIN |
| Cualquier otra ruta | — | Denegada |

## Pruebas con curl
Con la aplicación ejecutándose en `localhost:8080`:

```bash
# Debe responder 200 sin credenciales
curl -i http://localhost:8080/api/publico

# Sin credenciales, debe responder 401
curl -i http://localhost:8080/api/perfil

# Cliente autenticado: perfil 200; ruta administrativa 403
curl -i -u 'cliente:ClienteDemo-2026!' http://localhost:8080/api/perfil
curl -i -u 'cliente:ClienteDemo-2026!' http://localhost:8080/api/admin

# Empleado: endpoint de empleados 200; admin 403
curl -i -u 'empleado:EmpleadoDemo-2026!' http://localhost:8080/api/empleados
curl -i -u 'empleado:EmpleadoDemo-2026!' http://localhost:8080/api/admin

# Administrador: endpoint administrativo 200
curl -i -u 'admin:AdminDemo-2026!' http://localhost:8080/api/admin
```

## Importante: alcance académico
Este proyecto de demostración no implementa persistencia real, citas, expedientes clínicos,
inventario, ventas, auditoría persistente ni una base de datos. Los usuarios en memoria y las
credenciales de ejemplo sirven solo para demostrar el control de acceso y deben cambiarse
antes de cualquier despliegue real. HTTP Basic debe usarse únicamente sobre HTTPS.

Spring Security no elimina por sí solo todas las vulnerabilidades. Para producción se requiere
validación de entradas, consultas parametrizadas/ORM, codificación de salida contra XSS,
decisiones CSRF según el tipo de cliente, gestión segura de secretos, HTTPS, registros de auditoría,
pruebas de seguridad y protección de datos personales. No se afirma que estas medidas estén
implementadas en este proyecto de demostración.
