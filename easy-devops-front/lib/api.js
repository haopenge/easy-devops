import $http from './http'

/**
 * web socket
 */
const $ws = {

    /**
     * 机器终端
     */
    terminal: param => {
        return `${window.location.protocol === 'https:' ? 'wss' : 'ws'}://${window.location.host}/orion/keep-alive/machine/terminal/${param.token}`
    },

    /**
     * 机器终端监视
     */
    terminalWatcher: param => {
        return `${window.location.protocol === 'https:' ? 'wss' : 'ws'}://${window.location.host}/orion/keep-alive/watcher/terminal/${param.token}`
    },

}

/**
 * url
 */
const $url = {

    /**
     * 执行下载文件请求
     */
    fileDownloadExec: param => {
        return `/orion/api/file-download/${param.token}/exec`
    }

}

const $api = {

    /**
     * webSocketUrl
     */
    ...$ws,

    /**
     * url
     */
    ...$url,

    /**
     * 登陆
     */
    login: param => {
        return $http.$post('/auth/login', param, {auth: false})
    },

    /**
     * 登出
     */
    logout: () => {
        return $http.$get('/auth/logout', null, {auth: false})
    },

    /**
     * 修改密码
     */
    resetPassword: param => {
        return $http.$post('/auth/reset', param)
    },
    /**
     * 获取机器终端配置
     */
    getTerminalSetting: param => {
        return $http.$get(`/terminal/get/${param.machineId}`)
    },
    /**
     * 删除终端日志
     */
    deleteTerminalLog: param => {
        return $http.$post('/terminal/log/delete', param, {
            loading: '正在删除...'
        })
    },

    /**
     * 获取终端录屏 base64
     */
    getTerminalScreen: param => {
        return $http.$post('/terminal/log/screen', param, {
            loading: '数据加载中...'
        })
    },


    /**
     * sftp 上传文件
     */
    sftpUploadExec: param => {
        return $http.$post('/sftp/upload/exec', param, {
            timeout: 18000000, loading: '正在上传文件...', headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
    },



}

export default $api
