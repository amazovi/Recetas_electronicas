<template>
  <div class="container mt-5">
    <h2>Tipos de Medicamentos</h2>
    <button class="btn btn-success mb-3" @click="mostrarFormulario = !mostrarFormulario">
      {{ mostrarFormulario ? 'Cancelar' : 'Añadir Nuevo Tipo' }}
    </button>
    <form v-if="mostrarFormulario" @submit.prevent="agregarTipo">
      <input v-model="nuevoTipo.id" type="hidden" />
      <div class="mb-3">
        <label for="nombre" class="form-label">Nombre</label>
        <input v-model="nuevoTipo.nombre" type="text" class="form-control" id="nombre" required />
      </div>
      <div class="mb-3">
        <label for="descripcion" class="form-label">Descripción</label>
        <input v-model="nuevoTipo.descripcion" type="text" class="form-control" id="descripcion" required />
      </div>
      <div class="mb-3">
        <label for="usosComunes" class="form-label">Usos Comunes</label>
        <input v-model="nuevoTipo.usosComunes" type="text" class="form-control" id="usosComunes" required />
      </div>
      <div class="mb-3">
        <label for="interacciones" class="form-label">Interacciones</label>
        <input v-model="nuevoTipo.interacciones" type="text" class="form-control" id="interacciones" required />
      </div>
      <button type="submit" class="btn btn-primary">Guardar Tipo</button>
    </form>

    <table class="table table-striped mt-3">
      <thead>
        <tr>
          <th>Nombre</th>
          <th>Descripción</th>
          <th>Usos Comunes</th>
          <th>Interacciones</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="tipo in tipos" :key="tipo.id">
          <td>{{ tipo.nombre }}</td>
          <td>{{ tipo.descripcion }}</td>
          <td>{{ tipo.usosComunes }}</td>
          <td>{{ tipo.interacciones }}</td>
          <td>
            <button class="btn btn-danger" @click="confirmarEliminacion(tipo.id)">Eliminar</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      tipos: [],  // Lista de tipos
      nuevoTipo: { id: null, nombre: '', descripcion: '', usosComunes: '', interacciones: '' },
      mostrarFormulario: false
    };
  },
  methods: {
    async agregarTipo() {
      try {
        await axios.post('http://localhost:8080/api/tipos', this.nuevoTipo);
        this.obtenerTipos();
        this.nuevoTipo = { id: null, nombre: '', descripcion: '', usosComunes: '', interacciones: '' };
        this.mostrarFormulario = false;
      } catch (error) {
        console.error(error);
      }
    },
    confirmarEliminacion(id) {
      if (confirm("¿Está seguro de que desea eliminar este tipo?")) {
        this.eliminarTipo(id);
      }
    },
    async eliminarTipo(id) {
      try {
        await axios.delete(`http://localhost:8080/api/tipos/${id}`);
        this.obtenerTipos();
      } catch (error) {
        console.error(error);
      }
    },
    async obtenerTipos() {
      try {
        const response = await axios.get('http://localhost:8080/api/tipos');
        this.tipos = response.data;
      } catch (error) {
        console.error(error);
      }
    }
  },
  mounted() {
    this.obtenerTipos();
  }
};
</script>

<style scoped>
.container {
  padding: 20px;
}

h2 {
  color: #2c3e50;
  font-size: 2em;
  margin-bottom: 20px;
}

.table {
  margin-top: 20px;
}

.table th, .table td {
  text-align: left;
  padding: 10px;
}

.btn {
  margin-right: 10px;
}
</style>