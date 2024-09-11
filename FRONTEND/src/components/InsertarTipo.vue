<template>
  <div class="container mt-5">
    <h2>Medicamentos</h2>
    <div class="d-flex justify-content-start mb-3">
      <router-link to="/insertarmedicamento" class="btn btn-success">
        Añadir
      </router-link>
    </div>
    <table class="table table-striped mt-3">
      <thead>
        <tr>
          <th class="text-center">Nombre</th>
          <th class="text-center">Descripción</th>
          <th class="text-center">Dosis Recomendada</th>
          <th class="text-center">Precio</th>
          <th class="text-center">Tipo</th>
          <th class="text-center">Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="medicamento in medicamentos" :key="medicamento.id">
          <td>{{ medicamento.nombre }}</td>
          <td>{{ medicamento.descripcion }}</td>
          <td>{{ medicamento.dosisRecomendada }}</td>
          <td>{{ medicamento.precio }}</td>
          <td>{{ medicamento.tipo.nombre }}</td>
          <td>
            <div class="btn-group" role="group">
              <button class="btn btn-primary" @click="abrirModal(medicamento)">Modificar</button>
              <button class="btn btn-danger" @click="abrirModalConfirmacion(medicamento)">Eliminar</button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Modal para editar medicamento -->
    <div class="modal fade" id="editarModal" tabindex="-1" aria-labelledby="editarModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="editarModalLabel">Modificar Medicamento</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form @submit.prevent="guardarCambios">
              <div class="mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input v-model="medicamentoSeleccionado.nombre" type="text" class="form-control" id="nombre" required />
              </div>
              <div class="mb-3">
                <label for="descripcion" class="form-label">Descripción</label>
                <input v-model="medicamentoSeleccionado.descripcion" type="text" class="form-control" id="descripcion" required />
              </div>
              <div class="mb-3">
                <label for="dosisRecomendada" class="form-label">Dosis Recomendada</label>
                <input v-model="medicamentoSeleccionado.dosisRecomendada" type="text" class="form-control" id="dosisRecomendada" required />
              </div>
              <div class="mb-3">
                <label for="precio" class="form-label">Precio</label>
                <input v-model="medicamentoSeleccionado.precio" type="number" class="form-control" id="precio" required />
              </div>
              <div class="mb-3">
                <label for="tipo" class="form-label">Tipo</label>
                <select v-model="medicamentoSeleccionado.tipo.id" class="form-control" id="tipo" required>
                  <option v-for="tipo in tipos" :key="tipo.id" :value="tipo.id">{{ tipo.nombre }}</option>
                </select>
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
            ¿Está seguro de que desea eliminar el medicamento {{ medicamentoSeleccionado.nombre }}?
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
            <button type="button" class="btn btn-danger" @click="eliminarMedicamentoConfirmado">Eliminar</button>
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
            Cambios en el medicamento {{ medicamentoSeleccionado.nombre }} guardados.
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
            El medicamento {{ medicamentoSeleccionado.nombre }} ha sido eliminado.
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
  name: 'ListadoMedicamentos',
  setup() {
    const medicamentos = ref([]);
    const tipos = ref([]);
    const medicamentoSeleccionado = ref({ id: null, nombre: '', descripcion: '', dosisRecomendada: '', precio: '', tipo: { id: null, nombre: '' } });

    const obtenerMedicamentos = async () => {
      try {
        const response = await axios.get('/api/medicamentos');
        medicamentos.value = response.data;
      } catch (error) {
        console.error('Error al obtener los medicamentos:', error);
      }
    };

    const obtenerTipos = async () => {
      try {
        const response = await axios.get('/api/tipos');
        tipos.value = response.data;
      } catch (error) {
        console.error('Error al obtener los tipos:', error);
      }
    };

    const abrirModalConfirmacion = (medicamento) => {
      medicamentoSeleccionado.value = { ...medicamento };
      const modal = new bootstrap.Modal(document.getElementById('confirmacionModal'));
      modal.show();
    };

    const eliminarMedicamentoConfirmado = async () => {
      try {
        await axios.delete(`/api/medicamentos/${medicamentoSeleccionado.value.id}`);
        obtenerMedicamentos();
        const modal = bootstrap.Modal.getInstance(document.getElementById('confirmacionModal'));
        modal.hide();
        const confirmacionEliminacionModal = new bootstrap.Modal(document.getElementById('confirmacionEliminacionModal'));
        confirmacionEliminacionModal.show();
      } catch (error) {
        console.error('Error al eliminar el medicamento:', error);
      }
    };

    const abrirModal = (medicamento) => {
      medicamentoSeleccionado.value = { ...medicamento };
      const modal = new bootstrap.Modal(document.getElementById('editarModal'));
      modal.show();
    };

    const guardarCambios = async () => {
      try {
        await axios.put(`/api/medicamentos/${medicamentoSeleccionado.value.id}`, medicamentoSeleccionado.value);
        const modal = bootstrap.Modal.getInstance(document.getElementById('editarModal'));
        modal.hide();
        const confirmacionModal = new bootstrap.Modal(document.getElementById('confirmacionGuardadoModal'));
        confirmacionModal.show();
        obtenerMedicamentos();
      } catch (error) {
        console.error('Error al guardar los cambios:', error);
      }
    };

    // Llamar a obtenerMedicamentos y obtenerTipos cuando el componente se monta
    obtenerMedicamentos();
    obtenerTipos();

    return {
      medicamentos,
      tipos,
      medicamentoSeleccionado,
      obtenerMedicamentos,
      abrirModalConfirmacion,
      eliminarMedicamentoConfirmado,
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