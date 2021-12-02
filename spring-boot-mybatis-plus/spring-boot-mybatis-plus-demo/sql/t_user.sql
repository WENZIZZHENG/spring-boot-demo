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

 Date: 03/12/2021 00:16:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `gender` tinyint(3) NOT NULL DEFAULT 0 COMMENT '性别：0 未知， 1男， 2 女',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `manager_id` bigint(20) NULL DEFAULT NULL COMMENT '直属上级id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改者',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `tenant_id` int(11) NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `manager_fk`(`manager_id`) USING BTREE,
  CONSTRAINT `manager_fk` FOREIGN KEY (`manager_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, '黑白无常', 18, 0, 'xxxxx.xx.xx', NULL, '2021-12-02 23:13:35', 'testInsertName', '2021-12-02 23:20:42', 'testUpdateName2', 4, 0, NULL);
INSERT INTO `t_user` VALUES (1087982257332887553, '大boss', 40, 0, 'boss@baomidou.com', NULL, '2019-01-11 14:20:20', NULL, '2021-12-02 21:53:45', NULL, 0, 0, NULL);
INSERT INTO `t_user` VALUES (1088248166370832385, '王天风', 32, 1, 'wtf@baomidou.com', 1087982257332887553, '2019-02-05 11:12:22', NULL, '2021-12-02 21:53:45', NULL, 0, 0, NULL);
INSERT INTO `t_user` VALUES (1088250446457389058, '李艺伟', 21, 1, 'lyw@baomidou.com', 1088248166370832385, '2019-02-14 08:31:16', NULL, '2021-12-02 21:53:45', NULL, 0, 0, NULL);
INSERT INTO `t_user` VALUES (1094590409767661570, '张雨琪', 29, 2, 'lyw2020@163.com', 1088248166370832385, '2019-01-14 09:15:15', NULL, '2021-12-02 21:53:45', NULL, 0, 0, NULL);
INSERT INTO `t_user` VALUES (1094592041087729666, '刘红雨', 41, 2, 'lhm@baomidou.com', 1088248166370832385, '2019-01-14 09:48:16', NULL, '2021-12-02 21:53:45', NULL, 0, 0, NULL);
INSERT INTO `t_user` VALUES (1465682956551753730, '小黄', 18, 2, 'xxx@qq.com', 1094592041087729666, NULL, NULL, '2021-12-02 21:53:45', NULL, 0, 0, NULL);

SET FOREIGN_KEY_CHECKS = 1;
