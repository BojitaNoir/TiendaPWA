const express = require('express');
const path = require('path');
const http = require('http');
const https = require('https');
const fs = require('fs');

const app = express();
const PORT = 3000;

// Servir archivos estáticos desde /front
app.use('/front', express.static(path.join(__dirname, 'front')));

// Ruta raíz
app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, 'front', 'index.html'));
});

// Redirigir login
app.get('/login', (req, res) => {
  res.sendFile(path.join(__dirname, 'front', 'login.html'));
});

// Health check para backend
app.get('/api/health', (req, res) => {
  res.json({ status: 'ok' });
});

// Iniciar servidor HTTP
const httpServer = http.createServer(app);

httpServer.listen(PORT, '127.0.0.1', () => {
  console.log(`
╔════════════════════════════════════════════════╗
║          TiendaPWA Server Started              ║
╠════════════════════════════════════════════════╣
║                                                ║
║  Frontend:  http://127.0.0.1:${PORT}              ║
║  Backend:   http://localhost:8081              ║
║                                                ║
║  PWA Ready: Presiona Ctrl+Shift+I para DevTools║
║  App → Manifest para instalar la PWA          ║
║                                                ║
╚════════════════════════════════════════════════╝
  `);
});

// Manejo de errores
process.on('uncaughtException', (err) => {
  console.error('Error fatal:', err);
  process.exit(1);
});
