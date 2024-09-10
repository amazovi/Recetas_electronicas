<template>
    <div class="home">
    <img alt="logo" class="logo" src="@/assets/logo-transparente.svg"/>
      <h1>Recetas Electrónicas</h1>
      <p>Bienvenido a nuestra plataforma de recetas electrónicas. Aquí podrás gestionar tus recetas de manera fácil y rápida.</p>
    </div>
    <main>
      <RouterView />
    </main>
  </template>

  
  <style scoped>
  .home {
    text-align: center;
    padding: 20px;
    background-color: #f0f8ff;
    color: #333;
  }
  
  .home h1 {
    color: #2c3e50;
    font-size: 2.5em;
    margin-bottom: 20px;
  }
  
  .home p {
    font-size: 1.2em;
    margin-bottom: 20px;
  }
  
  .home .portada {
    max-width: 100%;
    height: auto;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  </style>