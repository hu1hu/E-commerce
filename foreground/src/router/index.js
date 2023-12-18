import { createRouter, createWebHistory } from 'vue-router'
import ForegroundLayout from '@/views/foregroundLayout/index.vue'
import BackgroundLayout from '@/views/backgroundLayout/index.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Foreground',
      component: ForegroundLayout,
      children: [{
        path: '',
        name: 'Home',
        component: () => import('@/views/Home/index.vue')
      }, {
        path: 'detail/:id',
        name: 'Detail',
        component: () => import('@/views/Detail/index.vue')
      }, {
        path: 'category/:id',
        name: 'Category',
        component: () => import('@/views/Category/index.vue')
      }, {
        path: 'pay',
        name: 'Pay',
        component: () => import('@/views/Pay/index.vue')
      }]
    }, {
      path: '/seller',
      name: 'Seller',
      component: BackgroundLayout,
      children: [{
        path: '',
        name: 'Sales',
        component: () => import('@/views/Sales/index.vue')
      }, {
        path: 'product',
        name: 'Product',
        component: () => import('@/views/Product/index.vue')
      }]
    }, {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/Login/index.vue')
    }
  ]
})

export default router
