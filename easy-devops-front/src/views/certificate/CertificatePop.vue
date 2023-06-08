<template>
  <Modal v-model="certificatePopVisible" title="编辑" @on-ok="ok()" @on-cancel="cancel()">
    <Form ref="certificate" :model="certificate" :label-width="80">

      <div v-if="!popEditEnable">
        <FormItem label="类型:" prop="type">
          <RadioGroup v-model="certificateType" @on-change="typeChange">
            <template v-for="(item,index) in certificateTypes" :key=index>
              <Radio :value=item.id :label=item.id>
                <span>{{ item.name }}</span>
              </Radio>
            </template>
          </RadioGroup>
        </FormItem>
      </div>

      <div v-if="certificateType === 1">
        <FormItem label="私钥:" prop="sshPrivateKey">
          <Input type="textarea" v-model="sshPrivateKey" :rows="4"
                 placeholder="-----BEGIN OPENSSH PRIVATE KEY-----&#10;私钥&#10;-----END OPENSSH PRIVATE KEY-----"/>
        </FormItem>
      </div>

      <div v-if="certificateType === 5">
        <FormItem label="认证:" prop="repositoryType">
          <Select v-model="repositoryType" style="width:200px" @on-change="repositoryTypeOptionChange">
            <template v-for="(item,index) in repositoryTypeOptions" :key=index>
              <Option :value=item.id :label=item.name>
                <span>{{ item.name }}</span>
              </Option>
            </template>
          </Select>
        </FormItem>

        <FormItem label="名称:" prop="name">
          <Input type="text" v-model="certificate.name" :disabled="certificateNameDisable" placeholder="凭证唯一标识"/>
        </FormItem>
        <FormItem label="描述:" prop="description">
          <Input type="text" v-model="certificate.description" placeholder="凭证描述信息"/>
        </FormItem>

        <FormItem label="用户名:" prop="username">
          <Input type="text" v-model="certificate.username" placeholder="用户名"/>
        </FormItem>
        <FormItem label="密码:" prop="accessToken">
          <Input type="password" v-model="certificate.accessToken" placeholder="认证令牌"/>
        </FormItem>
      </div>
    </Form>
  </Modal>
</template>
<script>
export default {
  props: {
    certificate: {
      type: Object,
      default: {}
    },
    certificateType: {
      type: Number,
      default: 1
    },
    popEditEnable: {
      type: Boolean,
      default: true
    },
    certificatePopVisible: {
      type: Boolean,
      default: false
    },
    repositoryType: {
      type: String,
      default: '2'
    },
    sshPrivateKey: {
      type: String,
      default: ''
    }

  },
  data() {
    return {
      repositoryTypeOptions: [
        {
          id: '1',
          name: 'github'
        },
        {
          id: '2',
          name: 'gitee'
        },
        {
          id: '3',
          name: 'gitlab'
        }
      ],
      certificateTypes: [
        {
          id: 1,
          name: '全局-ssh-私钥'
        },
        {
          id: 5,
          name: '仓库-用户名-令牌'
        }
      ]
    }
  },
  methods: {
    ok() {
      // 编辑
      if(this.certificate.id){
        this.$emit('certificate-edit')
      }else{
        // 新增
        this.$emit('certificate-add')
      }

    },
    cancel() {
    },
    handleDateChange(date) {
    },
    typeChange(typeValue) {
      console.log('typeChange = ' + typeValue)
      this.$emit('certificate-type-change', typeValue)
    },
    repositoryTypeOptionChange(gitType) {
      this.$emit('repository-type-change', gitType)
    }
  }
}
</script>
