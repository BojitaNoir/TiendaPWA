# 🚚 Narvaez - Progressive Web App (PWA)

## Descripción

Narvaez es una aplicación web progresiva completa para la gestión de pedidos, tiendas y entregas. Funciona perfectamente en dispositivos móviles y de escritorio, incluso sin conexión a internet.

## 🎯 Características

✅ **Progressive Web App (PWA)**
- Funciona offline con sincronización automática
- Se instala como una aplicación nativa en teléfonos y computadoras
- Actualizaciones automáticas en segundo plano

✅ **Service Worker**
- Cacheo inteligente de recursos
- Funcionamiento sin conexión
- Notificaciones push

✅ **Responsivo**
- Se adapta a cualquier tamaño de pantalla
- Soporta notch y bordes de pantalla (iPhone X+)
- Modo oscuro opcional

✅ **Características Completas**
- Gestión de pedidos
- Control de tiendas
- Administración de repartidores
- Panel de notificaciones
- Registro de visitas

## 📁 Estructura de Carpetas

```
cliente/
├── index.html              # Página principal
├── offline.html            # Página cuando no hay conexión
├── manifest.json           # Configuración de PWA
├── pages/                  # Páginas de la aplicación
│   ├── template.html       # Plantilla base
│   ├── homeRepartidor.html
│   ├── dasboardAdmin.html
│   ├── loginAdmin.html
│   ├── loginRepartidor.html
│   ├── pedidos.html
│   ├── pedidosPendientes.html
│   ├── gestionPedidos.html
│   ├── gestionProductos.html
│   ├── gestionTiendas.html
│   ├── gestionRepartidores.html
│   ├── detalleTienda.html
│   ├── perfil.html
│   ├── registroVisita.html
│   ├── visitasTemporal.html
│   ├── confirmacionPedido.html
│   └── panelNotificaciones.html
├── js/
│   ├── app.js              # Lógica principal y registro del Service Worker
│   ├── offline.js          # Manejo de modo offline
│   └── service-worker.js   # Service Worker
├── assets/
│   ├── css/
│   │   ├── styles.css      # Estilos principales
│   │   └── responsive.css  # Media queries y responsive
│   └── icons/              # Iconos de la app (agregar imágenes PNG)
│       ├── icon-72x72.png
│       ├── icon-96x96.png
│       ├── icon-128x128.png
│       ├── icon-144x144.png
│       ├── icon-152x152.png
│       ├── icon-192x192.png
│       ├── icon-384x384.png
│       ├── icon-512x512.png
│       ├── screenshot-1.png
│       └── screenshot-2.png
└── pages/                  # Más páginas personalizadas
```

## 🚀 Cómo Usar

### 1. Servidor Local

Para ejecutar la app localmente, necesitas un servidor HTTP (el navegador requiere HTTPS o localhost):

**Con Python 3:**
```bash
cd c:\Users\andre\Downloads\Narvaez
python -m http.server 8000
```

**Con Python 2:**
```bash
python -m SimpleHTTPServer 8000
```

**Con Node.js (http-server):**
```bash
npx http-server
```

**Con PHP:**
```bash
php -S localhost:8000
```

Luego abre: `http://localhost:8000/cliente/`

### 2. Instalación en el Teléfono

#### Android (Chrome):
1. Abre la app en Chrome
2. Toca el menú (⋮) en la esquina superior derecha
3. Selecciona "Instalar aplicación"
4. ¡Listo!

#### iPhone/iPad (Safari):
1. Abre en Safari
2. Toca el botón de compartir (↗️)
3. Selecciona "Añadir a la pantalla de inicio"
4. ¡Listo!

#### Desktop:
1. Abre en Chrome/Edge
2. Haz clic en el icono de instalación en la barra de dirección
3. Selecciona "Instalar"

## ⚙️ Configuración

### Cambiar Colores (Tema)

Edita `/cliente/assets/css/styles.css` y modifica las variables CSS:

```css
:root {
  --primary-color: #2563eb;
  --primary-dark: #1e40af;
  --secondary-color: #10b981;
  --danger-color: #ef4444;
  /* ... más variables */
}
```

### Agregar Iconos

Los iconos deben estar en `/cliente/assets/icons/` en estos tamaños:
- 72x72, 96x96, 128x128, 144x144, 152x152, 192x192, 384x384, 512x512 (PNG)
- Screenshots: 540x720 (PNG)

### Cambiar Nombre y Descripción

