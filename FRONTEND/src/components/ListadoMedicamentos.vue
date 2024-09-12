<template>
  <div class="container mt-5">
    <h2>Medicamentos</h2>
    <div class="d-flex justify-content-start mb-3">
      <button class="btn btn-success" @click="abrirModalNuevoMedicamento">
        Añadir
      </button>
    </div>
    <div class="table-responsive">
      <table class="table table-bordered">
        <thead>
          <tr>
            <th class="text-center">Nombre</th>
            <th class="text-center">Descripción</th>
            <th class="text-center">Dosis Recomendada</th>
            <th class="text-center">Precio</th>
            <th class="text-center">Tipo</th>
            <th class="text-center"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="medicamento in medicamentos" :key="medicamento.id">
            <td>{{ medicamento.nombre }}</td>
            <td>{{ medicamento.descripcion }}</td>
            <td>{{ medicamento.dosisRecomendada }}</td>
            <td class="text-end nowrap">{{ formatearPrecio(medicamento.precio) }}</td>
            <td>{{ medicamento.tipoNombre }}</td>
            <td class="text-center">
              <div class="btn-group" role="group">
                <button class="btn btn-warning" @click="abrirModalEditar(medicamento)">Modificar</button>
                <button class="btn btn-danger" @click="abrirModalConfirmacion(medicamento)">Eliminar</button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Modal para añadir o modificar medicamento -->
    <div class="modal fade" id="editarModal" tabindex="-1" aria-labelledby="editarModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="editarModalLabel">{{ esNuevoMedicamento ? 'Añadir Medicamento' : 'Modificar Medicamento' }}</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form @submit.prevent="guardarMedicamento">
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
                <input v-model="medicamentoSeleccionado.precio" type="text" class="form-control text-end" id="precio" required />
              </div>
              <div class="mb-3">
                <label for="tipo" class="form-label">Tipo</label>
                <select v-model="medicamentoSeleccionado.tipoId" class="form-control" id="tipo" required>
                  <option v-for="tipo in tipos" :key="tipo.id" :value="tipo.id">{{ tipo.nombre }}</option>
                </select>
              </div>
              <button type="submit" class="btn btn-primary">{{ esNuevoMedicamento ? 'Añadir Medicamento' : 'Guardar Cambios' }}</button>
            </form>
          </div>
        </div>
      </div>
    </div>

    <div class="modal fade" id="confirmacionModal" tabindex="-1" aria-labelledby="confirmacionModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
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

    <div class="modal fade" id="confirmacionGuardadoModal" tabindex="-1" aria-labelledby="confirmacionGuardadoModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
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

    <div class="modal fade" id="confirmacionEliminacionModal" tabindex="-1" aria-labelledby="confirmacionEliminacionModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
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
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import * as bootstrap from 'bootstrap';

export default {
  data() {
    return {
      medicamentos: [],
      medicamentoSeleccionado: {},
      tipos: [],
      esNuevoMedicamento: true
    };
  },
  methods: {
    abrirModalNuevoMedicamento() {
      this.esNuevoMedicamento = true;
      this.medicamentoSeleccionado = { id: null, nombre: '', descripcion: '', dosisRecomendada: '', precio: '', tipoId: null };
      const modal = new bootstrap.Modal(document.getElementById('editarModal'));
      modal.show();
    },
    abrirModalEditar(medicamento) {
      this.esNuevoMedicamento = false;
      this.medicamentoSeleccionado = { ...medicamento };
      const modal = new bootstrap.Modal(document.getElementById('editarModal'));
      modal.show();
    },
    abrirModalConfirmacion(medicamento) {
      this.medicamentoSeleccionado = medicamento;
      const modal = new bootstrap.Modal(document.getElementById('confirmacionModal'));
      modal.show();
    },
    async eliminarMedicamentoConfirmado() {
      try {
        await axios.delete(`/api/medicamentos/${this.medicamentoSeleccionado.id}`);
        this.obtenerMedicamentos();
        const modal = bootstrap.Modal.getInstance(document.getElementById('confirmacionModal'));
        modal.hide();
        const confirmacionEliminacionModal = new bootstrap.Modal(document.getElementById('confirmacionEliminacionModal'));
        confirmacionEliminacionModal.show();
      } catch (error) {
        console.error(error);
      }
    },
    async guardarMedicamento() {
      try {
        // Convertir comas a puntos en el precio
        this.medicamentoSeleccionado.precio = parseFloat(this.medicamentoSeleccionado.precio.replace(',', '.'));
        
        if (this.esNuevoMedicamento) {
          await axios.post('/api/medicamentos', this.medicamentoSeleccionado);
        } else {
          await axios.put(`/api/medicamentos/${this.medicamentoSeleccionado.id}`, this.medicamentoSeleccionado);
        }
        this.obtenerMedicamentos();
        const modal = bootstrap.Modal.getInstance(document.getElementById('editarModal'));
        modal.hide();
        const confirmacionModal = new bootstrap.Modal(document.getElementById('confirmacionGuardadoModal'));
        confirmacionModal.show();
      } catch (error) {
        console.error('Error al guardar el medicamento:', error);
      }
    },
    async obtenerMedicamentos() {
      try {
        const response = await axios.get('/api/medicamentos');
        this.medicamentos = response.data;
      } catch (error) {
        console.error(error);
      }
    },
    async obtenerTipos() {
      try {
        const response = await axios.get('/api/tipos');
        this.tipos = response.data;
      } catch (error) {
        console.error('Error al obtener los tipos:', error);
      }
    },
    formatearPrecio(precio) {
      return precio.toFixed(2).replace('.', ',').replace(/\d(?=(\d{3})+,\d{2})/g, '$&.') + ' €';
    }
  },
  mounted() {
    this.obtenerMedicamentos();
    this.obtenerTipos();
  }
};
</script>

<style scoped>
.container {
  max-width: 100%;
}

.table-responsive {
  overflow-x: auto;
}

.table th, .table td {
  text-align: center;
  vertical-align: middle;
}

.table td.text-end {
  text-align: right;
}

.nowrap {
  white-space: nowrap;
}

.table .btn {
  margin-right: 5px;
}

/* Ajustar el margen superior del modal para que no tape la barra de navegación */
.modal-dialog {
  margin-top: 150px; 
}

.text-end {
  text-align: right;
}
</style>