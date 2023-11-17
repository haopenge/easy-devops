<template>
	<div class="app-container">
		<el-container>
			<el-header>
				<el-steps :active="active" finish-status="success">
					<template v-for="loop in activities">
						<el-step :title="loop.title" @onclick="stepClick"/>
					</template>
				</el-steps>
			</el-header>

			<el-main>
				<el-form
						ref="dataFormRef"
						label-width="80px">
					<el-form-item label="名称:" prop="branch">
						<el-input v-model="newStepName" type="text"/>
					</el-form-item>
					<el-form-item label="脚本:" prop="branch">
						<div>
							<el-radio-group v-model="codeType" size="small" @change="typeChange">
								<el-radio-button label="Dockerfile"/>
								<el-radio-button label="Yaml"/>
								<el-radio-button label="Properties"/>
								<el-radio-button label="Xml"/>
							</el-radio-group>
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
					</el-form-item>
					<el-button style="margin-top: 12px" @click="next">新增</el-button>

				</el-form>

			</el-main>
		</el-container>
	</div>
</template>

<script lang="ts" setup>
import {BuildStepVo} from "@/api/config/types";
import {CodeMirrorVo} from "@/api/template/types";

const active = ref(2)
const newStepName = ref<string>();

const activities = ref<BuildStepVo[]>([
	{
		id: 1,
		title: "代码检出"
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
