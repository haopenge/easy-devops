<template>
  <div class="demo-split">
    <Split v-model="split">
      <template #left>
        <div class="demo-split-pane">
          <Left :gray-env-list="grayEnvList"
                @refresh-project='refreshProject'
                @show-env-pop="showEnvPop"
                @delete-env="envDelete"/>
        </div>
      </template>
      <template #right>
        <div class="demo-split-pane">
          <Right :gray-project-list="grayProjectList"
                 @show-project-pop="showProjectPop"
                 @delete-project="projectDelete"
                 @run-project="projectRun"
                 @stop-project="projectStop"
          />
        </div>
      </template>
    </Split>
  </div>

  <div>
    <EnvPop :gray-env="grayEnv"
            :env-pop-visible="envPopVisible"
            :env-pop-name-disable="envPopNameDisable"
            @env-edit="envEdit"
            @env-add="envAdd"
            />
    <ProjectPop :gray-project="grayProject"
                :project-pop-visible="projectPopVisible"
                :git-projects="gitProjects"
                :git-branches="gitBranches"
                @project-branch-fetch="projectBranchFetch"
                @project-add="projectAdd"
                @project-edit="projectEdit"
    />
  </div>

</template>
<script>
import axios from 'axios'
import Left from '@/views/gray/Env.vue'
import Right from '@/views/gray/EnvProject.vue'
import EnvPop from '@/views/gray/EnvPop.vue'
import ProjectPop from '@/views/gray/EnvProjectPop.vue'

