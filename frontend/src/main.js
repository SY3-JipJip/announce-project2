import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import './assets/main.css'
import Cookies from 'vue-cookies'

const app = createApp(App)
const pinia = createPinia()

app.use(router)
app.use(pinia)
app.use(Cookies)
app.mount('#app')
export { app }