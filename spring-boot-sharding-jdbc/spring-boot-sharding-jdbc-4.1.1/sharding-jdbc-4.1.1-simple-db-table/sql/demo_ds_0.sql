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
INSERT INTO `t_goods_0` VALUES (671741634882809856, '商品0', '件', '第0个数据库中，第0个表_0', 10.00, '2021-11-28 15:41:26', NULL, '2021-11-28 15:41:26', NULL, 0);
INSERT INTO `t_goods_0` VALUES (671741635725864960, '商品2', '件', '第0个数据库中，第0个表_2', 12.00, '2021-11-28 15:41:26', NULL, '2021-11-28 15:41:26', NULL, 0);
INSERT INTO `t_goods_0` VALUES (671741635813945344, '商品4', '件', '第0个数据库中，第0个表_4', 14.00, '2021-11-28 15:41:26', NULL, '2021-11-28 15:41:26', NULL, 0);

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
INSERT INTO `t_goods_1` VALUES (671741635893637120, '商品6', '件', '第0个数据库中，第1个表_6', 16.00, '2021-11-28 15:41:26', NULL, '2021-11-28 15:41:26', NULL, 0);
INSERT INTO `t_goods_1` VALUES (671741934725214208, '新增测试商品', '件', '修改测试商品22', 134.00, '2021-11-28 15:42:37', NULL, '2021-11-28 15:43:14', NULL, 1);

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
INSERT INTO `t_goods_2` VALUES (671741635973328896, '商品8', '件', '第0个数据库中，第2个表_8', 18.00, '2021-11-28 15:41:26', NULL, '2021-11-28 15:41:26', NULL, 0);
INSERT INTO `t_goods_2` VALUES (671741636036243456, '商品10', '件', '第0个数据库中，第2个表_10', 20.00, '2021-11-28 15:41:26', NULL, '2021-11-28 15:41:26', NULL, 0);

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
INSERT INTO `t_user` VALUES (671742603536347136, '第0个数据库_1', '2021-11-28 15:45:17', NULL, '2021-11-28 15:45:17', NULL, 0);
INSERT INTO `t_user` VALUES (671742603645399040, '第0个数据库_3', '2021-11-28 15:45:17', NULL, '2021-11-28 15:45:17', NULL, 0);
INSERT INTO `t_user` VALUES (671742603754450944, '第0个数据库_5', '2021-11-28 15:45:17', NULL, '2021-11-28 15:45:17', NULL, 0);
INSERT INTO `t_user` VALUES (671742603846725632, '第0个数据库_7', '2021-11-28 15:45:17', NULL, '2021-11-28 15:45:17', NULL, 0);
INSERT INTO `t_user` VALUES (671742603934806016, '第0个数据库_9', '2021-11-28 15:45:17', NULL, '2021-11-28 15:45:17', NULL, 0);
INSERT INTO `t_user` VALUES (671742604018692096, '第0个数据库_11', '2021-11-28 15:45:17', NULL, '2021-11-28 15:45:17', NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
