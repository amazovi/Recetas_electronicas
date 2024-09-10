describe('Recetas Electrónicas', () => {
    it('Visitar la página principal y ver el título', () => {
      cy.visit('http://localhost:3000');
      cy.contains('Recetas Electrónicas');
    });
  });