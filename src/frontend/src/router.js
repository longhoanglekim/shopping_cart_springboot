import { createRouter, createWebHistory } from 'vue-router';
import LoginUser from './components/LoginUser.vue';
//import WelcomePage from './components/WelcomePage.vue';

const routes = [
    {
        path: '/login',  // Thay đổi đường dẫn từ "/" thành "/login"
        name: 'Login',
        component: LoginUser
    },
    // {
    //     path: '/welcome',  // Đường dẫn cho trang chào mừng
    //     name: 'Welcome',
    //     component: WelcomePage
    // }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
