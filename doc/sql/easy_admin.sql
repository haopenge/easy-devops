/*
 Navicat MySQL Data Transfer

 Source Server         : local_nacos
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : easy_admin

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 08/11/2022 11:29:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gray_env
-- ----------------------------
DROP TABLE IF EXISTS `gray_env`;
CREATE TABLE `gray_env` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '1正常 2回收站资源 3 彻底删除资源',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '灰度环境名称',
  `expire_time` datetime DEFAULT NULL COMMENT '有效时间，此时间为截止日期',
  `ext_obj` varchar(2048) DEFAULT '' COMMENT '扩展信息，存储灰度的服务，分支',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='灰度环境';

SET FOREIGN_KEY_CHECKS = 1;
