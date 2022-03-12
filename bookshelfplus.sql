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

 Date: 12/03/2022 18:31:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book_author_info
-- ----------------------------
DROP TABLE IF EXISTS `book_author_info`;
CREATE TABLE `book_author_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_author_info
-- ----------------------------

-- ----------------------------
-- Table structure for book_info
-- ----------------------------
DROP TABLE IF EXISTS `book_info`;
CREATE TABLE `book_info`  (
  `id` tinyint(11) NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `book_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `category_id` int(11) NOT NULL DEFAULT 0,
  `publishing_house_id` int(11) NOT NULL DEFAULT '',
  `language` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `copyright` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '1为已删除项',
  PRIMARY KEY (`id`, `book_name`) USING BTREE,
  INDEX `分类`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_info
-- ----------------------------

-- ----------------------------
-- Table structure for book_publishing_house_info
-- ----------------------------
DROP TABLE IF EXISTS `book_publishing_house_info`;
CREATE TABLE `book_publishing_house_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_publishing_house_info
-- ----------------------------

-- ----------------------------
-- Table structure for book_thumbnail_info
-- ----------------------------
DROP TABLE IF EXISTS `book_thumbnail_info`;
CREATE TABLE `book_thumbnail_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_thumbnail_info
-- ----------------------------

-- ----------------------------
-- Table structure for category_info
-- ----------------------------
DROP TABLE IF EXISTS `category_info`;
CREATE TABLE `category_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类简介（预留字段）',
  `isShow` tinyint(1) NOT NULL DEFAULT 1 COMMENT '分类是否显示',
  `order` int(11) NOT NULL COMMENT '分类顺序',
  `level` int(11) NOT NULL COMMENT '分类层级（一、二、三级）',
  `parentId` int(11) NOT NULL DEFAULT 0 COMMENT '父分类ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `cateName`(`name`) USING BTREE COMMENT '索引名称不得重复',
  INDEX `parentCateId`(`parentId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category_info
