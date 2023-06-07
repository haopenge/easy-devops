<template>
  <Modal v-model="projectPopVisible" title="编辑" @on-ok="ok()" @on-cancel="cancel()">
    <Form ref="grayEnv" :model="grayProject" :label-width="80">
      <FormItem label="项目:" prop="name">
        <AutoComplete
            v-model="grayProject.name"
            :data="gitProjects"
            :filter-method="filterMethod"
            placeholder="项目名"
            style="width:350px"
            @on-select="projectOnSelect"
        >
        </AutoComplete>
      </FormItem>

      <FormItem label="分支:" prop="branch">
        <AutoComplete
            v-model="grayProject.branch"
            :data="gitBranches"
            :filter-method="filterMethod"
            placeholder="分支"
            style="width:200px"
        >
        </AutoComplete>
      </FormItem>

    </Form>
  </Modal>
</template>
<script>

export default {
  props: {
    projectPopVisible: false,
    grayProject: {
      type: Object,
      default: {}
    },
    gitProjects: {
      type: Array,
      default: []
    },
    gitBranches: {
      type: Array,
      default: []
    }
  },
  methods: {
    ok() {
      const projectId = this.grayProject.id
      if (projectId === undefined || projectId == null) {
        this.$emit('project-add', this.grayProject)
      } else {
        this.$emit('project-edit', this.grayProject)
      }
    },

    cancel() {
      // 清理对话框数据
    },
    filterMethod(value, option) {
      return option.toUpperCase()
        .indexOf(value.toUpperCase()) !== -1
    },
    projectOnSelect(value) {
      this.$emit('project-branch-fetch', value)
    }
  }
}
</script>
