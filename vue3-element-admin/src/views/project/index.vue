<script lang="ts" setup>

import {RepositoryVo} from "@/api/repository/types";
import $repository from "@/api/repository";
import $project from "@/api/project";
import {ProjectVo, ProjectForm, ProjectPopVo, ProjectConfigVo, ProjectTemplateOption} from "@/api/project/types";
import {CodeMirrorVo} from "@/api/template/types";

import $template from "@/api/template";

import Codemirror from "codemirror-editor-vue3"
import "codemirror/mode/shell/shell.js"
import "codemirror/theme/seti.css"


const projectVoList = ref<ProjectVo[]>();
const loading = ref(false);
const ids = ref<number[]>([]);
const dataFormRef = ref(ElForm);
const dialog = reactive<DialogOption>({
	visible: false,
});
const popVo = reactive<ProjectPopVo>({});

const rules = reactive({
	name: [{required: true, message: "名称不能为空", trigger: "blur"}],
	easyRepositoryName: [{required: true, message: "仓库名不能为空", trigger: "blur"}],
	branch: [{required: true, message: "分支不能为空", trigger: "blur"}],
});


const links = ref<RepositoryVo[]>([])

const querySearch = (queryString: string, cb: any) => {
	const results = queryString
			? links.value.filter(link => {
				var linkName = link.name;
				if (linkName) {
					return linkName.toLowerCase().indexOf(queryString.toLowerCase()) === 0
				} else {
					return ""
				}
			})
			: links.value
	cb(results)
}

const repositorySelect = (item: RepositoryVo) => {
	popVo.easyRepositoryName = item.name
	popVo.easyRepositoryId = item.id

	// 更新仓库分支列表
	$project.findBranches(popVo.easyRepositoryId).then(({data}) => {
		branchLinks.value = data
	}).finally(() => {

	})
}


const branchLinks = ref<string[]>([])
const branchQuerySearch = (queryString: string, cb: any) => {
	const results = queryString
			? branchLinks.value.filter(link => {
				var linkName = link;
				if (linkName) {
					return linkName.toLowerCase().indexOf(queryString.toLowerCase()) === 0
				} else {
					return ""
				}
			})
			: branchLinks.value
	cb(results)
}

const branchSelect = (item: string) => {
	popVo.branch = item
}

/**
 * 挂载时调用此方法
 */
onMounted(() => {
	httpFindAll();
	httpFindRepositoryAll();
});

/**
 * 行checkbox change事件
 */
function handleSelectionChange(selection: any) {
	ids.value = selection.map((item: any) => item.id);
}

/**
 * 表单提交
 */
const handleSubmit = useThrottleFn(() => {
	const formDataVo = reactive<ProjectForm>({
		name: popVo.name,
	});

	dataFormRef.value.validate((valid: any) => {
		if (valid) {
			const id = popVo.id;
			loading.value = true;
			formDataVo.name = popVo.name;
			formDataVo.branch = popVo.branch;
			formDataVo.easyRepositoryId = popVo.easyRepositoryId;

			if (id) {
				formDataVo.id = popVo.id;
				$project.edit(formDataVo)
						.then(() => {
							ElMessage.success("修改仓库成功");
							closeDialog();
							httpFindAll();
						})
						.finally(() => (loading.value = false));
			} else {
				$project.add(formDataVo)
						.then(() => {
							ElMessage.success("新增仓库成功");
							closeDialog();
							httpFindAll();
						})
						.finally(() => (loading.value = false));
			}
		}
	});
}, 3000);

/**
 * 获取项目列表
 */
function httpFindAll() {
	loading.value = true;
	$project.findAll().then(({data}) => {
		projectVoList.value = data
	}).finally(() => {
				loading.value = false;
			}
	);
}

/**
 * 获取仓库列表
 */
function httpFindRepositoryAll() {
	$repository.findAll().then(({data}) => {
		links.value = data
	}).finally(() => {
		loading.value = false;
	})
}

