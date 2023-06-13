<template>
    <Button type="primary" style="width: 70px;" @click="showProjectAddPop">新增</Button>

    <Table border :columns="columns" :data="projectList">
        <template #name="{ row }">
            <strong>{{ row.name }}</strong>
        </template>
        <template #action="{ row, index }">
            <Button type="success" size="small" style="margin-right: 5px" @click.stop="showConfigPage(row)">配置
            </Button>
            <Button type="info" size="small" style="margin-right: 5px" @click.stop="showProjectEditPop(row)">编辑
            </Button>
            <Button type="warning" size="small" @click.stop="remove(row.id)">删除</Button>
        </template>
    </Table>

    <ProjectPop :project="project"
                :project-pop-visible="projectPopVisible"
                :branches="branches"
                :repositories="repositories"
                :is-edit-pop="isEditPop"
                @repository-change="repositoryChange"
                @branch-change="branchChange"
                @project-save="projectSave"
                @project-edit="projectEdit"
    />
</template>
<script>
import ProjectPop from '@/views/project/ProjectPop.vue'
import $api from "@/lib/api";

export default {
    components: {
        ProjectPop
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
                    title: '分支',
                    key: 'branch'
                },
                {
                    title: '仓库',
                    key: 'easyRepositoryName'
                },
                {
                    title: '凭证',
                    key: 'easyCertificateName'
                },
                {
                    title: 'Action',
                    slot: 'action',
                    width: 250,
                    align: 'center'
                }
            ],
            project: {},
            projectList: [],
            repositories: [],
            branches: [],
            projectPopVisible: false,
            branchesMap: {},
            isEditPop: false
        }
    },
    methods: {
        async init() {
            console.log('invoke init()')
            this.findProjectHttp()
            this.findRepositoriesHttp()
        },

        /**
         * 获取项目列表
         */
        findProjectHttp() {
            $api.project.findAll({}).then(data => {
                this.projectList = data.map((item) => ({
                    id: item.id,
                    name: item.name,
                    easyEnvId: item.easyEnvId,
                    easyRepositoryId: item.easyRepositoryId,
                    easyRepositoryName: item.easyRepositoryName,
                    easyCertificateName: item.easyCertificateName,
                    branch: item.branch
                }))
            })
        },


        /**
         * 获取仓库列表
         */
        findRepositoriesHttp() {
            $api.repository.findAll().then(data => {
                this.repositories = data.map((item) => ({
                    id: item.id,
                    name: item.name,
                    description: item.description,
                    cloneUrl: item.cloneUrl,
                    easyCertificateId: item.easyRepositoryId,
                    easyCertificateName: item.easyCertificateName,
                    branch: item.branch
                }))
                this.repositories.forEach((item) => {
                    this.findRepositoryBranchesHttp(item.id)
                })
            })
        },

        /**
         * 展示新增项目弹窗
         */
        showProjectAddPop() {
            this.project = {
                id: 0,
                name: '',
                envId: 0,
                envName: '',
                easyRepositoryId: 0,
                easyRepositoryName: '',
                branch: ''
            }
            this.projectPopVisible = true
            this.isEditPop = false
        },

        /**
         * 获取仓库列表
         */
        findRepositoryBranchesHttp(easyRepositoryId) {
            if (this.branchesMap[easyRepositoryId]) {
                this.branches = this.branchesMap[easyRepositoryId];
                return
            }
            $api.project.findRepositoryBranches({
                easyRepositoryId: easyRepositoryId
            }).then(data => {
                this.branchesMap[easyRepositoryId] = data;
                this.branches = this.branchesMap[easyRepositoryId];
            })
        },

        /**
         * 展示修改项目弹窗
         * @param row 修改行记录信息
         */
        showProjectEditPop(row) {
            console.log(row.branch)
            this.project = {
                id: row.id,
                name: row.name,
                envId: row.envId,
                envName: row.envName,
                easyRepositoryId: row.easyRepositoryId,
                easyRepositoryName: row.easyRepositoryName,
                branch: row.branch
            }
            this.findRepositoryBranchesHttp(row.easyRepositoryId)
            this.projectPopVisible = true
            this.isEditPop = true
        },
        /**
         * 移除指定弹窗
         * @param id 弹窗id
         */
        remove(id) {
            $api.project.deleteById({
                id: id
            }).then(data => {
                this.findProjectHttp()
            })
        },
        /**
         * 项目保存
         */
        projectSave() {
            $api.project.add({
                name: this.project.name,
                easyRepositoryId: this.project.easyRepositoryId,
                branch: this.project.branch
            }).then(data => {
                this.findProjectHttp()
            })
        },

        /**
         * 项目编辑
         */
        projectEdit() {
            $api.project.edit({
                id: this.project.id,
                name: this.project.name,
                easyRepositoryId: this.project.easyRepositoryId,
                branch: this.project.branch
            }).then(data => {
                this.findProjectHttp()
            })
        },

        /**
         * 项目构建
         * @project 项目信息
         */
        showConfigPage(project) {
            this.$emit('show-config-page', project)
        },

        /**
         * 项目仓库更改事件
         */
        repositoryChange(repositoryId) {
            this.project.easyRepositoryId = repositoryId
            this.findRepositoryBranchesHttp(repositoryId)
        },
        /**
         * 分支被选择
         */
        branchChange(branch) {
            this.project.branch = branch
        }
    }
}

</script>
