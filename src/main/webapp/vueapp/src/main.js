import { createApp } from 'vue'; // Vue 3에서는 createApp을 사용합니다.
import App from './App.vue';
import axios from 'axios';
import { createRouter, createWebHistory } from 'vue-router'; // Vue Router를 import합니다.
import LoginPage from '@/components/LoginPage.vue';
import MainPage from '@/components/main_page/MainPage.vue';

const routes = [
    {
        path:'/',
        name:'login',
        component: LoginPage
    },
    {
        path:'/MainPage',
        name:'main',
        component: MainPage
    }
];

// 라우터 객체를 생성합니다.
const router = createRouter({
    history: createWebHistory(),
    routes
});

const app = createApp(App);

app.config.globalProperties.$axios = axios;

app.use(router); // 애플리케이션에 라우터를 등록합니다.

app.mount('#app');
