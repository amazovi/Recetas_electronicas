<template>
  <div class="container mt-5">
    <h2>Listado de Medicamentos</h2>
    <button class="btn btn-success mb-3" @click="navegarAInsertar">Añadir Nuevo Medicamento</button>
    <table class="table table-bordered">
      <thead>
        <tr>
          <th>Nombre</th>
          <th>Descripción</th>
          <th>Dosis Recomendada</th>
          <th>Precio</th>
          <th>Tipo</th>
          <th>Acciones</th>
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
            <button class="btn btn-warning" @click="navegarAEditar(medicamento.id)">Editar</button>
            <button class="btn btn-danger" @click="eliminarMedicamento(medicamento.id)">Eliminar</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from 'axios';
import { useRouter } from 'vue-router';

export default {
  data() {
    return {
      medicamentos: []
    };
  },
  methods: {
    navegarAInsertar() {
      this.$router.push('/insertar');
    },
    navegarAEditar(id) {
      this.$router.push(`/insertar?id=${id}`);
    },
    async eliminarMedicamento(id) {
      if (confirm("¿Está seguro de que desea eliminar este medicamento?")) {
        try {
          await axios.delete(`http://localhost:8080/api/medicamentos/${id}`);
          this.obtenerMedicamentos();
        } catch (error) {
          console.error(error);
        }
      }
    },
    async obtenerMedicamentos() {
      try {
        const response = await axios.get('http://localhost:8080/api/medicamentos');
        this.medicamentos = response.data;
      } catch (error) {
        console.error(error);
      }
    }
  },
  mounted() {
    this.obtenerMedicamentos();
  }
};
</script>