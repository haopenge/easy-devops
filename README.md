
![](doc/img/log.gif)

# 1. 介绍

此项目是一款通用的灰度发布服务组件；
![](doc/img/img_1.png)

## 1.1 可以用于解决的问题

### 案例1：

开发过程中，往往多个任务并行，并且一个项目可能由多人进行维护；

如果同时部署到QA环境，不仅会产生代码冲突、还有可能因为业务的关联性，导致与预想结果不符合；

注：核心解决的问题是这个，往大了搞，可以整一个灰度环境，用于灰度测试

### 案例2：

系统由多个版本，例如：公测版、内测版、开发版、稳定版；

他们之间的差异可能很小，可能仅有1个服务不一样，那么我们没有必要每个版本都整一套全服务的环境；那样成本过高；

灰度环境即可解决这个问题，根据修复的内容，新增相应的业务服务即可，携带业务环境变量那就走业务服务，不携带那就走公共的；

# 2. 快速开始

## 2.1 环境准备

-   nacos-server

环境搭建可参见：[docker 部署nacos](https://github.com/haopenge/interview/blob/master/docker/nacos-docker-2.0.2/README_ZH.md)

-   nacos配置---新增命名空间

新增命名空间：qa ,记录命名空间id

-   nacos配置---新增commom.yaml 配置

```
#开放灰度的环境
gray:
    enable: true
    env_list: qa,online

#灰度环境
log:
    level: error
#rocket-mq ip
rocket-mq:
    ip: 12138
```

-   host更改

本地host新增一行：(ip 根据实际nacos 地址更改)

```
# easy-gray nacos定义
127.0.0.1	nacos.easy.cn
```

## 2.2 项目配置更改

本服务jar包目前仓库地址配置如下：

```
<repositories>
    <repository>
        <id>rdc-snapshots</id>
        <url>https://repo.rdc.aliyun.com/repository/139501-snapshot-yAmm21/</url>
    </repository>
</repositories>
```

spring-cloud-gateway api网关中引入如下 jar:

```
<dependency>
    <groupId>com.easy</groupId>
    <artifactId>easy-gray-gateway</artifactId>
    <version>0.0.3-SNAPSHOT</version>
    
</dependency>
```

参见演示项目`easy-gray-gateway-api`

spring-boot-web 类服务中引入如下jar:

```
<dependency>
    <groupId>com.easy</groupId>
    <artifactId>easy-gray-core</artifactId>
    <version>0.0.19-SNAPSHOT</version>
</dependency>
```

参见演示项目： `easy-gray-consumer`、`easy-gray-provider-one`、`easy-gray-provider-two` ;

## 2.3 示例项目运行

同时启动`easy-gray-consumer`、`easy-gray-provider-one`、`easy-gray-provider-two`项目；

携带环境变量访问：

```
curl --request GET \
  --url http://localhost:10080/eat/apple \
  --header 'content-type: multipart/form-data'

# 结果
provider:  我吃了 苹果 on 10001
provider:  我吃了 苹果 on 10001
provider:  我吃了 苹果 on 10001
```

不携带环境变量访问：

```
curl --request GET \
  --url http://localhost:10080/eat/apple \
  --header 'content-type: multipart/form-data' \
  --header 'pod_env: EASY-12138'
  
# 结果
provider:  我吃了 苹果 on 10001
provider:  我吃了 苹果 on 10002
provider:  我吃了 苹果 on 10001
provider:  我吃了 苹果 on 10002
provider:  我吃了 苹果 on 10001
```

# 3. 资源文档

## 3.1 资源

[**gray-api 数据库表结构sql**](doc/%E5%AE%9E%E7%8E%B0%E5%8E%9F%E7%90%86-eureka-ribbon%E7%89%88.md)

## 3.2 文档

[**admin管理后台接口文档**](https://console-docs.apipost.cn/preview/a02138bb162545c3/d3b1ac7dc543eacf)

[**实现原理-eureka-ribbon版**](doc/sql/easy_admin.sql)

# 4. mr规范

commit格式：`type: ${decription}`

```
#示例
feat: 初始化项目
```

## type类型

-   build：影响生成系统或外部依赖性的更改
-   ci: 更改 CI 配置文件和脚本
-   feat: 新功能（feature）
-   fix: 修补 bug
-   perf: 提高性能的代码更改
-   docs: 文档（documentation）
-   style: 不影响代码含义的更改（不影响代码运行的变动）
-   refactor: 代码修改既不修复错误，也不添加特征（即不是新增功能，也不是修改 bug 的代码变动）
-   test: 添加缺失测试或纠正现有测试
-   revert: 撤回

# 5. 版本功能

## 5.1 版本号对应关系

| 本项目版本        | spring-cloud版本 | spring-boot版本 |
| ------------ | -------------- | ------------- |
| 0.0.1~0.3.0 | 3.1.1          | 2.6.3         |

## 5.2 计划上线：

| 功能                                 |
| ---------------------------------- |
| -   【灰度管理api服务】补充动态获取k8s灰度服务运行状态接口 |
| -   【文档补充】提供nacos、k8s非敏感配置         |
| -   【灰度管理前端服务】新增灰度管理前端页面           |

## 5.3 历史版本

-   0.3.0 新增功能

| 功能                                  |
| ----------------------------------- |
| -   【灰度管理api服务】支持git操作（获取分支、代码）     |
| -   【灰度管理api服务】支持k8s操作（获取组件信息、发布服务） |

-   0.0.2 版计划功能

| 功能                                |
| --------------------------------- |
| -   【基础功能】spring-cloud-gateway 集成 |
| -   【基础功能】spring-mvc 集成           |
| -   【基础功能】管理后台-api                |
| -   【基础功能】管理后台-git版本获取            |
| -   【基础功能】管理后台-服务管理ssh            |


## 5.4 如何参与（一起玩）

可以gitee、github 私信我，成为仓库成员，也可以提mr （要规范哦😄）,我来合并；

- github地址：https://github.com/haopenge/easy-gray
- gitee地址：https://gitee.com/xiaoyuxxx/easy-gray

# 项目贡献者

<table>
  <tr>
    <td align="center"><a href="https://github.com/haopenge"><img src="https://avatars.githubusercontent.com/u/37403993?v=4" width="100px;" alt=""/><br /><sub><b>小雨淅淅淅</b></sub></a><br />🤔🚀</td>
    <td align="center"><a href="https://github.com/RuanRoah"><img src="https://avatars.githubusercontent.com/u/12394571?v=4" width="100px;" alt=""/><br /><sub><b>阮阮酱！</b></sub></a><br />😄✈️</td>
    <td align="center"><a href="https://github.com/dislazy"><img src="https://avatars.githubusercontent.com/u/30426054?v=4" width="100px;" alt=""/><br /><sub><b>Jack Song</b></sub></a><br />😁🚗</td>
</tr>
</table>

0.0.1版：
- 基于nacos配置、注册中心的核心完成；


