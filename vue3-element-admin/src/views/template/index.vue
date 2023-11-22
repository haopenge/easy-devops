<script lang="ts" setup>

import "codemirror/mode/dockerfile/dockerfile.js"
import "codemirror/mode/yaml/yaml.js"
import "codemirror/mode/properties/properties.js"
import "codemirror/mode/xml/xml.js"
import "codemirror/mode/javascript/javascript.js"
import "codemirror/mode/shell/shell.js"

import "codemirror/theme/seti.css"
import {CodeMirrorVo, TemplateForm, TemplateVo} from "@/api/template/types";

import $template from "@/api/template"


// -------------------------------  弹窗 🔽   -------------------------------------

const dataFormRef = ref(ElForm);

const loading = ref(false);

/**
 * 表单提交
 */
const handleSubmit = useThrottleFn(() => {
	const formDataVo = reactive<TemplateForm>({
		id: popVo.id,
		name: popVo.name,
		type: codeTypeValueMap.value.get(codeType.value),
		content: codeMirrorVo.value.value
	});

	console.log("data : " + JSON.stringify(formDataVo))

	dataFormRef.value.validate((valid: any) => {
		if (valid) {
			loading.value = true;

			if (popVo.id) {
				formDataVo.id = popVo.id;
				$template.edit(formDataVo)
						.then(() => {
							ElMessage.success("修改模板成功");
							closeDialog();
							httpFindAll();
						})
						.finally(() => (loading.value = false));
			} else {
				$template.add(formDataVo)
						.then(() => {
							ElMessage.success("新增模板成功");
							closeDialog();
							httpFindAll();
						})
						.finally(() => (loading.value = false));
			}
		}
	});
}, 3000);

/**
 * 关闭弹窗
 */
function closeDialog() {
	dialog.visible = false;
	resetForm();
}

/**
 * 重置表单
 */
function resetForm() {
	dataFormRef.value.resetFields();
	dataFormRef.value.clearValidate();

	codeType.value = 'Dockerfile';
	codeMirrorVo.value.value = '';

	popVo.id = undefined;
}


// ------------------------------  弹窗 🔼    -------------------------------------

// -------------------------------  模板列表  🔽--------------------------------------

// 模板列表
const templateVoList = ref<TemplateVo[]>();


/**
 * 获取模板列表
 */
function httpFindAll() {
	$template.findAll().then(({data}) => {
		console.log("data : " + JSON.stringify(data))
		templateVoList.value = data;
	}).finally(() => {

	})
}

/**
 * 类型转化
 * @param row
 */
function rowFormatter(row?: any) {
	return typeFormatter(row.type);
}

/**
 * 类型转化
 * @param row
 */
function typeFormatter(type?: number) {
	switch (type) {
		case 1:
			return 'Dockerfile';
		case  2:
			return 'Yaml';
		case 3:
			return 'Properties';
		case 4:
			return 'Xml';
		case 5:
			return 'JavaScript';
		case 6:
			return 'Json';
		case 7:
			return 'Shell';
		default:
			return 'shell';
	}
}



const dialog = reactive<DialogOption>({
	visible: false,
});

const popVo = reactive<TemplateVo>({});

const ids = ref<number[]>([]);

/**
 * 打开仓库管理表单弹窗
 *
 * @param data 仓库vo
 */
function openDialog(data?: TemplateVo) {
	if (data && data.id) {
		dialog.title = "修改项目";
		popVo.id = data.id;
		popVo.name = data.name;
		console.log("dateType : " + JSON.stringify(data.type))
		codeType.value = typeFormatter(data.type);
		console.log("codeType : " + JSON.stringify(codeType.value))
		codeMirrorVo.value.value = data.content;
	} else {
		dialog.title = "新增模板"
	}
	dialog.visible = true;
}

/**
 * 删除项目
 */
function handleDelete(id?: number) {
	const deleteIds = [id || ids.value].join(",");
	if (!deleteIds) {
		ElMessage.warning("请勾选删除项");
		return;
	}

	ElMessageBox.confirm("确认删除已选中的数据项?", "警告", {
		confirmButtonText: "确定",
		cancelButtonText: "取消",
		type: "warning",
	}).then(() => {
		$template.deleteById(deleteIds).then(() => {
			ElMessage.success("删除成功");
			httpFindAll();
		});
	});
}

