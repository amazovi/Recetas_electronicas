import { createRouter, createWebHistory } from 'vue-router';
import Home from '../components/Home.vue';
import TipoMedicamento from '../components/TipoMedicamento.vue';
import ListadoMedicamentos from '../components/ListadoMedicamentos.vue';
import InsertarMedicamento from '../components/InsertarMedicamento.vue';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/listado',
    name: 'ListadoMedicamentos',
    component: ListadoMedicamentos
  },
  {
    path: '/categorias',
    name: 'Categorias',
    component: TipoMedicamento
  },
  {
    path: '/insertar',
    name: 'InsertarMedicamento',
    component: InsertarMedicamento
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;