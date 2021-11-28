SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_goods_0
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_0`;
CREATE TABLE `t_goods_0`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '商品名称',
  `unit` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '’件‘' COMMENT '商品单位，例如件、盒',
  `brief` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '商品简介',
  `retail_price` decimal(10, 2) NULL DEFAULT 100000.00 COMMENT '零售价格',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `create_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `update_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改者',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品基本信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_goods_0
-- ----------------------------
INSERT INTO `t_goods_0` VALUES (671741635683921921, '商品1', '件', '第1个数据库中，第0个表_1', 11.00, '2021-11-28 15:41:26', NULL, '2021-11-28 15:41:26', NULL, 0);

-- ----------------------------
-- Table structure for t_goods_1
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_1`;
CREATE TABLE `t_goods_1`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '商品名称',
  `unit` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '’件‘' COMMENT '商品单位，例如件、盒',
  `brief` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '商品简介',
  `retail_price` decimal(10, 2) NULL DEFAULT 100000.00 COMMENT '零售价格',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `create_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `update_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改者',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品基本信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_goods_1
-- ----------------------------
INSERT INTO `t_goods_1` VALUES (671741635776196609, '商品3', '件', '第1个数据库中，第1个表_3', 13.00, '2021-11-28 15:41:26', NULL, '2021-11-28 15:41:26', NULL, 0);
INSERT INTO `t_goods_1` VALUES (671741635927191553, '商品7', '件', '第1个数据库中，第1个表_7', 17.00, '2021-11-28 15:41:26', NULL, '2021-11-28 15:41:26', NULL, 0);
INSERT INTO `t_goods_1` VALUES (671741636002689025, '商品9', '件', '第1个数据库中，第1个表_9', 19.00, '2021-11-28 15:41:26', NULL, '2021-11-28 15:41:26', NULL, 0);

-- ----------------------------
-- Table structure for t_goods_2
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_2`;
CREATE TABLE `t_goods_2`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '商品名称',
  `unit` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '’件‘' COMMENT '商品单位，例如件、盒',
  `brief` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '商品简介',
  `retail_price` decimal(10, 2) NULL DEFAULT 100000.00 COMMENT '零售价格',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `create_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `update_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改者',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品基本信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_goods_2
-- ----------------------------
INSERT INTO `t_goods_2` VALUES (671741635855888385, '商品5', '件', '第1个数据库中，第2个表_5', 15.00, '2021-11-28 15:41:26', NULL, '2021-11-28 15:41:26', NULL, 0);
INSERT INTO `t_goods_2` VALUES (671741636082380801, '商品11', '件', '第1个数据库中，第2个表_11', 21.00, '2021-11-28 15:41:26', NULL, '2021-11-28 15:41:26', NULL, 0);

-- ----------------------------
-- Table structure for t_system
-- ----------------------------
DROP TABLE IF EXISTS `t_system`;
CREATE TABLE `t_system`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `key_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '系统配置名',
  `key_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '系统配置值',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `create_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `update_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改者',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统配置表格' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_system
-- ----------------------------
INSERT INTO `t_system` VALUES (22, 'key1', 'value1', '2021-11-28 15:47:47', NULL, '2021-11-28 15:48:10', NULL, 1);
INSERT INTO `t_system` VALUES (23, 'key2', 'value2', '2021-11-28 15:47:51', NULL, '2021-11-28 15:47:51', NULL, 0);
INSERT INTO `t_system` VALUES (24, 'key3', 'value3', '2021-11-28 15:47:55', NULL, '2021-11-28 15:47:55', NULL, 0);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户名称',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `create_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `update_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改者',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (671742603381157889, '第1个数据库_0', '2021-11-28 15:45:17', NULL, '2021-11-28 15:45:17', NULL, 0);
INSERT INTO `t_user` VALUES (671742603603456001, '第1个数据库_2', '2021-11-28 15:45:17', NULL, '2021-11-28 15:45:17', NULL, 0);
INSERT INTO `t_user` VALUES (671742603695730689, '第1个数据库_4', '2021-11-28 15:45:17', NULL, '2021-11-28 15:45:17', NULL, 0);
INSERT INTO `t_user` VALUES (671742603796393985, '第1个数据库_6', '2021-11-28 15:45:17', NULL, '2021-11-28 15:45:17', NULL, 0);
INSERT INTO `t_user` VALUES (671742603888668673, '第1个数据库_8', '2021-11-28 15:45:17', NULL, '2021-11-28 15:45:17', NULL, 0);
INSERT INTO `t_user` VALUES (671742603980943361, '第1个数据库_10', '2021-11-28 15:45:17', NULL, '2021-11-28 15:45:17', NULL, 0);
INSERT INTO `t_user` VALUES (671742955992100865, 'test新增-修改', '2021-11-28 15:46:41', NULL, '2021-11-28 15:47:09', NULL, 1);

SET FOREIGN_KEY_CHECKS = 1;
