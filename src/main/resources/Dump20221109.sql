-- MySQL dump 10.13  Distrib 8.0.31, for macos12 (x86_64)
--
-- Host: 127.0.0.1    Database: meli_fresh
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `announcement`
--

DROP TABLE IF EXISTS `announcement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `announcement` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `seller_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkjp0ij7gmr87d524p9mfrylx4` (`seller_id`),
  CONSTRAINT `FKkjp0ij7gmr87d524p9mfrylx4` FOREIGN KEY (`seller_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcement`
--

LOCK TABLES `announcement` WRITE;
/*!40000 ALTER TABLE `announcement` DISABLE KEYS */;
INSERT INTO `announcement` VALUES (1,'Maçã','descricao',3),(2,'Banana','descricao',3),(3,'Sorvete','descricao',4),(4,'Carne Moida','descricao',4),(5,'Manteiga','descricao',3),(6,'Requeijão','descricao',4),(7,'Cereja','descricao',3),(8,'Queijo','descricao',3),(9,'Camarão','descricao',4);
/*!40000 ALTER TABLE `announcement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `batch_stock`
--

DROP TABLE IF EXISTS `batch_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `batch_stock` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `announcement_id` bigint NOT NULL,
  `current_temperature` float NOT NULL,
  `due_date` date NOT NULL,
  `manufacturing_date` date NOT NULL,
  `manufacturing_time` datetime(6) NOT NULL,
  `order_number_id` bigint NOT NULL,
  `price` decimal(19,2) NOT NULL,
  `product_quantity` int NOT NULL,
  `volume` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3lts4yvcd4w8xpi15b2ru4cm8` (`announcement_id`),
  KEY `FK2wl49q6kcl6bpqub3mv0q8upv` (`order_number_id`),
  CONSTRAINT `FK2wl49q6kcl6bpqub3mv0q8upv` FOREIGN KEY (`order_number_id`) REFERENCES `inbound_order` (`id`),
  CONSTRAINT `FK3lts4yvcd4w8xpi15b2ru4cm8` FOREIGN KEY (`announcement_id`) REFERENCES `announcement` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batch_stock`
--

LOCK TABLES `batch_stock` WRITE;
/*!40000 ALTER TABLE `batch_stock` DISABLE KEYS */;
INSERT INTO `batch_stock` VALUES (1,1,17,'2022-12-30','2022-08-13','2020-08-13 17:55:00.000000',1,10.00,2,2.5),(2,2,18,'2022-12-30','2022-08-13','2020-08-13 17:55:00.000000',1,20.00,2,3.5),(3,3,1,'2023-12-31','2022-08-13','2020-08-13 17:55:00.000000',1,1.00,2,4),(4,4,2,'2023-02-28','2022-08-13','2020-08-13 17:55:00.000000',2,1.00,3,3),(5,5,10,'2023-11-25','2022-08-13','2020-08-13 17:55:00.000000',2,15.00,3,1),(6,6,13,'2023-01-05','2022-08-13','2020-08-13 17:55:00.000000',2,8.00,3,1),(7,7,13,'2022-12-30','2022-08-13','2020-08-13 17:55:00.000000',3,12.00,1,1),(8,8,10,'2022-12-30','2022-08-13','2020-08-13 17:55:00.000000',4,15.00,1,1),(9,9,3,'2023-01-15','2022-08-13','2020-08-13 17:55:00.000000',4,5.00,2,2.5);
/*!40000 ALTER TABLE `batch_stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inbound_order`
--

DROP TABLE IF EXISTS `inbound_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inbound_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_date` date NOT NULL,
  `section_code` bigint NOT NULL,
  `warehouse_code` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf3ycfu0pg4auw1tu7mee2rkbe` (`section_code`),
  KEY `FKt40w6a2pkaqetpo7gfax7j01l` (`warehouse_code`),
  CONSTRAINT `FKf3ycfu0pg4auw1tu7mee2rkbe` FOREIGN KEY (`section_code`) REFERENCES `section` (`id`),
  CONSTRAINT `FKt40w6a2pkaqetpo7gfax7j01l` FOREIGN KEY (`warehouse_code`) REFERENCES `warehouse` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inbound_order`
--

LOCK TABLES `inbound_order` WRITE;
/*!40000 ALTER TABLE `inbound_order` DISABLE KEYS */;
INSERT INTO `inbound_order` VALUES (1,'2023-11-09',1,1),(2,'2023-11-09',2,2),(3,'2023-11-09',2,1),(4,'2023-11-09',3,3);
/*!40000 ALTER TABLE `inbound_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `section`
--

DROP TABLE IF EXISTS `section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `section` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `section`
--

LOCK TABLES `section` WRITE;
/*!40000 ALTER TABLE `section` DISABLE KEYS */;
INSERT INTO `section` VALUES (1,'Fresco'),(2,'Fresco'),(3,'Refrigerado'),(4,'Refrigerado'),(5,'Congelado'),(6,'Congelado');
/*!40000 ALTER TABLE `section` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role` varchar(30) NOT NULL,
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'JonatasBene','1234','jonatasbene@email.com','buyer'),(2,'BrunaCampos','1234','brunacampos@email.com','buyer'),(3,'RosySantos','5678','rosysantos@email.com','seller'),(4,'EstherPorto','5678','estherporto@email.com','seller'),(5,'MarcelloAlves','9090','marcelloalves@email.com','manager'),(6,'HugoCaxias','9090','hugocaxias@email.com','manager');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `warehouse_name` varchar(50) NOT NULL,
  `id_user` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl0lq5qohv9c6yhr39nshhkp70` (`id_user`),
  CONSTRAINT `FKl0lq5qohv9c6yhr39nshhkp70` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse`
--

LOCK TABLES `warehouse` WRITE;
/*!40000 ALTER TABLE `warehouse` DISABLE KEYS */;
INSERT INTO `warehouse` VALUES (1,'MLBSP01',5),(2,'MLBSP02',5),(3,'MLARBA01',6);
/*!40000 ALTER TABLE `warehouse` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-09 12:18:43
