-- MySQL dump 10.13  Distrib 8.0.36, for macos14 (x86_64)
--
-- Host: localhost    Database: mdd_app
-- ------------------------------------------------------
-- Server version	8.3.0

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
-- Table structure for table `articles`
--

DROP TABLE IF EXISTS `articles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `articles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `contents` varchar(1000) DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `topics_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi6prltuwudqmd7lo0tdqaodvs` (`topics_id`),
  KEY `FKlc3sm3utetrj1sx4v9ahwopnr` (`user_id`),
  CONSTRAINT `FKi6prltuwudqmd7lo0tdqaodvs` FOREIGN KEY (`topics_id`) REFERENCES `topics` (`id`),
  CONSTRAINT `FKlc3sm3utetrj1sx4v9ahwopnr` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articles`
--

LOCK TABLES `articles` WRITE;
/*!40000 ALTER TABLE `articles` DISABLE KEYS */;
INSERT INTO `articles` VALUES (1,'Lorem ipsum odor amet, consectetuer adipiscing elit. Vitae mus nunc non penatibus; morbi tincidunt vel. Felis ut viverra id nunc tortor vulputate facilisis. Sapien magna vitae placerat suspendisse potenti vulputate facilisis. Sed rutrum tellus pharetra pellentesque aliquet. Maecenas varius lacus pulvinar molestie et. Lectus fames magnis montes urna; laoreet suscipit integer.','2024-07-29 15:51:43.399000','Le langage Java',4,6),(2,'Lorem ipsum odor amet, consectetuer adipiscing elit. Vitae mus nunc non penatibus; morbi tincidunt vel. Felis ut viverra id nunc tortor vulputate facilisis. Sapien magna vitae placerat suspendisse potenti vulputate facilisis. Sed rutrum tellus pharetra pellentesque aliquet. Maecenas varius lacus pulvinar molestie et. Lectus fames magnis montes urna; laoreet suscipit integer.','2024-07-29 15:52:27.187000','Vos tips sur Spring Boot',5,6),(3,'Lorem ipsum odor amet, consectetuer adipiscing elit. Vitae mus nunc non penatibus; morbi tincidunt vel. Felis ut viverra id nunc tortor vulputate facilisis. Sapien magna vitae placerat suspendisse potenti vulputate facilisis. Sed rutrum tellus pharetra pellentesque aliquet. Maecenas varius lacus pulvinar molestie et. Lectus fames magnis montes urna; laoreet suscipit integer.','2024-07-29 15:53:54.937000','Retour sur Zelda 2',7,7),(4,'Lorem ipsum odor amet, consectetuer adipiscing elit. Vitae mus nunc non penatibus; morbi tincidunt vel. Felis ut viverra id nunc tortor vulputate facilisis. Sapien magna vitae placerat suspendisse potenti vulputate facilisis. Sed rutrum tellus pharetra pellentesque aliquet. Maecenas varius lacus pulvinar molestie et. Lectus fames magnis montes urna; laoreet suscipit integer.','2024-07-29 15:56:01.811000','Back to the Future, the best !',8,8);
/*!40000 ALTER TABLE `articles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `articles_comments_list`
--

DROP TABLE IF EXISTS `articles_comments_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `articles_comments_list` (
  `articles_id` int NOT NULL,
  `comments_list_id` int NOT NULL,
  UNIQUE KEY `UK1l7ue4mfncrinhu1cju9cvvyg` (`comments_list_id`),
  KEY `FK3cj6mqltpq88pyl7dj71nu7q2` (`articles_id`),
  CONSTRAINT `FK3cj6mqltpq88pyl7dj71nu7q2` FOREIGN KEY (`articles_id`) REFERENCES `articles` (`id`),
  CONSTRAINT `FKcuip0h73vvbgygask9rwxvbyn` FOREIGN KEY (`comments_list_id`) REFERENCES `comments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articles_comments_list`
--

LOCK TABLES `articles_comments_list` WRITE;
/*!40000 ALTER TABLE `articles_comments_list` DISABLE KEYS */;
INSERT INTO `articles_comments_list` VALUES (2,1);
/*!40000 ALTER TABLE `articles_comments_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `comments` varchar(255) DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `users_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt55y2infwbbw3o942o2mhm0v4` (`users_id`),
  CONSTRAINT `FKt55y2infwbbw3o942o2mhm0v4` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,'Oui je suis d\'accord.','2024-07-29 15:53:31.468000',7);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topics`
--

DROP TABLE IF EXISTS `topics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topics` (
  `id` int NOT NULL AUTO_INCREMENT,
  `label` varchar(255) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `users_id` int DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK70krri02hxpu1le8pt4kaf42t` (`users_id`),
  KEY `FKfb9buem04s44itt3fb8vhru1l` (`id_user`),
  CONSTRAINT `FK70krri02hxpu1le8pt4kaf42t` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKfb9buem04s44itt3fb8vhru1l` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topics`
