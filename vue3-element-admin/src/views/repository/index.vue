<script lang="ts" setup>
import {GitRepositoryVo, RepositoryFormData, RepositoryPopVo, RepositoryVo} from "@/api/repository/types";
import $repository from "@/api/repository";
import $certificate from "@/api/certificate";
import {CertificateVo} from "@/api/certificate/types";


const repositoryList = ref<RepositoryVo[]>();
const certificateList = ref<CertificateVo[]>();
const certificateMap = ref(new Map<any, CertificateVo>());
const gitRepositoryMap = ref(new Map<number, GitRepositoryVo[]>());

const loading = ref(false);
const ids = ref<number[]>([]);
const dataFormRef = ref(ElForm);
const dialog = reactive<DialogOption>({
  visible: false,
});
const popVo = reactive<RepositoryPopVo>({});
const rules = reactive({
  name: [{required: true, message: "名称不能为空", trigger: "blur"}],
  easyCertificateId: [{required: true, message: "凭证不能为空", trigger: "blur"}],
  branch: [{required: true, message: "默认分支不能为空", trigger: "blur"}],

});


const state = ref('')
const links = ref<GitRepositoryVo[]>([])

const querySearch = (queryString: string, cb: any) => {
  const results = queryString
      ? links.value.filter(createFilter(queryString))
      : links.value
  // call callback function to return suggestion objects
  cb(results)
}
const createFilter = (queryString: string) => {
  return (restaurant: any) => {
    return (
        restaurant.name.toLowerCase().indexOf(queryString.toLowerCase()) === 0
    )
  }
}

const repositorySelect = (item: GitRepositoryVo) => {
  popVo.name = item.name
  popVo.branch = item.defaultBranch
  popVo.description = item.description
}

/**
 * 挂载时调用此方法
 */
onMounted(() => {
  httpFindAll();
  httpFindCertificateAll();
});

/**
 * 凭证变更
 * @param certificateId 凭证id
 */
function certificateChange(certificateId: number) {
  console.log("certificateChange id = " + certificateId)
  const certificateVo = certificateMap.value.get(certificateId);
  if (certificateVo) {
    popVo.easyCertificateId = certificateId
  }

  let gitRepositoryArray = gitRepositoryMap.value.get(certificateId)
  if (gitRepositoryArray) {
    popVo.name = ""
    popVo.branch = ""
    links.value = gitRepositoryArray
  }
}

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
  const formDataVo = reactive<RepositoryFormData>({
    name: popVo.name,
    easyCertificateId: popVo.easyCertificateId
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
 * 获取所有仓库信息
 */
function httpFindAll() {
  loading.value = true;
  $repository.findAll().then(({data}) => {
    repositoryList.value = data
  }).finally(() => {
        loading.value = false;
      }
  );
}

/**
 * 获取所有凭证信息
 */
function httpFindCertificateAll() {
  $certificate.findAll().then(({data}) => {
    for (let i = 0; i < data.length; i++) {
      var loopData = data[i];
      certificateMap.value.set(loopData.id, loopData)
      let linkSetSuccess = false
      data.forEach(loopData => {
        let certificateId = Number(loopData.id);
        if (certificateId === 3) {
          return
        }
        $repository.findGitRepositories(certificateId).then(
            ({data}) => {
              gitRepositoryMap.value.set(certificateId, data)
              if (!linkSetSuccess) {
                links.value = data
                linkSetSuccess = true
              }
            }
        )
      })
    }
    certificateList.value = data
  })
}

/**
 * 打开仓库管理表单弹窗
 *
 * @param data 仓库vo
 */
function openDialog(data?: RepositoryVo) {
  dialog.visible = true;
  if (data && data.id) {
    dialog.title = "修改仓库";
    popVo.id = data.id
    popVo.easyCertificateId = data.easyCertificateId
    popVo.name = data.name
    popVo.branch = data.branch
    popVo.description = data.description
  } else {
    dialog.title = "新增仓库"
  }
}

/**
 * 删除仓库管理
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
    $repository.deleteById(deleteIds).then(() => {
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

      <el-table :data="repositoryList"
                ref="dataTableRef"
                v-loading="loading"
                highlight-current-row
                border
                @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column label="名称" prop="name"/>
        <el-table-column label="描述" prop="description"/>
        <el-table-column label="仓库名" prop="easyCertificateName"/>
        <el-table-column label="分支" prop="branch"/>

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
          :model="popVo"
          :rules="rules"
          label-width="80px">

        <el-form-item label="凭证" prop="easyCertificateId">
          <el-select v-model="popVo.easyCertificateId" placeholder="请选择" @change="certificateChange">
            <template v-for="(item,index) in certificateList" key="index">
              <el-option :label="item.name" :value="item.id"/>
            </template>
          </el-select>
        </el-form-item>

        <el-form-item label="名称" prop="name">
          <el-autocomplete
              v-model="popVo.name"
              :fetch-suggestions="querySearch"
              popper-class="my-autocomplete"
              placeholder="请输入仓库名"
              @select="repositorySelect"
          >
            <template #default="{ item }">
              <div class="value">{{ item.name }}</div>
              <span class="link">{{ item.httpCloneUrl }}</span>
            </template>
          </el-autocomplete>
        </el-form-item>

        <el-form-item label="默认分支" prop="branch">
          <el-input v-model="popVo.branch" type="text" disabled/>
        </el-form-item>

        <el-form-item label="描述" prop="description">
          <el-input
              v-model="popVo.description"
              type="textarea"
              placeholder="描述"
              disabled
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
