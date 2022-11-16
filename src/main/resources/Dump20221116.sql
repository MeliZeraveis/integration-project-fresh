-- MySQL dump 10.13  Distrib 8.0.31, for macos12 (x86_64)
--
-- Host: localhost    Database: meli_fresh
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
  `price` decimal(11,2) NOT NULL,
  `section` bigint NOT NULL,
  `seller_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7rdhbftg9ekxsn5rvynoun5by` (`section`),
  KEY `FKkjp0ij7gmr87d524p9mfrylx4` (`seller_id`),
  CONSTRAINT `FK7rdhbftg9ekxsn5rvynoun5by` FOREIGN KEY (`section`) REFERENCES `section` (`id`),
  CONSTRAINT `FKkjp0ij7gmr87d524p9mfrylx4` FOREIGN KEY (`seller_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcement`
--

LOCK TABLES `announcement` WRITE;
/*!40000 ALTER TABLE `announcement` DISABLE KEYS */;
INSERT INTO `announcement` VALUES (1,'descricao','Maçã',2.50,1,3),(2,'descricao','Banana',3.00,1,3),(3,'descricao','Sorvete',20.00,3,4),(4,'descricao','Carne Moida',40.00,3,4),(5,'descricao','Manteiga',5.55,2,3),(6,'descricao','Requeijão',6.75,2,4),(7,'descricao','Cereja',18.55,3,3),(8,'descricao','Queijo',45.70,2,3),(9,'descricao','Camarão',80.70,3,4);
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
  `due_date` date NOT NULL,
  `manufacturing_date` date NOT NULL,
  `manufacturing_time` datetime(6) NOT NULL,
  `order_number_id` bigint NOT NULL,
  `price` decimal(19,2) NOT NULL,
  `product_quantity` int NOT NULL,
  `section_type` varchar(255) NOT NULL,
  `volume` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3lts4yvcd4w8xpi15b2ru4cm8` (`announcement_id`),
  KEY `FK2wl49q6kcl6bpqub3mv0q8upv` (`order_number_id`),
  CONSTRAINT `FK2wl49q6kcl6bpqub3mv0q8upv` FOREIGN KEY (`order_number_id`) REFERENCES `inbound_order` (`id`),
  CONSTRAINT `FK3lts4yvcd4w8xpi15b2ru4cm8` FOREIGN KEY (`announcement_id`) REFERENCES `announcement` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batch_stock`
--

LOCK TABLES `batch_stock` WRITE;
/*!40000 ALTER TABLE `batch_stock` DISABLE KEYS */;
INSERT INTO `batch_stock` VALUES (1,1,'2022-12-30','2022-08-13','2022-08-13 17:55:00.000000',1,450.00,150,'Fresh',150),(2,2,'2022-12-30','2022-08-13','2022-08-13 17:55:00.000000',1,450.00,150,'Fresh',150),(3,3,'2023-12-31','2022-08-13','2022-08-13 17:55:00.000000',1,2000.00,100,'Refrigerated',150),(4,4,'2023-02-27','2022-08-13','2022-08-13 17:55:00.000000',2,4000.00,100,'Refrigerated',100),(5,5,'2023-11-25','2022-08-13','2022-08-13 17:55:00.000000',2,555.00,100,'Frozen',100),(6,6,'2023-01-05','2022-08-13','2022-08-13 17:55:00.000000',3,675.00,100,'Frozen',100);
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inbound_order`
--

LOCK TABLES `inbound_order` WRITE;
/*!40000 ALTER TABLE `inbound_order` DISABLE KEYS */;
INSERT INTO `inbound_order` VALUES (1,'2023-01-25',1,1),(2,'2023-01-25',1,1),(3,'2023-01-25',3,2);
/*!40000 ALTER TABLE `inbound_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_order`
--

DROP TABLE IF EXISTS `purchase_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `buyer_id` bigint NOT NULL,
  `date` datetime(6) NOT NULL,
  `status` varchar(255) NOT NULL,
  `total` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnx7lx9kjhgwik0x63nyk1wnuo` (`buyer_id`),
  CONSTRAINT `FKnx7lx9kjhgwik0x63nyk1wnuo` FOREIGN KEY (`buyer_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_order`
--

LOCK TABLES `purchase_order` WRITE;
/*!40000 ALTER TABLE `purchase_order` DISABLE KEYS */;
INSERT INTO `purchase_order` VALUES (1,1,'2022-12-30 00:00:00.000000','Aberto',150.00),(2,1,'2022-12-30 00:00:00.000000','Finalizado',450.00),(3,1,'2022-12-30 00:00:00.000000','Finalizado',300.00),(4,2,'2022-12-30 00:00:00.000000','Finalizado',150.00),(5,2,'2022-12-30 00:00:00.000000','Finalizado',320.00),(6,1,'2022-12-30 00:00:00.000000','Finalizado',450.77),(7,2,'2022-12-30 00:00:00.000000','Finalizado',125.12),(8,1,'2022-12-30 00:00:00.000000','Finalizado',1000.55),(9,2,'2022-12-30 00:00:00.000000','Finalizado',150.88);
/*!40000 ALTER TABLE `purchase_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_order_items`
--

DROP TABLE IF EXISTS `purchase_order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_order_items` (
  `id` bigint NOT NULL,
  `announcement_id` bigint NOT NULL,
  `product_price` double NOT NULL,
  `product_quantity` int NOT NULL,
  `purchase_order_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh6o5mrr6xdv31e5vxkekur42m` (`announcement_id`),
  KEY `FKe5vf73k1ouhsxpth3vp7rnfis` (`purchase_order_id`),
  CONSTRAINT `FKe5vf73k1ouhsxpth3vp7rnfis` FOREIGN KEY (`purchase_order_id`) REFERENCES `purchase_order` (`id`),
  CONSTRAINT `FKh6o5mrr6xdv31e5vxkekur42m` FOREIGN KEY (`announcement_id`) REFERENCES `announcement` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_order_items`
--

LOCK TABLES `purchase_order_items` WRITE;
/*!40000 ALTER TABLE `purchase_order_items` DISABLE KEYS */;
INSERT INTO `purchase_order_items` VALUES (1,1,807,10,1),(2,8,450.7,10,2),(3,7,185.5,10,3),(4,6,101.25,15,4),(5,5,27.75,5,5),(6,4,80,2,6),(7,3,20,1,7),(8,2,12,4,8),(9,1,5,2,9);
/*!40000 ALTER TABLE `purchase_order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `section`
--

DROP TABLE IF EXISTS `section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `section` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `max_capacity` float NOT NULL,
  `type` varchar(50) NOT NULL,
  `used_capacity` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `section`
--

LOCK TABLES `section` WRITE;
/*!40000 ALTER TABLE `section` DISABLE KEYS */;
INSERT INTO `section` VALUES (1,1000,'Fresh',800),(2,1500,'Refrigerated',1000),(3,2000,'Frozen',1200);
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'jonatasbene@email.com','1234','buyer','JonatasBene'),(2,'brunacampos@email.com','1234','buyer','BrunaCampos'),(3,'rosysantos@email.com','5678','seller','RosySantos'),(4,'estherporto@email.com','5678','seller','EstherPorto'),(5,'marcelloalves@email.com','9090','manager','MarcelloAlves'),(6,'hugocaxias@email.com','9090','manager','HugoCaxias'),(7,'fulano@email.com','9080','manager','FulanoSilva');
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
  `address` varchar(100) NOT NULL,
  `country_code` varchar(2) NOT NULL,
  `name` varchar(50) NOT NULL,
  `manager_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmtyr26fo530xqjuttkatdh7xp` (`manager_id`),
  CONSTRAINT `FKmtyr26fo530xqjuttkatdh7xp` FOREIGN KEY (`manager_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse`
--

LOCK TABLES `warehouse` WRITE;
/*!40000 ALTER TABLE `warehouse` DISABLE KEYS */;
INSERT INTO `warehouse` VALUES (1,'Address A','BR','MLBSP01',5),(2,'Address B','BR','MLBSP02',7),(3,'AddressC','AR','MLARBA01',6),(4,'Address B','BR','MLBSP02',1);
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

-- Dump completed on 2022-11-16 17:53:09
-- MySQL dump 10.13  Distrib 8.0.31, for macos12 (x86_64)
--
-- Host: localhost    Database: meli_fresh_test
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
  `price` decimal(11,2) NOT NULL,
  `section` bigint NOT NULL,
  `seller_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7rdhbftg9ekxsn5rvynoun5by` (`section`),
  KEY `FKkjp0ij7gmr87d524p9mfrylx4` (`seller_id`),
  CONSTRAINT `FK7rdhbftg9ekxsn5rvynoun5by` FOREIGN KEY (`section`) REFERENCES `section` (`id`),
  CONSTRAINT `FKkjp0ij7gmr87d524p9mfrylx4` FOREIGN KEY (`seller_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcement`
--

LOCK TABLES `announcement` WRITE;
/*!40000 ALTER TABLE `announcement` DISABLE KEYS */;
INSERT INTO `announcement` VALUES (1,'descricao','Maçã',2.50,1,3),(2,'descricao','Banana',3.00,1,3),(3,'descricao','Sorvete',20.00,3,4),(4,'descricao','Carne Moida',40.00,3,4),(5,'descricao','Manteiga',5.55,2,3),(6,'descricao','Requeijão',6.75,2,4),(7,'descricao','Cereja',18.55,3,3),(8,'descricao','Queijo',45.70,2,3),(9,'descricao','Camarão',80.70,3,4);
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
  `due_date` date NOT NULL,
  `manufacturing_date` date NOT NULL,
  `manufacturing_time` datetime(6) NOT NULL,
  `order_number_id` bigint NOT NULL,
  `price` decimal(19,2) NOT NULL,
  `product_quantity` int NOT NULL,
  `section_type` varchar(255) NOT NULL,
  `volume` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3lts4yvcd4w8xpi15b2ru4cm8` (`announcement_id`),
  KEY `FK2wl49q6kcl6bpqub3mv0q8upv` (`order_number_id`),
  CONSTRAINT `FK2wl49q6kcl6bpqub3mv0q8upv` FOREIGN KEY (`order_number_id`) REFERENCES `inbound_order` (`id`),
  CONSTRAINT `FK3lts4yvcd4w8xpi15b2ru4cm8` FOREIGN KEY (`announcement_id`) REFERENCES `announcement` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batch_stock`
--

LOCK TABLES `batch_stock` WRITE;
/*!40000 ALTER TABLE `batch_stock` DISABLE KEYS */;
INSERT INTO `batch_stock` VALUES (1,1,'2023-02-01','2022-03-09','2020-03-09 17:55:00.000000',1,30.50,10,'Fresh',20.5),(2,2,'2023-02-01','2022-03-09','2020-03-09 17:55:00.000000',1,20.50,11,'Fresh',20.3);
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inbound_order`
--

LOCK TABLES `inbound_order` WRITE;
/*!40000 ALTER TABLE `inbound_order` DISABLE KEYS */;
INSERT INTO `inbound_order` VALUES (1,'2022-03-03',1,1);
/*!40000 ALTER TABLE `inbound_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_order`
--

DROP TABLE IF EXISTS `purchase_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `buyer_id` bigint NOT NULL,
  `date` datetime(6) NOT NULL,
  `status` varchar(255) NOT NULL,
  `total` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnx7lx9kjhgwik0x63nyk1wnuo` (`buyer_id`),
  CONSTRAINT `FKnx7lx9kjhgwik0x63nyk1wnuo` FOREIGN KEY (`buyer_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_order`
--

LOCK TABLES `purchase_order` WRITE;
/*!40000 ALTER TABLE `purchase_order` DISABLE KEYS */;
INSERT INTO `purchase_order` VALUES (1,1,'2022-12-30 00:00:00.000000','Pending',150.00),(2,1,'2022-12-30 00:00:00.000000','Approved',450.00),(3,1,'2022-12-30 00:00:00.000000','Approved',300.00),(4,2,'2022-12-30 00:00:00.000000','Approved',150.00),(5,2,'2022-12-30 00:00:00.000000','Approved',320.00),(6,1,'2022-12-30 00:00:00.000000','Approved',450.77),(7,2,'2022-12-30 00:00:00.000000','Approved',125.12),(8,1,'2022-12-30 00:00:00.000000','Approved',1000.55),(9,2,'2022-12-30 00:00:00.000000','Approved',150.88);
/*!40000 ALTER TABLE `purchase_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_order_items`
--

DROP TABLE IF EXISTS `purchase_order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_order_items` (
  `id` bigint NOT NULL,
  `announcement_id` bigint NOT NULL,
  `product_price` double NOT NULL,
  `product_quantity` int NOT NULL,
  `purchase_order_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh6o5mrr6xdv31e5vxkekur42m` (`announcement_id`),
  KEY `FKe5vf73k1ouhsxpth3vp7rnfis` (`purchase_order_id`),
  CONSTRAINT `FKe5vf73k1ouhsxpth3vp7rnfis` FOREIGN KEY (`purchase_order_id`) REFERENCES `purchase_order` (`id`),
  CONSTRAINT `FKh6o5mrr6xdv31e5vxkekur42m` FOREIGN KEY (`announcement_id`) REFERENCES `announcement` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_order_items`
--

LOCK TABLES `purchase_order_items` WRITE;
/*!40000 ALTER TABLE `purchase_order_items` DISABLE KEYS */;
INSERT INTO `purchase_order_items` VALUES (1,1,807,10,1),(2,8,450.7,10,2),(3,7,185.5,10,3),(4,6,101.25,15,4),(5,5,27.75,5,5),(6,4,80,2,6),(7,3,20,1,7),(8,2,12,4,8),(9,1,5,2,9);
/*!40000 ALTER TABLE `purchase_order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `section`
--

DROP TABLE IF EXISTS `section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `section` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `max_capacity` float NOT NULL,
  `type` varchar(50) NOT NULL,
  `used_capacity` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `section`
--

LOCK TABLES `section` WRITE;
/*!40000 ALTER TABLE `section` DISABLE KEYS */;
INSERT INTO `section` VALUES (1,1000,'Fresh',819.2),(2,1500,'Refrigerated',1000),(3,2000,'Frozen',1200);
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'jonatasbene@email.com','1234','buyer','JonatasBene'),(2,'brunacampos@email.com','1234','buyer','BrunaCampos'),(3,'rosysantos@email.com','5678','seller','RosySantos'),(4,'estherporto@email.com','5678','seller','EstherPorto'),(5,'marcelloalves@email.com','9090','manager','MarcelloAlves'),(6,'hugocaxias@email.com','9090','manager','HugoCaxias'),(7,'fulano@email.com','9080','manager','FulanoSilva');
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
  `address` varchar(100) NOT NULL,
  `country_code` varchar(2) NOT NULL,
  `name` varchar(50) NOT NULL,
  `manager_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmtyr26fo530xqjuttkatdh7xp` (`manager_id`),
  CONSTRAINT `FKmtyr26fo530xqjuttkatdh7xp` FOREIGN KEY (`manager_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse`
--

LOCK TABLES `warehouse` WRITE;
/*!40000 ALTER TABLE `warehouse` DISABLE KEYS */;
INSERT INTO `warehouse` VALUES (1,'Address A','BR','MLBSP01',5),(2,'Address B','BR','MLBSP02',7),(3,'AddressC','AR','MLARBA01',6),(4,'Address B','BR','MLBSP02',1);
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

-- Dump completed on 2022-11-16 17:53:10
