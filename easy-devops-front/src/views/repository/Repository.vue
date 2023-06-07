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
                 :authenticate-option="authenticateOption"
                 :authenticate-options="authenticateOptions"
                 :repository-pop-visible="repositoryPopVisible"
                 @repository-save="repositorySave"
                 @authenticate-option-change="authenticateOptionChange"
  />
</template>
<script>

import RepositoryPop from '@/views/repository/RepositoryPop.vue'

export default {
  components: {
    RepositoryPop
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
          title: '认证id',
          key: 'easyAuthenticateId'
        },
        {
          title: 'Action',
          slot: 'action',
          width: 150,
          align: 'center'
        }
      ],
      repository: {
        id: 1,
        name: 'easy-gray',
        cloneUrl: 'git@gitee.com:xiaoyuxxx/easy-gray.git',
        description: '灰度发布项目',
        easyAuthenticateId: 1
      },
      repositoryList: [
        {
          id: 1,
          name: 'easy-gray',
          cloneUrl: 'git@gitee.com:xiaoyuxxx/easy-gray.git',
          description: '灰度发布项目',
          easyAuthenticateId: 1,
        }
      ],
      repositoryPopVisible: false,
      authenticateOption: '1',
      authenticateOptions: [
        {
          id: 1,
          name: 'gitee',
          description: 'gitee认证'
        },
        {
          id: 2,
          name: 'github',
          description: 'github认证'
        }
      ]
    }
  },
  methods: {
    /**
     * 展示新增仓库弹窗
     */
    showRepositoryAddPop() {
      this.repository = {
        id: 0,
        name: '',
        cloneUrl: '',
        description: '',
        easyAuthenticateId: 0
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
        easyAuthenticateId: row.easyAuthenticateId
      }
      this.repositoryPopVisible = true
    },
    /**
     * 移除指定弹窗
     * @param id 弹窗id
     */
    remove(id) {
      alert('移除成功，id =' + id)
    },
    /**
     * 仓库保存
     */
    repositorySave() {
      let saveRepository = {
        id: this.repository.id,
        name: this.repository.name,
        cloneUrl: this.repository.cloneUrl,
        description: this.repository.description,
        easyAuthenticateId: this.authenticateOption
      }
      alert(saveRepository.easyAuthenticateId)
    },

    /**
     * 认证类型修改监听
     * @param authenticateOptionId
     */
    authenticateOptionChange(authenticateOptionId) {
      this.authenticateOption = authenticateOptionId
    }
  }
}
</script>
