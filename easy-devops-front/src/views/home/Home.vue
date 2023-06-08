<style scoped>
.layout {
    border: 1px solid #d7dde4;
    background: #f5f7f9;
    position: relative;
    border-radius: 4px;
    overflow: hidden;
}

.layout-logo {
    width: 50px;
    height: 50px;
    top: 10px;
    float: left;
    position: relative;
    background-size: contain;
    background-repeat: no-repeat
}

.layout-footer-center {
    text-align: center;
}

.menu-item span {
    display: inline-block;
    overflow: hidden;
    width: 69px;
    text-overflow: ellipsis;
    white-space: nowrap;
    vertical-align: bottom;
    transition: width .2s ease .2s;
}

.menu-item i {
    transform: translateX(0px);
    transition: font-size .2s ease, transform .2s ease;
    vertical-align: middle;
    font-size: 16px;
}

.collapsed-menu span {
    width: 0px;
    transition: width .2s ease;
}

.collapsed-menu i {
    transform: translateX(5px);
    transition: font-size .2s ease .2s, transform .2s ease .2s;
    vertical-align: middle;
    font-size: 22px;
}

.layout-footer-center {
    text-align: center;
}

.top-menu {
    height: 60px;
    line-height: 60px;
    left: 96px;
}

</style>
<template>
    <div class="layout">
        <Layout>
            <Header>
                <div @click="collapsedSider" class="layout-logo"
                     :style="{ backgroundImage: 'url(' + require('@/assets/logo.png') + ')' }">
                </div>
                <Menu mode="horizontal" theme="dark" active-name="1" class="top-menu">
                    <template v-for="(item,index) in topMenus" :key=index>
                        <MenuItem :name="item.id">
                            <Icon :type="item.iconType"></Icon>
                            <span>{{ item.name }}</span>
                        </MenuItem>
                    </template>
                </Menu>
            </Header>
            <Layout>
                <Sider ref="side1" hide-trigger collapsible :collapsed-width="78" v-model="isCollapsed">
                    <Menu :open-names="openNames" :active-name="activeName" theme="dark" width="auto"
                          :class="menuitemClasses"
                          @on-open-change="menuOpenChange" @on-select="switchPageContent">
                        <template v-for="(item,index) in menus" :key="index">
                            <Submenu :name=item.id>
                                <template #title>
                                    <Icon type="ios-navigate"></Icon>
                                    <span>{{ item.name }}</span>
                                </template>
                                <template v-for="(cellRow, cellIndex) in item.menus" :key="cellIndex">
                                    <MenuItem :name=cellRow.id>
                                        <span> {{ cellRow.name }}</span>
                                    </MenuItem>
                                </template>
                            </Submenu>
                        </template>
                    </Menu>
                </Sider>
                <Layout style="height: 2000px;">
                    <Main v-if="activeName === 11"/>
                    <Repository v-if="activeName === 12"/>
                    <Certificate v-if="activeName === 13"/>
                    <Project v-if="activeName === 14"
                             @show-config-page="showConfigPage"
                    />
                    <ProjectConfig v-if="activeName === 15"
                    />
                    <CodeEdit v-if="activeName === 16"
                    />
                    <Terminal v-if="activeName === 17"
                    />

                </Layout>
            </Layout>
            <Footer class="layout-footer-center">2011-2016 &copy;xiaoyuxxx</Footer>
        </Layout>

    </div>
</template>
<script>

import Main from '@/views/gray/Gray.vue'
import { Content } from 'view-ui-plus'
import Repository from '@/views/repository/Repository.vue'
import Certificate from '@/views/certificate/Certificate.vue'
import Project from '@/views/project/Project.vue'
import ProjectConfig from '@/views/project/ProjectConfig.vue'
import CodeEdit from '@/views/edit/CodeEdit.vue'
import Terminal from '@/views/terminal/Terminal.vue'

export default {
  components: {
      Terminal,
    CodeEdit,
    ProjectConfig,
    Content,
    Main,
    Repository,
    Certificate,
    Project
  },
  data() {
    return {
      isCollapsed: false,
      menus: [
        {
          id: 1,
          name: '版本管理',
          menus: [
            {
              id: 11,
              name: '灰度管理',
            },
            {
              id: 12,
              name: '仓库管理',
            },
            {
              id: 13,
              name: '凭证管理',
            },
            {
              id: 14,
              name: '项目管理',
            },
            {
              id: 15,
              name: '配置管理',
            },
            {
              id: 16,
              name: '代码编辑',
            },
            {
              id: 17,
              name: '终端'
            }
          ]
        },
      ],
      topMenus: [
        {
          id: 1,
          iconType: 'ios-analytics',
          name: '简单',
        },
        {
          id: 2,
          iconType: 'ios-paper',
          name: '易用',
        }
      ],
      openNames: [
        1
      ],
      activeName: 13
    }
  },
  computed: {
    menuitemClasses: function () {
      return [
        'menu-item',
        this.isCollapsed ? 'collapsed-menu' : ''
      ]
    }
  },
  methods: {
    collapsedSider() {
      this.$refs.side1.toggleCollapse()
      console.log('开关状态 ： ' + this.isCollapsed)

    },
    switchPageContent(id) {
      console.log(id)
      this.activeName = id
    },
    menuOpenChange(name) {
      console.log('menuOpenChange name = ' + name)
    },
    showConfigPage(project) {
      this.activeName = 15
    }
  }
}
</script>

