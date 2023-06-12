<template>
  <Modal v-model="certificatePopVisible" title="编辑" @on-ok="ok()" @on-cancel="cancel()"  width="800px">
    <Form ref="certificate" :model="certificate" :label-width="80">

      <div v-if="!popEditEnable">
        <FormItem label="类型:" prop="type">
          <RadioGroup v-model="certificate.type">
            <template v-for="(item,index) in certificateTypes" :key=index>
              <Radio :value=item.id :label=item.id>
                <span>{{ item.name }}</span>
              </Radio>
            </template>
          </RadioGroup>
        </FormItem>
      </div>

      <div v-if="certificate.type === 1">
        <FormItem label="私钥:" prop="sshPrivateKey">
          <Input type="textarea" v-model="certificate.sshPrivateKey" :rows="4"
                 placeholder="-----BEGIN OPENSSH PRIVATE KEY-----&#10;私钥&#10;-----END OPENSSH PRIVATE KEY-----"/>
        </FormItem>
      </div>
        <div v-if="certificate.type === 3">
            <FormItem label="k8s:" prop="content">
                <Codemirror
                        v-model="certificate.kubeConfig"
                        :placeholder="kubeConfigPlaceholder"
                        :style="{ height: '400px' }"
                        :autofocus="true"
                        :indent-with-tab="true"
                        :tabSize="2"
                        :extensions="extensions"
                        @change="codeChange($event)"
                />
            </FormItem>
        </div>

      <div v-if="certificate.type === 5">
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
          <Input type="text" v-model="certificate.name"  placeholder="凭证唯一标识"/>
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
import {java} from "@codemirror/lang-java";
import {oneDark} from "@codemirror/theme-one-dark";
import {Codemirror} from "vue-codemirror";

export default {
    components: {
        Codemirror,
    },
  props: {
    certificate: {
      type: Object,
      default: {}
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
      type: Number,
      default: 2
    },
      extensions: {
          type: Array,
          default: [java(), oneDark]
      }
  },
  data() {
    return {
      repositoryTypeOptions: [
        {
          id: 1,
          name: 'github'
        },
        {
          id: 2,
          name: 'gitee'
        },
        {
          id: 3,
          name: 'gitlab'
        }
      ],
      certificateTypes: [
          {
              id: 5,
              name: '仓库-用户名-令牌'
          },
        {
          id: 1,
          name: '全局-ssh-私钥'
        },
          {
              id: 3,
              name: 'k8s配置'
          }
      ],
        kubeConfigPlaceholder: 'apiVersion: v1\n' +
            'clusters:\n' +
            '  - cluster:\n' +
            '      certificate-authority-data: \n' +
            '      server: https://10.8.0.1:6443\n' +
            '    name: cluster.local\n' +
            'contexts:\n' +
            '  - context:\n' +
            '      cluster: cluster.local\n' +
            '      user: kubernetes-admin\n' +
            '    name: kubernetes-admin@cluster.local\n' +
            'current-context: kubernetes-admin@cluster.local\n' +
            'kind: Config\n' +
            'preferences: {}\n' +
            'users:\n' +
            '  - name: kubernetes-admin\n' +
            '    user:\n' +
            '      client-certificate-data: \n' +
            '\n'
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
    repositoryTypeOptionChange(gitType) {
      this.$emit('repository-type-change', gitType)
    },
      codeChange(value) {
          console.log('内容：' + value)
      },
  }
}
</script>
