<template>
  <div id="app">
    <nav>
      <router-link to="/" exact-active-class="active">Inicio</router-link>
      <router-link to="/categorias" exact-active-class="active">Categorías</router-link>
    </nav>
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
};
</script>

<style>
nav {
  display: flex;
  justify-content: center;
  background-color: #333;
  padding: 10px;
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