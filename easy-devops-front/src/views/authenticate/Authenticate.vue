<template>
  <Button type="primary" style="width: 70px;" @click="showAuthenticateAddPop">新增</Button>

  <Table border :columns="columns" :data="authenticateList" @on-row-click="showDetail">
    <template #name="{ row }">
      <strong>{{ row.name }}</strong>
    </template>
    <template #action="{ row, index }">
      <Button type="info" size="small" style="margin-right: 5px" @click="showAuthenticateEditPop(row)">编辑
      </Button>
      <Button type="warning" size="small">删除</Button>
    </template>
  </Table>

  <AuthenticatePop
      :authenticate="authenticate"
      :authenticate-name-disable="authenticateNameDisable"
      :authenticate-pop-visible="authenticatePopVisible"
      @authenticate-type-change="authenticateTypeChange"/>

</template>


<script>
import AuthenticatePop from '@/views/authenticate/AuthenticatePop.vue'

export default {
  name: 'Authenticate',
  components: {
    AuthenticatePop
  },
  data() {
    return {
      columns: [
        {
          title: '名称',
          slot: 'name'
        },
        {
          title: '描述',
          key: 'description'
        },
        {
          title: '类型',
          key: 'type'
        },
        {
          title: 'Action',
          slot: 'action',
          width: 150,
          align: 'center'
        }
      ],
      authenticateList: [
        {
          id: 1,
          type: 1,
          name: 'gitee',
          description: 'gitee 仓库',
          username: 'xiaoyuxxx',
          password: '12138',
          sshPrivateKey: ''
        }
      ],
      authenticate: {
        id: 1,
        type: 1,
        name: 'gitee',
        description: 'gitee 仓库',
        username: 'xiaoyuxxx',
        password: '12138',
        sshPrivateKey: ''
      },
      authenticateType: '1',
      authenticatePopVisible: false,
      authenticateNameDisable: false
    }
  },
  methods: {
    /**
     * 展示认证新增弹窗
     * @param row 目标认证行信息
     */
    showAuthenticateAddPop(row) {
      this.authenticate = {
        id: 0,
        type: 1,
        name: '',
        username: '',
        password: '',
        sshPrivateKey: ''
      }
      this.authenticateNameDisable = false
      this.authenticatePopVisible = true
    },

    /**
     * 展示认证修改弹窗
     * @param row 目标认证行信息
     */
    showAuthenticateEditPop(row) {
      this.authenticate = {
        id: row.id,
        type: row.type,
        name: row.name,
        username: row.username,
        password: row.password,
        sshPrivateKey: row.sshPrivateKey
      }
      console.log(this.authenticate.name)
      this.authenticateNameDisable = true
      this.authenticatePopVisible = true
    },

    /**
     * 认证类型更改时间
     * @param typeValue 认证类型
     */
    authenticateTypeChange(typeValue) {
      this.authenticate.type = typeValue
    },

    showDetail() {

    }
  }
}
</script>

