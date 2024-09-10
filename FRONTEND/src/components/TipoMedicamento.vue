<template>
  <div class="container mt-5">
    <h2>Tipos de Medicamentos</h2>
    <div class="d-flex justify-content-start mb-3">
      <router-link to="/insertar-tipo" class="btn btn-success">
        Añadir Nuevo Tipo
      </router-link>
    </div>
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
            <div class="btn-group" role="group">
              <button class="btn btn-primary" @click="abrirModal(tipo)">Modificar</button>
              <button class="btn btn-danger" @click="confirmarEliminacion(tipo.id)">Eliminar</button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Modal para editar tipo -->
    <div class="modal fade" id="editarModal" tabindex="-1" aria-labelledby="editarModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="editarModalLabel">Modificar Tipo</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form @submit.prevent="guardarCambios">
              <div class="mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input v-model="tipoSeleccionado.nombre" type="text" class="form-control" id="nombre" required />
              </div>
              <div class="mb-3">
                <label for="descripcion" class="form-label">Descripción</label>
                <input v-model="tipoSeleccionado.descripcion" type="text" class="form-control" id="descripcion" required />
              </div>
              <div class="mb-3">
                <label for="usosComunes" class="form-label">Usos Comunes</label>
                <input v-model="tipoSeleccionado.usosComunes" type="text" class="form-control" id="usosComunes" required />
              </div>
              <div class="mb-3">
                <label for="interacciones" class="form-label">Interacciones</label>
                <input v-model="tipoSeleccionado.interacciones" type="text" class="form-control" id="interacciones" required />
              </div>
              <button type="submit" class="btn btn-primary">Guardar Cambios</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { ref } from 'vue';

export default {
  name: 'TipoMedicamento',
  setup() {
    const tipos = ref([]);
    const tipoSeleccionado = ref({ id: null, nombre: '', descripcion: '', usosComunes: '', interacciones: '' });

    const obtenerTipos = async () => {
      try {
        const response = await axios.get('/api/tipos');
        tipos.value = response.data;
      } catch (error) {
        console.error('Error al obtener los tipos:', error);
      }
    };

    const confirmarEliminacion = (id) => {
      if (confirm("¿Está seguro de que desea eliminar este tipo?")) {
        eliminarTipo(id);
      }
    };

    const eliminarTipo = async (id) => {
      try {
        await axios.delete(`/api/tipos/${id}`);
        obtenerTipos();
      } catch (error) {
        console.error('Error al eliminar el tipo:', error);
      }
    };

    const abrirModal = (tipo) => {
      tipoSeleccionado.value = { ...tipo };
      const modal = new bootstrap.Modal(document.getElementById('editarModal'));
      modal.show();
    };

    const guardarCambios = async () => {
      try {
        await axios.put(`/api/tipos/${tipoSeleccionado.value.id}`, tipoSeleccionado.value);
        obtenerTipos();
        const modal = bootstrap.Modal.getInstance(document.getElementById('editarModal'));
        modal.hide();
      } catch (error) {
        console.error('Error al guardar los cambios:', error);
      }
    };

    // Llamar a obtenerTipos cuando el componente se monta
    obtenerTipos();

    return {
      tipos,
      tipoSeleccionado,
      obtenerTipos,
      confirmarEliminacion,
      eliminarTipo,
      abrirModal,
      guardarCambios
    };
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