-- ----------------------------
INSERT INTO `category_info` VALUES (1, 'Git与代码版本管理', '简介123', 1, 1, 1, 0);
INSERT INTO `category_info` VALUES (2, 'LeetCode与面试', '\r\n', 1, 2, 1, 0);
INSERT INTO `category_info` VALUES (3, '操作系统（Android、Linux等）', '\r\n', 1, 3, 1, 0);
INSERT INTO `category_info` VALUES (4, 'Android', '\r\n', 1, 1, 2, 3);
INSERT INTO `category_info` VALUES (5, 'Linux', '\r\n', 1, 2, 2, 3);
INSERT INTO `category_info` VALUES (6, '产品与时代', '\r\n', 1, 4, 1, 0);
INSERT INTO `category_info` VALUES (7, '分布式与高并发（Hadoop、ElasticSearch、区块链、架构）', '\r\n', 1, 5, 1, 0);
INSERT INTO `category_info` VALUES (8, 'ElasticSearch 分布式搜索引擎', '\r\n', 1, 1, 2, 7);
INSERT INTO `category_info` VALUES (9, 'Hadoop', '\r\n', 1, 2, 2, 7);
INSERT INTO `category_info` VALUES (10, '架构', '\r\n', 1, 3, 2, 7);
INSERT INTO `category_info` VALUES (11, '区块链', '\r\n', 1, 4, 2, 7);
INSERT INTO `category_info` VALUES (12, '机器学习', '\r\n', 1, 6, 1, 0);
INSERT INTO `category_info` VALUES (13, '计算机基础（计算机组成原理、计算机网络、数据结构与算法）', '\r\n', 1, 7, 1, 0);
INSERT INTO `category_info` VALUES (14, '计算机网络', '\r\n', 1, 1, 2, 13);
INSERT INTO `category_info` VALUES (15, '计算机组成原理', '\r\n', 1, 2, 2, 13);
INSERT INTO `category_info` VALUES (16, '数据结构与算法', '\r\n', 1, 3, 2, 13);
INSERT INTO `category_info` VALUES (17, '开发语言（C、C++、Java、Go、Python、HTML、JavaScript、CSS、汇编等）', '\r\n', 1, 8, 1, 0);
INSERT INTO `category_info` VALUES (18, 'C++', '\r\n', 1, 2, 2, 17);
INSERT INTO `category_info` VALUES (19, 'Go', '\r\n', 1, 3, 2, 17);
INSERT INTO `category_info` VALUES (20, 'Java', '\r\n', 1, 4, 2, 17);
INSERT INTO `category_info` VALUES (21, 'Java工具', '\r\n', 1, 1, 3, 20);
INSERT INTO `category_info` VALUES (22, 'Java基础', '\r\n', 1, 2, 3, 20);
INSERT INTO `category_info` VALUES (23, 'Netty', '\r\n', 1, 3, 3, 20);
INSERT INTO `category_info` VALUES (24, 'Spring', '\r\n', 1, 4, 3, 20);
INSERT INTO `category_info` VALUES (25, '线程', '\r\n', 1, 5, 3, 20);
INSERT INTO `category_info` VALUES (26, '虚拟机', '\r\n', 1, 6, 3, 20);
INSERT INTO `category_info` VALUES (27, 'Python', '\r\n', 1, 5, 2, 17);
INSERT INTO `category_info` VALUES (28, '汇编语言', '\r\n', 1, 6, 2, 17);
INSERT INTO `category_info` VALUES (29, '前端（HTML、JavaScript、CSS）', '\r\n', 1, 7, 2, 17);
INSERT INTO `category_info` VALUES (30, '设计模式', '\r\n', 1, 9, 1, 0);
INSERT INTO `category_info` VALUES (31, '数据库（MySQL、Redis、SQLite、Mybatis、MongoDB等）', '\r\n', 1, 10, 1, 0);
INSERT INTO `category_info` VALUES (32, 'MongoDB', '\r\n', 1, 1, 2, 31);
INSERT INTO `category_info` VALUES (33, 'Mybatis', '\r\n', 1, 2, 2, 31);
INSERT INTO `category_info` VALUES (34, 'MySQL', '\r\n', 1, 3, 2, 31);
INSERT INTO `category_info` VALUES (35, 'Redis', '\r\n', 1, 4, 2, 31);
INSERT INTO `category_info` VALUES (36, 'SQLite', '\r\n', 1, 5, 2, 31);
INSERT INTO `category_info` VALUES (37, '消息队列', '\r\n', 1, 11, 1, 0);
INSERT INTO `category_info` VALUES (38, 'C语言', '\r\n', 1, 1, 2, 17);
INSERT INTO `category_info` VALUES (39, 'Spring Boot', '\r\n', 1, 7, 3, 20);
INSERT INTO `category_info` VALUES (40, 'Java进阶', '\r\n', 1, 8, 3, 20);
INSERT INTO `category_info` VALUES (41, 'Java Web', '\r\n', 1, 9, 3, 20);
INSERT INTO `category_info` VALUES (42, 'Spring Cloud', '\r\n', 1, 10, 3, 20);
INSERT INTO `category_info` VALUES (43, '其他', '\r\n', 1, 12, 1, 0);

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `book_id` int(11) NOT NULL,
  `file_display_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `file_format` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `number_of_pages` int(11) NOT NULL DEFAULT 0,
  `watermark` tinyint(1) NOT NULL DEFAULT 0,
  `advertising` tinyint(1) NOT NULL DEFAULT 0 COMMENT '1为已删除项',
  `book_origin` tinyint(4) NOT NULL,
  `thumbnail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `file_create_at` datetime NOT NULL,
  `file_modified_at` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `file_size` int(18) NOT NULL DEFAULT 0,
  `hash_md5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `hash_sha1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `hash_sha256` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`, `book_id`) USING BTREE,
  INDEX `分类`(`file_name`) USING BTREE,
  INDEX `fileId`(`hash_md5`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_info
-- ----------------------------

-- ----------------------------
-- Table structure for file_object_info
-- ----------------------------
DROP TABLE IF EXISTS `file_object_info`;
CREATE TABLE `file_object_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `fileId` int(11) NOT NULL,
  `storage_medium_type` tinyint(4) NOT NULL DEFAULT 0,
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '本地文件保存相对路径（本地维护用，非线上使用）',
  `file_pwd` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `file_share_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `additional_fields` json NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_object_info
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
