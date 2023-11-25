import {createRouter, createWebHistory} from 'vue-router'
import {useStore} from "@/stores";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'welcome',
            component: () => import('@/views/WelcomeView.vue'),
            children: [
                {
                    path: '',
                    name: 'welcome-login',
                    component: () => import('@/components/welcome/LoginPage.vue')
                },
                {
                    path: '/register',
                    name: 'welcome-register',
                    component: () => import('@/components/welcome/RegisterPage.vue')
                },
                {
                    path: '/forget',
                    name: 'welcome-forget',
                    component: () => import('@/components/welcome/ForgetPassword.vue')
                }
            ]
        },
        {
            path: '/index',
            name: 'index',
            component: () => import('@/views/IndexView.vue'),
            children:[
                {
                    path: '',
                    name:'index-postList',
                    component:()=>import('@/components/index/PostList.vue')
                },
                {
                    path: 'settings',
                    name: 'index-settings',
                    component:()=>import('@/components/index/Settings.vue')
                }
            ]
        }
    ]
})
router.beforeEach((to, from, next) => {
    const store = useStore()
    if (store.auth.user != null && to.name.startsWith('welcome')) {
        next('/index')
    } else if (store.auth.user == null && to.fullPath.startsWith('/index')) {
        next('/')
    } else if (to.matched.length === 0) {
        next('/')
    } else {
        next()
    }
})
export default router
