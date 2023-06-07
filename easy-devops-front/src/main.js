import { createApp } from 'vue'
import ViewUIPlus from 'view-ui-plus'

import App from './App.vue'
import router from './router'
import store from './store'
import './styles/index.less'

import Terminal from 'vue-web-terminal'


const app = createApp(App).use(Terminal)

app.use(store)
  .use(router)
  .use(ViewUIPlus)
  .mount('#app')

app.config.errorHandler = () => {
  /* 错误处理 */
}
