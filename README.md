# 1. 介绍
此项目是一款通用的灰度发布服务组件；

灰度流程如下：
![img.png](doc/img/gray_process.png)

灰度环境架构图：
![img_1.png](doc/img/gray_architecture.png)

# 2. 快速开始
## 2.1 环境准备

- nacos-server
 
环境搭建可参见：[docker 部署nacos](https://github.com/haopenge/interview/blob/master/docker/nacos-docker-2.0.2/README_ZH.md)

- nacos配置---新增命名空间

新增命名空间：qa ,记录命名空间id

- nacos配置---新增commom.yaml 配置

```yaml
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

- host更改

本地host新增一行：(ip 根据实际nacos 地址更改)

```shell
# easy-gray nacos定义
127.0.0.1	nacos.easy.cn
```

## 2.2 项目配置更改

修改 `easy-gray-consumer`、`easy-gray-provider-one`、`easy-gray-provider-two` 三个项目中的`bootstrap.yml` 配置文件；
namespace 改为2.1 中创建的`命名空间的id`;

## 2.3 运行
同时启动`easy-gray-consumer`、`easy-gray-provider-one`、`easy-gray-provider-two`项目；

不带环境变量访问：
```shell
curl --request GET \
  --url http://localhost:20001/eat/apple \
  --header 'content-type: multipart/form-data'

# 结果
provider:  我吃了 苹果 on 10001
provider:  我吃了 苹果 on 10001
provider:  我吃了 苹果 on 10001
```

携带环境变量访问：
```shell
curl --request GET \
  --url http://localhost:20001/eat/apple \
  --header 'content-type: multipart/form-data' \
  --header 'pod_env: EASY-12138'
  
# 结果
provider:  我吃了 苹果 on 10001
provider:  我吃了 苹果 on 10002
provider:  我吃了 苹果 on 10001
provider:  我吃了 苹果 on 10002
provider:  我吃了 苹果 on 10001
```

# 3. 文档

# 4. mr规范

commit格式：`type: ${decription}`
```shell
#示例
feat: 初始化项目
```
    
## type类型
- build：影响生成系统或外部依赖性的更改
- ci: 更改 CI 配置文件和脚本
- feat: 新功能（feature）
- fix: 修补 bug
- perf: 提高性能的代码更改
- docs: 文档（documentation）
- style: 不影响代码含义的更改（不影响代码运行的变动）
- refactor: 代码修改既不修复错误，也不添加特征（即不是新增功能，也不是修改 bug 的代码变动）
- test: 添加缺失测试或纠正现有测试
- revert: 撤回
