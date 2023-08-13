<script lang="ts" setup>
import {CertificateForm, CertificateVo} from "@/api/certificate/types";
import $certificate from "@/api/certificate";

/**
 * 挂载时调用此方法
 */
onMounted(() => {
  httpFindAll();
});
const certificateList = ref<CertificateVo[]>();
const search = ref("");
const loading = ref(false);
const ids = ref<number[]>([]);
const dataFormRef = ref(ElForm);
const dialog = reactive<DialogOption>({
  visible: false,
});
const formData = reactive<CertificateForm>({
  repositoryType: 1,
});
const rules = reactive({
  name: [{required: true, message: "名称不能为空", trigger: "blur"}],
  repositoryType: [{required: true, message: "仓库类型不能为空", trigger: "blur"}],
  description: [{required: false}],
  username: [{required: true, message: "用户名不能为空", trigger: "blur"}],
  accessToken: [{required: true, message: "认证秘钥不能为空", trigger: "blur"}],
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
        $certificate.edit(formData)
            .then(() => {
              ElMessage.success("修改凭证成功");
              closeDialog();
              httpFindAll();

            })
            .finally(() => (loading.value = false));
      } else {
        $certificate.add(formData)
            .then(() => {
              ElMessage.success("新增凭证成功");
              closeDialog();
              httpFindAll();
            })
            .finally(() => (loading.value = false));
      }
    }
  });
}, 3000);


/**
 * 获取所有凭证信息
 */
function httpFindAll() {
  loading.value = true;
  $certificate.findAll().then(({data}) => {
    certificateList.value = data.map((item) => ({
      id: item.id,
      type: item.type,
      typeShow: getTypeShow({type: item.type}),
      name: item.name,
      description: item.description,
      username: item.username,
      accessToken: item.accessToken,
      repositoryType: item.repositoryType,
      repositoryTypeShow: getRepositoryTypeShow({repositoryType: item.repositoryType})
    }))
  }).finally(() => {
        loading.value = false;
      }
  );
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

/**
 * 打开凭证管理表单弹窗
 *
 * @param data 凭证vo
 */
function openDialog(data?: CertificateVo) {
  dialog.visible = true;
  if (data && data.id) {
    dialog.title = "修改凭证";
    formData.id = data.id
    formData.name = data.name
    formData.description = data.description
    formData.username = data.username
    formData.accessToken = data.accessToken
    formData.repositoryType = data.repositoryType
  } else {
    dialog.title = "新增凭证"
  }
}

/**
 * 删除凭证管理
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
    $certificate.deleteById(deleteIds).then(() => {
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
            v-hasPerm="['sys:dict_type:add']"
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

      <el-table :data="certificateList"
                ref="dataTableRef"
                v-loading="loading"
                highlight-current-row
                border
                @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column label="名称" prop="name"/>
        <el-table-column label="仓库类型" prop="repositoryTypeShow"/>
        <el-table-column label="描述" prop="description"/>

        <el-table-column fixed="right" label="操作" width="220">
          <template #default="scope">
            <el-button
                v-hasPerm="['sys:dict_type:edit']"
                type="primary"
                link
                size="small"
                @click.stop="openDialog(scope.row)">
              <i-ep-edit/>
              编辑
            </el-button>
            <el-button
                v-hasPerm="['sys:dict_type:delete']"
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
        <el-form-item label="类型" prop="repositoryType">
          <el-select v-model="formData.repositoryType" placeholder="请选择">
            <el-option label="github" :value="1"/>
            <el-option label="gitee" :value="2"/>
            <el-option label="gitlab" :value="3"/>
          </el-select>
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="formData.username" type="text" placeholder="请输入用户名"/>
        </el-form-item>
        <el-form-item label="授权秘钥" prop="accessToken">
          <el-input v-model="formData.accessToken" type="password" placeholder="请输入授权秘钥"/>
        </el-form-item>
        <el-form-item label="备注" prop="description">
          <el-input
              v-model="formData.description"
              type="textarea"
              placeholder="备注"
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
