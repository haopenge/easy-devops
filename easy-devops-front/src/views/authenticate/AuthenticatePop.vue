<template>
  <Modal v-model="authenticatePopVisible" title="编辑" @on-ok="ok()" @on-cancel="cancel()">
    <Form ref="authenticate" :model="authenticate" :label-width="80">
      <FormItem label="名称:" prop="name">
        <Input type="text" v-model="authenticate.name" :disabled="authenticateNameDisable"/>
      </FormItem>

      <FormItem label="描述:" prop="description">
        <Input type="text" v-model="authenticate.description"/>
      </FormItem>

      <FormItem label="认证方式:" prop="type">
        <RadioGroup v-model="authenticateType" @on-change="typeChange">
          <Radio label="1">ssh-私钥</Radio>
          <Radio label="5">用户名+密码</Radio>
        </RadioGroup>
      </FormItem>

      <div v-if="authenticateType === '1'">
        <FormItem label="ssh-私钥:" prop="sshPrivateKey" >
          <Input type="textarea" v-model="authenticate.sshPrivateKey" :rows="4" placeholder="Enter something..."/>
        </FormItem>
      </div>

      <div v-if="authenticateType === '5'">
        <FormItem label="用户名:" prop="username" >
          <Input type="text" v-model="authenticate.username"/>
        </FormItem>
        <FormItem label="密码:" prop="password">
          <Input type="password" v-model="authenticate.password"/>
        </FormItem>
      </div>
    </Form>
  </Modal>
</template>
<script>
export default {
  props: {
    authenticate: {
      type: Object,
      default: {
      }
    },
    authenticateType:  {
      type: String,
      default: '1'
    },
    authenticateNameDisable: {
      type: Boolean,
      default: true
    },
    authenticatePopVisible: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    ok() {
    },
    cancel() {
    },
    handleDateChange(date) {
    },
    typeChange(typeValue) {
      this.$emit('authenticate-type-change', typeValue)
    }
  }
}
</script>
