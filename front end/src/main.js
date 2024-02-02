import { createApp } from 'vue'
import App from './App.vue'

const app = createApp(App)

app.config.globalProperties.sender = '' ;

app.mount('#app')

import 'bootstrap/dist/js/bootstrap.js'
import 'bootstrap/dist/css/bootstrap.css';