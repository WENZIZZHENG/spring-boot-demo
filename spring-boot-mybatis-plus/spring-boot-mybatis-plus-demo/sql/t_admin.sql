/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 03/12/2021 00:15:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '管理员名称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '头像图片',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改者',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `tenant_id` int(11) NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES (28, '大白', '222', '2021-09-25 16:51:32', 'testInsertName', '2021-09-25 16:51:32', 'testInsertName', 0, 0, 1);
INSERT INTO `t_admin` VALUES (29, '小李子', '333', '2021-09-25 16:52:17', 'testInsertName', '2021-09-25 17:00:59', 'testUpdateName', 1, 0, 2);
INSERT INTO `t_admin` VALUES (30, '飞刀', '333', '2021-09-25 18:54:00', 'testInsertName', '2021-09-25 18:54:00', 'testInsertName', 0, 0, 1);
INSERT INTO `t_admin` VALUES (31, '测试用户369', '222', '2021-09-27 00:15:19', 'testInsertName', '2021-09-27 00:15:19', 'testInsertName', 0, 0, 1);
INSERT INTO `t_admin` VALUES (32, '新增管理员', '222', '2021-12-02 23:13:35', 'testInsertName', '2021-12-02 23:13:35', 'testInsertName', 0, 0, 1);

SET FOREIGN_KEY_CHECKS = 1;
