import axios from 'axios'

const $http = axios.create({
    responseType: 'json',
    timeout: 10000
})

// 默认配置项
const DEFAULT_CONFIG = {
    // 是否需要登陆
    auth: true,
    // 超时时间
    timeout: 10000,
    // 请求头
    headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('token')
    }
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
const fillDefaultConfig = (config) => {
    return { ...DEFAULT_CONFIG, ...config }
}

/**
 * 请求拦截器
 */
$http.interceptors.request.use(config => {
    console.log('请求拦截器：' + JSON.stringify(config))
    config.url = '/api' + config.url
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
    return Promise.reject('error')
}, err => {
    if (err.response) {
        // 请求已发出，但服务器响应的状态码不在 2xx 范围内
        console.log(err.response.data);
        console.log(err.response.status);
        console.log(err.response.headers);
    } else if (err.request) {
        // 请求已发出，但没有收到响应
        console.log(err.request);
    } else {
        // 其他错误
        console.log('Error', err.message);
    }
    console.log(err.config);
    return Promise.reject(err)
})

export default {
    $get, $post, $put, $delete, $fetch, $export
}
