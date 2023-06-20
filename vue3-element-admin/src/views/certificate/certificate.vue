<template>
	<el-table :data="certificateList" style="width: 100%">
		<el-table-column label="名称" prop="name"/>
		<el-table-column label="类型" prop="typeShow"/>
		<el-table-column label="仓库类型" prop="repositoryTypeShow"/>
		<el-table-column label="描述" prop="description"/>

		<el-table-column align="right">
			<template #header>
				<el-input v-model="search" size="small" placeholder="Type to search"/>
			</template>
			<template #default="scope">
				<el-button size="small" @click="handleEdit(scope.$index, scope.row)"
				>Edit
				</el-button
				>
				<el-button
						size="small"
						type="danger"
						@click="handleDelete(scope.$index, scope.row)"
				>Delete
				</el-button
				>
			</template>
		</el-table-column>
	</el-table>
</template>

<script lang="ts" setup>
import {findAll} from "@/api/certificate";
import {CertificateVo} from "@/api/certificate/types";

const certificateList = ref<CertificateVo[]>();

function httpFindAll() {
	findAll().then(({data}) => {
		certificateList.value = data.map((item) => ({
			id: item.id,
			type: item.type,
			typeShow: getTypeShow({type: item.type}),
			name: item.name,
			description: item.description,
			username: item.username,
			repositoryType: item.repositoryType,
			repositoryTypeShow: getRepositoryTypeShow({repositoryType: item.repositoryType})
		}))
	});
}

/**
 * 获取仓库类型对应的展示信息
 * @param repositoryType 仓库类型
 * @returns {string} 展示信息
 */
function getRepositoryTypeShow({repositoryType}: { repositoryType: any }) {
	if (repositoryType === 1) {
		return 'github'
	} else if (repositoryType === 2) {
		return 'gitee'
	} else if (repositoryType === 3) {
		return 'gitlab'
	} else {
		return ''
	}
}

/**
 * 获取对应的type展示信息
 * @param type type
 * @returns {string} type描述
 */
function getTypeShow({type}: { type: any }) {
	if (type === 1) {
		return '全局ssh'
	} else if (type === 3) {
		return 'k8s配置'
	} else if (type === 5) {
		return '仓库凭证'
	} else {
		return ''
	}
}

onMounted(() => {
	httpFindAll();
});

const search = ref("");

const handleEdit = (index: number, row: CertificateVo) => {
	console.log(index, row);
};
const handleDelete = (index: number, row: CertificateVo) => {
	console.log(index, row);
};
</script>
