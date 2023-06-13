import $http from './http'

const $api = {

    // 凭证接口定义
    certificate: {
        findAll: param => {
            return $http.$get('/certificate/findAll', param)
        }, addAccessToken: param => {
            return $http.$post('/certificate/add', param)
        }, deleteById: param => {
            return $http.$delete('/certificate/deleteById', param)
        }, updateGlobalSsh: param => {
            return $http.$put('/certificate/updateSshPrivateKey', param)
        }, updateGlobalK8sConfig: param => {
            return $http.$put('/certificate/updateK8sConfig', param)
        }, editAccessToken: param => {
            return $http.$put('/certificate/edit', param)
        },
    },

    // 项目接口定义
    project: {
        findAll: param => {
            return $http.$get('/project/findAll', param)
        }, add: param => {
            return $http.$post('/project/add', param)
        }, deleteById: param => {
            return $http.$delete('/project/deleteById', param)
        }, findRepositoryBranches: param => {
            return $http.$get('/project/findBranches', param)
        }, edit: param => {
            return $http.$put('/project/edit', param)
        },
    },

    // 仓库接口定义
    repository: {
        findAll: param => {
            return $http.$get('/repository/findAll', param)
        }, add: param => {
            return $http.$post('/repository/add', param)
        }, deleteById: param => {
            return $http.$delete('/repository/deleteById', param)
        }, edit: param => {
            return $http.$put('/repository/edit', param)
        }, findGitRepositories: param => {
            return $http.$get('/repository/findGitRepositories', param)
        }
    },

    // 模板接口定义
    template: {
        findAll: param => {
            return $http.$get('/template/findAll', param)
        }, add: param => {
            return $http.$post('/template/add', param)
        }, deleteById: param => {
            return $http.$delete('/template/deleteById', param)
        }, edit: param => {
            return $http.$put('/template/edit', param)
        }
    },


}

export default $api
