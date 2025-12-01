# 📁 Estructura de Carpetas - Narvaez PWA

## Organización por Rol de Usuario

### 🏢 Páginas de Admin
```
cliente/pages/admin/
├── dashboard.html           # Dashboard principal del administrador
├── login.html              # Login para administradores
├── pedidos.html            # Gestión de pedidos (copiar gestionPedidos.html)
├── productos.html          # Gestión de productos (copiar gestionProductos.html)
├── tiendas.html            # Gestión de tiendas (copiar gestionTiendas.html)
├── repartidores.html       # Gestión de repartidores (copiar gestionRepartidores.html)
└── notificaciones.html     # Panel de notificaciones (copiar panelNotificaciones.html)
```

**Acceso:** `/cliente/pages/admin/dashboard.html`

---

### 🚚 Páginas de Repartidor
```
cliente/pages/repartidor/
├── inicio.html             # Mis visitas de hoy (copiar homeRepartidor.html)
├── login.html              # Login para repartidores
├── pedidos.html            # Nuevo pedido (copiar pedidos.html)
├── pedidos-pendientes.html # Pedidos pendientes (copiar pedidosPendientes.html)
├── perfil.html             # Perfil y configuración (copiar perfil.html)
├── registro-visita.html    # Confirmación de visita (copiar registroVisita.html)
└── visitas-extra.html      # Asignaciones extra (copiar visitasTemporal.html)
```

**Acceso:** `/cliente/pages/repartidor/inicio.html`

---

### 📝 Páginas Comunes
```
cliente/pages/comun/
├── detalle-tienda.html     # Detalles de la tienda
└── confirmacion-pedido.html # Resumen del pedido
```

**Acceso:** Cualquier usuario autenticado

---

### 📱 Estructura Raíz
```
cliente/
├── index.html              # Página principal / Welcome
├── offline.html            # Página de error sin conexión
├── manifest.json           # Configuración PWA
├── README.md              # Documentación
├── pages/
│   ├── admin/
│   ├── repartidor/
│   ├── comun/
│   └── template.html       # Plantilla base para nuevas páginas
├── js/
│   ├── app.js             # Registro Service Worker
│   ├── offline.js         # Manejo de modo offline
│   └── service-worker.js  # Service Worker
├── assets/
│   ├── css/
│   │   ├── styles.css      # Estilos principales
│   │   └── responsive.css  # Media queries
│   └── icons/              # Iconos PWA (agregar tus imágenes)
└── [archivos antiguos]    # Los HTML originales están aquí (eliminar después)
```

---

## 🔄 Rutas de Navegación

### Admin
```
/cliente/pages/admin/login.html
    ↓
/cliente/pages/admin/dashboard.html
    ├── /cliente/pages/admin/pedidos.html
    ├── /cliente/pages/admin/productos.html
    ├── /cliente/pages/admin/tiendas.html
    ├── /cliente/pages/admin/repartidores.html
    └── /cliente/pages/admin/notificaciones.html
```

### Repartidor
```
/cliente/pages/repartidor/login.html
    ↓
/cliente/pages/repartidor/inicio.html
    ├── /cliente/pages/repartidor/pedidos.html
    ├── /cliente/pages/repartidor/pedidos-pendientes.html
    ├── /cliente/pages/repartidor/registro-visita.html
    ├── /cliente/pages/repartidor/visitas-extra.html
    ├── /cliente/pages/repartidor/perfil.html
    └── /cliente/pages/comun/detalle-tienda.html
```

---

## 📥 URLs de Acceso

| Página | URL |
|--------|-----|
| Principal | `/cliente/index.html` |
| Login Admin | `/cliente/pages/admin/login.html` |
| Dashboard Admin | `/cliente/pages/admin/dashboard.html` |
| Login Repartidor | `/cliente/pages/repartidor/login.html` |
| Inicio Repartidor | `/cliente/pages/repartidor/inicio.html` |
| Detalle Tienda | `/cliente/pages/comun/detalle-tienda.html` |
| Confirmación Pedido | `/cliente/pages/comun/confirmacion-pedido.html` |
| Sin Conexión | `/cliente/offline.html` |

---

## 🛠️ Scripts en Cada Página

Todas las páginas incluyen automáticamente:

```html
<!-- PWA Setup -->
<meta name="manifest" content="/cliente/manifest.json">
<link rel="stylesheet" href="/cliente/assets/css/styles.css">
<link rel="stylesheet" href="/cliente/assets/css/responsive.css">

<!-- Scripts -->
<script src="/cliente/js/app.js"></script>
<script src="/cliente/js/offline.js"></script>
```

---

## 🎨 Personalización

### Cambiar Colores Primarios

Edita el archivo correspondiente:

**Para Admin:** Busca `#007AFF` en `admin/dashboard.html`
**Para Repartidor:** Busca `#f47b25` en `repartidor/inicio.html`

O modifica en `/cliente/assets/css/styles.css`:
```css
:root {
  --primary-color: #2563eb;  /* Cambia este valor */
}
```

---

## 📦 Migración desde Archivos Antiguos

Los archivos HTML originales están en la raíz de `/cliente/`. Si necesitas migrar datos:

1. **Copiar contenido HTML** de los archivos antiguos
2. **Pegar en la nueva estructura**:
   - `dasboardAdmin.html` → `/pages/admin/dashboard.html`
   - `loginAdmin.html` → `/pages/admin/login.html`
   - `homeRepartidor.html` → `/pages/repartidor/inicio.html`
   - etc.

3. **Actualizar rutas** en enlaces internos
4. **Eliminar archivos antiguos** una vez migrados

---

## ✅ Checklist para Nuevas Páginas

Al crear nuevas páginas, incluye:

- [ ] `<meta name="manifest" content="/cliente/manifest.json">`
- [ ] Links a CSS: `styles.css` y `responsive.css`
- [ ] Scripts: `app.js` y `offline.js`
- [ ] Botón de instalación: `id="install-app-button"`
- [ ] Responsive design (mobile first)
- [ ] Dark mode support

---

**Versión:** 1.0.0  
**Última actualización:** Noviembre 2025  
**Estado:** Estructura completa, archivos en migración
