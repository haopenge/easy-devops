// 导入必要的模块
import { createApp } from 'vue'
import ViewUIPlus from 'view-ui-plus'
import Terminal from 'vue-web-terminal'

// 导入必要的文件
import App from './App.vue'
import router from './router'
import store from './store'
import './styles/index.less'
import watermark from '@/lib/watermark'

// 设置路由导航守卫
router.beforeEach((to, from, next) => {
    // 设置页面标题
    if (to.meta.title) {
        document.title = to.meta.title
    }
    watermark.set('easy-devops')
    next()
})

// 创建 Vue 应用实例
const app = createApp(App).use(Terminal)

// 使用必要的插件
app.use(store)
    .use(router)
    .use(ViewUIPlus)
    .mount('#app')

// 设置全局错误处理程序
app.config.errorHandler = () => {
    /* 处理错误 */
}
