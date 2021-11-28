
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_goods_2020_01_03
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_2020_01_03`;
CREATE TABLE `t_goods_2020_01_03`  (
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
-- Records of t_goods_2020_01_03
-- ----------------------------
INSERT INTO `t_goods_2020_01_03` VALUES (55, '商品0', '件', '第ds0个数据库中，在01_03表_0', 10.00, '2020-01-01 00:00:00', 'MrWen', '2021-11-28 21:46:27', 'MrWen', 0);
INSERT INTO `t_goods_2020_01_03` VALUES (56, '商品1', '件', '第ds0个数据库中，在01_03表_1', 11.00, '2020-02-01 00:00:00', 'MrWen', '2021-11-28 21:46:27', 'MrWen', 0);
INSERT INTO `t_goods_2020_01_03` VALUES (57, '商品2', '件', '第ds0个数据库中，在01_03表_2', 12.00, '2020-03-01 00:00:00', 'MrWen', '2021-11-28 21:46:27', 'MrWen', 0);

-- ----------------------------
-- Table structure for t_goods_2020_04_06
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_2020_04_06`;
CREATE TABLE `t_goods_2020_04_06`  (
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
-- Records of t_goods_2020_04_06
-- ----------------------------
INSERT INTO `t_goods_2020_04_06` VALUES (58, '商品3', '件', '第ds0个数据库中，在04_06表_3', 13.00, '2020-04-01 00:00:00', 'MrWen', '2021-11-28 21:46:27', 'MrWen', 0);
INSERT INTO `t_goods_2020_04_06` VALUES (59, '商品4', '件', '第ds0个数据库中，在04_06表_4', 14.00, '2020-05-01 00:00:00', 'MrWen', '2021-11-28 21:46:27', 'MrWen', 0);
INSERT INTO `t_goods_2020_04_06` VALUES (60, '商品5', '件', '第ds0个数据库中，在04_06表_5', 15.00, '2020-06-01 00:00:00', 'MrWen', '2021-11-28 21:46:27', 'MrWen', 0);

-- ----------------------------
-- Table structure for t_goods_2020_07_09
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_2020_07_09`;
CREATE TABLE `t_goods_2020_07_09`  (
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
-- Records of t_goods_2020_07_09
-- ----------------------------
INSERT INTO `t_goods_2020_07_09` VALUES (61, '商品6', '件', '第ds0个数据库中，在07_09表_6', 16.00, '2020-07-01 00:00:00', 'MrWen', '2021-11-28 21:46:27', 'MrWen', 0);
INSERT INTO `t_goods_2020_07_09` VALUES (62, '商品7', '件', '第ds0个数据库中，在07_09表_7', 17.00, '2020-08-01 00:00:00', 'MrWen', '2021-11-28 21:46:27', 'MrWen', 0);
INSERT INTO `t_goods_2020_07_09` VALUES (63, '商品8', '件', '第ds0个数据库中，在07_09表_8', 18.00, '2020-09-01 00:00:00', 'MrWen', '2021-11-28 21:46:27', 'MrWen', 0);

-- ----------------------------
-- Table structure for t_goods_2020_10_12
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_2020_10_12`;
CREATE TABLE `t_goods_2020_10_12`  (
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
-- Records of t_goods_2020_10_12
-- ----------------------------
INSERT INTO `t_goods_2020_10_12` VALUES (64, '商品9', '件', '第ds0个数据库中，在10_12表_9', 19.00, '2020-10-01 00:00:00', 'MrWen', '2021-11-28 21:46:27', 'MrWen', 0);
INSERT INTO `t_goods_2020_10_12` VALUES (65, '商品10', '件', '第ds0个数据库中，在10_12表_10', 20.00, '2020-11-01 00:00:00', 'MrWen', '2021-11-28 21:46:27', 'MrWen', 0);
INSERT INTO `t_goods_2020_10_12` VALUES (66, '商品11', '件', '第ds0个数据库中，在10_12表_11', 21.00, '2020-12-01 00:00:00', 'MrWen', '2021-11-28 21:46:27', 'MrWen', 0);

-- ----------------------------
-- Table structure for t_goods_2021_01_03
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_2021_01_03`;
CREATE TABLE `t_goods_2021_01_03`  (
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
-- Records of t_goods_2021_01_03
-- ----------------------------
INSERT INTO `t_goods_2021_01_03` VALUES (67, '商品12', '件', '第ds0个数据库中，在01_03表_12', 22.00, '2021-01-01 00:00:00', 'MrWen', '2021-11-28 21:46:27', 'MrWen', 0);
INSERT INTO `t_goods_2021_01_03` VALUES (68, '商品13', '件', '第ds0个数据库中，在01_03表_13', 23.00, '2021-02-01 00:00:00', 'MrWen', '2021-11-28 21:46:27', 'MrWen', 0);
INSERT INTO `t_goods_2021_01_03` VALUES (69, '商品14', '件', '第ds0个数据库中，在01_03表_14', 24.00, '2021-03-01 00:00:00', 'MrWen', '2021-11-28 21:46:27', 'MrWen', 0);

-- ----------------------------
-- Table structure for t_goods_2021_04_06
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_2021_04_06`;
CREATE TABLE `t_goods_2021_04_06`  (
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
-- Records of t_goods_2021_04_06
-- ----------------------------
INSERT INTO `t_goods_2021_04_06` VALUES (70, '商品15', '件', '第ds0个数据库中，在04_06表_15', 25.00, '2021-04-01 00:00:00', 'MrWen', '2021-11-28 21:46:27', 'MrWen', 0);
INSERT INTO `t_goods_2021_04_06` VALUES (71, '商品16', '件', '第ds0个数据库中，在04_06表_16', 26.00, '2021-05-01 00:00:00', 'MrWen', '2021-11-28 21:46:27', 'MrWen', 0);
INSERT INTO `t_goods_2021_04_06` VALUES (72, '商品17', '件', '第ds0个数据库中，在04_06表_17', 27.00, '2021-06-01 00:00:00', 'MrWen', '2021-11-28 21:46:27', 'MrWen', 0);

-- ----------------------------
-- Table structure for t_goods_2021_07_09
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_2021_07_09`;
CREATE TABLE `t_goods_2021_07_09`  (
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
-- Records of t_goods_2021_07_09
-- ----------------------------
INSERT INTO `t_goods_2021_07_09` VALUES (73, '商品18', '件', '第ds0个数据库中，在07_09表_18', 28.00, '2021-07-01 00:00:00', 'MrWen', '2021-11-28 21:46:27', 'MrWen', 0);
INSERT INTO `t_goods_2021_07_09` VALUES (74, '商品19', '件', '第ds0个数据库中，在07_09表_19', 29.00, '2021-08-01 00:00:00', 'MrWen', '2021-11-28 21:46:27', 'MrWen', 0);
INSERT INTO `t_goods_2021_07_09` VALUES (75, '商品20', '件', '第ds0个数据库中，在07_09表_20', 30.00, '2021-09-01 00:00:00', 'MrWen', '2021-11-28 21:46:27', 'MrWen', 0);

-- ----------------------------
-- Table structure for t_goods_2021_10_12
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_2021_10_12`;
CREATE TABLE `t_goods_2021_10_12`  (
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
-- Records of t_goods_2021_10_12
-- ----------------------------
INSERT INTO `t_goods_2021_10_12` VALUES (76, '商品21', '件', '第ds0个数据库中，在10_12表_21', 31.00, '2021-10-01 00:00:00', 'MrWen', '2021-11-28 21:46:28', 'MrWen', 0);
INSERT INTO `t_goods_2021_10_12` VALUES (77, '商品22', '件', '第ds0个数据库中，在10_12表_22', 32.00, '2021-11-01 00:00:00', 'MrWen', '2021-11-28 21:46:28', 'MrWen', 0);
INSERT INTO `t_goods_2021_10_12` VALUES (78, '商品23', '件', '第ds0个数据库中，在10_12表_23', 33.00, '2021-12-01 00:00:00', 'MrWen', '2021-11-28 21:46:28', 'MrWen', 0);
INSERT INTO `t_goods_2021_10_12` VALUES (79, '测试新增1', '’件‘', '测试新增1', 777.00, '2021-11-28 22:02:11', 'MrWen', '2021-11-28 22:02:11', 'MrWen', 0);
INSERT INTO `t_goods_2021_10_12` VALUES (80, '测试修改2', '’件‘', '测试新增2', 777.00, '2021-11-28 22:02:44', 'MrWen', '2021-11-28 22:03:23', 'MrWen', 1);

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
INSERT INTO `t_user` VALUES (671838047889240064, '第0个数据库_0', '2021-11-28 22:04:33', 'MrWen', '2021-11-28 22:04:33', 'MrWen', 0);
INSERT INTO `t_user` VALUES (671838048258338816, '第0个数据库_2', '2021-11-28 22:04:33', 'MrWen', '2021-11-28 22:04:33', 'MrWen', 0);
INSERT INTO `t_user` VALUES (671838048333836288, '第0个数据库_4', '2021-11-28 22:04:33', 'MrWen', '2021-11-28 22:04:33', 'MrWen', 0);
INSERT INTO `t_user` VALUES (671838048400945152, '第0个数据库_6', '2021-11-28 22:04:33', 'MrWen', '2021-11-28 22:04:33', 'MrWen', 0);
INSERT INTO `t_user` VALUES (671838048468054016, '第0个数据库_8', '2021-11-28 22:04:33', 'MrWen', '2021-11-28 22:04:33', 'MrWen', 0);
INSERT INTO `t_user` VALUES (671838048530968576, '第0个数据库_10', '2021-11-28 22:04:33', 'MrWen', '2021-11-28 22:04:33', 'MrWen', 0);

SET FOREIGN_KEY_CHECKS = 1;
