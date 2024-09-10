<template>
    <div class="container mt-5">
      <h2>{{ id ? 'Editar' : 'Añadir' }} Medicamento</h2>
      <form @submit.prevent="guardarMedicamento">
        <div class="mb-3">
          <label for="nombre" class="form-label">Nombre</label>
          <input v-model="medicamento.nombre" type="text" class="form-control" id="nombre" required />
        </div>
        <div class="mb-3">
          <label for="descripcion" class="form-label">Descripción</label>
          <input v-model="medicamento.descripcion" type="text" class="form-control" id="descripcion" required />
        </div>
        <div class="mb-3">
          <label for="dosis" class="form-label">Dosis Recomendada</label>
          <input v-model="medicamento.dosisRecomendada" type="text" class="form-control" id="dosis" required />
        </div>
        <div class="mb-3">
          <label for="precio" class="form-label">Precio</label>
          <input v-model="medicamento.precio" type="number" class="form-control" id="precio" required />
        </div>
        <div class="mb-3">
          <label for="tipo" class="form-label">Tipo</label>
          <select v-model="medicamento.tipoId" class="form-select" id="tipo" required>
            <option v-for="tipo in tipos" :key="tipo.id" :value="tipo.id">{{ tipo.nombre }}</option>
          </select>
        </div>
        <button type="submit" class="btn btn-primary">{{ id ? 'Actualizar' : 'Guardar' }} Medicamento</button>
      </form>
  
      <h2 class="mt-5">Añadir Tipo de Medicamento</h2>
      <form @submit.prevent="guardarTipo">
        <div class="mb-3">
          <label for="tipoNombre" class="form-label">Nombre</label>
          <input v-model="nuevoTipo.nombre" type="text" class="form-control" id="tipoNombre" required />
        </div>
        <div class="mb-3">
          <label for="tipoDescripcion" class="form-label">Descripción</label>
          <input v-model="nuevoTipo.descripcion" type="text" class="form-control" id="tipoDescripcion" required />
        </div>
        <div class="mb-3">
          <label for="tipoUsosComunes" class="form-label">Usos Comunes</label>
          <input v-model="nuevoTipo.usosComunes" type="text" class="form-control" id="tipoUsosComunes" required />
        </div>
        <div class="mb-3">
          <label for="tipoInteracciones" class="form-label">Interacciones</label>
          <input v-model="nuevoTipo.interacciones" type="text" class="form-control" id="tipoInteracciones" required />
        </div>
        <button type="submit" class="btn btn-success">Guardar Tipo</button>
      </form>
  
      <!-- Toast -->
      <div class="toast-container position-fixed bottom-0 end-0 p-3">
        <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
          <div class="toast-header">
            <strong class="me-auto">Notificación</strong>
            <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
          </div>
          <div class="toast-body">
            {{ mensajeToast }}
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  import { ref, onMounted } from 'vue';
  import { useRoute, useRouter } from 'vue-router';
  
  export default {
    name: 'InsertarTipo',
    setup() {
      const route = useRoute();
      const router = useRouter();
      const id = route.params.id;
      const medicamento = ref({
        nombre: '',
        descripcion: '',
        dosisRecomendada: '',
        precio: 0,
        tipoId: null
      });
      const tipos = ref([]);
      const nuevoTipo = ref({
        nombre: '',
        descripcion: '',
        usosComunes: '',
        interacciones: ''
      });
      const mensajeToast = ref('');
  
      const obtenerTipos = async () => {
        try {
          const response = await axios.get('/api/tipos');
          tipos.value = response.data;
        } catch (error) {
          console.error('Error al obtener los tipos:', error);
        }
      };
  
      const obtenerMedicamento = async () => {
        if (id) {
          try {
            const response = await axios.get(`/api/tipos/${id}`);
            medicamento.value = response.data;
          } catch (error) {
            console.error('Error al obtener el tipo de medicamento:', error);
          }
        }
      };
  
      const guardarMedicamento = async () => {
        try {
          if (id) {
            await axios.put(`/api/tipos/${id}`, medicamento.value);
          } else {
            await axios.post('/api/tipos', medicamento.value);
          }
          router.push('/');
        } catch (error) {
          console.error('Error al guardar el tipo de medicamento:', error);
        }
      };
  
      const guardarTipo = async () => {
        try {
          const response = await axios.post('/api/tipos', nuevoTipo.value);
          obtenerTipos();
          mensajeToast.value = `El tipo ${response.data.nombre} ha sido agregado`;
          mostrarToast();
          nuevoTipo.value = {
            nombre: '',
            descripcion: '',
            usosComunes: '',
            interacciones: ''
          };
        } catch (error) {
          console.error('Error al guardar el tipo:', error);
        }
      };
  
      const mostrarToast = () => {
        const toastEl = document.getElementById('liveToast');
        const toast = new bootstrap.Toast(toastEl);
        toast.show();
      };
  
      onMounted(() => {
        obtenerTipos();
        obtenerMedicamento();
      });
  
      return {
        id,
        medicamento,
        tipos,
        nuevoTipo,
        mensajeToast,
        guardarMedicamento,
        guardarTipo
      };
    }
  };
  </script>
  
  <style scoped>
  .container {
    max-width: 600px;
  }
  
  .toast-container {
    z-index: 1055;
  }
  
  .toast {
    background-color: #343a40;
    color: white;
  }
  
  .toast-header {
    background-color: #495057;
    color: white;
  }
  
  .toast-body {
    font-size: 1rem;
  }
  </style>