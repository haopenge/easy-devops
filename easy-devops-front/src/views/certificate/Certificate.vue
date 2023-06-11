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
      :certificate-type="certificateType"
      :pop-edit-enable="popEditEnable"
      :certificate-pop-visible="certificatePopVisible"
      @certificate-type-change="certificateTypeChange"
      @repository-type-change="repositoryTypeChange"
      @certificate-add="certificateAdd"
      @certificate-edit="certificateEdit"
  />

</template>


<script>
import CertificatePop from '@/views/certificate/CertificatePop.vue'
import axios from "axios";

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
      certificateType: 1,
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
      axios
          .get('/certificate/findAll?containSsh=' + true)
          .then((response) => {
            const resultData = fetchResponseData(response)
            this.certificateList = resultData.map((item) => ({
              id: item.id,
              type: item.type,
              typeShow: item.type === 1 ? '全局ssh' : '仓库凭证',
              name: item.name,
              description: item.description,
              username: item.username,
              repositoryType: item.repositoryType,
              repositoryTypeShow: this.getRepositoryTypeShow(item.repositoryType)
            }))
          })
          .catch((error) => { // 请求失败处理
            console.log(error)
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
     * 新增仓库凭证
     */
    addAccessTokenCertificate() {
      const payload = {
        name: this.certificate.name,
        username: this.certificate.username,
        description: this.certificate.description,
        accessToken: this.certificate.accessToken,
        repositoryType: this.certificate.repositoryType
      }
      axios
          .post('/certificate/add', payload)
          .then((response) => {
            fetchResponseData(response)
            // 刷新列表
            this.findCertificate()
          })
          .catch((error) => { // 请求失败处理
            console.log(error)
          })
    },


    /**
     * 展示认证新增弹窗
     * @param row 目标认证行信息
     */
    showCertificateAddPop(row) {
      this.certificate = {
        id: 0,
        type: 5,
        name: '',
        username: '',
        accessToken: '',
        repositoryType: '2'
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
      this.certificateType = row.type
      this.popEditEnable = true
      this.certificatePopVisible = true
    },

    /**
     * 认证类型更改时间
     * @param typeValue 认证类型
     */
    certificateTypeChange(typeValue) {
      this.certificate.type = typeValue
    },

    /**
     * 仓库类型变更
     * @param repositoryType 类型变更
     */
    repositoryTypeChange(repositoryType) {
      this.certificate.repositoryType = repositoryType
    },

    /**
     * 更新全局仓库凭证
     */
    addGlobalSshCertificate() {
        console.log('----------------' + this.certificate.sshPrivateKey)
      const payload = {
        sshPrivateKey: this.certificate.sshPrivateKey
      }
      axios
          .put('/certificate/updateSshPrivateKey', payload)
          .then((response) => {
            fetchResponseData(response)
            //this.refreshProject(this.rightProjectEnvId)
          })
          .catch((error) => { // 请求失败处理
            console.log(error)
          })
    },

    /**
     * 新增凭证
     */
    certificateAdd() {
      if (this.certificate.type === 1) {
        this.addGlobalSshCertificate()
      } else {
        this.addAccessTokenCertificate()
      }
    },

    /**
     * 编辑仓库凭证
     */
    editAccessTokenCertificate() {
      const payload = {
        id: this.certificate.id,
        username: this.certificate.username,
        accessToken: this.certificate.accessToken,
        description: this.certificate.description,
        repositoryType: this.certificate.repositoryType
      }
      axios
          .put('/certificate/edit', payload)
          .then((response) => {
            fetchResponseData(response)
            // 刷新列表
            this.findCertificate()
          })
          .catch((error) => { // 请求失败处理
            console.log(error)
          })
    },

    /**
     * 凭证编辑
     */
    certificateEdit() {
      if (this.certificate.type === 1) {
        this.addGlobalSshCertificate()
      } else {
        this.editAccessTokenCertificate()
      }
    },

    /**
     * 删除凭证
     * @param id
     */
    deleteCertificate(id) {
      axios
          .delete(`/certificate/deleteById?id=${id}`)
          .then((response) => {
            fetchResponseData(response)
            // 刷新列表
            this.findCertificate()
          })
          .catch((error) => { // 请求失败处理
            console.log(error)
          })
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

