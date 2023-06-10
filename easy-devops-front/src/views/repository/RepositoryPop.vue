<template>
    <Modal v-model="repositoryPopVisible" title="编辑" @on-ok="ok()" @on-cancel="cancel()">
        <Form ref="repository" :model="repository" :label-width="80">

            <FormItem label="凭证:" prop="description">
                <Select v-model="repository.easyCertificateId" style="width:200px" @on-change="certificateOptionChange" >
                    <template v-for="(item,index) in certificateOptions" :key=index>
                        <Option :value=item.id :label=item.name>
                            <span>{{ item.name }}</span>
                            <span style="float:right;color:#ccc">{{ item.description }}</span>
                        </Option>
                    </template>
                </Select>
            </FormItem>

            <FormItem label="名称:" prop="name">
                <AutoComplete
                        :model-value="repository.name"
                        icon="ios-search"
                        placeholder="input here"
                        @on-select="repositoryOnSelect"
                        style="width:350px">
                    <Option v-for="option in gitRepositories" :value="option.name" :key="option.name">
                        <span>{{ option.name }}  </span> <br/>
                        <span style="color: #7f8c8d; font-size: 12px">{{ option.description }}</span>
                    </Option>
                </AutoComplete>

            </FormItem>

            <FormItem label="描述:" prop="description">
                <Input type="text" v-model="repository.description" disabled/>
            </FormItem>

            <FormItem label="git地址:" prop="description">
                <Input type="text" v-model="repository.cloneUrl" disabled/>
            </FormItem>
            <FormItem label="分支:" prop="branch">
                <Input type="text" v-model="repository.branch" disabled/>
            </FormItem>

        </Form>
    </Modal>
</template>


<script>

import repository from "@/views/repository/Repository.vue";

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
        certificateOptions: {
            type: Array,
            default: []
        },
        gitRepositories: {
            type: Array,
            default: []
        }
    },
    methods: {
        ok() {
            if (this.repository.id) {
                this.$emit('repository-edit')
            } else {
                this.$emit('repository-save')
            }
        },
        cancel() {
            this.$emit('repository-cancel')
        },
        /**
         * 认证类型修改
         * @param certificateOptionId 认证类型选项id
         */
        certificateOptionChange(certificateOptionId) {
            if (certificateOptionId) {
                this.$emit('certificate-option-change', certificateOptionId)
            }
        },
        repositoryOnSelect(value) {
            this.$emit('repository-select-event', value)
        },
        filterMethod(value, option) {
            return option.toUpperCase()
                .indexOf(value.toUpperCase()) !== -1
        },
    }
}
</script>
