<template>
  <Modal v-model="envPopVisible" title="编辑" @on-ok="ok()" @on-cancel="cancel()">
    <Form ref="grayEnv" :model="grayEnv" :label-width="80">
      <FormItem label="名称:" prop="name">
        <Input type="text" v-model="grayEnv.name" :disabled="envPopNameDisable"/>
      </FormItem>
      <FormItem label="描述:" prop="description">
        <Input type="text" v-model="grayEnv.description"/>
      </FormItem>
      <FormItem label="有效期至:">
        <DatePicker type="date" format="yyyy-MM-dd" placeholder="Select date" :model-value="grayEnv.expireTime"
                    @on-change="handleDateChange"/>
      </FormItem>
    </Form>
  </Modal>
</template>
<script>

export default {
  props: {
    envPopVisible: false,
    envPopNameDisable: {
      default: false
    },
    grayEnv: {
      type: Object,
      default: {}
    }
  },
  methods: {
    ok() {
      const grayId = this.grayEnv.id
      if (grayId === undefined || grayId == null) {
        this.$emit('env-add', this.grayEnv)
      } else {
        this.$emit('env-edit', this.grayEnv)
      }
    },
    cancel() {
      // 清理对话框数据
      console.log(`-----cancel------${this.grayEnv.name}`)
    },
    handleDateChange(date) {
      this.grayEnv.expireTime = date
    }
  }
}
</script>
