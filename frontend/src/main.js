import { createApp } from 'vue'
import './style.css'
import App from './App.vue'

// Router
import { router } from './router/index.js'

// PrimeVue
import PrimeVue from 'primevue/config';
import Button from "primevue/button"
import Menubar from 'primevue/menubar';
import 'primevue/resources/themes/aura-light-green/theme.css'
import 'primeicons/primeicons.css'

// axios
import axios from "axios";
import VueAxios from "vue-axios";


// app
const app = createApp(App)

// router
app.use(router)

// prime
app.use(PrimeVue);
app.component('Button', Button);
app.component('Menubar', Menubar);

// axios
app.use(VueAxios, axios);
app.provide("axios", app.config.globalProperties.axios);

app.mount('#app')
