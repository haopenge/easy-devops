<style scoped>
.ivu-space-center {
  -webkit-box-align: start;
  align-items: start;
  margin-top: 20px;
  margin-left: 20px;
  margin-right: 50px;
}

</style>

<template>
  <Space>
    <div>
      <Steps :current="buildStepIndex" direction="vertical" class="ivu-space-center">
        <template v-for="(item,index) in buildSteps" :key="index">
          <Step :title=item.name :content="item.description" @click="buildStepClick(index)"/>
        </template>
      </Steps>
    </div>

    <div>
      <Divider type="vertical" style="height: 2000px"/>
    </div>

    <div>
      <template v-if="buildStepIndex === 0">
        <Form>
          <FormItem label="模板:" prop="templates">
            <Select v-model="template" multiple :max-tag-count="5" :max-tag-placeholder="maxTagPlaceholder">
              <Option v-for="item in templates" :value="item.name" :key="item.name">{{ item.name }}</Option>
            </Select>
          </FormItem>
        </Form>
        <Button type="primary" style="width: 800px; margin-top: 10px" @click="saveConfig">保存</Button>
      </template>

      <template v-if="buildStepIndex === 1">
        <Form>
          <div v-for="(item, index) in builds" :key="index">
            <Divider>步骤{{ index + 1 }}</Divider>
            <FormItem label="名称:" prop="name">
              <Input type="text" v-model="item.name" style="inline-size: auto" :disabled="item.nameDisable"
                     placeholder="请输入此构建步骤名称"/>
            </FormItem>

            <FormItem label="脚本:" prop="name" v-if="item.type === 2">
              <br>
              <Codemirror
                  v-model="item.content"
                  :placeholder="contentPlaceholder"
                  :style="{ height: '400px' }"
                  :autofocus="true"
                  :indent-with-tab="true"
                  :tabSize="2"
                  :extensions="extensions"
                  @ready="log('ready', $event)"
                  @change="codeChange($event,item)"
                  @focus="log('focus', $event)"
                  @blur="log('blur', $event)"
              />
            </FormItem>
          </div>
          <Button type="info" ghost style="width: 400px; margin-right: 11px" @click="addCheckOut">新增检出</Button>
          <Button type="success" ghost style="width: 400px" @click="addSh">新增脚本</Button>
        </Form>
        <Button type="primary" style="width: 800px; margin-top: 10px" @click="saveConfig">保存</Button>
      </template>

      <template v-if="buildStepIndex === 2">
        <Form>
          <FormItem label="命名空间:" prop="namespace">
            <Select v-model="namespace" style="width:333px" @on-change="namespaceOnSelect">
              <template v-for="(item,index) in namespaces" :key=index>
                <Option :value=item.name :label=item.name>
                  <span>{{ item.name }}</span>
                  <span style="float:right;color:#ccc">{{ item.description }}</span>
                </Option>
              </template>
            </Select>
          </FormItem>

        </Form>
      </template>
    </div>
  </Space>

</template>


<script>

import {Button, Form, Layout, Space} from 'view-ui-plus'
import {Codemirror} from "vue-codemirror";
import {java} from "@codemirror/lang-java";
import {oneDark} from "@codemirror/theme-one-dark";

export default {
  name: 'build',
  components: {
    Space,
    Layout,
    Form,
    Button,
    Codemirror
  },
  props: {},
  data() {
    return {
      builds: [],
      buildSteps: [
        {
          id: 1,
          name: '模板挂载',
          description: '挂载模板文件，方便服务构建'
        },
        {
          id: 2,
          name: '构建配置',
          description: '执行服务构建操作，确保发布所需配置加载完毕'
        },
        {
          id: 3,
          name: '发布配置',
          description: '执行启动服务操作，服务可以对外提供服务'
        },
      ],
      buildStepIndex: 0,
      namespace: 'qa',
      namespaces: [
        {
          name: 'qa',
          description: '公共测试环境'
        },
        {
          name: 'easy-12138',
          description: '灰度功能测试环境'
        }
      ],
      extensions: [java(), oneDark],
      log: console.log,
      templates : [
        {
          id: 1,
          name: 'java-dockerfile',
          description:'后端java通用dockerfile'
        },
        {
          id: 2,
          name: 'maven-setting',
          description:'maven仓库配置'
        },
        {
          id: 3,
          name: 'pod-develop_yaml',
          description:'通用k8s pod发布yaml模板'
        }
      ],
      template: [1,2],
      maxTagPlaceholder (num) {
        return 'more '+ num;
      }
    }
  },
  methods: {
    buildStepClick(index) {
      this.buildStepIndex = index
    },

    /**
     * 新增代码检出构建步骤
     */
    addCheckOut() {
      const hasCheckout = this.builds.some(item => item.type === 1)
      if (hasCheckout) {
        alert('已存在代码检出操作')
        return
      }
      let newCheckOutBuild = {
        name: '代码检出',
        type: 1,
        content: '',
        nameDisable: true
      }
      this.builds.push(newCheckOutBuild)
    },
    /**
     * 新增执行脚本
     */
    addSh() {
      const shArray = this.builds.filter(item => item.type === 2)
      if (shArray.length > 5) {
        alert('执行脚本数量已达上限5')
        return
      }
      let newShBuild = {
        name: '',
        type: 2,
        content: '',
        nameDisable: false
      }
      this.contentPlaceholder = 'FROM registry.cn-hangzhou.aliyuncs.com/ranmo/mvn:1.6\n' +
          'EXPOSE 10080\n' +
          'COPY ./target/*.jar $APP_PATH/app.jar\n' +
          'CMD ["sh","-c","java -jar $APP_PATH/app.jar"]'
      this.builds.push(newShBuild)
    },

    /**
     * 命名空间变更
     * @param namespace 空间变更
     */
    namespaceOnSelect(namespace) {
      this.namespace = namespace
    },
    /**
     * 脚本变更
     * @param value 脚本值
     * @param item  对应行记录
     */
    codeChange(value, item) {
      item.content = value;
    },

    /**
     * 保存配置
     */
    saveConfig() {
      alert(this.builds.at(0).content)
    }

  }
}
</script>
