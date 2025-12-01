// Registrar el Service Worker
if ('serviceWorker' in navigator) {
  window.addEventListener('load', () => {
    navigator.serviceWorker.register('/front/js/service-worker.js')
      .then(registration => {
        console.log('Service Worker registrado exitosamente:', registration);
        // Verificar actualizaciones
        registration.addEventListener('updatefound', () => {
          const newWorker = registration.installing;
          newWorker.addEventListener('statechange', () => {
            if (newWorker.state === 'activated') {
              console.log('Nueva versión del Service Worker disponible');
              showUpdateNotification();
            }
          });
        });
      })
      .catch(error => {
        console.log('Error al registrar Service Worker:', error);
      });

    // Escuchar cambios del Service Worker
    navigator.serviceWorker.addEventListener('controllerchange', () => {
      console.log('Controlador de Service Worker cambió');
    });
  });
}

// Detectar conexión online/offline
window.addEventListener('online', () => {
  console.log('Conexión en línea restaurada');
  showNotification('Conectado', 'success');
  syncPendingData();
});

window.addEventListener('offline', () => {
  console.log('Conexión perdida - Modo offline');
  showNotification('Sin conexión - Modo offline', 'warning');
});

// Notificar sobre disponibilidad de actualización
function showUpdateNotification() {
  const notification = document.createElement('div');
  notification.className = 'update-notification';
  notification.innerHTML = `
    <p>Una nueva versión está disponible</p>
    <button onclick="location.reload()">Actualizar</button>
  `;
  document.body.appendChild(notification);
}

// Función genérica para mostrar notificaciones
function showNotification(message, type = 'info') {
  const notification = document.createElement('div');
  notification.className = `notification notification-${type}`;
  notification.textContent = message;
  notification.style.cssText = `
    position: fixed;
    top: 20px;
    right: 20px;
    padding: 15px 20px;
    border-radius: 4px;
    z-index: 9999;
    animation: slideIn 0.3s ease-in-out;
  `;
  
  if (type === 'success') {
    notification.style.backgroundColor = '#10b981';
  } else if (type === 'warning') {
    notification.style.backgroundColor = '#f59e0b';
  } else {
    notification.style.backgroundColor = '#3b82f6';
  }
  notification.style.color = 'white';
  
  document.body.appendChild(notification);
  
  setTimeout(() => {
    notification.remove();
  }, 3000);
}

// Solicitar permiso para notificaciones
function requestNotificationPermission() {
  if ('Notification' in window && Notification.permission === 'default') {
    Notification.requestPermission()
      .then(permission => {
        console.log('Permiso de notificaciones:', permission);
      });
  }
}

// Sincronizar datos pendientes cuando vuelva la conexión
async function syncPendingData() {
  if ('serviceWorker' in navigator && 'SyncManager' in window) {
    try {
      const registration = await navigator.serviceWorker.ready;
      await registration.sync.register('sync-pedidos');
      console.log('Sincronización programada');
    } catch (error) {
      console.log('Error al programar sincronización:', error);
    }
  }
}

// Detectar si está instalado como PWA
function checkPWAInstallation() {
  // Detectar si está en modo standalone (instalado como app)
  const isStandalone = window.navigator.standalone === true ||
                       window.matchMedia('(display-mode: standalone)').matches ||
                       window.matchMedia('(display-mode: fullscreen)').matches;
  
  console.log('Modo standalone:', isStandalone);
  
  if (isStandalone) {
    document.body.classList.add('pwa-installed');
  }
}

// Manejar instalación de PWA
let deferredPrompt;

window.addEventListener('beforeinstallprompt', (e) => {
  e.preventDefault();
  deferredPrompt = e;
  showInstallPrompt();
});

function showInstallPrompt() {
  const installButton = document.getElementById('install-app-button');
  if (installButton) {
    installButton.style.display = 'block';
    installButton.addEventListener('click', async () => {
      if (deferredPrompt) {
        deferredPrompt.prompt();
        const { outcome } = await deferredPrompt.userChoice;
        console.log(`Usuario respondió a la instalación: ${outcome}`);
        deferredPrompt = null;
        installButton.style.display = 'none';
      }
    });
  }
}

window.addEventListener('appinstalled', () => {
  console.log('PWA instalada exitosamente');
  showNotification('App instalada correctamente', 'success');
  deferredPrompt = null;
});

// Inicializar cuando el documento esté listo
document.addEventListener('DOMContentLoaded', () => {
  checkPWAInstallation();
  requestNotificationPermission();
  
  // Cargar estilos para notificaciones
  addNotificationStyles();
});

function addNotificationStyles() {
  const style = document.createElement('style');
  style.textContent = `
    @keyframes slideIn {
      from {
        transform: translateX(400px);
        opacity: 0;
      }
      to {
        transform: translateX(0);
        opacity: 1;
      }
    }
    
    .notification {
      animation: slideIn 0.3s ease-in-out;
    }
    
    .update-notification {
      position: fixed;
      bottom: 20px;
      left: 20px;
      padding: 15px 20px;
      background-color: #3b82f6;
      color: white;
      border-radius: 4px;
      z-index: 9999;
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }
    
    .update-notification button {
      margin-left: 10px;
      padding: 5px 15px;
      background-color: white;
      color: #3b82f6;
      border: none;
      border-radius: 3px;
      cursor: pointer;
      font-weight: bold;
    }
    
    .update-notification button:hover {
      background-color: #f0f9ff;
    }
  `;
  document.head.appendChild(style);
}
