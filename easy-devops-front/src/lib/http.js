import axios from 'axios'

const $http = axios.create({
    responseType: 'json',
    timeout: 10000
})

// 默认配置项
const defaultConfig = {
    // 是否需要登陆
    auth: true, // 超时时间
    timeout: 10000, // 请求头
    contentType: 'application/json'
}

/**
 * get请求
 */
const $get = (url, params = {}, config = {}) => {
    config.params = params
    return new Promise((resolve, reject) => {
        $http.get(url, fillDefaultConfig(config))
            .then(res => resolve(res))
            .catch(err => reject(err))
    })
}

/**
 * post请求
 */
const $post = (url, data = {}, config = {}) => {
    return new Promise((resolve, reject) => {
        $http.post(url, data, fillDefaultConfig(config))
            .then(res => resolve(res))
            .catch(err => reject(err))
    })
}

/**
 * put请求
 */
const $put = (url, data = {}, config = {}) => {
    return new Promise((resolve, reject) => {
        $http.put(url, data, fillDefaultConfig(config))
            .then(res => resolve(res))
            .catch(err => reject(err))
    })
}


/**
 * delete请求
 */
const $delete = (url, params = {}, config = {}) => {
    config.params = params
    return new Promise((resolve, reject) => {
        $http.delete(url, fillDefaultConfig(config))
            .then(res => resolve(res))
            .catch(err => reject(err))
    })
}

/**
 * http请求
 */
const $fetch = (url, method = 'get', config) => {
    return new Promise((resolve, reject) => {
        $http.request({
            url: url, method: method, ...fillDefaultConfig(config)
        })
            .then(res => resolve(res))
            .catch(err => reject(err))
    })
}

/**
 * 导出请求
 */
const $export = (url, data, config) => {
    return new Promise((resolve, reject) => {
        $http.post(url, data, fillDefaultConfig({
            skipRespInterceptor: true,
            responseType: 'blob',
            timeout: 600000,
            loading: '正在导出请耐心等待...', ...config
        })).then(res => resolve(res))
            .catch(err => reject(err))
    })
}

/**
 * 填充默认配置
 */
function fillDefaultConfig(config) {
    for (const defaultConfigKey in defaultConfig) {
        if (!(defaultConfigKey in config)) {
            config[defaultConfigKey] = defaultConfig[defaultConfigKey]
        }
    }
    return config
}

/**
 * 请求拦截器
 */
$http.interceptors.request.use(config => {
    console.log('请求拦截器 ' + JSON.stringify(config))
    return config;
}, err => {
    return Promise.reject(err)
})

/**
 * 响应拦截器
 */
$http.interceptors.response.use(response => {
    if (response && response.data) {
        const result = response.data
        if (!result.success) {
            alert(result.message)
        }
        return result.data || []
    }
}, err => {
    return Promise.reject('error')
})

export default {
    $get, $post, $put, $delete, $fetch, $export
}
