<template>
  <div id="app">
    <nav class="navbar fixed-top navbar-expand-lg navbar-dark bg-dark">
      <div class="container-fluid">
        <router-link class="navbar-brand" to="/">Recetas</router-link>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <router-link class="nav-link" to="/" exact-active-class="active">Inicio</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/categorias" exact-active-class="active">Categorías</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/medicamentos" exact-active-class="active">Medicamentos</router-link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
    <main class="mt-5 pt-3">
      <router-view />
    </main>
  </div>
</template>

<script>
export default {
  name: 'App'
};
</script>

<style>
nav {
  z-index: 1030; /* Asegúrate de que la barra de navegación esté por encima de otros elementos */
}

main {
  margin-top: 70px; /* Ajusta este valor según la altura de tu barra de navegación */
}

nav a {
  color: white;
  margin: 0 15px;
  text-decoration: none;
  padding: 10px;
  border-radius: 5px;
  transition: background-color 0.3s, color 0.3s;
}

nav a:hover {
  background-color: #555;
}

nav a.active {
  background-color: #007bff;
  color: white;
}
</style>