<template>
  <div class="container mt-5">
    <h2>{{ id ? 'Editar' : 'Añadir' }} Categoría</h2>
    <form @submit.prevent="guardarTipo">
      <div class="mb-3">
        <label for="nombre" class="form-label">Nombre</label>
        <input v-model="tipo.nombre" type="text" class="form-control" id="nombre" required />
      </div>
      <div class="mb-3">
        <label for="descripcion" class="form-label">Descripción</label>
        <input v-model="tipo.descripcion" type="text" class="form-control" id="descripcion" required />
      </div>
      <div class="mb-3">
        <label for="usosComunes" class="form-label">Usos Comunes</label>
        <input v-model="tipo.usosComunes" type="text" class="form-control" id="usosComunes" required />
      </div>
      <div class="mb-3">
        <label for="interacciones" class="form-label">Interacciones</label>
        <input v-model="tipo.interacciones" type="text" class="form-control" id="interacciones" required />
      </div>
      <button type="submit" class="btn btn-primary">{{ id ? 'Actualizar' : 'Guardar' }} </button>
    </form>

    <!-- Modal -->
    <div class="modal fade" id="messageModal" tabindex="-1" aria-labelledby="messageModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="messageModalLabel">Notificación</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            {{ mensajeModal }}
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
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import * as bootstrap from 'bootstrap';

export default {
  name: 'InsertarTipo',
  setup() {
    const route = useRoute();
    const id = route.params.id;
    const tipo = ref({
      nombre: '',
      descripcion: '',
      usosComunes: '',
      interacciones: ''
    });
    const mensajeModal = ref('');

    const obtenerTipo = async () => {
      if (id) {
        try {
          const response = await axios.get(`/api/tipos/${id}`);
          tipo.value = response.data;
        } catch (error) {
          console.error('Error al obtener el tipo de medicamento:', error);
        }
      }
    };

    const guardarTipo = async () => {
      try {
        if (id) {
          await axios.put(`/api/tipos/${id}`, tipo.value);
          mensajeModal.value = 'El tipo de medicamento ha sido actualizado correctamente';
        } else {
          await axios.post('/api/tipos', tipo.value);
          mensajeModal.value = 'El tipo de medicamento ha sido guardado correctamente';
        }
        mostrarModal();
      } catch (error) {
        console.error('Error al guardar el tipo de medicamento:', error);
        mensajeModal.value = 'Error al guardar el tipo de medicamento';
        mostrarModal();
      }
    };

    const mostrarModal = () => {
      const modalEl = document.getElementById('messageModal');
      const modal = new bootstrap.Modal(modalEl);
      modal.show();
    };

    onMounted(() => {
      obtenerTipo();
    });

    return {
      id,
      tipo,
      mensajeModal,
      guardarTipo
    };
  }
};
</script>

<style scoped>
.container {
  max-width: 800px; 
}
</style>