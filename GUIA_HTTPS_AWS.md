# 🔒 Guía para Configurar HTTPS en AWS Elastic Beanstalk

## ⚠️ REQUISITO PREVIO: Necesitas un Dominio

Para usar AWS Certificate Manager **GRATIS**, necesitas tener un dominio (ejemplo: miapp.com).

### Opciones de Dominio:
1. **Comprar dominio barato** (Recomendado)
   - **Namecheap**: $0.99 USD primer año (.xyz, .site)
   - **GoDaddy**: ~$1 USD primer año
   - **Freenom**: GRATIS (.tk, .ml, .ga) - Menos confiable

2. **Usar subdominio gratuito** (No compatible con ACM)
   - No sirve para AWS Certificate Manager
   - Necesitas certificado manual

---

## 📋 OPCIÓN 1: Con Dominio Propio (RECOMENDADO)

### Paso 1: Solicitar Certificado SSL en AWS Certificate Manager

1. **Ir a AWS Certificate Manager**
   - Inicia sesión en AWS Console
   - Busca "Certificate Manager" o ve a: https://console.aws.amazon.com/acm/
   - **⚠️ IMPORTANTE**: Selecciona región **us-east-1 (Virginia)** en la esquina superior derecha

2. **Solicitar Certificado**
   ```
   Click en "Request a certificate"
   → Selecciona "Request a public certificate"
   → Click "Next"
   ```

3. **Configurar Dominio**
   ```
   Fully qualified domain name: tudominio.com
   
   También agrega:
   - *.tudominio.com (para subdominios)
   ```
   
   **Ejemplo:**
   ```
   tiendaucq.com
   *.tiendaucq.com
   ```
   
   - Validation method: **DNS validation** (Recomendado)
   - Key algorithm: RSA 2048
   - Click "Request"

4. **Validar el Dominio**
   
   AWS te dará unos registros DNS que debes agregar:
   
   ```
   Type: CNAME
   Name: _abc123.tudominio.com
   Value: _xyz789.acm-validations.aws.
   ```
   
   **Dónde agregar los registros:**
   - Ve a tu proveedor de dominio (Namecheap, GoDaddy, etc.)
   - Busca "DNS Settings" o "Manage DNS"
   - Agrega el registro CNAME que AWS te dio
   - Guarda los cambios

5. **Esperar Validación** (5-30 minutos)
   - AWS verificará que eres dueño del dominio
   - El estado cambiará de "Pending validation" a "Issued"

---

### Paso 2: Configurar Load Balancer en Elastic Beanstalk

1. **Ir a Elastic Beanstalk**
   - AWS Console → Elastic Beanstalk
   - Selecciona tu ambiente: `app-tiendaucq-env`

2. **Modificar Configuración**
   ```
   Click en "Configuration" (menú lateral izquierdo)
   → Busca "Load balancer"
   → Click "Edit"
   ```

3. **Agregar Listener HTTPS**
   
   En la sección "Listeners":
   ```
   Click "Add listener"
   
   Port: 443
   Protocol: HTTPS
   SSL certificate: [Selecciona el certificado que creaste]
   SSL policy: ELBSecurityPolicy-2016-08 (recomendado)
   
   Click "Add"
   ```

4. **Modificar Listener HTTP (Opcional)**
   
   Puedes hacer que HTTP redirija a HTTPS:
   ```
   En el listener 80 (HTTP):
   → Process: default
   → Click "Actions" → "Edit"
   → Cambiar a "Redirect to HTTPS"
   Port: 443
   Status code: 301 (Permanent)
   ```

5. **Guardar Cambios**
   ```
   Click "Apply" (abajo)
   Espera 5-10 minutos mientras AWS actualiza
   ```

---

### Paso 3: Configurar tu Dominio para Apuntar a AWS

1. **Obtener URL del Load Balancer**
   ```
   En Elastic Beanstalk → Configuration → Load balancer
   Copia la "URL" del load balancer
   Ejemplo: awseb-AWSEB-123ABC.us-east-1.elb.amazonaws.com
   ```

