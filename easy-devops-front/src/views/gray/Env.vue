<template>
    <Button type="primary" size="small" style="margin-right: 5px" @click.stop="showEnvPop">新增</Button>

    <Table border :columns="columns" :data="grayEnvList" @on-row-click="showDetail">
        <template #name="{ row }">
            <strong>{{ row.name }}</strong>
        </template>
        <template #action="{ row, index }">
            <Button type="info" size="small" style="margin-right: 5px" @click.stop="showEnvPop(row)">编辑</Button>
            <Button type="warning" size="small" @click.stop="remove(row)">删除</Button>
        </template>
    </Table>
</template>
<script>

export default {
  props: {
    grayEnvList: {
      type: Array,
      default: []
    }
  },
  data() {
    return {
      columns: [
        {
          title: '环境名',
          slot: 'name'
        },
        {
          title: '详情',
          key: 'description'
        },
        {
          title: '过期时间',
          key: 'expireTime'
        },
        {
          title: 'Action',
          slot: 'action',
          width: 150,
          align: 'center'
        }
      ],
    }
  },
  methods: {
    showEnvPop(row) {
      this.$emit('show-env-pop', row)
    },
    showDetail(row) {
      console.log(row.id)
      this.$emit('refresh-project', row.id)
    },
    remove(row) {
      this.$emit('delete-env', row.id)
    }
  }
}
</script>