/**
 * 打开仓库管理表单弹窗
 *
 * @param data 仓库vo
 */
function openDialog(data?: ProjectVo) {
	dialog.visible = true;
	if (data && data.id) {
		dialog.title = "修改项目";
		popVo.id = data.id
		popVo.name = data.name
		popVo.branch = data.branch
		popVo.easyRepositoryName = data.easyRepositoryName
	} else {
		dialog.title = "新增项目"
	}
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
		$project.deleteById(deleteIds).then(() => {
			ElMessage.success("删除成功");
			httpFindAll();
		});
	});
}

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

	popVo.id = undefined;
}

// =========================== 配置弹窗 =======================
const configDialogPop = reactive<DialogOption>({
	visible: false,
});

/**
 * 打开仓库管理表单弹窗
 *
 * @param data 仓库vo
 */
function openConfigDialog(data?: ProjectVo) {
	findTemplateList();
	findConfigById(data?.id);

	configDialogPop.visible = true;
	configDialogPop.title = "配置项目";
	popVo.id = data?.id
	popVo.name = data?.name
}

// 模板
const templates = ref<number[]>([]);
const templateList = ref<ProjectTemplateOption[]>([]);

/**
 * 获取项目配置
 */
function findConfigById(id?: number) {
	$project.findConfigById(id).then(({data}) => {
		if (data.easyTemplateIds != "0") {
			templates.value = data.easyTemplateIds.split(",").map(Number);
		}
		if (data.dockerScript != "") {
			dockerScriptVo.value.value = data.dockerScript;
		}
		if (data.pushScript != "") {
			pushScriptVo.value.value = data.pushScript;
		}
	}).finally(() => {

	});
}

/**
 * 获取模板列表
 */