2. **Configurar DNS en tu Dominio**
   
   Ve a tu proveedor de dominio (Namecheap, GoDaddy, etc.)
   
   **Opción A: Usar CNAME (para subdominios)**
   ```
   Type: CNAME
   Name: api (o www)
   Value: awseb-AWSEB-123ABC.us-east-1.elb.amazonaws.com
   TTL: Automático
   ```
   
   **Opción B: Usar Alias/ANAME (para dominio raíz)**
   ```
   Si tu proveedor lo permite:
   Type: ALIAS o ANAME
   Name: @ (raíz)
   Value: awseb-AWSEB-123ABC.us-east-1.elb.amazonaws.com
   ```
   
   **Opción C: Usar Route 53 de AWS (Más avanzado)**
   - Migra tu dominio a Route 53
   - Permite crear alias directo al Load Balancer

3. **Esperar Propagación DNS** (5 minutos - 48 horas)
   ```
   Verificar con:
   nslookup tudominio.com
   
   O desde el navegador:
   ping tudominio.com
   ```

---

### Paso 4: Probar HTTPS

1. **Abrir en navegador**
   ```
   https://tudominio.com/api/products
   ```
   
   Deberías ver:
   - ✅ Candado verde en la barra de direcciones
   - ✅ Certificado válido
   - ✅ Respuesta de tu API

2. **Actualizar tu Frontend**
   
   En `front/js/api.js` línea 3:
   ```javascript
   // Cambiar de:
   const API_BASE_URL = "http://app-tiendaucq-env.eba-w3s5zsy2.us-east-1.elasticbeanstalk.com/api";
   
   // A:
   const API_BASE_URL = "https://tudominio.com/api";
   ```

3. **Remover el Meta Tag CSP**
   
   Ya NO necesitas:
   ```html
   <meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">
   ```
   
   Puedes eliminarlo de todos los HTML.

---

## 📋 OPCIÓN 2: Sin Dominio (Usando CloudFront)

Si **NO** tienes dominio, puedes usar CloudFront (CDN de AWS) que te da HTTPS gratis:

### Paso 1: Crear Distribución CloudFront

1. **Ir a CloudFront**
   - AWS Console → CloudFront
   - Click "Create distribution"

2. **Configurar Origin**
   ```
   Origin domain: app-tiendaucq-env.eba-w3s5zsy2.us-east-1.elasticbeanstalk.com
   Protocol: HTTP only
   Origin path: (vacío)
   Name: backend-api
   ```

3. **Configurar Comportamiento**
   ```
   Viewer protocol policy: Redirect HTTP to HTTPS
   Allowed HTTP methods: GET, HEAD, OPTIONS, PUT, POST, PATCH, DELETE
   Cache policy: CachingDisabled (para APIs dinámicas)
   Origin request policy: AllViewer
   ```

4. **Configurar Distribución**
   ```
   Price class: Use all edge locations (mejor rendimiento)
   Alternate domain names (CNAMEs): (vacío por ahora)
   SSL certificate: Default CloudFront certificate
   ```

5. **Crear**
   - Click "Create distribution"
   - Espera 5-15 minutos

6. **Obtener URL**
   ```
   Tu nueva URL HTTPS será:
   https://d123abc.cloudfront.net/api
   ```

7. **Actualizar Frontend**
   ```javascript
   const API_BASE_URL = "https://d123abc.cloudfront.net/api";
   ```

---

## 📋 OPCIÓN 3: Certificado SSL Manual (SIN Certificate Manager)

Si tienes dominio pero no quieres usar ACM:

### Paso 1: Obtener Certificado Gratis con Let's Encrypt

```bash
# En tu servidor o localmente
sudo apt-get update
sudo apt-get install certbot

# Generar certificado
sudo certbot certonly --manual --preferred-challenges dns -d tudominio.com

# Seguir instrucciones para agregar registro TXT en DNS
```

