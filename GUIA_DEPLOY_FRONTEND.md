# 🚀 Guía para Subir Frontend a GitHub Pages

## ✅ Paso 1: Preparación (COMPLETADO)
- [x] Meta tag CSP agregado a todos los HTML
- [x] Permite llamadas HTTP desde HTTPS
- [x] 18 archivos procesados

---

## 📂 Paso 2: Subir a GitHub

### Opción A: Desde GitHub Desktop (MÁS FÁCIL)

1. **Abre GitHub Desktop**

2. **Add Local Repository**
   - File → Add Local Repository
   - Selecciona: `C:\Users\angry\Documents\PWA-Abarrotes\TiendaPWA`

3. **Crear nuevo repositorio en GitHub.com**
   - Click en "Publish repository"
   - Nombre: `TiendaPWA`
   - Descripción: "Sistema de Gestión de Pedidos - PWA"
   - ⚠️ **DESMARCAR** "Keep this code private" (o márcar si quieres privado)

4. **Publish**
   - Click en "Publish repository"

---

### Opción B: Desde Línea de Comandos

```powershell
# 1. Inicializar Git (si no está inicializado)
cd C:\Users\angry\Documents\PWA-Abarrotes\TiendaPWA
git init

# 2. Agregar archivos
git add .

# 3. Commit inicial
git commit -m "feat: frontend PWA completo con soporte Mixed Content"

# 4. Crear repositorio en GitHub.com
# Ve a: https://github.com/new
# Nombre: TiendaPWA
# NO marques "Add README"

# 5. Vincular con GitHub (reemplaza TU_USUARIO)
git remote add origin https://github.com/TU_USUARIO/TiendaPWA.git

# 6. Subir a GitHub
git branch -M main
git push -u origin main
```

---

## 🌐 Paso 3: Activar GitHub Pages

1. **Ve a tu repositorio en GitHub**
   - `https://github.com/TU_USUARIO/TiendaPWA`

2. **Settings** (⚙️ arriba derecha)

3. **Pages** (menú lateral izquierdo)

4. **Source**
   - Branch: `main`
   - Folder: `/ (root)` ⚠️ **IMPORTANTE**
   - Click "Save"

5. **Espera 1-2 minutos**

6. **Tu URL será:**
   ```
   https://TU_USUARIO.github.io/TiendaPWA/front/
   ```
   
   ⚠️ **NOTA:** La carpeta `front` está en la URL

---

## 🔧 Paso 4: Ajustar Rutas (OPCIONAL)

Si quieres que la URL sea más limpia, puedes:

### Opción 1: Mover todo de `front/` a la raíz
```powershell
# Mover contenido de front a raiz
Move-Item -Path front\* -Destination . -Force

# Actualizar manifest.json
# Cambiar todas las rutas /front/ por /

# Actualizar service-worker.js
# Cambiar todas las rutas /front/ por /
```

### Opción 2: Usar una rama `gh-pages` solo con front
```powershell
# Crear rama gh-pages
git checkout --orphan gh-pages

# Borrar todo excepto front
git rm -rf .
git clean -fdx
mv front/* .
rmdir front

# Commit y push
git add .
git commit -m "Deploy: frontend only"
git push origin gh-pages

# Volver a main
git checkout main
```

Luego en Settings → Pages → Branch: `gh-pages` → Folder: `/ (root)`

Tu URL sería: `https://TU_USUARIO.github.io/TiendaPWA/`

---

## ✅ Paso 5: Verificar que Funciona

1. **Abre la URL en tu navegador**

2. **Abre DevTools (F12) → Console**
   - No debes ver errores de Mixed Content
   - Debes ver: "Service Worker registrado exitosamente"

3. **Prueba el login**
   - Admin o Repartidor
   - Verifica que conecta con tu backend AWS

4. **Prueba modo offline**
   - DevTools → Network → Offline
   - Refresca la página
   - Debería cargar del cache

---

## 🔥 Comandos Útiles para Actualizaciones

Cada vez que hagas cambios:

```powershell
cd C:\Users\angry\Documents\PWA-Abarrotes\TiendaPWA

# Ver cambios
git status

# Agregar todos los cambios
git add .

# Commit
git commit -m "fix: descripcion del cambio"

# Subir a GitHub
git push

# GitHub Pages se actualiza automáticamente en 1-2 minutos
```

---

## 🌍 Alternativas a GitHub Pages

### Si GitHub Pages te da problemas:

1. **Vercel** (Recomendado #2)
   ```powershell
   # Instalar Vercel CLI
   npm install -g vercel
   
   # Deploy
   cd front
   vercel
   # Sigue las instrucciones
   ```
   - URL: `https://tu-proyecto.vercel.app`
   - Gratis, muy rápido

2. **Netlify** (Recomendado #3)
   - Arrastra la carpeta `front` a https://app.netlify.com/drop
   - Instantáneo, gratis

3. **Firebase Hosting** (Ya tienes Firebase)
   ```powershell
   # Instalar Firebase CLI
   npm install -g firebase-tools
   
   # Login
   firebase login
   
   # Inicializar
   firebase init hosting
   # Public directory: front
   # Single-page app: No
   
   # Deploy
   firebase deploy --only hosting
   ```
   - URL: `https://tu-proyecto.web.app`

---

## ⚠️ IMPORTANTE: Cambiar URL del Backend

Una vez desplegado, **NO OLVIDES** actualizar en tu código:

### En `front/js/api.js` línea 3:
```javascript
// Actualmente:
const API_BASE_URL = "http://app-tiendaucq-env.eba-w3s5zsy2.us-east-1.elasticbeanstalk.com/api";

// Si cambias el backend a HTTPS, actualizar a:
// const API_BASE_URL = "https://tu-backend.com/api";
```

---

## 🎯 Checklist Final

- [ ] Git inicializado
- [ ] Archivos subidos a GitHub
- [ ] GitHub Pages activado
- [ ] URL accesible
- [ ] Service Worker funcionando
- [ ] Login conecta con AWS backend
- [ ] Mixed Content resuelto
- [ ] PWA instalable desde navegador

---

## 📞 Solución de Problemas

### Error: "Failed to load resource: net::ERR_BLOCKED_BY_CLIENT"
- Deshabilita AdBlock temporalmente

### Error: "Mixed Content"
- Verifica que el meta tag CSP esté en el HTML
- Refresca con Ctrl+Shift+R

### 404 en GitHub Pages
- Verifica que la carpeta sea correcta en Settings → Pages
- Espera 2-3 minutos para que se actualice
- La URL incluye `/front/` al final

### Backend no responde
- Verifica que tu AWS Elastic Beanstalk esté running
- Prueba el endpoint directo en el navegador
- Verifica CORS en el backend

---

**¡Listo para desplegar! 🚀**
