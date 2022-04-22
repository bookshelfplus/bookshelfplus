/*
 Navicat Premium Data Transfer

 Source Server         : localhost MySQL 5.7
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : bookshelfplus

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 22/04/2022 17:27:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book_info
-- ----------------------------
DROP TABLE IF EXISTS `book_info`;
CREATE TABLE `book_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `book_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `category_id` int(11) NOT NULL DEFAULT 0,
  `publishing_house` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `language` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `copyright` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '1为已删除项',
  `thumbnail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '缩略图',
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '作者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of book_info
-- ----------------------------

-- ----------------------------
-- Table structure for category_info
-- ----------------------------
DROP TABLE IF EXISTS `category_info`;
CREATE TABLE `category_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类简介（预留字段）',
  `is_show` tinyint(1) NOT NULL DEFAULT 1 COMMENT '分类是否显示',
  `order` int(11) NOT NULL COMMENT '分类顺序',
  `level` int(11) NOT NULL COMMENT '分类层级（一、二、三级）',
  `parent_id` int(11) NOT NULL DEFAULT 0 COMMENT '父分类ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category_info
-- ----------------------------
INSERT INTO `category_info` VALUES (1, 'Git与代码版本管理', '暂无简介', 1, 1, 1, 0);
INSERT INTO `category_info` VALUES (2, 'LeetCode与面试', '暂无简介', 1, 2, 1, 0);
INSERT INTO `category_info` VALUES (3, '操作系统（Android、Linux等）', '暂无简介', 1, 3, 1, 0);
INSERT INTO `category_info` VALUES (4, 'Android', '暂无简介', 1, 1, 2, 3);
INSERT INTO `category_info` VALUES (5, 'Linux', '暂无简介', 1, 2, 2, 3);
INSERT INTO `category_info` VALUES (6, '产品与时代', '暂无简介', 1, 4, 1, 0);
INSERT INTO `category_info` VALUES (7, '分布式与高并发（Hadoop、ElasticSearch、区块链、架构）', '暂无简介', 1, 5, 1, 0);
INSERT INTO `category_info` VALUES (8, 'ElasticSearch 分布式搜索引擎', '暂无简介', 1, 1, 2, 7);
INSERT INTO `category_info` VALUES (9, 'Hadoop', '暂无简介', 1, 2, 2, 7);
INSERT INTO `category_info` VALUES (10, '架构', '暂无简介', 1, 3, 2, 7);
INSERT INTO `category_info` VALUES (11, '区块链', '暂无简介', 1, 4, 2, 7);
INSERT INTO `category_info` VALUES (12, '机器学习', '暂无简介', 1, 6, 1, 0);
INSERT INTO `category_info` VALUES (13, '计算机基础（计算机组成原理、计算机网络、数据结构与算法）', '暂无简介', 1, 7, 1, 0);
INSERT INTO `category_info` VALUES (14, '计算机网络', '暂无简介', 1, 1, 2, 13);
INSERT INTO `category_info` VALUES (15, '计算机组成原理', '暂无简介', 1, 2, 2, 13);
INSERT INTO `category_info` VALUES (16, '数据结构与算法', '暂无简介', 1, 3, 2, 13);
INSERT INTO `category_info` VALUES (17, '开发语言（C、C++、Java、Go、Python、HTML、JavaScript、CSS、汇编等）', '暂无简介', 1, 8, 1, 0);
INSERT INTO `category_info` VALUES (18, 'C++', '暂无简介', 1, 2, 2, 17);
INSERT INTO `category_info` VALUES (19, 'Go', '暂无简介', 1, 3, 2, 17);
INSERT INTO `category_info` VALUES (20, 'Java', '暂无简介', 1, 4, 2, 17);
INSERT INTO `category_info` VALUES (21, 'Java工具', '暂无简介', 1, 1, 3, 20);
INSERT INTO `category_info` VALUES (22, 'Java基础', '暂无简介', 1, 2, 3, 20);
INSERT INTO `category_info` VALUES (23, 'Netty', '暂无简介', 1, 3, 3, 20);
INSERT INTO `category_info` VALUES (24, 'Spring', '暂无简介', 1, 4, 3, 20);
INSERT INTO `category_info` VALUES (25, '线程', '暂无简介', 1, 5, 3, 20);
INSERT INTO `category_info` VALUES (26, '虚拟机', '暂无简介', 1, 6, 3, 20);
INSERT INTO `category_info` VALUES (27, 'Python', '暂无简介', 1, 5, 2, 17);
INSERT INTO `category_info` VALUES (28, '汇编语言', '暂无简介', 1, 6, 2, 17);
INSERT INTO `category_info` VALUES (29, '前端（HTML、JavaScript、CSS）', '暂无简介', 1, 7, 2, 17);
INSERT INTO `category_info` VALUES (30, '设计模式', '暂无简介', 1, 9, 1, 0);
INSERT INTO `category_info` VALUES (31, '数据库（MySQL、Redis、SQLite、Mybatis、MongoDB等）', '暂无简介', 1, 10, 1, 0);
INSERT INTO `category_info` VALUES (32, 'MongoDB', '暂无简介', 1, 1, 2, 31);
INSERT INTO `category_info` VALUES (33, 'Mybatis', '暂无简介', 1, 2, 2, 31);
INSERT INTO `category_info` VALUES (34, 'MySQL', '暂无简介', 1, 3, 2, 31);
INSERT INTO `category_info` VALUES (35, 'Redis', '暂无简介', 1, 4, 2, 31);
INSERT INTO `category_info` VALUES (36, 'SQLite', '暂无简介', 1, 5, 2, 31);
INSERT INTO `category_info` VALUES (37, '消息队列', '暂无简介', 1, 11, 1, 0);
INSERT INTO `category_info` VALUES (38, 'C语言', '暂无简介', 1, 1, 2, 17);
INSERT INTO `category_info` VALUES (39, 'Spring Boot', '暂无简介', 1, 7, 3, 20);
INSERT INTO `category_info` VALUES (40, 'Java进阶', '暂无简介', 1, 8, 3, 20);
INSERT INTO `category_info` VALUES (41, 'Java Web', '暂无简介', 1, 9, 3, 20);
INSERT INTO `category_info` VALUES (42, 'Spring Cloud', '暂无简介', 1, 10, 3, 20);
INSERT INTO `category_info` VALUES (43, '其他', '暂无简介', 1, 12, 1, 0);

-- ----------------------------
-- Table structure for cos_presigned_url_generate_log
-- ----------------------------
DROP TABLE IF EXISTS `cos_presigned_url_generate_log`;
CREATE TABLE `cos_presigned_url_generate_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `user_id` int(11) NOT NULL COMMENT '用户Id',
  `time` datetime NOT NULL COMMENT '链接生成时间',
  `expire_minute` int(255) NOT NULL COMMENT '链接有效期（单位：分钟）',
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作方法（上传请求用 PUT，下载请求用 GET，删除请求用 DELETE）',
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '下载的文件链接',
  `url_guid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '链接生成时创建的全局唯一NanoID，便于出现异常下载记录与腾讯云下载日志做对应',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cos_presigned_url_generate_log
-- ----------------------------

-- ----------------------------
-- Table structure for failure_feedback
-- ----------------------------
DROP TABLE IF EXISTS `failure_feedback`;
CREATE TABLE `failure_feedback`  (
  `book_id` int(11) NOT NULL DEFAULT 0,
  `file_id` int(11) NOT NULL DEFAULT 0,
  `file_object_id` int(11) NOT NULL DEFAULT 0,
  `user_id` int(11) NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '失效反馈' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of failure_feedback
-- ----------------------------

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `book_id` int(11) NOT NULL,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件名（不含扩展名）',
  `file_ext` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `file_size` bigint(20) UNSIGNED NOT NULL DEFAULT 0,
  `file_sha1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `number_of_pages` int(11) NOT NULL DEFAULT 0,
  `watermark` tinyint(1) NOT NULL DEFAULT 0,
  `advertising` tinyint(1) NOT NULL DEFAULT 0 COMMENT '1为已删除项',
  `source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `file_create_at` datetime NOT NULL,
  `file_modified_at` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of file_info
-- ----------------------------

-- ----------------------------
-- Table structure for file_object_info
-- ----------------------------
DROP TABLE IF EXISTS `file_object_info`;
CREATE TABLE `file_object_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `file_id` int(11) NOT NULL,
  `upload_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '上传状态（上传成功 SUCCESS，正在上传 UPLOADING，上传终止 NOT_EXIST）',
  `storage_medium` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '本地文件保存相对路径（本地维护用，非线上使用）',
  `file_pwd` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `file_share_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `last_modified` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '文件最后修改时间戳',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of file_object_info
-- ----------------------------

-- ----------------------------
-- Table structure for third_party_user_auth_relation
-- ----------------------------
DROP TABLE IF EXISTS `third_party_user_auth_relation`;
CREATE TABLE `third_party_user_auth_relation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '系统用户ID',
  `third_party_user_id` int(11) NOT NULL COMMENT '社会化用户ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `relation`(`user_id`, `third_party_user_id`) USING BTREE,
  INDEX `third_party_user_id`(`third_party_user_id`) USING BTREE,
  CONSTRAINT `third_party_user_auth_relation_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `third_party_user_auth_relation_ibfk_2` FOREIGN KEY (`third_party_user_id`) REFERENCES `third_party_user_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of third_party_user_auth_relation
-- ----------------------------

-- ----------------------------
-- Table structure for third_party_user_info
-- ----------------------------
DROP TABLE IF EXISTS `third_party_user_info`;
CREATE TABLE `third_party_user_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uuid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '第三方系统的唯一ID',
  `source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'gitee、qq、github、......',
  `access_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户的授权令牌',
  `expire_in` int(255) NULL DEFAULT NULL COMMENT '第三方用户的授权令牌有效期',
  `refresh_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '刷新令牌',
  `open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '第三方用户的Open ID',
  `uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '第三方的用户ID',
  `access_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '个别平台的授权信息',
  `union_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '第三方用户的 Union ID',
  `scope` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '第三方用户授予的权限',
  `token_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '个别平台的授权信息',
  `id_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'id token',
  `mac_algorithm` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '小米平台用户的附带属性',
  `mac_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '小米平台用户的附带属性',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户的授权Code',
  `oauth_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'Twitter平台用户的附带属性',
  `oauth_token_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'Twitter平台用户的附带属性',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'refer: https://justauth.wiki/features/integrate-existing-systems' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of third_party_user_info
-- ----------------------------

-- ----------------------------
-- Table structure for user_book_favorites_relation
-- ----------------------------
DROP TABLE IF EXISTS `user_book_favorites_relation`;
CREATE TABLE `user_book_favorites_relation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`, `book_id`) USING BTREE,
  INDEX `book_id`(`book_id`) USING BTREE,
  CONSTRAINT `user_book_favorites_relation_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `user_book_favorites_relation_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book_info` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_book_favorites_relation
-- ----------------------------

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `encript_pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `group` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `weixin_third_party_auth_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `qq_third_party_auth_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 'admin', '7c4a8d09ca3762af61e59520943dc26494f8941b', '小小墨（密码是123456）', 'ADMIN', '', '', '', '', '');

-- ----------------------------
-- Table structure for visitor_fingerprint_log
-- ----------------------------
DROP TABLE IF EXISTS `visitor_fingerprint_log`;
CREATE TABLE `visitor_fingerprint_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `visitor_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '未登录用户 userId 为 0',
  `create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `action` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '注意，用户浏览器指纹有概率重复，所以仅供参考，不能作为判断的依据\r\n\r\n获取不到浏览器指纹的将使用浏览器UA代替' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of visitor_fingerprint_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
