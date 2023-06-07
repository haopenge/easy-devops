<template>
  <Modal v-model="repositoryPopVisible" title="编辑" @on-ok="ok()" @on-cancel="cancel()">
    <Form ref="repository" :model="repository" :label-width="80">
      <FormItem label="名称:" prop="name">
        <Input type="text" v-model="repository.name"/>
      </FormItem>

      <FormItem label="描述:" prop="description">
        <Input type="text" v-model="repository.description"/>
      </FormItem>

      <FormItem label="git地址:" prop="description">
        <Input type="text" v-model="repository.cloneUrl"/>
      </FormItem>

      <FormItem label="认证:" prop="description">
        <Select v-model="authenticateOption" style="width:200px" @on-change="authenticateOptionChange">
          <template v-for="(item,index) in authenticateOptions" :key=index>
            <Option :value=item.id :label=item.name>
              <span>{{ item.name }}</span>
              <span style="float:right;color:#ccc">{{ item.description }}</span>
            </Option>
          </template>
        </Select>
      </FormItem>

    </Form>
  </Modal>
</template>


<script>

export default {
  props: {
    repository: {
      type: Object,
      default: {}
    },
    repositoryPopVisible: {
      type: Boolean,
      default: false
    },
    authenticateOptions: {
      type: Array,
      default: []
    },
    authenticateOption: {
      type: String,
      default: ''
    }
  },
  methods: {
    ok() {
      this.$emit('repository-save')
    },
    cancel() {
      this.$emit('repository-cancel')
    },
    /**
     * 认证类型修改
     * @param authenticateOptionId 认证类型选项id
     */
    authenticateOptionChange(authenticateOptionId) {
      this.$emit('authenticate-option-change', authenticateOptionId)
    }
  }
}
</script>
