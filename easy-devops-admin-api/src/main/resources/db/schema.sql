/*
 Navicat Premium Data Transfer

 Source Server         : rainxx
 Source Server Type    : MySQL
 Source Server Version : 80016 (8.0.16)
 Source Host           : 127.0.0.11:3306
 Source Schema         : easy_admin

 Target Server Type    : MySQL
 Target Server Version : 80016 (8.0.16)
 File Encoding         : 65001

 Date: 11/06/2023 21:10:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for easy_build
-- ----------------------------
CREATE TABLE IF NOT EXISTS `easy_build`  (
                               `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id',
                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               `easy_repository_id` int(11) NOT NULL COMMENT '仓库id',
                               `order` int(4) NOT NULL DEFAULT 0 COMMENT '构建顺序，执行时由小到大',
                               `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
                               `type` int(4) NOT NULL COMMENT '构建类型：0-检出代码；5-脚本执行；10-镜像构建；15-发布项目',
                               `hash_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '构建时的hash值',
                               PRIMARY KEY (`id`) USING BTREE,
                               UNIQUE INDEX `id_name_uique`(`id` ASC) USING BTREE COMMENT '同环境项目唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '构建' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for easy_certificate
-- ----------------------------
CREATE TABLE IF NOT EXISTS `easy_certificate`  (
                                     `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id',
                                     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '名称',
                                     `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述信息',
                                     `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
                                     `access_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '认证秘钥',
                                     `repository_type` int(4) NOT NULL COMMENT 'git仓库类型，1-github  2-gitee   3-gitlab\n',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE INDEX `id_name_uique`(`id` ASC, `name` ASC) USING BTREE COMMENT '同环境项目唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '凭证' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for easy_env
-- ----------------------------
CREATE TABLE IF NOT EXISTS `easy_env`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '描述',
                             `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '灰度环境名称',
                             `expire_time` datetime NULL DEFAULT NULL COMMENT '有效时间，此时间为截止日期',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '环境' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for easy_project
-- ----------------------------
CREATE TABLE IF NOT EXISTS `easy_project`  (
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目名',
                                 `easy_env_id` int(11) NOT NULL DEFAULT 0 COMMENT '环境id',
                                 `easy_repository_id` int(11) NOT NULL COMMENT '仓库id',
                                 `branch` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'git项目分支',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 UNIQUE INDEX `id_name_uique`(`id` ASC, `name` ASC) USING BTREE COMMENT '同环境项目唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '项目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for easy_repository
-- ----------------------------
CREATE TABLE IF NOT EXISTS `easy_repository`  (
                                    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id',
                                    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '描述',
                                    `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '项目名',
                                    `branch` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'git项目分支',
                                    `clone_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Git 克隆地址',
                                    `easy_certificate_id` int(11) NOT NULL DEFAULT 0 COMMENT '认证id',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '仓库' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for easy_template
-- ----------------------------
CREATE TABLE IF NOT EXISTS `easy_template`  (
                                  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id',
                                  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
                                  `content_file_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
                                  `type` int(4) NOT NULL COMMENT '模板类型：1-dockerfile ；5-shell;  10-yaml ; ',
                                  PRIMARY KEY (`id`) USING BTREE,
                                  UNIQUE INDEX `id_name_uique`(`id` ASC) USING BTREE COMMENT '同环境项目唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '模板' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
