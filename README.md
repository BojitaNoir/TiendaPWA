# 🏪 TiendaPWA - Sistema de Gestión de Tiendas

Sistema integrado de gestión de tiendas y entregas con frontend PWA y backend Spring Boot.

## 📋 Características

### Admin (Administrador)
- ✅ Dashboard con estadísticas en tiempo real
- ✅ Gestión de productos (CRUD completo)
- ✅ Carga de fotos en base64
- ✅ Generación y escaneo de códigos QR
- ✅ Gestión de tiendas
- ✅ Gestión de repartidores
- ✅ Control de pedidos y entregas

### Repartidor (Delivery)
- ✅ Panel de inicio con resumen
- ✅ Lista de pedidos asignados
- ✅ Seguimiento de entregas
- ✅ Perfil personal

## 🚀 Inicio Rápido

### Opción 1: Script Batch (Windows)
```bash
iniciar.bat
```

### Opción 2: Script PowerShell (Windows)
```powershell
.\iniciar.ps1
```

### Opción 3: Manual
**Terminal 1 - Backend:**
```bash
cd back
mvn spring-boot:run
```

**Terminal 2 - Frontend:**
```bash
cd front
python -m http.server 8000
```
O con Node.js:
```bash
cd front
npx http-server -p 8000
```

## 🌐 Acceso

Una vez iniciado:
- **Frontend:** http://localhost:8000/login.html
- **Backend API:** http://localhost:8081/api
- **H2 Console:** http://localhost:8081/h2-console

## 👤 Credenciales de Prueba

### Administrador
- Email: `admin@example.com`
- Contraseña: `admin123`

### Repartidor
- Email: `rep1@example.com`
- Contraseña: `rep123`

O:
- Email: `rep2@example.com`
- Contraseña: `rep123`

## 📋 Requisitos Previos

- **Java 21+** - [Descargar](https://www.oracle.com/java/technologies/downloads/#java21)
- **Maven 3.8+** - [Descargar](https://maven.apache.org/download.cgi)
- **Python 3.7+** O **Node.js** - Para servir el frontend

## 📁 Estructura del Proyecto

```
TiendaPWA/
├── back/                  # Backend Spring Boot
│   ├── src/
│   │   ├── main/java/     # Controladores, modelos, servicios
│   │   └── resources/     # Configuración (application.properties)
│   ├── pom.xml            # Dependencias Maven
│   └── mvnw / mvnw.cmd    # Maven Wrapper
│
├── front/                 # Frontend PWA
│   ├── pages/
│   │   ├── admin/         # Panel administrativo
│   │   ├── repartidor/    # Panel del repartidor
│   │   └── comun/         # Páginas compartidas
│   ├── css/               # Estilos
│   ├── js/                # Scripts (incluyendo api.js)
│   ├── index.html         # Página principal
│   └── login.html         # Página de login
│
├── iniciar.bat            # Script de inicio para Windows
├── iniciar.ps1            # Script de inicio para PowerShell
└── README.md              # Este archivo
```

## 🛠️ Tecnologías

### Backend
- **Spring Boot 4.0.0** - Framework web Java
- **Spring Security** - Autenticación y autorización
- **JPA/Hibernate** - ORM para base de datos
- **H2 Database** - Base de datos en memoria para desarrollo
- **Maven** - Gestor de dependencias

### Frontend
- **HTML5** - Estructura
- **CSS3** - Estilos con Bootstrap 5.3.2
- **JavaScript Vanilla** - Lógica
- **QRCode.js** - Generación de códigos QR
- **jsQR** - Escaneo de códigos QR

## 🔐 Seguridad

- Contraseñas hasheadas con BCrypt
- CORS configurado para desarrollo
- Control de acceso basado en roles (RBAC)
- Autenticación simple con localStorage (desarrollo)

## 📚 Documentación Adicional

- **[INSTALACION.md](INSTALACION.md)** - Guía detallada de instalación
- **[INTEGRACION.md](INTEGRACION.md)** - Documentación técnica de integración
- **[CAMBIOS_ADMIN.md](CAMBIOS_ADMIN.md)** - Cambios implementados en el panel admin

## 🐛 Solución de Problemas

### Backend no inicia
```bash
# Verifica que Java 21 esté instalado
java -version

# Verifica que Maven esté instalado
mvn -version

# Limpiar y reconstruir
cd back
mvn clean install
mvn spring-boot:run
```

### Frontend no carga
```bash
# Si usas Python, verifica la versión
python --version

# Si usas Node.js
npm install -g http-server
npx http-server front -p 8000
```

### CORS errors
El backend tiene CORS configurado para localhost:8000. Asegúrate de:
1. Acceder via http://localhost:8000 (no http://127.0.0.1)
2. Usar los puertos correctos (8081 para backend, 8000 para frontend)

### Base de datos vacía
La base de datos H2 se inicializa automáticamente con datos de prueba al iniciar el backend. Si no aparecen:
1. Verifica los logs del backend para errores
2. Accede a http://localhost:8081/h2-console
3. Comprueba que se ejecutó DataInitializer.java

## 📝 Notas de Desarrollo

### API Base URL
La configuración de la API se define en `front/js/api.js`:
```javascript
const API_BASE_URL = 'http://localhost:8081/api';
```

### Endpoints Principales
- `POST /api/auth/login` - Login
- `GET /api/products` - Listar productos
- `POST /api/products` - Crear producto
- `PUT /api/products/{id}` - Actualizar producto
- `DELETE /api/products/{id}` - Eliminar producto

## 📧 Contacto y Soporte

Para reportar bugs o sugerencias, contacta al equipo de desarrollo.

---

**Última actualización:** 2024
**Versión:** 1.0.0