--

LOCK TABLES `topics` WRITE;
/*!40000 ALTER TABLE `topics` DISABLE KEYS */;
INSERT INTO `topics` VALUES (4,'Java','Elementum duis habitasse suscipit mollis nisl litora taciti consequat lacinia. Inceptos inceptos class rhoncus massa dis libero nisl class. ',NULL,NULL),(5,'Spring Boot','Elementum duis habitasse suscipit mollis nisl litora taciti consequat lacinia. Inceptos inceptos class rhoncus massa dis libero nisl class. ',NULL,NULL),(6,'Angular','Elementum duis habitasse suscipit mollis nisl litora taciti consequat lacinia. Inceptos inceptos class rhoncus massa dis libero nisl class. ',NULL,NULL),(7,'Video Games','Elementum duis habitasse suscipit mollis nisl litora taciti consequat lacinia. Inceptos inceptos class rhoncus massa dis libero nisl class. ',NULL,NULL),(8,'Movies','Elementum duis habitasse suscipit mollis nisl litora taciti consequat lacinia. Inceptos inceptos class rhoncus massa dis libero nisl class. ',NULL,NULL),(9,'Space','At ac imperdiet faucibus fusce natoque accumsan nam lacinia. Ligula vivamus suspendisse amet eget sed vestibulum congue porttitor.',NULL,NULL);
/*!40000 ALTER TABLE `topics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topics_user`
--

DROP TABLE IF EXISTS `topics_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topics_user` (
  `id_user` int NOT NULL,
  `id_topic` int NOT NULL,
  UNIQUE KEY `UKbru3incdieb0tv9id0ytcu2ui` (`id_user`,`id_topic`),
  KEY `FKjr3bp1ddu8i0c4g1ed86pflia` (`id_topic`),
  CONSTRAINT `FKjr3bp1ddu8i0c4g1ed86pflia` FOREIGN KEY (`id_topic`) REFERENCES `topics` (`id`),
  CONSTRAINT `FKrjchy9vwhrhy85pt4mn4x4sm8` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topics_user`
--

LOCK TABLES `topics_user` WRITE;
/*!40000 ALTER TABLE `topics_user` DISABLE KEYS */;
INSERT INTO `topics_user` VALUES (6,4),(7,4),(8,4),(6,5),(7,5),(8,5),(6,7),(8,7),(8,8);
/*!40000 ALTER TABLE `topics_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `pseudo` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (6,'2024-07-29 13:52:03.837000','user@test.com','$2a$10$TKwv72u6qOGt4D1NP0I9XOgbyIkp.8CUcDz1avwcn3E7vok01XC/O','username','2024-07-29 13:52:03.837000'),(7,'2024-07-29 15:53:06.817000','maru@test.com','$2a$10$ImzJBJLF4K.m1/vHCBHj7uzKcNXgXes0cQOZMcmxqd8M5ED84Q.z2','marush974','2024-07-31 08:36:37.066000'),(8,'2024-07-29 15:54:59.718000','test@test.com','$2a$10$w1n6HKjpclSWHPM4J/0TguBA5Bd2ejbSORLAE4ooeAQO5w7SwHLl6','testuser','2024-07-29 15:54:59.718000');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-06 20:28:51