### Paso 2: Subir Certificado a AWS

1. **Ir a IAM**
   - AWS Console → IAM → Server Certificates
   - Upload server certificate

2. **Subir archivos**
   ```
   Certificate body: cert.pem
   Certificate private key: privkey.pem
   Certificate chain: chain.pem
   ```

3. **Usar en Load Balancer**
   - Elastic Beanstalk → Configuration → Load balancer
   - Listener 443 → SSL certificate → Seleccionar el subido

---

## 🚨 SOLUCIÓN RÁPIDA SIN DOMINIO (5 minutos)

Si NO tienes dominio y quieres algo RÁPIDO:

### Usar Tunneling Service (Desarrollo/Demo)

**ngrok** (Gratis para desarrollo):

```powershell
# Descargar ngrok: https://ngrok.com/download

# Ejecutar (en tu máquina local)
ngrok http http://app-tiendaucq-env.eba-w3s5zsy2.us-east-1.elasticbeanstalk.com:80

# Te dará una URL HTTPS:
# https://abc123.ngrok.io → forwards to tu backend
```

**Ventajas:**
- ✅ HTTPS instantáneo
- ✅ No requiere dominio
- ✅ Gratis

**Desventajas:**
- ❌ URL cambia cada vez que reinicias
- ❌ Solo para desarrollo/demo
- ❌ No es profesional para producción

---

## 🔧 TROUBLESHOOTING

### Error: "Certificate not found"
- Verifica que el certificado esté en **us-east-1**
- Espera a que el estado sea "Issued"

### Error: "ERR_SSL_PROTOCOL_ERROR"
- Verifica que el listener 443 esté configurado
- Revisa que el Security Group permita puerto 443

### Error: "502 Bad Gateway"
- Tu aplicación Spring Boot no está escuchando correctamente
- Verifica logs en Elastic Beanstalk

### Error: CORS después de HTTPS
- Agrega HTTPS a tu configuración CORS en Spring Boot:
```java
@CrossOrigin(origins = {"https://tudominio.com", "https://usuario.github.io"})
```

---

## 📊 COMPARACIÓN DE OPCIONES

| Opción | Costo | Tiempo Setup | Dificultad | Recomendado para |
|--------|-------|--------------|------------|------------------|
| **ACM + Dominio** | $1-10/año (dominio) | 30-60 min | Media | ⭐ Producción |
| **CloudFront** | Gratis | 20 min | Fácil | Demo/Testing |
| **Let's Encrypt** | Gratis | 45 min | Alta | Avanzado |
| **ngrok** | Gratis | 5 min | Muy fácil | Desarrollo |

---

## 🎯 MI RECOMENDACIÓN

**Para tu proyecto:**

1. **AHORA (5 minutos)**: Usa **CloudFront** (Opción 2)
   - No requiere dominio
   - HTTPS gratis
   - Solo cambias la URL en api.js

2. **DESPUÉS (cuando tengas tiempo)**: Compra dominio + ACM (Opción 1)
   - Más profesional
   - URL bonita: `https://api.tiendaucq.com`
   - $1-10 por año

---

## 📝 CHECKLIST FINAL

- [ ] Certificado SSL creado (ACM o CloudFront)
- [ ] Load Balancer configurado con listener 443
- [ ] DNS configurado (si tienes dominio)
- [ ] URL actualizada en `front/js/api.js`
- [ ] Meta tag CSP removido de HTML
- [ ] Probado en navegador con candado verde
- [ ] CORS configurado para HTTPS
- [ ] Frontend desplegado en GitHub Pages

---

**¿Cuál opción prefieres?**

- **Tengo dominio** → Opción 1 (ACM)
- **No tengo dominio y quiero rápido** → Opción 2 (CloudFront)
- **Solo para probar/demo** → ngrok

**¡Dime cuál eliges y te guío paso a paso! 🚀**
