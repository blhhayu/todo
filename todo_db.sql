-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: todo_db
-- ------------------------------------------------------
-- Server version	8.0.45

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
-- Current Database: `todo_db`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `todo_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `todo_db`;

--
-- Table structure for table `todo`
--

DROP TABLE IF EXISTS `todo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `todo` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(100) NOT NULL COMMENT '事项标题',
  `description` varchar(500) DEFAULT NULL COMMENT '事件描述',
  `completed` tinyint(1) DEFAULT '0' COMMENT '是否完成（0：未完成，1：已完成）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_id` bigint DEFAULT NULL COMMENT '关联的用户ID',
  PRIMARY KEY (`id`),
  KEY `fk_todo_user_id` (`user_id`),
  CONSTRAINT `fk_todo_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='待办事项表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `todo`
--

LOCK TABLES `todo` WRITE;
/*!40000 ALTER TABLE `todo` DISABLE KEYS */;
INSERT INTO `todo` VALUES (3,'测试待办','这是第一个关联用户的待办',0,NULL,NULL,3),(4,'测试待办','这是第一个关联用户的待办',0,NULL,NULL,3),(6,'测试待办','这是第一个关联用户的待办',0,NULL,NULL,4),(7,'测试','这是一条测试待办',0,'2026-09-15 14:41:27','2026-09-15 14:41:27',NULL),(8,'测试','这是一user1的测试待办',0,'2026-09-16 16:48:44','2026-09-16 16:48:44',20),(9,'测试1','这是一user1的测试待办',0,'2026-09-16 16:49:19','2026-09-16 16:49:19',20),(10,'测试3','这是一user1的测试待办',0,'2026-09-16 16:49:35','2026-09-16 16:49:35',20),(11,'测试a','这是一user2的测试待办',0,'2026-09-16 16:52:02','2026-09-16 16:52:02',21),(12,'测试b','这是一user2的测试待办',0,'2026-09-16 16:52:24','2026-09-16 16:52:24',21),(13,'测试c','这是一user2的测试待办',0,'2026-09-16 16:52:39','2026-09-16 16:52:39',21),(15,'修改测试','这是一user3的测试待办',0,'2026-09-16 19:23:21','2026-09-16 19:43:29',22),(16,'修改测试','这是一user3的测试待办',0,'2026-09-16 19:51:54','2026-09-16 20:25:17',22),(17,'修改测试','这是一user3的测试的测试待办',0,'2026-09-16 21:09:26','2026-09-16 21:44:10',22);
/*!40000 ALTER TABLE `todo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户主键ID',
  `username` varchar(50) NOT NULL COMMENT '用户名（唯一）',
  `password` varchar(100) NOT NULL COMMENT '密码（加密存储）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (3,'admin','$2a$10$4gEW1aZlgrE1AphvkZ.AzOkZqw40ioKi6kSPlXYp/cNX2uXvMDSji',NULL,NULL),(4,'dawf','$2a$10$Mci1Oz7KLTlYE0yB1yu28eaiesgSNQdTDiMalZFdG3suFZaR.fnFi',NULL,NULL),(5,'cakdw','$2a$10$jQnoh//0IDIHvxFt3lqaVez4GfPrOz4jmA8Uy1.JSW/t9QJkYygf.','2026-09-11 22:24:08','2026-09-11 22:24:08'),(6,'vesnf','$2a$10$k8AbTuui83cH4SHmKkbyyuT2D6jGSvEKWdCTmDzV5C3g0zd4lscqy','2026-09-12 21:22:02','2026-09-12 21:22:02'),(7,'adfegse','$2a$10$1ZdDTx1MTIYoeDFpJiXrn.SoKdELXr3SgcZu6PBiCUL4QTHx82ZsW','2026-09-12 21:26:54','2026-09-12 21:26:54'),(8,'{{username}}','$2a$10$P/7U9nreMExkk3tlHijlW.Apo3jDpJjhir4JhFmoH2v3qDByotPSS','2026-09-12 22:38:06','2026-09-12 22:38:06'),(9,'rdkrg','$2a$10$mXk2IYyB4PY9xnisn9fQkenNp5yKxDUhQJm44UnrAXtUm4wlkvmjK','2026-09-12 23:04:15','2026-09-12 23:04:15'),(11,'htshtt','$2a$10$29Ne150Mby7a89G5QEG.m.2.BDFanEhgZNOzo1g4QQcu6a53T9.Ia','2026-09-12 23:16:28','2026-09-12 23:16:28'),(13,'zhangsan','$2a$10$iTT6pxKi7bsPmvGLvOoYrOboSHP3.aZVg5E.LNXcBM77n6yH8Fg.q','2026-09-13 15:42:59','2026-09-13 15:42:59'),(14,'gsejgsne','$2a$10$/l/E2TdAbk3tVY6vV1kLfezv4234Usm/7yGl7Duj38fvfgYPe7WEG','2026-09-13 16:55:37','2026-09-13 16:55:37'),(15,'fwifah','$2a$10$004quDaI/RD.wZZYQ..ruuom9WJdP/iBdbFxk5BjbU6/7fCYRdeN.','2026-09-13 20:31:51','2026-09-13 20:31:51'),(16,'fawjie','$2a$10$icUJczWk8t68bKRQDTwG1ubPLWntjF0NUxxdf5tUNBc0xDrnQatu.','2026-09-13 21:37:56','2026-09-13 21:37:56'),(19,'fsenef','$2a$10$elxP5GpOQcAlQBlAF8FwnuIlK.UsbmzMdWivtjImc.2DbVqc5KeRG','2026-09-15 14:39:02','2026-09-15 14:39:02'),(20,'user1','$2a$10$gBmQfe1Cbc1XyG1YDA9ese66XYTapWYSNgeqoXXR6n.okiE9kHeXK','2026-09-16 16:46:04','2026-09-16 16:46:04'),(21,'user2','$2a$10$TdZzxeMRSO1UPq9Mkq0N8..j8cnBxCWoEBH1Oz7iv1g.aALP5wKBK','2026-09-16 16:49:55','2026-09-16 16:49:55'),(22,'user3','$2a$10$dVgttyW8DXknUXSBC6wbMuM0LUaq0ownJ/KrdYmVpp9BsbXM.k9h6','2026-09-16 18:07:37','2026-09-16 18:07:37'),(23,'sedrhf','$2a$10$Hc6nF7mNaFW4qM80fMpcXujRL0Qm7zifh9Llnsr5z738esT.u0EMK','2026-09-16 21:55:37','2026-09-16 21:55:37');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-09-19 14:55:10
