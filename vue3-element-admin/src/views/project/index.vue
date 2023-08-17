<script lang="ts" setup>

import {RepositoryPopVo, RepositoryVo} from "@/api/repository/types";
import $repository from "@/api/repository";
import $project from "@/api/project";
import {ProjectVo, ProjectForm, ProjectPopVo} from "@/api/project/types";
import Repository from "@/api/repository";

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
	// call callback function to return suggestion objects
	cb(results)
}

const repositorySelect = (item: RepositoryVo) => {
	popVo.easyRepositoryName = item.name
	popVo.easyRepositoryId = item.id


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
			if (id) {
				formDataVo.id = popVo.id;
				$repository.edit(formDataVo)
						.then(() => {
							ElMessage.success("修改仓库成功");
							closeDialog();
							httpFindAll();

						})
						.finally(() => (loading.value = false));
			} else {
				$repository.add(formDataVo)
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

</script>

<template>
	<div class="app-container">
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
								@click.stop="handleDelete(scope.row.id)">
							<i-ep-delete/>
							删除
						</el-button>
					</template>

				</el-table-column>
			</el-table>
		</el-card>

		<el-dialog
				v-model="dialog.visible"
				:title="dialog.title"
				width="500px"
				@close="closeDialog">
			<el-form
					ref="dataFormRef"
					:model="popVo"
					:rules="rules"
					label-width="80px">

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
					<el-input v-model="popVo.branch" type="text"/>
				</el-form-item>
			</el-form>
			<template #footer>
				<div class="dialog-footer">
					<el-button type="primary" @click="handleSubmit">确 定</el-button>
					<el-button @click="closeDialog">取 消</el-button>
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

.my-autocomplete li .name {
	text-overflow: ellipsis;
	overflow: hidden;
}

.my-autocomplete li .addr {
	font-size: 12px;
	color: #b4b4b4;
}

.my-autocomplete li .highlighted .addr {
	color: #ddd;
}
</style>
