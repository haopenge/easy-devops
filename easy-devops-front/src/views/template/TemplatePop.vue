<template>
    <Modal v-model="templatePopVisible" title="编辑" @on-ok="ok()" @on-cancel="cancel()" width="800px">
        <Form ref="repository" :model="template" :label-width="80">

            <FormItem label="名称:" prop="description">
                <Input type="text" v-model="template.name"/>
            </FormItem>

            <FormItem label="类型:" prop="type">
                <RadioGroup v-model="template.typeShow" type="button" button-style="solid" @on-change="templateTypeChangeEvent">
                    <template v-for="item in templateTypes" >
                        <Radio :label="item.name"></Radio>
                    </template>
                </RadioGroup>

            </FormItem>
            <FormItem label="内容:" prop="type">
                <Codemirror
                        v-model="template.content"
                        placeholder="Code gose here..."
                        :style="{ height: '400px' }"
                        :autofocus="true"
                        :indent-with-tab="true"
                        :tabSize="2"
                        :extensions="extensions"
                        @change="codeChange($event)"
                />
            </FormItem>
        </Form>
    </Modal>

</template>

<script>
import {Codemirror} from "vue-codemirror";
import {java} from "@codemirror/lang-java";
import {oneDark} from "@codemirror/theme-one-dark";

export default {
    components: {
        Codemirror,
    },
    props: {
        templatePopVisible: {
            type: Boolean,
            default: false
        },
        template: {
            type: Object,
            default: {}
        },
        templateTypes: {
            type: Array,
            default: []
        },
        extensions: {
            type: Array,
            default: [java(), oneDark]
        }
    },
    methods: {
        ok() {
            if (this.template.id) {
                this.$emit('template-edit')
            } else {
                this.$emit('template-save')
            }
        },
        cancel() {
            this.$emit('template-cancel')
        },
        codeChange(value) {
            console.log('内容：' + value)
        },
        templateTypeChangeEvent(templateTypeOption) {
            console.log('templateTypeChangeEvent ' + JSON.stringify(templateTypeOption))
            this.$emit('template-type-change',templateTypeOption)
        }
    }
};
</script>