function findTemplateList() {
	if (templateList.value.length > 0) {
		return;
	}
	$template.findAll().then(({data}) => {
		data.forEach(loopTemplate => {
			templateList.value?.push({
				id: loopTemplate.id,
				name: loopTemplate.name,
				type: typeFormatter(loopTemplate.type)
			});
		})
	}).finally(() => {

	});
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


/**
 * 项目配置表单提交
 */
const handleConfigSubmit = useThrottleFn(() => {
	$project.config({
		id: popVo.id,
		easyTemplateIds: templates.value.join(","),
		dockerScript: dockerScriptVo.value.value,
		pushScript: pushScriptVo.value.value
	}).then((data => {
		console.log("response data = " + JSON.stringify(data));
		ElMessage.success("配置成功");
		closeConfigDialog();
	})).finally(

	)
}, 3000);


/**
 * 关闭配置弹窗
 */
function closeConfigDialog() {
	configDialogPop.visible = false;
	configResetForm();
}


const configDataFormRef = ref(ElForm);

/**
 * 重置表单
 */
function configResetForm() {
	configDataFormRef.value.resetFields();
	configDataFormRef.value.clearValidate();
	popVo.id = undefined;

	templates.value = [];
	dockerScriptVo.value.value = '#!/bin/bash \n';
	pushScriptVo.value.value = '#!/bin/bash \n';

}


// ---------------------------------  codemirror	--------------------------------
const dockerScriptVo = ref<CodeMirrorVo>({
	value: '#!/bin/bash',
	cmOptions: {
		mode: "text/x-sh",
		theme: "seti"
	}
})
const dockerScriptRef = ref()

const pushScriptVo = ref<CodeMirrorVo>({
	value: '#!/bin/bash',
	cmOptions: {
		mode: "text/x-sh",
		theme: "seti"
	}
})
const pushScriptRef = ref()

/**
 * 解决弹窗中codemirror 行号与代码重叠的问题
 */
function initializeCodemirror() {
	dockerScriptRef.value?.refresh();
	pushScriptRef.value?.refresh();
}

</script>

<template>
	<div class="app-container">

		<!--	    ========================= 项目列表 ===================================	-->
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

			<el-table :data="projectVoList"
								ref="dataTableRef"
								v-loading="loading"
								highlight-current-row
								border
								@selection-change="handleSelectionChange"
			>
				<el-table-column type="selection" width="55" align="center"/>
				<el-table-column label="名称" prop="name"/>
				<el-table-column label="仓库名称" prop="easyRepositoryName"/>
				<el-table-column label="凭证名称" prop="easyCertificateName"/>
				<el-table-column label="分支" prop="branch"/>
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
								@click.stop="openConfigDialog(scope.row)">
							<i-ep-setting/>
							配置
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

		<!--			========================== 项目弹窗 ================================== -->

		<el-dialog
				v-model="dialog.visible"
				:title="dialog.title"
				width="500px"
				@close="closeDialog">
			<el-form
					ref="dataFormRef"
					:model="popVo"
					:rules="rules"
					label-width="90px">

				<el-form-item label="名称" prop="name">
					<el-input v-model="popVo.name" type="text"/>
				</el-form-item>

				<el-form-item label="仓库" prop="easyRepositoryName">
					<el-autocomplete
							v-model="popVo.easyRepositoryName"
							:fetch-suggestions="querySearch"
							popper-class="my-autocomplete"
							placeholder="请输入仓库名"
							@select="repositorySelect"
					>
						<template #default="{ item }">
							<div class="value">{{ item.name }}</div>
							<span class="link">{{ item.cloneUrl }}</span>
						</template>
					</el-autocomplete>
				</el-form-item>

				<el-form-item label="分支" prop="branch">
					<el-autocomplete
							v-model="popVo.branch"
							:fetch-suggestions="branchQuerySearch"
							placeholder="请输入分支名"
							@select="branchSelect"
					>
						<template #default="{ item }">
							<div class="value">{{ item }}</div>
						</template>
					</el-autocomplete>
				</el-form-item>
			</el-form>
			<template #footer>
				<div class="dialog-footer">
					<el-button type="primary" @click="handleSubmit">确 定</el-button>
					<el-button @click="closeDialog">取 消</el-button>
				</div>
			</template>
		</el-dialog>


		<!--			========================== 配置弹窗 ================================== -->
		<el-dialog
				v-model="configDialogPop.visible"
				:title="configDialogPop.title"
				width="900px"
				@opened="initializeCodemirror"
		>
			<el-form
					ref="configDataFormRef"
					:model="popVo">
				<el-form-item label="项目名称" prop="name">
					<el-input disabled v-model="popVo.name" type="text"/>
				</el-form-item>
				<el-form-item label="模板挂载" prop="name">
					<el-select
							v-model="templates"
							multiple
							collapse-tags
							collapse-tags-tooltip
							:max-collapse-tags="6"
							placeholder="请选择需要挂载的模板。 。 。"
							style="width: 800px"
					>
						<el-option
								v-for="item in templateList"
								:key="item.type"
								:label="item.name"
								:value="item.id"
						>
							<span style="float: left">{{ item.name }}</span>
							<span style="float: right;color: var(--el-text-color-secondary);font-size: 13px;">{{ item.type }}</span>
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="构建脚本" prop="dockerScript">
					<Codemirror
							style="line-height: 1.5"
							width="800"
							height="300"
							border
							ref="dockerScriptRef"
							v-model:value="dockerScriptVo.value"
							:options="dockerScriptVo.cmOptions"
					/>
				</el-form-item>
				<el-form-item label="发布脚本" prop="pushScript">
					<Codemirror
							style="line-height: 1.5"
							width="800"
							height="300"
							border
							ref="pushScriptRef"
							v-model:value="pushScriptVo.value"
							:options="pushScriptVo.cmOptions"
					/>
				</el-form-item>
			</el-form>
			<template #footer>
				<div class="dialog-footer">
					<el-button type="primary" @click="handleConfigSubmit(popVo)">确 定</el-button>
					<el-button @click="closeConfigDialog">取 消</el-button>
				</div>
			</template>
		</el-dialog>
	</div>
</template>

<style>
.my-autocomplete li {
	line-height: normal;
	padding: 7px;
}
</style>
