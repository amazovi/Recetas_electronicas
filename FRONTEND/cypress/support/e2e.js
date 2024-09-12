// cypress/support/e2e.js

// Importar comandos personalizados
import './commands';

// Configuración global
Cypress.on('uncaught:exception', (err, runnable) => {
  // Prevenir que Cypress falle el test en caso de excepciones no capturadas
  return false;
});