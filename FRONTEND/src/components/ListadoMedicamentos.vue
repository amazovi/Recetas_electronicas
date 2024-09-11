<template>
  <div class="container mt-5">
    <h2>Medicamentos</h2>
    <div class="d-flex justify-content-start mb-3">
      <button class="btn btn-success" @click="navegarAInsertar">Añadir</button>
    </div>
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

    <!-- Modal de edición de medicamento -->
    <div class="modal fade" id="editarModal" tabindex="-1" aria-labelledby="editarModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="editarModalLabel">Editar Medicamento</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form @submit.prevent="guardarMedicamento">
              <div class="mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" class="form-control" id="nombre" v-model="medicamentoSeleccionado.nombre" required>
              </div>
              <div class="mb-3">
                <label for="descripcion" class="form-label">Descripción</label>
                <textarea class="form-control" id="descripcion" v-model="medicamentoSeleccionado.descripcion" required></textarea>
              </div>
              <div class="mb-3">
                <label for="dosisRecomendada" class="form-label">Dosis Recomendada</label>
                <input type="text" class="form-control" id="dosisRecomendada" v-model="medicamentoSeleccionado.dosisRecomendada" required>
              </div>
              <div class="mb-3">
                <label for="precio" class="form-label">Precio</label>
                <div class="input-group">
                  <input type="text" class="form-control text-end" id="precio" v-model="medicamentoSeleccionado.precio" v-currency required>
                  <span class="input-group-text">€</span>
                </div>
              </div>
              <div class="mb-3">
                <label for="tipo" class="form-label">Tipo</label>
                <input type="text" class="form-control" id="tipo" v-model="medicamentoSeleccionado.tipoNombre" required>
              </div>
              <button type="submit" class="btn btn-primary">Guardar</button>
            </form>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal de confirmación de modificación -->
    <div class="modal fade" id="confirmacionModificacionModal" tabindex="-1" aria-labelledby="confirmacionModificacionModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="confirmacionModificacionModalLabel">Modificación Exitosa</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            El medicamento {{ medicamentoSeleccionado.nombre }} se ha modificado correctamente.
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
      medicamentoSeleccionado: {}
    };
  },
  directives: {
    currency: {
      bind(el) {
        el.addEventListener('input', () => {
          let value = el.value.replace(/\D/g, '');
          value = (value / 100).toFixed(2).replace('.', ',').replace(/\d(?=(\d{3})+,\d{2})/g, '$&.');
          el.value = value;
        });
      },
      update(el) {
        let value = el.value.replace(/\D/g, '');
        value = (value / 100).toFixed(2).replace('.', ',').replace(/\d(?=(\d{3})+,\d{2})/g, '$&.');
        el.value = value;
      }
    }
  },
  methods: {
    navegarAInsertar() {
      this.$router.push('/insertar');
    },
    abrirModalEditar(medicamento) {
      this.medicamentoSeleccionado = { ...medicamento };
      this.medicamentoSeleccionado.precio = this.formatearPrecio(this.medicamentoSeleccionado.precio);
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
      } catch (error) {
        console.error(error);
      }
    },
    async guardarMedicamento() {
      try {
        this.medicamentoSeleccionado.precio = parseFloat(this.medicamentoSeleccionado.precio.replace(',', '.').replace(/[^0-9.-]+/g, ""));
        await axios.put(`/api/medicamentos/${this.medicamentoSeleccionado.id}`, this.medicamentoSeleccionado);
        this.obtenerMedicamentos();
        const modal = bootstrap.Modal.getInstance(document.getElementById('editarModal'));
        modal.hide();
        const confirmacionModal = new bootstrap.Modal(document.getElementById('confirmacionModificacionModal'));
        confirmacionModal.show();
      } catch (error) {
        console.error(error);
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
    formatearPrecio(precio) {
      return precio.toFixed(2).replace('.', ',').replace(/\d(?=(\d{3})+,\d{2})/g, '$&.') + ' €';
    }
  },
  mounted() {
    this.obtenerMedicamentos();
  }
};
</script>

<style scoped>
.container {
  max-width: 800px;
}

.table th {
  text-align: center;
}

.table td {
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
</style>