import { createApp } from 'vue'
import './style.css'
import App from './App.vue'

// Router
import { router } from './router/index.js'

// PrimeVue
import PrimeVue from 'primevue/config';


import Button from "primevue/button"
import Menubar from 'primevue/menubar';
import Badge from 'primevue/badge';
import Card from 'primevue/card';
import Textarea from 'primevue/textarea';
import Dialog from 'primevue/dialog';
import InputText from 'primevue/inputtext';
import Rating from 'primevue/rating';

import 'primevue/resources/themes/aura-light-green/theme.css'
import 'primeicons/primeicons.css'
import 'primeflex/primeflex.css';


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
app.component('Badge', Badge);
app.component('Card', Card);
app.component('Rating', Rating);
app.component('Textarea', Textarea);
app.component('Dialog', Dialog);
app.component('InputText', InputText);
// axios
app.use(VueAxios, axios);
app.provide("axios", app.config.globalProperties.axios);

app.mount('#app')
