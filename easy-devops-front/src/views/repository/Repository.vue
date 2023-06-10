<template>
    <Button type="primary" style="width: 70px;" @click="showRepositoryAddPop">新增</Button>

    <Table border :columns="columns" :data="repositoryList">
        <template #name="{ row }">
            <strong>{{ row.name }}</strong>
        </template>
        <template #action="{ row, index }">
            <Button type="info" size="small" style="margin-right: 5px" @click.stop="showRepositoryEditPop(row)">编辑
            </Button>
            <Button type="warning" size="small" @click.stop="remove(row.id)">删除</Button>
        </template>
    </Table>

    <RepositoryPop :repository="repository"
                   :certificate-options="certificateOptions"
                   :repository-pop-visible="repositoryPopVisible"
                   :git-repositories="gitRepositories"
                   @repository-save="repositorySaveHttp"
                   @repository-edit="repositoryEditHttp"
                   @certificate-option-change="certificateOptionChange"
                   @repository-select-event="repositorySelectEvent"
    />
</template>
<script>

import RepositoryPop from '@/views/repository/RepositoryPop.vue'
import axios from "axios";

export default {
    components: {
        RepositoryPop
    },
    created() {
        this.init()
    },
    data() {
        return {
            columns: [
                {
                    title: '名称',
                    key: 'name'
                },
                {
                    title: 'git克隆地址',
                    key: 'cloneUrl'
                },
                {
                    title: '详情',
                    key: 'description'
                },
                {
                    title: '认证',
                    key: 'easyCertificateName'
                },
                {
                    title: 'Action',
                    slot: 'action',
                    width: 150,
                    align: 'center'
                }
            ],
            repository: {},
            repositoryList: [],
            repositoryPopVisible: false,
            certificateOptions: [],
            gitRepositories: [],
            gitRepositoriesMap : {

            }
        }
    },
    methods: {
        async init() {
            console.log('invoke init()')
            this.findCertificate()
            this.findRepositoriesHttp()
        },

        /**
         * 展示新增仓库弹窗
         */
        showRepositoryAddPop() {
            this.repository = {
                id: 0,
                name: '',
                cloneUrl: '',
                description: '',
                easyCertificateId: 0,
                branch: ''
            }
            this.repositoryPopVisible = true
        },

        /**
         * 展示修改仓库弹窗
         * @param row 修改行记录信息
         */
        showRepositoryEditPop(row) {
            this.repository = {
                id: row.id,
                name: row.name,
                cloneUrl: row.cloneUrl,
                description: row.description,
                easyCertificateId: row.easyCertificateId,
                branch: row.branch
            }
            this.findGitRepositoriesHttp(row.easyCertificateId)
            this.repositoryPopVisible = true
        },
        /**
         * 移除指定弹窗
         * @param id 弹窗id
         */
        remove(id) {
            axios
                .delete('/repository/deleteById?id=' + id)
                .then((response) => {
                    fetchResponseData(response)
                    this.findRepositoriesHttp()
                })
                .catch((error) => { // 请求失败处理
                    console.log(error)
                })
        },
        /**
         * 仓库保存
         */
        repositorySaveHttp() {
            let payload = {
                name: this.repository.name,
                description: this.repository.description,
                cloneUrl: this.repository.cloneUrl,
                easyCertificateId: this.repository.certificateId,
                branch: this.repository.branch
            }
            axios
                .post('/repository/add', payload)
                .then((response) => {
                    fetchResponseData(response)
                    this.findRepositoriesHttp()
                })
                .catch((error) => { // 请求失败处理
                    console.log(error)
                })
        },

        /**
         * 仓库更新
         */
        repositoryEditHttp() {
            let payload = {
                id: this.repository.id,
                name: this.repository.name,
                description: this.repository.description,
                cloneUrl: this.repository.cloneUrl,
                easyCertificateId: this.repository.certificateId,
                branch: this.repository.branch
            }
            axios
                .put('/repository/edit', payload)
                .then((response) => {
                    fetchResponseData(response)
                    this.findRepositoriesHttp()
                })
                .catch((error) => { // 请求失败处理
                    console.log(error)
                })
        },

        /**
         * 认证类型修改监听
         * @param certificateOptionId
         */
        certificateOptionChange(certificateOptionId) {
            this.repository.certificateId = certificateOptionId
            // 获取
            this.findGitRepositoriesHttp(certificateOptionId)
        },

        /**
         * 获取git仓库列表
         */
        findGitRepositoriesHttp(certificateOptionId){
            if(this.gitRepositoriesMap[certificateOptionId]){
                this.gitRepositories =  this.gitRepositoriesMap[certificateOptionId]
                console.log('缓存命中  certificateOptionId = ' + certificateOptionId)
            }
            axios
                .get('/repository/findGitRepositories?certificateId=' + certificateOptionId)
                .then((response) => {
                    const resultData = fetchResponseData(response)
                    this.gitRepositoriesMap[certificateOptionId] = resultData.map((item) => ({
                        id: item.id,
                        name: item.name,
                        description: item.description,
                        isPublic: item.isPublic,
                        branch: item.defaultBranch,
                        cloneUrl: item.httpCloneUrl
                    }))
                    console.log('添加缓存  certificateOptionId = ' + certificateOptionId)
                    this.gitRepositories =  this.gitRepositoriesMap[certificateOptionId]
                })
                .catch((error) => { // 请求失败处理
                    console.log(error)
                })
        },

        /**
         * 获取凭证
         */
        findCertificate() {
            axios
                .get('/certificate/findAll?containSsh=' + false)
                .then((response) => {
                    const resultData = fetchResponseData(response)
                    this.certificateOptions = resultData.map((item) => ({
                        id: item.id,
                        name: item.name,
                        description: item.description
                    }))
                })
                .catch((error) => { // 请求失败处理
                    console.log(error)
                })
        },

        /**
         * 获取仓库列表
         */
        findRepositoriesHttp() {
            axios
                .get('/repository/findAll')
                .then((response) => {
                    const resultData = fetchResponseData(response)
                    this.repositoryList = resultData.map((item) => ({
                        id: item.id,
                        name: item.name,
                        description: item.description,
                        cloneUrl: item.cloneUrl,
                        easyCertificateId: item.easyCertificateId,
                        easyCertificateName: item.easyCertificateName,
                        branch: item.branch
                    }))
                })
                .catch((error) => { // 请求失败处理
                    console.log(error)
                })
        },

        /**
         * 仓库选择变更事件处理
         * @param gitRepositoryName  仓库名称
         */
        repositorySelectEvent(gitRepositoryName) {
            const result = this.gitRepositories.find(item => item.name === gitRepositoryName);
            this.repository.name = result.name
            this.repository.description = result.description
            this.repository.cloneUrl = result.cloneUrl
            this.repository.branch = result.branch
        }
    }
}

/**
 * 获取http 相应信息
 * @param response http响应体
 * @returns {*[]}
 */
function fetchResponseData(response) {
    if (response && response.data) {
        const result = response.data
        const {success} = result
        const {message} = result

        if (!success) {
            alert(message)
        }
        return result.data || []
    }
}
</script>
