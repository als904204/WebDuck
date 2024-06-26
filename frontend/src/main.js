import { createApp } from 'vue'
import './style.css'
import App from './App.vue'

// Router
import { router } from './router/index.js'

// Pinia
import { createPinia } from 'pinia'

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
import RadioButton from 'primevue/radiobutton';
import Checkbox from 'primevue/checkbox';
import InlineMessage from 'primevue/inlinemessage';
import Tag from 'primevue/tag';
import ProgressBar from 'primevue/progressbar';
import 'primevue/resources/themes/aura-light-green/theme.css'
import 'primeicons/primeicons.css'
import 'primeflex/primeflex.css';




// axios
import axios from "axios";
import VueAxios from "vue-axios";

// pinia
const pinia = createPinia()

// app
const app = createApp(App)

// router
app.use(router)

// prime
app.use(PrimeVue);

app.use(pinia)


app.component('Button', Button);
app.component('Menubar', Menubar);
app.component('Badge', Badge);
app.component('Card', Card);
app.component('Rating', Rating);
app.component('Textarea', Textarea);
app.component('Dialog', Dialog);
app.component('InputText', InputText);
app.component('RadioButton', RadioButton);
app.component('Checkbox', Checkbox);
app.component('InlineMessage', InlineMessage);
app.component('Tag', Tag);
app.component('ProgressBar', ProgressBar);

// axios
app.use(VueAxios, axios);
app.provide("axios", app.config.globalProperties.axios);

app.mount('#app')
