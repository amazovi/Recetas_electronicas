import { createRouter, createWebHistory } from 'vue-router';
import Home from '../components/Home.vue';
import TipoMedicamento from '../components/TipoMedicamento.vue';
import ListadoMedicamentos from '../components/ListadoMedicamentos.vue';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/medicamentos',
    name: 'ListadoMedicamentos',
    component: ListadoMedicamentos
  },
  {
    path: '/categorias',
    name: 'Categorias',
    component: TipoMedicamento
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;