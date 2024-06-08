-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: 10.88.0.5    Database: dndgames
-- ------------------------------------------------------
-- Server version	5.5.5-10.11.7-MariaDB-1:10.11.7+maria~ubu2204

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `CampaignPosts`
--

DROP TABLE IF EXISTS `CampaignPosts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CampaignPosts` (
  `post_id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_id` int(11) NOT NULL,
  `author_id` int(11) NOT NULL,
  `text` mediumtext NOT NULL,
  `visibility` enum('MASTER','SINGLE_PLAYER','EVERYBODY') NOT NULL,
  `game_date` date NOT NULL,
  `post_date` datetime NOT NULL,
  PRIMARY KEY (`post_id`),
  KEY `campaign_id` (`campaign_id`),
  KEY `author_id` (`author_id`),
  CONSTRAINT `CampaignPosts_ibfk_1` FOREIGN KEY (`campaign_id`) REFERENCES `Campaigns` (`campaign_id`),
  CONSTRAINT `CampaignPosts_ibfk_2` FOREIGN KEY (`author_id`) REFERENCES `Users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CampaignPosts`
--

LOCK TABLES `CampaignPosts` WRITE;
/*!40000 ALTER TABLE `CampaignPosts` DISABLE KEYS */;
INSERT INTO `CampaignPosts` VALUES (1,83,3,'ExamplePostText asf 348967 *(&*&(^_+)#(@_) sdghfdhdf','EVERYBODY','2024-04-13','2024-03-13 16:54:00'),(2,83,3,' Проверка или не проверка','MASTER','2003-03-14','2024-04-15 18:56:09'),(3,84,3,'Ещё одна проверка в новой кампании','MASTER','2024-04-15','2024-04-15 19:52:57'),(8,83,3,'Несколько постов в одной партии','EVERYBODY','2024-04-15','2024-04-21 19:25:28'),(9,83,3,'Несколько постов в одной партии','EVERYBODY','2024-04-15','2024-04-21 19:25:39'),(10,83,3,'1. пункт 1\r\n бла бла бла\r\n2. пункт 2\r\n бла бла бла','EVERYBODY','2024-04-15','2024-04-21 19:28:43'),(12,83,3,' бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бла бла бла  бл','MASTER','2024-04-21','2024-04-21 21:51:17'),(13,86,3,'wsjesuhugdfiwsuefgiwuse\r\n','EVERYBODY','2003-03-13','2024-04-21 22:14:51'),(15,84,5,'esdgrsgd','EVERYBODY','2024-04-14','2024-04-26 22:33:58'),(16,100,5,'rfehrhe &#13;&#10; ваапооа','EVERYBODY','2011-11-11','2024-04-27 19:00:06'),(17,83,5,'saf','EVERYBODY','2024-04-17','2024-04-27 19:29:46'),(18,83,5,'iuiuti7ott\r\n','EVERYBODY','2024-04-19','2024-04-27 19:31:17'),(19,95,5,'rdhthdr','SINGLE_PLAYER','2022-03-11','2024-04-27 19:38:44'),(53,98,5,'rsdhhdfr','MASTER','2011-11-11','2024-04-28 12:31:32'),(54,98,5,'не скрыт\r\n','EVERYBODY','2011-11-11','2024-04-28 12:31:46'),(55,98,14,'текст','EVERYBODY','2011-11-11','2024-04-28 12:32:49'),(56,98,14,'вапор','EVERYBODY','2011-11-12','2024-04-28 13:08:07'),(57,98,14,'тест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрштест варпващшпрщшварпщшварпрщшващпрш','EVERYBODY','2011-11-12','2024-04-28 13:14:23'),(58,98,14,'бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла ','EVERYBODY','2011-11-12','2024-04-28 13:16:46'),(59,100,5,'tjftjf','EVERYBODY','2000-12-11','2024-04-28 14:34:46'),(60,100,5,'rhdrh','EVERYBODY','2011-11-11','2024-04-28 14:34:54');
/*!40000 ALTER TABLE `CampaignPosts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CampaignUsers`
--

DROP TABLE IF EXISTS `CampaignUsers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CampaignUsers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `campaign_id` int(11) NOT NULL,
  `player_role` enum('MASTER','PLAYER','NONE') NOT NULL,
  `character_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `campaign_id` (`campaign_id`),
  KEY `Character_id_NULLABLE` (`character_id`),
  CONSTRAINT `CampaignUsers_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`),
  CONSTRAINT `CampaignUsers_ibfk_2` FOREIGN KEY (`campaign_id`) REFERENCES `Campaigns` (`campaign_id`),
  CONSTRAINT `CampaignUsers_ibfk_3` FOREIGN KEY (`character_id`) REFERENCES `Characters` (`character_id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CampaignUsers`
--

LOCK TABLES `CampaignUsers` WRITE;
/*!40000 ALTER TABLE `CampaignUsers` DISABLE KEYS */;
INSERT INTO `CampaignUsers` VALUES (6,3,83,'MASTER',NULL),(7,3,84,'MASTER',NULL),(8,5,84,'PLAYER',8),(19,3,86,'MASTER',NULL),(23,5,83,'PLAYER',NULL),(24,3,87,'MASTER',NULL),(28,8,92,'MASTER',NULL),(29,8,93,'MASTER',NULL),(30,8,94,'MASTER',NULL),(31,5,95,'MASTER',NULL),(32,5,96,'MASTER',NULL),(33,5,97,'MASTER',NULL),(34,5,98,'MASTER',NULL),(35,5,99,'MASTER',NULL),(36,5,100,'MASTER',NULL),(48,14,98,'PLAYER',NULL);
/*!40000 ALTER TABLE `CampaignUsers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Campaigns`
--

DROP TABLE IF EXISTS `Campaigns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Campaigns` (
  `campaign_id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_name` varchar(100) NOT NULL,
  `owner_id` int(11) NOT NULL,
  PRIMARY KEY (`campaign_id`),
  KEY `owner_id` (`owner_id`),
  CONSTRAINT `Campaigns_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `Users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Campaigns`
--

LOCK TABLES `Campaigns` WRITE;
/*!40000 ALTER TABLE `Campaigns` DISABLE KEYS */;
INSERT INTO `Campaigns` VALUES (83,'Example',3),(84,'новый пример',3),(86,'Example325',3),(87,'BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB',3),(92,'аааа',8),(93,'ааааааааа',8),(94,'ааааааааааа',8),(95,'4',5),(96,'5',5),(97,'6',5),(98,'7',5),(99,'a',5),(100,'sdgf',5);
/*!40000 ALTER TABLE `Campaigns` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Characters`
--

DROP TABLE IF EXISTS `Characters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Characters` (
  `character_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `class` varchar(100) NOT NULL,
  `race` varchar(50) NOT NULL,
  `level` int(11) NOT NULL,
  PRIMARY KEY (`character_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `Characters_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Characters`
--

LOCK TABLES `Characters` WRITE;
/*!40000 ALTER TABLE `Characters` DISABLE KEYS */;
INSERT INTO `Characters` VALUES (4,3,'Example Character','Example Class','Example Race',3),(7,3,'Петя','Петя','Петя',20),(8,5,'example','example','example',1),(9,5,'4','4','4',4),(10,5,'5','5','5',5),(11,5,'6','6','6',6),(14,5,'serhgdff','esghdrdhf','esghdrdhf',14),(15,5,'11','11','11',11),(16,5,'12','12','12',12),(17,5,'13','13','13',13),(18,5,'14','14','14',14),(19,5,'15','15','15',15),(20,5,'16','16','16',16),(21,5,'17','17','17',17),(22,5,'18','18','18',18),(23,5,'19','19','19',19),(24,5,'20','20','20',20);
/*!40000 ALTER TABLE `Characters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `hashed_password` varchar(255) NOT NULL,
  `user_role` enum('USER','REDACTOR','MODERATOR','ADMIN') NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES (3,'Admin','c8b3533a71d70c2973dc313c15131dd440733e31f02cedd1d1a05faecf40c6c9','ADMIN'),(5,'GOBLIN_THE_SLAYER','c8b3533a71d70c2973dc313c15131dd440733e31f02cedd1d1a05faecf40c6c9','USER'),(8,'KOBOLD_THE_DESTROYER','c8b3533a71d70c2973dc313c15131dd440733e31f02cedd1d1a05faecf40c6c9','USER'),(9,'Redactor','c8b3533a71d70c2973dc313c15131dd440733e31f02cedd1d1a05faecf40c6c9','REDACTOR'),(10,'Moderator','c8b3533a71d70c2973dc313c15131dd440733e31f02cedd1d1a05faecf40c6c9','MODERATOR'),(14,'exampleUser2','c8b3533a71d70c2973dc313c15131dd440733e31f02cedd1d1a05faecf40c6c9','USER');
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flyway_schema_history`
--

LOCK TABLES `flyway_schema_history` WRITE;
/*!40000 ALTER TABLE `flyway_schema_history` DISABLE KEYS */;
INSERT INTO `flyway_schema_history` VALUES (1,'1','Base','SQL','V1__Base.sql',-1527967175,'root','2024-05-14 12:28:16',231,1),(2,'2','WithData','SQL','V2__WithData.sql',1844648836,'root','2024-05-14 12:44:29',613,1);
/*!40000 ALTER TABLE `flyway_schema_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'dndgames'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-14 15:47:55
