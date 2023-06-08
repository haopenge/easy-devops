const { defineConfig } = require('@vue/cli-service')
const path = require('path')

const { NODE_ENV, VUE_APP_TITLE = '' } = process.env

const config = {
  transpileDependencies: false,
  productionSourceMap: NODE_ENV === 'production',
  chainWebpack: (setting) => {
    setting.plugin('html').tap((args) => {
      args[0].APP_TITLE = VUE_APP_TITLE
      return args
    })
  },
  css: {
    loaderOptions: {
      less: {
        lessOptions: {
          javascriptEnabled: true,
        }
      }
    }
  },
  pluginOptions: {
    'style-resources-loader': {
      preProcessor: 'less',
      patterns: [
        path.resolve(__dirname, 'src/styles/variable.less')
      ]
    }
  },
  devServer: {
    open: true,
    port: 8081,
    host: 'localhost',
    proxy: {
      '/': {
        target: 'http://localhost:10081',
        changeOrigin: true,
        /* pathRewrite: {
          '^/api': ''
        } */
      }
    }
  }
}

module.exports = defineConfig(config)
