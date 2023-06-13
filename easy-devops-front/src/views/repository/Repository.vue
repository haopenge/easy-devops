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
import $api from "@/lib/api";

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
            $api.repository.deleteById({
                id: id
            }).then(data => {
                this.findRepositoriesHttp()
            })
        },
        /**
         * 仓库保存
         */
        repositorySaveHttp() {
            $api.repository.add({
                name: this.repository.name,
                description: this.repository.description,
                cloneUrl: this.repository.cloneUrl,
                easyCertificateId: this.repository.certificateId,
                branch: this.repository.branch
            }).then(data => {
                this.findRepositoriesHttp()
            })
        },

        /**
         * 仓库更新
         */
        repositoryEditHttp() {
            $api.repository.edit({
                id: this.repository.id,
                name: this.repository.name,
                description: this.repository.description,
                cloneUrl: this.repository.cloneUrl,
                easyCertificateId: this.repository.certificateId,
                branch: this.repository.branch
            }).then(data => {
                this.findRepositoriesHttp()
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
            $api.repository.findGitRepositories({
                certificateId: certificateOptionId
            }).then(data => {
                this.gitRepositoriesMap[certificateOptionId] = data.map((item) => ({
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
        },

        /**
         * 获取凭证
         */
        findCertificate() {
            $api.certificate.findAll({
                containSsh:false
            }).then(data => {
                this.certificateOptions = data.map((item) => ({
                    id: item.id,
                    name: item.name,
                    description: item.description
                }))
            })
        },

        /**
         * 获取仓库列表
         */
        findRepositoriesHttp() {
            $api.repository.findAll({}).then(data => {
                this.repositoryList = data.map((item) => ({
                    id: item.id,
                    name: item.name,
                    description: item.description,
                    cloneUrl: item.cloneUrl,
                    easyCertificateId: item.easyCertificateId,
                    easyCertificateName: item.easyCertificateName,
                    branch: item.branch
                }))
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
</script>
