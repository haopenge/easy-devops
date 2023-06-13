import $http from './http'

const $api = {

    // 凭证接口定义
    certificate: {
        findCertificate: param => {
            return $http.$get('/certificate/findAll', param)
        }, addAccessTokenCertificate: param => {
            return $http.$post('/certificate/add', param)
        }, deleteCertificate: param => {
            return $http.$delete('/certificate/deleteById',param)
        }
    }


}

export default $api
