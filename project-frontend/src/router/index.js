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
                    path: '/',
                    name: 'welcome-login',
                    component: () => import('@/views/welcome/LoginPage.vue')
                },
                {
                    path: '/register',
                    name: 'welcome-register',
                    component: () => import('@/views/welcome/RegisterPage.vue')
                },
                {
                    path: '/forget',
                    name: 'welcome-forget',
                    component: () => import('@/views/welcome/ForgetPassword.vue')
                }
            ]
        },
        {
            path: '/index',
            name: 'index',
            component: () => import('@/views/IndexView.vue'),
            children: [
                {
                    path: '',
                    name: 'topics',
                    component: () => import('@/views/index/Forum.vue'),
                    children: [
                        {
                            path: '',
                            name: 'index-postList',
                            component: () => import('@/views/index/PostList.vue')
                        }, {
                            path: 'topic-detail/:tid',
                            name: 'topic-detail',
                            component: () => import('@/components/TopicDetail.vue')
                        }
                    ]
                },
                {
                    path: '/index/settings',
                    name: 'index-settings',
                    component: () => import('@/views/index/Settings.vue')
                },
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
