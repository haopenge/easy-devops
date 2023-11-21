<template>

	<div class="app-container">
		<div class="common-layout">
			<el-container>
				<el-aside width="150px" style="margin-top: 30px; margin-left: 20px">
					<el-steps :active="active" finish-status="success" direction="vertical" space="100px">
						<template v-for="loop in activities">
							<el-step :title="loop.title" @onclick="stepClick"/>
						</template>
					</el-steps>
				</el-aside>
				<el-main>
					<el-form
							:rules="rules"
							ref="dataFormRef"
							label-width="80px">
						<el-form-item label="名称:" prop="name">
							<el-input v-model="newStepName" type="text" placeholder="代码检出"/>
						</el-form-item>
						<el-form-item label="脚本:" prop="script">
							<Codemirror
									width="800"
									height="500"
									v-model:value="codeMirrorVo.value"
									:options="codeMirrorVo.cmOptions"
									border
									ref="cmRef"
									@change="onChange"
									@input="onInput"
									@ready="onReady"
							>
							</Codemirror>
						</el-form-item>
						<div style="margin-left: 81px">
							<div style="margin-bottom: 10px">
								<el-button type="primary" plain @click="next" style="width: 380px">新增检出</el-button>
								<el-button type="success" plain @click="next" style="width: 380px">新增脚本</el-button>
							</div>
							<el-button type="primary" @click="next" style="width: 780px">保存</el-button>
						</div>

					</el-form>
				</el-main>
			</el-container>
		</div>

	</div>
</template>

<script lang="ts" setup>
import {BuildStepVo} from "@/api/config/types";
import "codemirror/mode/shell/shell.js"

import "codemirror/theme/seti.css"
import Codemirror from "codemirror-editor-vue3"
import {CodeMirrorVo} from "@/api/template/types";

const active = ref(2)
const newStepName = ref<string>();

const activities = ref<BuildStepVo[]>([
	{
		id: 1,
		title: "代码检出"
	},
	{
		id: 2,
		title: "项目构建"
	}
]);
const next = () => {
	if (activities.value && activities.value.length > 0) {
		activities.value.push({
			id: 1,
			title: newStepName.value
		})
	}
}

const stepClick = () => {
	console.log('stepClick = ')
}


const rules = reactive({
	name: [{required: true, message: "名称不能为空", trigger: "blur"}],
	script: [{required: true, message: "脚本不能为空", trigger: "blur"}],
});


// ---------------------------------  codemirror	--------------------------------

// ---------------------------------  codemirror	--------------------------------
const codeMirrorVo = ref<CodeMirrorVo>({
	value: '#!/bin/bash \nhello world',
	cmOptions: {
		// mode: "text/x-dockerfile",
		mode: "text/x-sh",
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


onMounted(() => {

})

onUnmounted(() => {
	cmRef.value?.destroy()
});


</script>