// -------------------------------  模板列表  🔼  --------------------------------------


// ---------------------------------  codemirror	🔽--------------------------------

const codeTypeMap = ref(new Map<string, string>());
codeTypeMap.value.set("Dockerfile", "text/x-dockerfile");
codeTypeMap.value.set("Yaml", "text/x-yaml");
codeTypeMap.value.set("Properties", "text/x-properties");
codeTypeMap.value.set("Xml", "application/xml");
codeTypeMap.value.set("JavaScript", "text/javascript");
codeTypeMap.value.set("Json", "application/json");
codeTypeMap.value.set("Shell", "text/x-sh");

const codeTypeValueMap = ref(new Map<string, number>());
codeTypeValueMap.value.set("Dockerfile", 1);
codeTypeValueMap.value.set("Yaml", 2);
codeTypeValueMap.value.set("Properties", 3);
codeTypeValueMap.value.set("Xml", 4);
codeTypeValueMap.value.set("JavaScript", 5);
codeTypeValueMap.value.set("Json", 6);
codeTypeValueMap.value.set("Shell", 7);


const codeType = ref<string>("Dockerfile");
const codeMirrorVo = ref<CodeMirrorVo>({
	value: '',
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

/**
 * 解决弹窗中codemirror 行号与代码重叠的问题
 */
function initializeCodemirror() {
	cmRef.value?.refresh();
}

// ---------------------------------  codemirror	🔼--------------------------------


// --------------------------------- global 🔽------------------------------------

onMounted(() => {
	httpFindAll();
})

onUnmounted(() => {
	cmRef.value?.destroy()
});

// -------------------------------- global 🔼 ----------------------------------

</script>


<template>

	<div class="app-container">

		<!--	============================= 模板列表  ================================		-->
		<el-card shadow="never">
			<template #header>
				<el-button
						type="success"
						@click="openDialog()">
					<i-ep-plus/>
					新增
				</el-button>
				<el-button
						type="danger"
						:disabled="ids.length === 0"
						@click="handleDelete()">
					<i-ep-delete/>
					删除
				</el-button>
			</template>

			<el-table :data="templateVoList"
								ref="dataTableRef"
								highlight-current-row
								border
			>
				<el-table-column type="selection" width="55" align="center"/>
				<el-table-column label="名称" prop="name"/>
				<el-table-column label="类型" prop="type" :formatter="rowFormatter"/>
				<el-table-column label="更新时间" prop="updateTime"/>
				<el-table-column fixed="right" label="操作" width="220">
					<template #default="scope">
						<el-button
								type="primary"
								link
								size="small"
								@click.stop="openDialog(scope.row)">
							<i-ep-edit/>
							编辑
						</el-button>
						<el-button
								type="primary"
								link
								size="small"
								@click.stop="handleDelete(scope.row.id)">
							<i-ep-delete/>
							删除
						</el-button>

					</template>

				</el-table-column>
			</el-table>
		</el-card>

	</div>

	<!--	=============================== 模板弹窗  ====================================	-->
	<el-dialog
			v-model="dialog.visible"
			:title="dialog.title"
			width="900px"
			@opened="initializeCodemirror"
	>

		<el-form
				ref="dataFormRef"
				:model="popVo"
				:rules="rules"
				label-width="90px">

			<el-form-item label="名称" prop="name">
				<el-input v-model="popVo.name" type="text"/>
			</el-form-item>

			<el-form-item label="内容" prop="content">

				<el-radio-group v-model="codeType" size="small" @change="typeChange">
					<el-radio-button label="Dockerfile"/>
					<el-radio-button label="Yaml"/>
					<el-radio-button label="Properties"/>
					<el-radio-button label="Xml"/>
					<el-radio-button label="JavaScript"/>
					<el-radio-button label="Json"/>
					<el-radio-button label="Shell"/>
				</el-radio-group>
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
		</el-form>

		<template #footer>
			<div class="dialog-footer">
				<el-button type="primary" @click="handleSubmit">确 定</el-button>
				<el-button @click="closeDialog">取 消</el-button>
			</div>
		</template>

	</el-dialog>


</template>
