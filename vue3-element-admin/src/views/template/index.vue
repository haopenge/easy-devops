<script lang="ts" setup>
import "codemirror/mode/javascript/javascript.js"
import "codemirror/mode/dockerfile/dockerfile.js"
import "codemirror/theme/seti.css"
import Codemirror from "codemirror-editor-vue3"
import {CodeMirrorVo} from "@/api/template/types";

// ---------------------------------  codemirror	--------------------------------
const codeType = ref<string>("Xml")

// ---------------------------------  codemirror	--------------------------------
const codeMirrorVo = ref<CodeMirrorVo>({
	value: 'hello world',
	cmOptions: {
		mode: "text/x-dockerfile",
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
}


onMounted(() => {
	setTimeout(() => {
		cmRef.value?.refresh()
	}, 1000)

	setTimeout(() => {
		cmRef.value?.resize(800, 200)
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
		</el-radio-group>
	  <el-button type="primary">保存</el-button>
	</div>
	<Codemirror
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
