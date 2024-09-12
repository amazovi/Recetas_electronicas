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
        <div class="input-group">
          <input v-model="medicamento.precio" type="text" class="form-control text-end" id="precio" v-currency required />
          <span class="input-group-text">€</span>
        </div>
      </div>
      <div class="mb-3">
        <label for="tipo" class="form-label">Tipo</label>
        <select v-model="medicamento.tipoId" class="form-select" id="tipo" required>
          <option value="" disabled>Seleccione un tipo</option>
          <option v-for="tipo in tipos" :key="tipo.id" :value="tipo.id">{{ tipo.nombre }}</option>
        </select>
      </div>
      <button type="submit" class="btn btn-primary">{{ id ? 'Actualizar' : 'Guardar' }} Medicamento</button>
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
import { useRoute, useRouter } from 'vue-router';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import * as bootstrap from 'bootstrap';

export default {
  name: 'InsertarMedicamento',
  setup() {
    const route = useRoute();
    const router = useRouter();
    const id = route.params.id;
    const medicamento = ref({
      nombre: '',
      descripcion: '',
      dosisRecomendada: '',
      precio: '0', 
      tipoId: null
    });
    const tipos = ref([]);
    const mensajeModal = ref('');

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
          const response = await axios.get(`/api/medicamentos/${id}`);
          medicamento.value = response.data;
          medicamento.value.precio = formatearPrecio(medicamento.value.precio);
        } catch (error) {
          console.error('Error al obtener el medicamento:', error);
        }
      }
    };

    const guardarMedicamento = async () => {
      try {
        console.log('Datos enviados:', medicamento.value); 
        medicamento.value.precio = parseFloat(medicamento.value.precio.replace(',', '.').replace(/[^0-9.-]+/g, ""));
        if (id) {
          await axios.put(`/api/medicamentos/${id}`, medicamento.value);
          mensajeModal.value = 'El medicamento ha sido actualizado correctamente';
        } else {
          await axios.post('/api/medicamentos', medicamento.value); // Asegúrate de que la ruta es correcta
          mensajeModal.value = 'El medicamento ha sido guardado correctamente';
        }
        mostrarModal();
      } catch (error) {
        console.error('Error al guardar el medicamento:', error);
        mensajeModal.value = 'Error al guardar el medicamento';
        mostrarModal();
      }
    };

    const mostrarModal = () => {
      const modalEl = document.getElementById('messageModal');
      const modal = new bootstrap.Modal(modalEl);
      modal.show();
    };

    const formatearPrecio = (precio) => {
      return precio.toFixed(2).replace('.', ',').replace(/\d(?=(\d{3})+,\d{2})/g, '$&.');
    };

    onMounted(() => {
      obtenerTipos();
      obtenerMedicamento();
    });

    return {
      id,
      medicamento,
      tipos,
      mensajeModal,
      guardarMedicamento
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
  }
};
</script>

<style scoped>
.container {
  max-width: 600px;
}
</style>