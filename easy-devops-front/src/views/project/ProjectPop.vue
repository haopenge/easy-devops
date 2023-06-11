<template>
    <Modal v-model="projectPopVisible" title="编辑" @on-ok="ok()" @on-cancel="cancel()">
        <Form ref="repository" :model="project" :label-width="80">
            <FormItem label="名称:" prop="name">
                <Input type="text" v-model="project.name" style="width:350px" :disabled="isEditPop"/>
            </FormItem>

            <FormItem label="仓库:" prop="name">
                <Select v-model="project.easyRepositoryId" style="width:350px" @on-select="repositoryOnSelect">
                        <Option v-for="(item,index) in repositories" :key=index :label="item.name" :value="item.id">
                            <span>{{ item.name }}</span>
                            <span style="float:right;color:#ccc">{{ item.easyCertificateName }}</span>
                        </Option>
                </Select>
            </FormItem>

            <FormItem label="分支:" prop="branch">
                <Select v-model="project.branch" style="width:350px" @on-select="branchOnSelect">
                    <Option v-for="item in branches" :value="item" :key="item">
                        {{ item }}
                    </Option>
                </Select>
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
        repositories: {
            type: Array,
            default: []
        },
        branches: {
            type: Array,
            default: []
        },
        isEditPop: {
            type: Boolean,
            default: false
        }

    },
    methods: {
        ok() {
            if (this.project.id) {
                this.$emit('project-edit')
            } else {
                this.$emit('project-save')
            }

        },
        cancel() {
            this.$emit('project-cancel')
        },
        filterMethod(value, option) {
            return option.toUpperCase()
                .indexOf(value.toUpperCase()) !== -1
        },
        repositoryOnSelect(repositoryOption) {
            if (repositoryOption) {
                console.log('repositoryOnSelect ' + JSON.stringify(repositoryOption))
                this.$emit('repository-change', repositoryOption.value)
            }
        },
        branchOnSelect(branchOption){
            if (branchOption) {
                console.log('branchOnSelect ' + JSON.stringify(branchOption))
                this.$emit('branch-change', branchOption.value)
            }
        }
    }
}
</script>