Edita `/cliente/manifest.json`:

```json
{
  "name": "Tu Nombre",
  "short_name": "Nombre Corto",
  "description": "Descripción de tu app",
  "theme_color": "#color"
}
```

## 🔧 Personalización de Páginas

### Usar la Plantilla Base

Copia `/cliente/pages/template.html` y modifica el contenido:

```html
<main>
  <div class="container">
    <div class="card">
      <div class="card-header">Tu Título</div>
      <div class="card-body">
        <!-- Tu contenido aquí -->
      </div>
    </div>
  </div>
</main>
```

### Componentes Disponibles

#### Cards/Tarjetas:
```html
<div class="card">
  <div class="card-header">Título</div>
  <div class="card-body">Contenido</div>
</div>
```

#### Botones:
```html
<button class="btn btn-primary">Primario</button>
<button class="btn btn-success">Éxito</button>
<button class="btn btn-danger">Peligro</button>
<button class="btn btn-warning">Advertencia</button>
```

#### Alertas:
```html
<div class="alert alert-info">Info</div>
<div class="alert alert-success">Éxito</div>
<div class="alert alert-warning">Advertencia</div>
<div class="alert alert-danger">Error</div>
```

#### Badges:
```html
<span class="badge badge-primary">Etiqueta</span>
<span class="badge badge-success">Éxito</span>
```

#### Tablas:
```html
<table>
  <thead>
    <tr>
      <th>Encabezado</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>Dato</td>
    </tr>
  </tbody>
</table>
```

#### Grid (Responsivo):
```html
<div class="grid-2">
  <div class="card">Columna 1</div>
  <div class="card">Columna 2</div>
</div>

<div class="grid-3">
  <div class="card">Columna 1</div>
  <div class="card">Columna 2</div>
  <div class="card">Columna 3</div>
</div>
```

## 🌐 Funcionalidades JavaScript

### Detectar Conexión

```javascript
if (navigator.onLine) {
  console.log('En línea');
} else {
  console.log('Sin conexión');
}
```

### Guardar Datos Offline

```javascript
saveOfflineData('nombre_clave', datos);
```

### Recuperar Datos Offline

```javascript
const datos = getOfflineData('nombre_clave');
```

### Sincronizar Cuando Vuelva Conexión

```javascript
window.addEventListener('online', () => {
  syncPendingData();
});
```

### Mostrar Notificación

```javascript
showNotification('Mensaje', 'success'); // success, warning, info
```

## 📱 Caching Strategy

La app usa dos estrategias:

1. **Network First** (para páginas HTML)
   - Intenta obtener del servidor primero
   - Si falla, usa cache
   - Ideal para contenido que cambia frecuentemente

2. **Cache First** (para otros recursos)
   - Intenta obtener del cache primero
   - Si no está, obtiene del servidor
   - Ideal para assets (CSS, JS, imágenes)

## 🐛 Solución de Problemas

### "Service Worker no se registra"
- Asegúrate de usar HTTPS o localhost
- Verifica la consola del navegador para errores

### "La app no funciona offline"
- Abre la app online primero para cachear recursos
- Verifica que todos los archivos estén en la ruta correcta

### "No aparece el botón de instalar"
- El navegador debe ser compatible (Chrome, Edge, Samsung Internet)
- La app debe servirse sobre HTTPS (o localhost)

### "Cambios no se reflejan"
- Limpia el cache del navegador
- Desinstala y vuelve a instalar la app
- Usa Ctrl+Shift+Delete (DevTools > Application > Clear storage)

## 📚 Recursos Útiles

- [MDN: Progressive Web Apps](https://developer.mozilla.org/en-US/docs/Web/Progressive_web_apps)
- [Google: Build a PWA](https://developers.google.com/codelabs/your-first-pwapp)
- [Web.dev: PWA Guide](https://web.dev/progressive-web-apps/)

## 🔐 Seguridad

Para producción:
- Usa HTTPS obligatoriamente
- Valida todos los datos en el servidor
- Usa tokens JWT para autenticación
- Implementa CORS correctamente
- Sanitiza entrada de usuarios

## 📞 Soporte

¿Tienes problemas? Verifica:
1. La consola del navegador (F12)
2. La pestaña "Application" en DevTools
3. El registro del Service Worker
4. La carpeta de archivos está en la ruta correcta

---

**Versión:** 1.0.0  
**Última actualización:** 2024  
**Licencia:** Privada - Narvaez © 2024
