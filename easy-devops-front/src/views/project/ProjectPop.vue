<template>
  <Modal v-model="projectPopVisible" title="编辑" @on-ok="ok()" @on-cancel="cancel()">
    <Form ref="repository" :model="project" :label-width="80">
      <FormItem label="名称:" prop="name">
        <Input type="text" v-model="project.name"/>
      </FormItem>

      <FormItem label="仓库:" prop="name" >
        <Select v-model="projectRepositoryId" style="width:200px" @on-change="projectRepositoryOnSelect">
          <template v-for="(item,index) in projectRepositories" :key=index>
            <Option :value=item.id :label=item.name>
              <span>{{ item.name }}</span>
              <span style="float:right;color:#ccc">{{ item.authenticateName }}</span>
            </Option>
          </template>
        </Select>
      </FormItem>

      <FormItem label="分支:" prop="branch">
        <AutoComplete
            v-model="project.branch"
            :data="projectBranches"
            :filter-method="filterMethod"
            placeholder="分支"
            style="width:350px"
        >
        </AutoComplete>
      </FormItem>

    </Form>
  </Modal>
</template>


<script>

export default {
  props: {
    project: {
      type: Object,
      default: {}
    },
    projectPopVisible: {
      type: Boolean,
      default: false
    },
    projectRepositoryId: {
      type: Number,
      default: {}
    },
    projectRepositories: {
      type: Array,
      default: []
    },
    projectBranches: {
      type: Array,
      default: []
    }

  },
  methods: {
    ok() {
      this.$emit('project-save')
    },
    cancel() {
      this.$emit('project-cancel')
    },
    filterMethod(value, option) {
      return option.toUpperCase()
          .indexOf(value.toUpperCase()) !== -1
    },
    projectRepositoryOnSelect(projectRepositoryId){
      console.log(projectRepositoryId)
      this.$emit('project-repository-change', projectRepositoryId)
    }
  }
}
</script>
