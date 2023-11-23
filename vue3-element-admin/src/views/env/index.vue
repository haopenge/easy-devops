<script lang="ts" setup>
import {EnvVo, EnvForm} from "@/api/env/types";
import $env from "@/api/env";

/**
 * 挂载时调用此方法
 */
onMounted(() => {
	httpFindAll();
});
const envList = ref<EnvVo[]>();
const search = ref("");
const loading = ref(false);
const ids = ref<number[]>([]);
const dataFormRef = ref(ElForm);
const dialog = reactive<DialogOption>({
	visible: false,
});
const formData = reactive<EnvForm>({});
const rules = reactive({
	name: [{required: true, message: "名称不能为空", trigger: "blur"}],
	description: [{required: false}],
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
	dataFormRef.value.validate((valid: any) => {
		if (valid) {
			const id = formData.id;
			loading.value = true;
			if (id) {
				$env.edit(formData)
						.then(() => {
							ElMessage.success("修改环境成功");
							closeDialog();
							httpFindAll();

						})
						.finally(() => (loading.value = false));
			} else {
				$env.add(formData)
						.then(() => {
							ElMessage.success("新增环境成功");
							closeDialog();
							httpFindAll();
						})
						.finally(() => (loading.value = false));
			}
		}
	});
}, 3000);


/**
 * 获取所有环境信息
 */
function httpFindAll() {
	loading.value = true;
	$env.findAll().then(({data}) => {
		envList.value = data.map((item) => ({
			id: item.id,
			name: item.name,
			description: item.description,
			expireTime: item.expireTime,
			createTime: item.createTime,
			updateTime: item.updateTime
		}))
	}).finally(() => {
				loading.value = false;
			}
	);
}


/**
 * 打开环境管理表单弹窗
 *
 * @param data 环境vo
 */
function openDialog(data?: EnvVo) {
	dialog.visible = true;
	if (data && data.id) {
		dialog.title = "修改环境";
		formData.id = data.id
		formData.name = data.name
		formData.description = data.description
		formData.expireTime = data.expireTime
	} else {
		dialog.title = "新增环境"
	}
}

/**
 * 删除环境管理
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
		$env.deleteById(deleteIds).then(() => {
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

	formData.id = undefined;
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

			<el-table :data="envList"
								ref="dataTableRef"
								v-loading="loading"
								highlight-current-row
								border
								@selection-change="handleSelectionChange"
			>
				<el-table-column type="selection" width="55" align="center"/>
				<el-table-column label="名称" prop="name"/>
				<el-table-column label="过期时间" prop="expireTime"/>
				<el-table-column label="描述" prop="description"/>

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
					:model="formData"
					:rules="rules"
					label-width="80px">
				<el-form-item label="名称" prop="name">
					<el-input v-model="formData.name" type="text" placeholder="请输入名称"/>
				</el-form-item>

				<el-form-item label="过期时间" prop="expireTime">
					<el-date-picker
							v-model="formData.expireTime"
							type="datetime"
							placeholder="请选择过期时间"
							format="YYYY-MM-DD HH:mm:ss"
							value-format="YYYY-MM-DD H:m:s"
					/>
				</el-form-item>
				<el-form-item label="描述" prop="description">
					<el-input
							v-model="formData.description"
							type="textarea"
							placeholder="描述"
							:autosize="{ minRows: 2, maxRows: 4 }"
					/>
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
