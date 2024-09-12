describe('Página de TipoMedicamento', () => {
  beforeEach(() => {
    // Visita la página de tipos de medicamentos
    cy.visit('/categorias');
  });

  it('debería mostrar el título "Categorías"', () => {
    cy.contains('h2', 'Categorías').should('be.visible');
  });

  it('debería mostrar la tabla con los tipos', () => {
    cy.get('table').should('be.visible');
    cy.get('thead').contains('Nombre').should('be.visible');
    cy.get('thead').contains('Descripción').should('be.visible');
    cy.get('thead').contains('Usos Comunes').should('be.visible');
    cy.get('thead').contains('Interacciones').should('be.visible');
  });

  it('debería abrir el modal para añadir un nuevo tipo', () => {
    cy.get('button').contains('Añadir').click();
    cy.get('#editarModal').should('be.visible');
    cy.get('#editarModal').contains('Añadir Tipo').should('be.visible');
  });

  it('debería completar el formulario para añadir un nuevo tipo', () => {
    cy.get('button').contains('Añadir').click();
    cy.get('#editarModal').should('be.visible');
    cy.get('#nombre').type('Nuevo Tipo');
    cy.get('#descripcion').type('Descripción del nuevo tipo');
    cy.get('#usosComunes').type('Usos comunes del nuevo tipo');
    cy.get('#interacciones').type('Interacciones del nuevo tipo');
    cy.get('#nombre').should('have.value', 'Nuevo Tipo');
    cy.get('#descripcion').should('have.value', 'Descripción del nuevo tipo');
    cy.get('#usosComunes').should('have.value', 'Usos comunes del nuevo tipo');
    cy.get('#interacciones').should('have.value', 'Interacciones del nuevo tipo');
  });

 
});