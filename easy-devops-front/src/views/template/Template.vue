<template>
    <Button type="primary" style="width: 70px;" @click="showTemplateAddPop">新增</Button>

    <Table border :columns="columns" :data="templateList">
        <template #name="{ row }">
            <strong>{{ row.name }}</strong>
        </template>
        <template #action="{ row, index }">
            <Button type="info" size="small" style="margin-right: 5px" @click.stop="showTemplateEditPop(row)">编辑
            </Button>
            <Button type="warning" size="small" @click.stop="remove(row.id)">删除</Button>
        </template>
    </Table>

    <TemplatePop
            :template-pop-visible="templatePopVisible"
            :template-types="templateTypes"
            :template="template"
            :content-placeholder="contentPlaceholder"
            @template-type-change="templateTypeChange"
            @template-save="templateSave"
    />

</template>
<script>

import TemplatePop from '@/views/template/TemplatePop.vue'
import axios from "axios";

export default {
    computed: {
        TemplatePop
    },
    components: {
        TemplatePop
    },
    created() {
        this.init()
    },
    data() {
        return {
            columns: [
                {
                    title: '名称',
                    key: 'name'
                },
                {
                    title: '类型',
                    key: 'typeShow'
                },
                {
                    title: 'Action',
                    slot: 'action',
                    width: 150,
                    align: 'center'
                }
            ],
            templatePopVisible: false,
            templateTypes: [
                {
                    id: 1,
                    name: 'Dockerfile'
                },
                {
                    id: 5,
                    name: 'Shell'
                },
                {
                    id: 10,
                    name: 'Yaml'
                }
            ],
            templateList: [

            ],
            template: {

            },
          contentPlaceholderMap: {
            1: 'FROM registry.cn-hangzhou.aliyuncs.com/ranmo/mvn:1.6\n' +
                'EXPOSE 10080\n' +
                'COPY ./target/*.jar $APP_PATH/app.jar\n' +
                'CMD ["sh","-c","java -jar $APP_PATH/app.jar"]'
            ,
            5: '#!/bin/bash\n' +
                'SCRIPT=$(readlink -f "$0")\n' +
                'DEPLOY_DIR=$(dirname "$SCRIPT")\n' +
                'cd "${DEPLOY_DIR}"\n' +
                '\n' +
                'echo "发布目录: ${DEPLOY_DIR} "\n' +
                'echo "<<====================== 开始打包 ===================>>"\n' +
                'mvn clean package  -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -s /root/.m2/settings.xml',
            10: '# deployment.yaml\n' +
                'apiVersion: apps/v1\n' +
                'kind: Deployment\n' +
                'metadata:\n' +
                '  namespace: qa\n' +
                '  name: easy-gray-gateway-api\n' +
                '  labels:\n' +
                '    app: easy-gray-gateway-api\n' +
                '    release: easy-gray-gateway-api\n' +
                '    podEnv: pod-env\n' +
                'spec:\n' +
                '  replicas: 1\n' +
                '  strategy:\n' +
                '    rollingUpdate:\n' +
                '      maxSurge: 1\n' +
                '      maxUnavailable: 0\n' +
                '    type: RollingUpdate\n' +
                '\n' +
                '  selector:\n' +
                '    matchLabels:\n' +
                '      app: easy-gray-gateway-api\n' +
                '      release: easy-gray-gateway-api\n' +
                '  template:\n' +
                '    metadata:\n' +
                '      labels:\n' +
                '        app: easy-gray-gateway-api\n' +
                '        release: easy-gray-gateway-api\n' +
                '    spec:\n' +
                '      imagePullSecrets:\n' +
                '        - name: ali-docker-repository\n' +
                '      containers:\n' +
                '        - name: easy-gray-gateway-api\n' +
                '          image: registry.cn-hangzhou.aliyuncs.com/ranmo/easy-gray-gateway-api:build_number\n' +
                '          imagePullPolicy: IfNotPresent\n' +
                '          ports:\n' +
                '            - name: http\n' +
                '              containerPort: 10080\n' +
                '              protocol: TCP\n' +
                '          resources:\n' +
                '            limits:\n' +
                '              cpu: 1024m\n' +
                '              memory: 1024Mi\n' +
                '            requests:\n' +
                '              cpu: 1024m\n' +
                '              memory: 1024Mi\n' +
                '\n' +
                '\n'
          },
          contentPlaceholder: ''

        }
    },
    methods: {
        async init() {
            console.log('invoke init()')
            this.findTemplate()
        },
        /**
         * 获取模板列表
         */
        findTemplate() {
            axios
                .get('/template/findAll')
                .then((response) => {
                    const resultData = fetchResponseData(response)
                    this.templateList = resultData.map((item) => ({
                        id: item.id,
                        name: item.name,
                        type: item.type,
                        typeShow: item.typeShow,
                        content: item.content
                    }))
                })
                .catch((error) => { // 请求失败处理
                    console.log(error)
                })
        },

        showTemplateAddPop() {
            this.template = {
                id: 0,
                name: '',
                type: 1,
                typeShow: this.templateTypes[0].name,
                content: ''
            }
            this.contentPlaceholder = this.contentPlaceholderMap[1]
            this.templatePopVisible = true
        },
        showTemplateEditPop(row) {
            this.template = {
                id: row.id,
                name: row.name,
                type: row.type,
                typeShow: row.typeShow,
                content: row.content
            }
            this.templatePopVisible = true
        },
        /**
         * 模板保存
         */
        templateSave() {
            let payload = {
                name: this.template.name,
                type: this.template.type,
                content: this.template.content
            }
            axios
                .post('/template/add', payload)
                .then((response) => {
                    fetchResponseData(response)
                    this.findTemplate()
                })
                .catch((error) => { // 请求失败处理
                    console.log(error)
                })
        },
        templateTypeChange(typeName) {
            let templateTypes = this.templateTypes.filter(loopType => loopType.name === typeName);
            console.log('templateTypeChange ' + JSON.stringify(templateTypes))
            const type = templateTypes[0].id
            this.contentPlaceholder = this.contentPlaceholderMap[type]
        },
        remove(id){
            console.log('remove(id) ' + id)
            axios
                .delete('/template/deleteById?id=' + id)
                .then((response) => {
                    fetchResponseData(response)
                    this.findTemplate()
                })
                .catch((error) => { // 请求失败处理
                    console.log(error)
                })
        }

    }
}

/**
 * 获取http 相应信息
 * @param response http响应体
 * @returns {*[]}
 */
function fetchResponseData(response) {
    if (response && response.data) {
        const result = response.data
        const {success} = result
        const {message} = result

        if (!success) {
            alert(message)
        }
        return result.data || []
    }
}
</script>