export default {
  components: {
    ProjectPop,
    Left,
    Right,
    EnvPop
  },
  created() {
    this.init()
  },
  data() {
    return {
      split: 0.4,
      grayEnvList: [],
      grayProjectList: [],
      grayEnv: {
        id: '',
        name: '',
        description: '',
        expireTime: new Date('2023-01-01')
      },
      envPopVisible: false,
      envPopNameDisable: false,
      grayProject: {
        id: '',
        name: '',
        description: '',
        branch: ''
      },
      projectPopVisible: false,
      gitProjects: [
        'easy-gray/easy-gray-core',
        'easy-gray/easy-gray-api',
        'easy-gray/easy-gray-gateway',
        'easy-gray/easy-gray-example/easy-gray-intf',
        'easy-gray/easy-gray-example/easy-gray-gateway-api',
        'easy-gray/easy-gray-example/easy-gray-provider-one',
        'easy-gray/easy-gray-example/easy-gray-provider-two'
      ],
      gitBranches: [
        'master', 'qa', 'easy-12138'
      ],
      rightProjectEnvId: 0
    }
  },
  methods: {
    async init() {
      console.log('invoke init()')
      this.findGrayEnvList()
    },

    /**
     * 刷新项目列表
     * @returns {[{name: string, description: string, id: number},{name: string, description: string, id: number}]}
     */
    findGrayEnvList() {
      axios
          .get('/env/findAll')
          .then((response) => {
            const resultData = fetchResponseData(response)
            this.grayEnvList = resultData.map((item) => ({
              id: item.id,
              name: item.name,
              description: item.description ? item.description : '未填写环境描述',
              expireTime: item.expireTime
            }))
            this.rightProjectEnvId = this.grayEnvList[0].id
            this.refreshProject(this.rightProjectEnvId)
          })
          .catch((error) => { // 请求失败处理
            console.log(error)
          })
    },

    /**
     * 查看灰度项目
     * @param envId 环境id
     */
    refreshProject(envId) {
      console.log('.... findProject', envId)
      axios
          .get(`/project/findByEnvId?envId=${envId}`)
          .then((response) => {
            const resultData = fetchResponseData(response)
            this.grayProjectList = resultData.map((item) => ({
              id: item.id,
              name: item.name,
              description: item.description ? item.description : '未填写项目描述',
              branch: item.branch
            }))
            this.rightProjectEnvId = envId
          })
          .catch((error) => { // 请求失败处理
            console.log(error)
          })
    },

    /**
     * 展示环境弹窗
     */
    showEnvPop(row) {
      this.grayEnv = {
        id: row.id,
        name: row.name,
        description: row.description,
        expireTime: row.expireTime
      }
      this.envPopVisible = true
      // 环境名称不允许更改
      if (row.id !== 0 && row.id !== undefined) {
        this.envPopNameDisable = true
      } else {
        this.envPopNameDisable = false
      }
    },

    /**
     * 修改环境信息
     * @param grayEnv
     */
    envEdit(grayEnv) {
      const payload = {
        id: grayEnv.id,
        name: grayEnv.name,
        description: grayEnv.description,
        expireTime: grayEnv.expireTime
      }
      axios.post('/env/edit', payload)
          .then((response) => {
            fetchResponseData(response)
            this.findGrayEnvList()
          })
          .catch((error) => {
            console.error(error)
          })
    },
    /**
     * 新增环境信息
     * @param grayEnv
     */
    envAdd(grayEnv) {
      const payload = {
        name: grayEnv.name,
        description: grayEnv.description,
        expireTime: grayEnv.expireTime
      }
      axios.post('/env/add', payload)
          .then((response) => {
            fetchResponseData(response)
            this.findGrayEnvList()
          })
          .catch((error) => {
            console.error(error)
          })
    },
    /**
     * 删除环境信息
     * @param envId 环境id
     */
    envDelete(envId) {
      axios.delete(`/env/deleteById?id=${envId}`)
          .then((response) => {
            fetchResponseData(response)
            this.findGrayEnvList()
          })
          .catch((error) => {
            console.error(error)
          })
    },

    /**
     * 展示项目弹窗
     */
    showProjectPop(grayProject) {
      this.grayProject = {
        id: grayProject.id,
        name: grayProject.name,
        description: grayProject.description,
      }
      this.projectPopVisible = true
    },

    /**
     * 项目全路径
     * @param project
     */
    projectBranchFetch(project) {
      console.log('.... projectBranchFetch', project)
      axios
          //  TODO 目前仅一个git项目，先固定
          .get('/git/findBranches?projectUrl=' + 'https://gitee.com/xiaoyuxxx/easy-gray.git')
          .then((response) => {
            this.gitBranches = fetchResponseData(response)
          })
          .catch((error) => { // 请求失败处理
            console.log(error)
          })
    },

    /**
     * 新增项目到环境中
     * @param grayProject 项目
     */
    projectEdit(grayProject) {
      const payload = {
        id: grayProject.id,
        branchName: grayProject.branch
      }
      axios
          .post('/project/edit', payload)
          .then((response) => {
            fetchResponseData(response)
            this.refreshProject(this.rightProjectEnvId)
          })
          .catch((error) => { // 请求失败处理
            console.log(error)
          })
    },

    /**
     * 编辑项目
     * @param grayProject 项目
     */
    projectAdd(grayProject) {
      const payload = {
        envId: this.rightProjectEnvId,
        fullName: grayProject.name,
        branchName: grayProject.branch
      }
      axios
          .post('/project/add', payload)
          .then((response) => {
            // eslint-disable-next-line no-use-before-define
            fetchResponseData(response)
            this.refreshProject(this.rightProjectEnvId)
          })
          .catch((error) => { // 请求失败处理
            console.log(error)
          })
    },

    /**
     * 新增项目到环境中
     * @param projectId 项目id
     */
    projectDelete(projectId) {
      axios
          .delete(`/project/deleteById?id=${projectId}`)
          .then((response) => {
            // eslint-disable-next-line no-use-before-define
            fetchResponseData(response)
            this.refreshProject(this.rightProjectEnvId)
          })
          .catch((error) => { // 请求失败处理
            console.log(error)
          })
    },

    /**
     * 运行项目
     * @param projectId
     */
    projectRun(projectId) {
      axios
          .post(`/project/run?id=${projectId}`)
          .then((response) => {
            fetchResponseData(response)
            this.refreshProject(this.rightProjectEnvId)
          })
          .catch((error) => { // 请求失败处理
            console.log(error)
          })
    },
    /**
     * 停止项目
     * @param projectId
     */
    projectStop(projectId) {
      axios
          .post(`/project/stop?id=${projectId}`)
          .then((response) => {
            fetchResponseData(response)
            this.refreshProject(this.rightProjectEnvId)
          })
          .catch((error) => { // 请求失败处理
            console.log(error)
          })
    },

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
    const { success } = result
    const { message } = result

    if (!success) {
      alert(message)
    }
    return result.data || []
  }
}
</script>
<style>
.demo-split {
  height: 1000px;
  border: 1px solid #dcdee2;
}

.demo-split-pane {
  padding: 10px;
}
</style>
