<template>
  <div class="container mt-5">
    <h2>Tipos de Medicamentos</h2>
    <div class="d-flex justify-content-start mb-3">
      <router-link to="/insertartipo" class="btn btn-success">
        Añadir
      </router-link>
    </div>
    <table class="table table-striped mt-3">
      <thead>
        <tr>
          <th class="text-center">Nombre</th>
          <th class="text-center">Descripción</th>
          <th class="text-center">Usos Comunes</th>
          <th class="text-center">Interacciones</th>
          <th class="text-center">Acciones</th>
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
              <button class="btn btn-danger" @click="abrirModalConfirmacion(tipo)">Eliminar</button>
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

    <!-- Modal de confirmación de eliminación -->
    <div class="modal fade" id="confirmacionModal" tabindex="-1" aria-labelledby="confirmacionModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="confirmacionModalLabel">Confirmar Eliminación</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            ¿Está seguro de que desea eliminar el tipo {{ tipoSeleccionado.nombre }}?
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
            <button type="button" class="btn btn-danger" @click="eliminarTipoConfirmado">Eliminar</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal de confirmación de guardado -->
    <div class="modal fade" id="confirmacionGuardadoModal" tabindex="-1" aria-labelledby="confirmacionGuardadoModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="confirmacionGuardadoModalLabel">Confirmación</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            Cambios en el tipo {{ tipoSeleccionado.nombre }} guardados.
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal de confirmación de eliminación exitosa -->
    <div class="modal fade" id="confirmacionEliminacionModal" tabindex="-1" aria-labelledby="confirmacionEliminacionModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="confirmacionEliminacionModalLabel">Confirmación</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            El tipo {{ tipoSeleccionado.nombre }} ha sido eliminado.
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { ref } from 'vue';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import * as bootstrap from 'bootstrap';

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

    const abrirModalConfirmacion = (tipo) => {
      tipoSeleccionado.value = { ...tipo };
      const modal = new bootstrap.Modal(document.getElementById('confirmacionModal'));
      modal.show();
    };

    const eliminarTipoConfirmado = async () => {
      try {
        await axios.delete(`/api/tipos/${tipoSeleccionado.value.id}`);
        obtenerTipos();
        const modal = bootstrap.Modal.getInstance(document.getElementById('confirmacionModal'));
        modal.hide();
        const confirmacionEliminacionModal = new bootstrap.Modal(document.getElementById('confirmacionEliminacionModal'));
        confirmacionEliminacionModal.show();
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
        const modal = bootstrap.Modal.getInstance(document.getElementById('editarModal'));
        modal.hide();
        const confirmacionModal = new bootstrap.Modal(document.getElementById('confirmacionGuardadoModal'));
        confirmacionModal.show();
        obtenerTipos();
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
      abrirModalConfirmacion,
      eliminarTipoConfirmado,
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

.table th {
  text-align: center;
}

.btn {
  margin-right: 10px;
}
</style>