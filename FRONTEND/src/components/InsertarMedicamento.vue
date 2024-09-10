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
  </div>
</template>

<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';

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
      precio: 0,
      tipoId: null
    });
    const tipos = ref([]);

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
        } catch (error) {
          console.error('Error al obtener el medicamento:', error);
        }
      }
    };

    const guardarMedicamento = async () => {
      try {
        if (id) {
          await axios.put(`/api/medicamentos/${id}`, medicamento.value);
        } else {
          await axios.post('/api/medicamentos', medicamento.value);
        }
        router.push('/');
      } catch (error) {
        console.error('Error al guardar el medicamento:', error);
      }
    };

    onMounted(() => {
      obtenerTipos();
      obtenerMedicamento();
    });

    return {
      id,
      medicamento,
      tipos,
      guardarMedicamento
    };
  }
};
</script>

<style scoped>
.container {
  max-width: 600px;
}
</style>