import {createRouter, createWebHistory} from 'vue-router'
import Home from '@/views/Home.vue'

const routes = [
    {
        path: '/',
        name: 'home',
        component: Home
    },
    {
        path: '/certificate',
        name: 'certificate',
        meta: {
            title: '凭证管理'
        },
        component: () => import('../views/certificate/Certificate.vue')
    },
    {
        path: '/repository',
        name: 'repository',
        meta: {
            title: '仓库管理'
        },
        component: () => import('../views/repository/Repository.vue')
    },
    {
        path: '/project',
        name: 'project',
        meta: {
            title: '项目管理'
        },
        component: () => import('../views/project/Project.vue')
    },
    {
        path: '/template',
        name: 'template',
        meta: {
            title: '项目管理'
        },
        component: () => import('../views/template/Template.vue')
    },


]

const router = createRouter({
    routes, history: createWebHistory(process.env.BASE_URL), scrollBehavior() {
        return {top: 0}
    }
})

export default router
