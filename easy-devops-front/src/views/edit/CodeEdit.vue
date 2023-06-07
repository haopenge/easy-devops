<template>
    <Codemirror
        v-model="code"
        placeholder="Code gose here..."
        :style="{ height: '400px' }"
        :autofocus="true"
        :indent-with-tab="true"
        :tabSize="2"
        :extensions="extensions"
        @ready="log('ready', $event)"
        @change="codeChange($event)"
        @focus="log('focus', $event)"
        @blur="log('blur', $event)"
    />
</template>

<script>
import { Codemirror } from "vue-codemirror";
import { java } from "@codemirror/lang-java";
import { oneDark } from "@codemirror/theme-one-dark";

export default {
    components: {
        Codemirror,
    },
    data() {
       return {
           extensions : [java(), oneDark],
           log: console.log,
           code: 'FROM registry.cn-hangzhou.aliyuncs.com/ranmo/mvn:1.6\n' +
               'EXPOSE 10080\n' +
               'COPY ./target/*.jar $APP_PATH/app.jar\n' +
               'CMD ["sh","-c","java -jar $APP_PATH/app.jar"]'
       }
    },
    methods: {
        codeChange(value){
            console.log( '内容：' + value)
        }
    }
};
</script>
