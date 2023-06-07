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
              :project-branches="projectBranches"
              :project-repositories="projectRepositories"
              :project-repository-id="projectRepositoryId"
              @project-repository-change="projectRepositoryChange"
              @project-save="projectSave"
  />
</template>
<script>

import ProjectPop from '@/views/project/ProjectPop.vue'

export default {
  components: {
    ProjectPop
  },
  data() {
    return {
      columns: [
        {
          title: '名称',
          key: 'name'
        },
        {
          title: '仓库',
          key: 'repositoryName'
        },
        {
          title: '分支',
          key: 'branch'
        },
        {
          title: 'Action',
          slot: 'action',
          width: 250,
          align: 'center'
        }
      ],
      project: {
        id: 1,
        name: 'easy-gray-api',
        envName: 'qa',
        repositoryName: 'easy-gray',
        branch: 'main'
      },
      projectList: [
        {
          id: 1,
          name: 'easy-gray-api',
          repositoryName: 'easy-gray',
          branch: 'main'
        }
      ],
      projectRepositories: [
        {
          id: 1,
          name: 'easy-gray',
          authenticateName: 'gitee'
        },
        {
          id: 2,
          name: 'easy-deploy',
          authenticateName: 'github'
        }

      ],
      projectBranches: [
        'main', 'qa', 'easy-12138'
      ],
      projectPopVisible: false,
      projectRepositoryId: 1,
      projectBranch: 'main'
    }
  },
  methods: {
    /**
     * 展示新增项目弹窗
     */
    showProjectAddPop() {
      this.project = {
        id: 0,
        name: '',
        repositoryName: '',
        branch: ''
      }
      this.projectPopVisible = true
    },
    /**
     * 展示修改项目弹窗
     * @param row 修改行记录信息
     */
    showProjectEditPop(row) {
      this.project = {
        id: row.id,
        name: row.name,
        envName: row.envName,
        repositoryName: row.repositoryName,
        branch: row.branch
      }
      this.projectPopVisible = true
    },
    /**
     * 移除指定弹窗
     * @param id 弹窗id
     */
    remove(id) {
      alert('移除成功，id =' + id)
    },
    /**
     * 项目保存
     */
    projectSave() {
      let saveRepository = {
        id: this.project.id,
        name: this.project.name,
        projectRepositoryId: this.projectRepositoryId,
        branch: this.project.branch
      }
      alert(saveRepository.projectRepositoryId)
    },
    /**
     * 项目构建
     * @project 项目信息
     */
    showConfigPage(project) {
      this.$emit('show-config-page', project)
    },

    /**
     * 项目仓库更改时间
     */
    projectRepositoryChange(projectRepositoryId) {
      this.projectRepositoryId = projectRepositoryId
    }
  }
}
</script>
