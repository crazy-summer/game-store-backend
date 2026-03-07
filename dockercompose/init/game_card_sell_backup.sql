-- MySQL dump 10.13  Distrib 8.0.45, for Linux (x86_64)
--
-- Host: localhost    Database: game_card_sell
-- ------------------------------------------------------
-- Server version	8.0.45-0ubuntu0.24.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cart`
--
-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS `game_card_sell`;

-- 切换到该数据库
USE `game_card_sell`;

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '购物车ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`),
  CONSTRAINT `fk_cart_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户购物车表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (3,29,'2025-06-25 16:23:25');
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_item` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '购物车项目ID',
  `cart_id` bigint NOT NULL COMMENT '购物车ID',
  `cartridge_id` bigint NOT NULL COMMENT '卡带ID',
  `quantity` int NOT NULL DEFAULT '1' COMMENT '数量',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_cart_cartridge` (`cart_id`,`cartridge_id`),
  KEY `idx_cartridge_id` (`cartridge_id`),
  CONSTRAINT `fk_cart_item_cart` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_cart_item_cartridge` FOREIGN KEY (`cartridge_id`) REFERENCES `cartridge` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='购物车项目表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_item`
--

LOCK TABLES `cart_item` WRITE;
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
INSERT INTO `cart_item` VALUES (1,3,25,1,'2025-06-25 16:23:25','2025-06-26 07:07:59'),(6,3,21,1,'2025-06-26 07:08:02','2025-06-26 07:08:02'),(7,3,10,1,'2025-06-26 07:08:05','2025-06-26 07:08:05'),(8,3,56,1,'2025-06-26 07:08:09','2025-06-26 07:08:09'),(9,3,26,1,'2025-06-26 07:08:12','2025-06-26 07:08:12'),(10,3,2,1,'2025-06-26 07:08:16','2025-06-26 07:08:16');
/*!40000 ALTER TABLE `cart_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cartridge`
--

DROP TABLE IF EXISTS `cartridge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartridge` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '卡带ID',
  `title` varchar(255) NOT NULL COMMENT '游戏标题',
  `score` int DEFAULT NULL COMMENT '评分',
  `cover_image_path` varchar(255) DEFAULT NULL COMMENT '封面路径图片',
  `cover_image_url` varchar(255) DEFAULT NULL COMMENT '封面图片URL',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `release_date` datetime DEFAULT NULL COMMENT '发布日期',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_title` (`title`) USING BTREE,
  KEY `idx_score` (`score`) USING BTREE,
  KEY `idx_price` (`price`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='游戏卡带主表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartridge`
--

LOCK TABLES `cartridge` WRITE;
/*!40000 ALTER TABLE `cartridge` DISABLE KEYS */;
INSERT INTO `cartridge` VALUES (1,'任天堂Switch游戏卡带NS异度神剑X 异度之刃X 终极版中文二手',75,NULL,NULL,206.00,NULL,'2025-06-13 06:03:02'),(2,'任天堂Switch游戏卡带NS     异度之刃2 异度神剑2   中文二手',96,NULL,'/images/19629945-4c57-4ee4-992f-804e8e1f423d.jpg',220.00,NULL,'2025-06-13 06:03:02'),(3,'任天堂Switch游戏NS 圣兽之王 香草社 策略战旗 中文二手',70,NULL,NULL,185.00,NULL,'2025-06-13 06:03:02'),(4,'任天堂Switch游戏卡带NS 蜡笔小新 煤炭镇的小白 中文二手',94,NULL,'/images/d5e7ef14-4f60-4ff0-a445-3b44cc9e5429.jpg',224.00,NULL,'2025-06-13 06:03:02'),(5,'任天堂Switch游戏卡带NS   逆转裁判123合集  456     中文二手',93,NULL,NULL,236.00,NULL,'2025-06-13 06:03:02'),(6,'任天堂Switch游戏卡带 NS 塞尔达无双 灾厄启示录 Zelda中文 二手',87,NULL,NULL,193.00,NULL,'2025-06-13 06:03:02'),(7,'任天堂Switch游戏卡带NS 星之卡比Wii 豪华版 重返梦幻岛中文二手',89,NULL,NULL,227.00,NULL,'2025-06-13 06:03:02'),(8,'任天堂Switch游戏卡带NS新超级马里奥玛丽兄弟UDX豪华版 中文二手',78,NULL,NULL,182.00,NULL,'2025-06-13 06:03:02'),(9,'任天堂Switch游戏卡带NS 海绵宝宝 大闹蟹堡王 含DLC  中文二手',74,NULL,NULL,210.00,NULL,'2025-06-13 06:03:02'),(10,'任天堂Switch2游戏NS2 塞尔达王国之泪 塞尔达传说2 二手',97,NULL,'/images/f41df6ba-0036-4317-901a-1dd2d35ac61b.jpg',280.00,NULL,'2025-06-13 06:03:02'),(11,'任天堂Switch游戏卡带NS    异度神剑3 异度之刃3    中文二手',85,NULL,NULL,181.00,NULL,'2025-06-13 06:03:02'),(12,'任天堂Switch游戏卡带NS   塞尔达传说 织梦岛 梦见岛   中文二手',86,NULL,NULL,225.00,NULL,'2025-06-13 06:03:02'),(13,'任天堂Switch游戏卡带NS 超级马里奥3D世界库巴之怒+狂怒中文二手',94,NULL,'/images/675c3ad0-77ed-4e72-9a57-1413be2d29a7.jpg',200.00,NULL,'2025-06-13 06:03:02'),(14,'任天堂Switch游戏卡带NS  勇者斗恶龙   创世小玩家2  中文二手',80,NULL,NULL,238.00,NULL,'2025-06-13 06:03:02'),(15,'任天堂Switch游戏卡带NS 精灵宝可梦 皮卡丘/伊布 中文二手',76,NULL,NULL,212.00,NULL,'2025-06-13 06:03:02'),(16,'任天堂Switch游戏卡带NS 有氧拳击2健身拳击2Fit Boxing 中文二手',91,NULL,NULL,167.00,NULL,'2025-06-13 06:03:02'),(17,'任天堂Switch游戏卡带NS  我的世界 基岩版 MINECRAF    中文二手',90,NULL,'/images/1ba26206-3925-4ee6-a926-efbc7b58c962.png',175.00,NULL,'2025-06-13 06:03:02'),(18,'任天堂Switch游戏卡带NS   宝可梦 晶璨钻石 明亮珍珠   中文二手',81,NULL,NULL,205.00,NULL,'2025-06-13 06:03:02'),(19,'任天堂Switch游戏卡带NS  勇者斗恶龙11S 追忆 DQ11S 中文二手',84,NULL,NULL,182.00,NULL,'2025-06-13 06:03:02'),(20,'任天堂Switch游戏卡带NS 马里奥与索尼克东京奥运会2020 中文二手',75,NULL,NULL,185.00,NULL,'2025-06-13 06:03:02'),(21,'任天堂Switch游戏卡带NS 怪物猎人 崛起 Rise 猛汉MHR  中文二手',97,NULL,'/images/2a15e90b-b8e7-4a52-af5d-cc7af340dfa2.jpg',100.00,'2022-02-01 00:00:00','2025-06-13 06:03:02'),(22,'任天堂Switch游戏卡带NS 三国志14 with 威力加强版 中文二手',82,NULL,NULL,248.00,NULL,'2025-06-13 06:03:02'),(23,'任天堂Switch游戏卡带NS 皮克敏4 PIKMIN4  中文二手',93,NULL,NULL,187.00,NULL,'2025-06-13 06:03:02'),(24,'任天堂Switch游戏卡带 NS 暗黑破坏神3 永恒之战版 中文二手',76,NULL,NULL,187.00,NULL,'2025-06-13 06:03:02'),(25,'任天堂Switch游戏卡带NS 双点医院年度版 主题医院含DLC 中文二手',98,NULL,'/images/814150f8-ea16-4709-bd5a-90c3043f8112.jpg',270.50,'2025-06-11 00:00:00','2025-06-13 06:03:02'),(26,'任天堂Switch游戏卡带NS 星露谷物语 StardewValley 中文二手',96,NULL,'/images/078bb31d-85da-4033-9ead-52a0d2fcc5f8.jpg',225.00,NULL,'2025-06-13 06:03:02'),(27,'任天堂Switch游戏卡带NS 异度神剑1 异度之刃 决定版   中文二简',86,NULL,NULL,191.00,NULL,'2025-06-13 06:03:02'),(28,'任天堂Switch游戏卡带NS 八方旅人2 歧路旅人2 八方2中文二手',70,NULL,NULL,216.00,NULL,'2025-06-13 06:03:02'),(29,'任天堂Switch游戏卡带NS   耀西的手工世界  毛线耀西   中文二手',79,NULL,NULL,173.00,NULL,'2025-06-13 06:03:02'),(30,'任天堂Switch游戏卡带NS 疯狂兔子 奇遇派对 多人聚会  中文二手',80,NULL,NULL,218.00,NULL,'2025-06-13 06:03:02'),(31,'索尼PS4游戏 地平线2 西部禁域 西之绝境 禁地 Horizon2 中文二手',72,NULL,NULL,114.00,NULL,'2025-06-13 06:03:02'),(32,'索尼PS4游戏 瑞奇与叮当 中文版 高清重置版  中文二手',77,NULL,NULL,207.00,NULL,'2025-06-13 06:03:02'),(33,'索尼PS4游戏 刺客信条：奥德赛 刺客信条奥德赛  中文二手',74,NULL,NULL,141.00,NULL,'2025-06-13 06:03:02'),(34,'索尼PS4游戏 使命召唤18先锋 决胜时刻 COD18 中文二手',86,NULL,NULL,175.00,NULL,'2025-06-13 06:03:02'),(35,'索尼PS4游戏 二手 勇者斗恶龙 建造者2 创世小玩家2  中文二手',79,NULL,NULL,188.00,NULL,'2025-06-13 06:03:02'),(36,'索尼PS4游戏  怪物猎人世界冰原 怪猎冰原 ICEBORNE中文二手',70,NULL,NULL,128.00,NULL,'2025-06-13 06:03:02'),(37,'索尼PS4游戏 底特律 变人 化身为人 成为人类 中文二手',80,NULL,NULL,203.00,NULL,'2025-06-13 06:03:02'),(38,'索尼PS5游戏 地平线2 禁忌西域 西部禁域 西之禁地 中文二手',72,NULL,NULL,202.00,NULL,'2025-06-13 06:03:02'),(39,'索尼PS4游戏 英雄传说 闪之轨迹4 闪轨4 中文二手',85,NULL,NULL,271.00,NULL,'2025-06-13 06:03:02'),(40,'索尼PS5游戏 卧龙 苍天陨落 三国版仁王 WOLONG  中文二手',77,NULL,NULL,265.00,NULL,'2025-06-13 06:03:02'),(41,'索尼PS5游戏  仙剑奇侠传7 中文二手',80,NULL,NULL,178.00,NULL,'2025-06-13 06:03:02'),(42,'索尼PS5游戏 哆啦A梦牧场物语 自然王国与和乐家人 大雄 中文二手',85,NULL,NULL,233.00,NULL,'2025-06-13 06:03:02'),(43,'索尼PS5游戏  迷途猫 浪猫 Stray 流浪猫 迷失猫  中文二手',94,NULL,'/images/c6182ac1-77fc-4c9b-b69c-a2a4c576dbf6.jpg',150.00,NULL,'2025-06-13 06:03:02'),(44,'索尼PS4游戏 二手 神秘海域123 HD合集 神海合集 中文二手',77,NULL,NULL,175.00,NULL,'2025-06-13 06:03:02'),(45,'索尼PS4游戏 巫师3狂猎 年度版 石之心+血与酒DLC 中文二手',89,NULL,NULL,185.00,NULL,'2025-06-13 06:03:02'),(46,'索尼PS5游戏 优米雅的炼金工房 中文二手',95,NULL,'/images/cbf886a3-01fd-4610-ac21-d23348463819.jpg',400.00,NULL,'2025-06-13 06:03:02'),(47,'索尼PS5游戏  忍者龙剑传2 黑之章 中文二手',91,NULL,NULL,113.00,NULL,'2025-06-13 06:03:02'),(48,'索尼PS5游戏 使命召唤18 先锋 中文版 COD18 中文二手',74,NULL,NULL,200.00,NULL,'2025-06-13 06:03:02'),(49,'索尼PS4游戏  茶杯头 Cuphead 双人游戏  中文二手',92,NULL,NULL,118.00,NULL,'2025-06-13 06:03:02'),(50,'索尼PS5游戏  死神 魂魄觉醒BLEACH 中文二手',80,NULL,NULL,258.00,NULL,'2025-06-13 06:03:02'),(51,'索尼PS4游戏 小小大星球3  中文二手',74,NULL,NULL,199.00,NULL,'2025-06-13 06:03:02'),(52,'索尼PS4游戏  刺客信条5 大革命 中文二手',81,NULL,NULL,228.00,NULL,'2025-06-13 06:03:02'),(53,'索尼PS5游戏  使命召唤19 现代战争2 COD19 需全程联网  中文二手',89,NULL,NULL,159.00,NULL,'2025-06-13 06:03:02'),(54,'索尼PS5游戏   双点博物馆 中文二手',83,NULL,NULL,266.00,NULL,'2025-06-13 06:03:02'),(55,'索尼PS5游戏 铁拳8 Tekken8 格斗对战  中文二手',94,NULL,'/images/13fc6f41-97fb-45db-a3d7-134fb0eb9efd.png',389.00,NULL,'2025-06-13 06:03:02'),(56,'索尼PS5游戏  英雄传说 界之轨迹  中文二手',96,NULL,'/images/e07116be-e29b-44ff-83dd-910ee1e587cb.png',398.00,NULL,'2025-06-13 06:03:02'),(57,'索尼PS4游戏 十三机兵防卫圈13  中文二手',81,NULL,NULL,221.00,NULL,'2025-06-13 06:03:02'),(58,'索尼PS4游戏 古墓丽影9  古墓9  中文二手',93,NULL,NULL,169.00,NULL,'2025-06-13 06:03:02'),(59,'索尼PS4游戏 麻布仔 麻布仔大冒险  中文二手',80,NULL,NULL,182.00,NULL,'2025-06-13 06:03:02'),(60,'索尼PS5游戏  心灵杀手2 Alan Wake 2 中文二手',72,NULL,NULL,174.00,NULL,'2025-06-13 06:03:02');
/*!40000 ALTER TABLE `cartridge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cartridge_category_dict`
--

DROP TABLE IF EXISTS `cartridge_category_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartridge_category_dict` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `category_name` varchar(50) NOT NULL COMMENT '分类名称',
  `description` varchar(255) DEFAULT NULL COMMENT '分类描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_category_name` (`category_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='游戏分类字典表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartridge_category_dict`
--

LOCK TABLES `cartridge_category_dict` WRITE;
/*!40000 ALTER TABLE `cartridge_category_dict` DISABLE KEYS */;
INSERT INTO `cartridge_category_dict` VALUES (1,'默认','默认分类，无特殊指向'),(2,'聚会','适合多人聚会游玩的游戏分类'),(3,'动作','以动作玩法为核心的游戏分类'),(4,'角色扮演','玩家扮演角色展开冒险等体验的分类'),(5,'冒险','强调探索、冒险元素的游戏分类'),(6,'射击','以射击玩法为主的游戏分类'),(7,'策略','注重策略规划的游戏分类'),(8,'横版过关','采用横版卷轴，以闯关为目标的分类'),(9,'益智','侧重益智解谜等玩法的游戏分类'),(10,'模拟','模拟现实或虚构场景经营、生活等的分类'),(11,'体育','模拟体育运动玩法的游戏分类'),(12,'即时战略','即时进行战略部署、战斗的游戏分类'),(13,'竞速','以比赛速度为核心玩法的游戏分类'),(14,'格斗','主打角色格斗对战的游戏分类'),(15,'乙女','面向女性玩家，多含恋爱等元素的分类'),(16,'多人游戏','支持多人同时参与游玩的游戏分类'),(17,'音乐','以音乐节奏玩法为核心的游戏分类'),(18,'赛车','聚焦赛车竞速体验的游戏分类'),(19,'训练','侧重技能训练、练习玩法的游戏分类'),(20,'国行','适配国内发行、规范的游戏分类（可根据实际细化）'),(21,'恋爱','围绕恋爱养成、互动等玩法的游戏分类'),(22,'文字冒险','以文字互动、剧情冒险为主的游戏分类'),(23,'像素','采用像素美术风格的游戏分类'),(24,'解谜','以解谜闯关为主要玩法的游戏分类'),(25,'街机','模拟街机游戏体验、玩法的分类'),(26,'恐怖','主打恐怖氛围、惊悚体验的游戏分类'),(27,'独立','独立游戏开发者制作的游戏分类（可根据实际细化）'),(28,'休闲','玩法轻松、休闲的游戏分类'),(29,'桌游','模拟桌面游戏玩法的游戏分类'),(30,'飞行','以飞行模拟、空战等玩法为主的游戏分类'),(31,'球类','模拟球类运动玩法的游戏分类'),(32,'体感','需借助体感设备交互玩法的游戏分类'),(33,'养成','以角色、事物养成为核心玩法的游戏分类');
/*!40000 ALTER TABLE `cartridge_category_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cartridge_category_relation`
--

DROP TABLE IF EXISTS `cartridge_category_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartridge_category_relation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '游戏卡带分类关系ID',
  `cartridge_id` bigint NOT NULL COMMENT '游戏卡带ID',
  `category_id` bigint NOT NULL COMMENT '分类字典ID',
  PRIMARY KEY (`id`),
  KEY `idx_cartridge_id` (`cartridge_id`) USING BTREE,
  KEY `idx_category_id` (`category_id`) USING BTREE,
  CONSTRAINT `fk_cartridge_id` FOREIGN KEY (`cartridge_id`) REFERENCES `cartridge` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_category_id` FOREIGN KEY (`category_id`) REFERENCES `cartridge_category_dict` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='游戏分类关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartridge_category_relation`
--

LOCK TABLES `cartridge_category_relation` WRITE;
/*!40000 ALTER TABLE `cartridge_category_relation` DISABLE KEYS */;
INSERT INTO `cartridge_category_relation` VALUES (41,21,3),(42,21,4),(43,21,5),(44,10,3),(45,10,4),(46,56,4),(47,26,9),(48,26,10),(49,26,23),(50,26,27),(51,26,33),(52,2,4),(53,2,5),(54,46,4),(55,46,15),(56,55,3),(57,55,14),(58,25,1),(59,25,2),(60,43,4),(61,17,9),(62,17,10),(63,17,23),(64,13,5),(65,4,4);
/*!40000 ALTER TABLE `cartridge_category_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cartridge_platform_dict`
--

DROP TABLE IF EXISTS `cartridge_platform_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartridge_platform_dict` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '平台ID',
  `platform_name` varchar(50) NOT NULL COMMENT '平台名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_platform_name` (`platform_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='游戏平台字典表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartridge_platform_dict`
--

LOCK TABLES `cartridge_platform_dict` WRITE;
/*!40000 ALTER TABLE `cartridge_platform_dict` DISABLE KEYS */;
INSERT INTO `cartridge_platform_dict` VALUES (1,'ns'),(2,'ns2'),(3,'ps4'),(4,'ps5');
/*!40000 ALTER TABLE `cartridge_platform_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cartridge_platform_relation`
--

DROP TABLE IF EXISTS `cartridge_platform_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartridge_platform_relation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cartridge_id` bigint NOT NULL COMMENT '卡带ID',
  `platform_id` bigint NOT NULL COMMENT '平台字典ID',
  PRIMARY KEY (`id`),
  KEY `idx_cartridge_id` (`cartridge_id`) USING BTREE,
  KEY `idx_platform_id` (`platform_id`) USING BTREE,
  CONSTRAINT `fk_platform_cartridge` FOREIGN KEY (`cartridge_id`) REFERENCES `cartridge` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_platform_dict` FOREIGN KEY (`platform_id`) REFERENCES `cartridge_platform_dict` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='游戏平台关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartridge_platform_relation`
--

LOCK TABLES `cartridge_platform_relation` WRITE;
/*!40000 ALTER TABLE `cartridge_platform_relation` DISABLE KEYS */;
INSERT INTO `cartridge_platform_relation` VALUES (55,21,1),(56,21,2),(57,10,1),(58,10,2),(59,56,4),(60,26,1),(61,2,1),(62,46,4),(63,55,4),(64,25,1),(65,25,2),(66,43,4),(67,17,1),(68,13,1),(69,4,1);
/*!40000 ALTER TABLE `cartridge_platform_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `description` varchar(100) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_role_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN','系统管理员，有权限修改用户信息'),(2,'MANAGER','普通管理员，有权限上架，下架，修改卡带信息'),(3,'USER','用户');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码（加密）',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `enabled_status` tinyint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '用户注册时间',
  `phone` varchar(11) DEFAULT NULL COMMENT '用户手机号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_username` (`username`),
  UNIQUE KEY `UK_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (3,'段振东','$2a$10$xBfAipxupizee4Ikr.eFjuvRc2PW/T6IyXvwAx/v.KI4tqZtlbvxG','i1cvdk_lw1@126.com',1,'2025-06-04 12:27:07',NULL),(9,'ztgNOmpmP','$2a$10$3kQpHox3L7b57ltID2P68O2uhmfudI5TBrBcbQgwaGfExu15Aexm2','fnqlzm1@gmail.com',1,'2025-06-04 12:27:07',NULL),(10,'yXiKGrbYZH1','$2a$10$XCJ9NdhVgMpBryw0hxBEC.R8BT0yWvbn6L/.fwauo0F9T6kszH7g6','RN1WF@yahoo.com',1,'2025-06-04 12:27:07',NULL),(11,'azpht5V86','$2a$10$Of6m4ix6HGZ8FqRun6uBWO5mZGAAXM7tGQ.H0Lw4bVZrH5AfjuOoO','36gK@hotmail.com',1,'2025-06-04 12:27:07',NULL),(12,'mMiqgIEGt','$2a$10$XqdFNbpypAS6cgFpYB4pZeLyPEI1QxuWxlUeqGEjR1GV9wB/r6jCK','dE7@example.com',1,'2025-06-04 12:27:07',NULL),(13,'CMT4Z83YW427M4EDQvO','$2a$10$pS2PbBvdCtv5caC8e1JPTudzIUfsO3gq.r9I.GpRP1Ac8Npf3KdhS','ISUqY@gmail.com',1,'2025-06-04 12:27:07',NULL),(14,'axci9cY','$2a$10$SThDXxFaGjXTDiyxkwvctOlGZ.Ev.AE8GAXvmg7RlHoLPss3QHxLe','MDA@hotmail.com',1,'2025-06-04 12:27:07',NULL),(15,'OLYt7N8','$2a$10$7XuWb6JR4DkEMCpOA1iMqOSbp5P81GuI2bbHI0Eb3PqGr8qls3JTW','AF8@gmail.com',1,'2025-06-04 12:27:07',NULL),(16,'hHGVELHZxJ','$2a$10$PEAsAZFK6Xg0IQq05r55CepoQmB8AF1N99CHO4IcKSCm4YTnQfuDG','TzrHy@gmail.com',1,'2025-06-04 12:27:07',NULL),(17,'IyuUWEBXY3t','$2a$10$mJJOAJe.tXv1r2Eyf.21zusgg0BMpXIcKKv0yIi5ZMEzsYnINCTd2','UEt@example.com',1,'2025-06-04 12:27:07',NULL),(18,'JJzHGWMLjSre6fA','$2a$10$MFo40ZWeC8nR0eKPlYhkZuzaeeYRvnwLJvo1WvmP0xPXXjyFOy7k.','4GEZUt8@gmail.com',1,'2025-06-04 12:27:07',NULL),(19,'PnPuOTa4ybQvkfVWD1','$2a$10$perxBJ8vv6KSej5YE6hOYeReLzjhJKJaa8iC4I1O/XwErWPYH0Lhm','KmmLXphFp@gmail.com',1,'2025-06-04 12:27:07',NULL),(20,'XazAw','$2a$10$W14m8aNhXgSRc5lCAj4eDuZC18SUIbHJQZ/2EPh2PaWQKvMs29DBq','6d9FdW9b@hotmail.com',1,'2025-06-04 12:27:07',NULL),(21,'s2zO','$2a$10$VuxEFPydy5aJnZiJDAJ2seIYBQSrSzSzplKK7FMzSR/lExPkMUys6','Ci8W3I@gmail.com',1,'2025-06-04 12:27:07',NULL),(22,'l0Sz4zpyzYF','$2a$10$v/CvqKCKAEj.t1YpFUwSUeHVzGzOqLYo8zKp0Hx8rEShWt9Ze6oti','hTYJl@hotmail.com',1,'2025-06-04 12:27:07',NULL),(23,'vB','$2a$10$3AH0AmZuHhFNle0Tc6gqcuIP5AZyfCKkLXL27MGonM6ml5jnzheJy','lOD3sc9gb0@example.com',1,'2025-06-04 12:27:07',NULL),(24,'OQHys2zz','$2a$10$pwhjmwKnON8KnNE/.gtBu.XdoMGyeKBn0BkjGSBn3E.e7jhQeg.zi','MgW@yahoo.com',1,'2025-06-04 12:27:07',NULL),(25,'XZE241gghKydKJrw','$2a$10$7I8Ulfq9hHzWS0xjGf8S1eul06Sa6MAJTIm/lQRBwGF0yQo5OdYFO','4KK9s@yahoo.com',1,'2025-06-04 12:27:07',NULL),(26,'2VgZd6uhjfP2UtDg','$2a$10$6stFiI2d1yVkDJq1f1YeZeYXhvtR1s.wi/3ifKp4fvfJffZQPANsS','BBic57GQn@hotmail.com',1,'2025-06-04 12:27:07',NULL),(27,'4PxKDGLRDa','$2a$10$DhlfHYvaFlmGxQAfCloTO.d7MLyNlXcSVRl3pWZg.4NWOLpPlxMri','T5XfFR@example.com',1,'2025-06-04 12:27:07',NULL),(28,'IEqOHfW','$2a$10$WnTcuBYxHrl8H.S/c7.Jze5XhSSVy4rOt2.kSN9duyft2dTtOEZx2','p8W61nm9nk@yahoo.com',1,'2025-06-04 12:27:07',NULL),(29,'admin','$2a$10$zghSP62p6EXrM0NWBuJtKeKlM91LelpfgHZZ5gEGKCh.a6iv3obtG','1571092454@qq.com',1,'2025-06-04 12:27:07',NULL),(31,'user_fAtMJH','$2a$10$FQgd/9zv8r/vEXQOGaqgx.IJN.RB0kLL2Zv11LxHZaseRSxB/HmIG','nz9h4NwFCx@example.com',1,'2025-06-04 20:44:04',NULL),(32,'VRDEBf8l','$2a$10$.guOBvkCNBhANCF1WmXmp.gvMMdLicpaPSuUf9UmZeHtZkZYhFmES','Jn5SxfaV@icloud.com',1,'2025-06-08 12:40:51',NULL),(33,'0DPxUEmd','$2a$10$9ECRm1StYKfre1/y2ZlBxumTN0GMHJua7omsooJXfonifHh5SYA1a','LrJzsTvU@126.com',1,'2025-06-08 16:18:04',NULL),(34,'6OM6gEG2','$2a$10$4HLvYuOyM32W0JDxKEJ4ces8inke9KPy7JxondXJFoaYzsaeI5v9q','5wbJGAUg@icloud.com',1,'2025-06-08 16:18:49',NULL),(35,'liuao','$2a$10$cCgdRPGAch1o1PxTVkHewuGKGSFCdFOD4EIpl3/r0LsSURusyxrxK','123@qq.com',1,'2025-06-08 18:44:00',NULL),(36,'BxeDWAmL','$2a$10$Z49lQ8XY2dgEq3OCQwGFmO86ULsV00KJkRLLupr2BxmP8MZncmhIu','J5Z9F15l@yahoo.com',1,'2025-06-10 18:18:59',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_cartridge_relation`
--

DROP TABLE IF EXISTS `user_cartridge_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_cartridge_relation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '关系ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `cartridge_id` bigint NOT NULL COMMENT '卡带ID',
  `is_favorite` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否收藏：0=否，1=是',
  `is_wish_list` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否想玩：0=否，1=是',
  `is_played` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否玩过：0=否，1=是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_cartridge` (`user_id`,`cartridge_id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_cartridge_id` (`cartridge_id`) USING BTREE,
  KEY `idx_is_favorite` (`is_favorite`) USING BTREE,
  KEY `idx_is_wish_list` (`is_wish_list`) USING BTREE,
  KEY `idx_is_played` (`is_played`) USING BTREE,
  CONSTRAINT `fk_ucr_cartridge` FOREIGN KEY (`cartridge_id`) REFERENCES `cartridge` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_ucr_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户与卡带关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_cartridge_relation`
--

LOCK TABLES `user_cartridge_relation` WRITE;
/*!40000 ALTER TABLE `user_cartridge_relation` DISABLE KEYS */;
INSERT INTO `user_cartridge_relation` VALUES (1,29,25,1,1,0,'2025-06-20 15:37:44','2025-06-20 15:47:00'),(29,29,21,1,1,0,'2025-06-20 15:53:54','2025-06-20 15:54:02'),(35,29,47,0,1,0,'2025-06-21 05:51:31','2025-06-21 05:51:31'),(36,29,46,0,0,0,'2025-06-26 07:14:24','2025-06-26 07:14:26');
/*!40000 ALTER TABLE `user_cartridge_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_role_id` (`role_id`),
  CONSTRAINT `FK_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (29,1),(3,3),(9,3),(10,3),(11,3),(12,3),(13,3),(14,3),(15,3),(16,3),(17,3),(18,3),(19,3),(20,3),(21,3),(22,3),(23,3),(24,3),(25,3),(26,3),(27,3),(28,3),(31,3),(32,3),(33,3),(34,3),(35,3),(36,3);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'game_card_sell'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-24  4:33:02
