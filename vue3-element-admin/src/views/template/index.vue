<script lang="ts" setup>
import "codemirror/mode/dockerfile/dockerfile.js"
import "codemirror/mode/yaml/yaml.js"
import "codemirror/mode/properties/properties.js"
import "codemirror/mode/xml/xml.js"
import "codemirror/mode/javascript/javascript.js"
import "codemirror/mode/shell/shell.js"

import "codemirror/theme/seti.css"
import Codemirror from "codemirror-editor-vue3"
import {CodeMirrorVo} from "@/api/template/types";

// ---------------------------------  codemirror	--------------------------------

const codeTypeMap = ref(new Map<string, string>());
codeTypeMap.value.set("Dockerfile", "text/x-dockerfile");
codeTypeMap.value.set("Yaml", "text/x-yaml");
codeTypeMap.value.set("Properties", "text/x-properties");
codeTypeMap.value.set("Xml", "application/xml");
codeTypeMap.value.set("JavaScript", "text/javascript");
codeTypeMap.value.set("Json", "application/json");
codeTypeMap.value.set("Shell", "text/x-sh");


// ---------------------------------  codemirror	--------------------------------
const codeType = ref<string>("Shell");

const codeMirrorVo = ref<CodeMirrorVo>({
	value: 'hello world',
	cmOptions: {
		mode: "application/json",
		theme: "seti"
	}
})
const cmRef = ref()
const onChange = (val: string, cm: any) => {
	//console.log('code-type: ' + codeType.value)
	console.log('。。。。。。')
	console.log(cm.getValue())
}
const onInput = (val: string) => {
	console.log(val)
}
const onReady = (cm: any) => {
	console.log(cm.focus())
}


// ------------ 类型选择	--------------------------------
const typeChange = (value: string | number | boolean) => {
	console.log('typeChange = ' + value)
	let temp = codeMirrorVo.value;
	codeMirrorVo.value = {
		value: temp.value,
		cmOptions: {
			mode: codeTypeMap.value.get(value.toString())
		}
	}

}


onMounted(() => {
	setTimeout(() => {
		cmRef.value?.refresh()
	}, 1000)

	setTimeout(() => {
		cmRef.value?.resize(800, 1000)
	}, 2000)

	setTimeout(() => {
		cmRef.value?.cminstance.isClean()
	}, 3000)
})

onUnmounted(() => {
	cmRef.value?.destroy()
});

</script>


<template>
	<div style="margin-top: 20px">
		<el-radio-group v-model="codeType" size="small" @change="typeChange">
			<el-radio-button label="Dockerfile"/>
			<el-radio-button label="Yaml"/>
			<el-radio-button label="Properties"/>
			<el-radio-button label="Xml"/>
			<el-radio-button label="JavaScript"/>
			<el-radio-button label="Json"/>
			<el-radio-button label="Shell"/>

		</el-radio-group>
		<el-button type="primary">保存</el-button>
	</div>
	<Codemirror
			width="800"
			height="1000"
			v-model:value="codeMirrorVo.value"
			:options="codeMirrorVo.cmOptions"
			border
			ref="cmRef"
			@change="onChange"
			@input="onInput"
			@ready="onReady"
	>
	</Codemirror>

</template>
