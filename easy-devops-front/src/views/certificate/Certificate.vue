<template>
    <Button type="primary" style="width: 70px;" @click="showCertificateAddPop">新增</Button>

    <Table border :columns="columns" :data="certificateList">
        <template #name="{ row }">
            <strong>{{ row.name }}</strong>
        </template>
        <template #action="{ row}">
            <Button type="info" size="small" style="margin-right: 5px" @click="showCertificateEditPop(row)">编辑
            </Button>
            <Button type="warning" size="small" @click="deleteCertificate(row.id)">删除</Button>
        </template>
    </Table>

    <CertificatePop
            :certificate="certificate"
            :pop-edit-enable="popEditEnable"
            :certificate-pop-visible="certificatePopVisible"
            @repository-type-change="repositoryTypeChange"
            @certificate-add="certificateAdd"
            @certificate-edit="certificateEdit"
    />

</template>


<script>
import CertificatePop from '@/views/certificate/CertificatePop.vue'
import $api from '@/lib/api.js'

export default {
    name: 'certificate',
    components: {
        CertificatePop
    },
    created() {
        this.init()
    },
    data() {
        return {
            columns: [
                {
                    title: '名称',
                    slot: 'name'
                },
                {
                    title: '类型',
                    key: 'typeShow'
                },
                {
                    title: '仓库类型',
                    key: 'repositoryTypeShow'
                },
                {
                    title: '描述',
                    key: 'description'
                },
                {
                    title: 'Action',
                    slot: 'action',
                    width: 150,
                    align: 'center'
                }
            ],
            certificateList: [],
            certificate: {},
            certificatePopVisible: false,
            popEditEnable: false,
            repositoryType: 1,
        }
    },
    methods: {
        async init() {
            console.log('invoke init()')
            this.findCertificate()
        },

        /**
         * 获取凭证
         */
        findCertificate() {
            $api.certificate.findAll({
                containSsh: true
            }).then(data => {
                this.certificateList = data.map((item) => ({
                    id: item.id,
                    type: item.type,
                    typeShow: this.getTypeShow(item.type),
                    name: item.name,
                    description: item.description,
                    username: item.username,
                    repositoryType: item.repositoryType,
                    repositoryTypeShow: this.getRepositoryTypeShow(item.repositoryType)
                }))
            })
        },

        /**
         * 获取仓库类型对应的展示信息
         * @param repositoryType 仓库类型
         * @returns {string} 展示信息
         */
        getRepositoryTypeShow(repositoryType) {
            if (repositoryType === 1) {
                return 'github'
            } else if (repositoryType === 2) {
                return 'gitee'
            } else if (repositoryType === 3) {
                return 'gitlab'
            } else {
                return ''
            }
        },

        /**
         * 获取对应的type展示信息
         * @param type type
         * @returns {string} type描述
         */
        getTypeShow(type) {
            if (type === 1) {
                return '全局ssh'
            } else if (type === 3) {
                return 'k8s配置'
            } else if (type === 5) {
                return '仓库凭证'
            } else {
                return ''
            }
        },

        /**
         * 新增仓库凭证
         */
        addAccessTokenCertificate() {
            $api.certificate.addAccessToken(
                {
                    name: this.certificate.name,
                    username: this.certificate.username,
                    description: this.certificate.description,
                    accessToken: this.certificate.accessToken,
                    repositoryType: this.certificate.repositoryType
                }
            ).then(data => {
                // 刷新列表
                this.findCertificate()
            }).catch(error => {
                console.error('addAccessToken ' + JSON.stringify(error))
            })
        },


        /**
         * 展示认证新增弹窗
         */
        showCertificateAddPop() {
            this.certificate = {
                id: 0,
                type: 5,
                name: '',
                username: '',
                accessToken: '',
                repositoryType: 2,
                kubeConfig: ''
            }
            this.popEditEnable = false
            this.certificatePopVisible = true
        },

        /**
         * 展示认证修改弹窗
         * @param row 目标认证行信息
         */
        showCertificateEditPop(row) {
            this.certificate = {
                id: row.id,
                type: row.type,
                name: row.name,
                username: row.username,
                description: row.description,
                repositoryType: row.repositoryType
            }
            this.popEditEnable = true
            this.certificatePopVisible = true
        },

        /**
         * 仓库类型变更
         * @param repositoryType 类型变更
         */
        repositoryTypeChange(repositoryType) {
            this.certificate.repositoryType = repositoryType
        },

        /**
         * 更新全局ssh凭证
         */
        updateGlobalSshCertificate() {
            $api.certificate.updateGlobalSsh({
                sshPrivateKey: this.certificate.sshPrivateKey
            }).then(data => {
                // 刷新列表
                this.findCertificate()
            })
        },

        /**
         * 更新全局k8s config仓库凭证
         */
        updateGlobalK8sConfigCertificate() {
            $api.certificate.updateGlobalK8sConfig({
                kubeConfig: this.certificate.kubeConfig
            }).then(data => {
                // 刷新列表
                this.findCertificate()
            })
        },

        /**
         * 新增凭证
         */
        certificateAdd() {
            if (this.certificate.type === 1) {
                this.updateGlobalSshCertificate()
            } else if (this.certificate.type === 3) {
                this.updateGlobalK8sConfigCertificate()
            } else {
                this.addAccessTokenCertificate()
            }
        },

        /**
         * 编辑仓库凭证
         */
        editAccessTokenCertificate() {
            $api.certificate.editAccessToken(
                {
                    id: this.certificate.id,
                    username: this.certificate.username,
                    description: this.certificate.description,
                    accessToken: this.certificate.accessToken,
                    repositoryType: this.certificate.repositoryType
                }
            ).then(data => {
                // 刷新列表
                this.findCertificate()
            })
        },

        /**
         * 凭证编辑
         */
        certificateEdit() {
            if (this.certificate.type === 1) {
                this.updateGlobalSshCertificate()
            } else {
                this.editAccessTokenCertificate()
            }
        },

        /**
         * 删除凭证
         * @param id
         */
        deleteCertificate(id) {
            $api.certificate.deleteById({
                id: id
            }).then((data) => {
                // 刷新列表
                this.findCertificate()
            })
        }
    }
}

</script>

