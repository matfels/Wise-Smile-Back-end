-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: sistema_gestao_consultas
-- ------------------------------------------------------
-- Server version	9.7.0

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '72afd0e0-4bfd-11f1-9627-a04f5224a79c:1-283';

--
-- Dumping data for table `consultas`
--

LOCK TABLES `consultas` WRITE;
/*!40000 ALTER TABLE `consultas` DISABLE KEYS */;
INSERT INTO `consultas` VALUES (1,1,1,2,'Consulta inicial','Paciente desmarcou por telefone','2026-05-17 21:00:00','2026-05-17 22:00:00','2026-05-17 21:56:09','CANCELADA'),(5,2,2,6,'Consulta inicial','sfsafas','2026-05-17 18:00:00','2026-05-17 19:00:00','2026-05-17 11:00:09','CANCELADA'),(6,3,4,7,'Consulta inicial',NULL,'2026-05-17 18:00:00','2026-05-17 19:00:00','2026-05-17 11:00:09','FINALIZADA'),(7,4,3,7,'Consulta inicial',NULL,'2026-05-17 18:00:00','2026-05-17 19:00:00','2026-05-17 11:00:09','FINALIZADA'),(8,2,4,7,'Consulta inicial','Não comparecimento','2026-05-24 18:00:00','2026-05-24 19:00:00',NULL,'CANCELADA'),(9,2,4,7,'Consulta inicial',NULL,'2026-05-24 18:00:00','2026-05-24 19:00:00',NULL,'FINALIZADA'),(10,2,4,7,'Consulta inicial',NULL,'2026-05-24 18:00:00','2026-05-24 19:00:00',NULL,'FINALIZADA'),(11,2,4,7,'Consulta inicial',NULL,'2026-05-24 18:00:00','2026-05-24 19:00:00',NULL,'FINALIZADA'),(13,11,8,2,'Consulta de rotina para manutenção de aparelho',NULL,'2026-05-30 00:56:25','2026-05-30 01:56:25','2026-05-29 00:56:25','FINALIZADA'),(14,12,9,2,'Consulta de rotina para manutenção de aparelho',NULL,'2026-05-30 01:05:26','2026-05-30 02:05:26','2026-05-29 01:05:26','FINALIZADA'),(15,1,1,2,'Consulta de Rotina',NULL,'2026-06-15 17:00:00','2026-06-15 18:00:00','2026-06-09 05:54:52','FINALIZADA'),(16,2,2,6,'Consulta inicial',NULL,'2026-06-15 17:00:00','2026-06-15 18:00:00','2026-06-09 05:56:24','FINALIZADA'),(17,3,3,7,'Consulta inicial',NULL,'2026-06-15 17:00:00','2026-06-15 18:00:00','2026-06-09 06:06:29','FINALIZADA'),(18,2,2,6,'Consulta inicial',NULL,'2026-06-16 17:00:00','2026-06-16 18:00:00','2026-06-09 06:18:25','FINALIZADA'),(19,9,5,9,'Consulta inicial',NULL,'2026-06-16 17:00:00','2026-06-16 18:00:00','2026-06-09 06:24:11','FINALIZADA'),(20,2,2,6,'Consulta inicial de avaliação',NULL,'2026-06-20 17:00:00','2026-06-20 18:00:00','2026-06-11 01:42:02','AGENDADA'),(21,2,2,6,'Consulta inicial de avaliação',NULL,'2026-06-18 17:00:00','2026-06-19 18:00:00','2026-06-15 05:20:01','AGENDADA'),(22,2,2,6,'Consulta inicial de avaliação',NULL,'2026-06-22 17:00:00','2026-06-22 18:00:00','2026-06-15 05:25:33','AGENDADA'),(23,2,3,6,'dsadsa',NULL,'2026-06-16 11:00:00','2026-06-16 12:00:00','2026-06-15 05:51:09','FINALIZADA'),(24,1,4,33,'dasda',NULL,'2026-06-17 11:00:00','2026-06-17 12:00:00','2026-06-15 06:08:35','AGENDADA'),(25,1,1,33,'Consulta teste se der certo e a ultima da noite!!!!',NULL,'2026-12-03 17:00:00','2026-12-03 18:00:00','2026-06-15 06:09:49','AGENDADA'),(26,2,3,33,'sadsad',NULL,'2026-06-16 01:00:00','2026-06-16 02:00:00','2026-06-15 23:54:40','FINALIZADA'),(27,1,3,33,'dsasa',NULL,'2026-06-19 16:00:00','2026-06-19 17:00:00','2026-06-16 01:30:45','AGENDADA');
/*!40000 ALTER TABLE `consultas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `dentista`
--

LOCK TABLES `dentista` WRITE;
/*!40000 ALTER TABLE `dentista` DISABLE KEYS */;
INSERT INTO `dentista` VALUES (1,'Matheus Lucas','Matheus.abrances@email.com.br','58263215562','526149','2026-05-17 22:26:12',0),(2,'Marcelino Ferreira','MArcelino.ferreira@email.com.br','96382574112','143582','2026-05-17 22:27:13',1),(3,'Jucelia Clarice','jucelia.clarice@email.com.br','82649153728','125963','2026-05-17 22:28:05',1),(4,'Cibeli Abranches','cibeli.abranches@email.com.br','74652895547','566847','2026-05-17 22:28:55',1),(5,'Dr. Wilson','wilson@clinica.com','11122233344','12345','2026-05-29 00:25:37',0),(7,'Ddr. Wilson','wilson@clini3a.com','11122233744','12345','2026-05-29 00:31:35',1),(8,'Ddr. Wi3lson','wilson@clwini3a.com','11122235744','12345','2026-05-29 00:56:25',1),(9,'Ddr. W33lson','wilso32@clwini3a.com','11122236744','12345','2026-05-29 01:05:26',1),(10,'Matheus Melo','teste@web.com','32145698741','316545','2026-06-15 00:43:09',1),(11,'Dra Natielly Natally Olech ','natielly.olech','10100921413','230534','2026-06-15 01:13:48',1),(12,'Teste','teste@web123.com','15684952145','134587','2026-06-16 05:51:45',1);
/*!40000 ALTER TABLE `dentista` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `dentista_especialidade`
--

LOCK TABLES `dentista_especialidade` WRITE;
/*!40000 ALTER TABLE `dentista_especialidade` DISABLE KEYS */;
INSERT INTO `dentista_especialidade` VALUES (3,3,2),(4,4,5),(5,5,8),(6,7,10),(7,8,11),(8,9,12),(9,2,3),(11,2,4),(12,2,5),(13,3,3),(14,3,2),(15,4,10),(16,4,10);
/*!40000 ALTER TABLE `dentista_especialidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `especialidades`
--

LOCK TABLES `especialidades` WRITE;
/*!40000 ALTER TABLE `especialidades` DISABLE KEYS */;
INSERT INTO `especialidades` VALUES (1,'Dentística',1),(2,'Ortodontia',1),(3,'Implantodontia',1),(4,'Endodontia',1),(5,'Periodontia',1),(6,'Odontopediatria',1),(7,'Cirurgia e Traumatologia Buco-Maxilo-Facial',1),(8,'Ortodontia',1),(9,'Ortodontia',1),(10,'Tiartodontia',1),(11,'Miopia',1),(12,'Miopia',1),(13,'Tiartodontia',0);
/*!40000 ALTER TABLE `especialidades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `pacientes`
--

LOCK TABLES `pacientes` WRITE;
/*!40000 ALTER TABLE `pacientes` DISABLE KEYS */;
INSERT INTO `pacientes` VALUES (1,'Clarindo Aparecido','Clarindo.aparecido@email.com.br','68135495552','54559804406','2026-05-17 21:49:54',1),(2,'Alfredo Ginacio','alfredo,ginacio@email.com.br','35980186675','54559804406','2026-05-17 21:54:41',1),(3,'MArcelo Arlindo','marcelo.arlindo@email.com.br','50684019978','54559804406','2026-05-17 21:55:22',0),(4,'Juliano Miquelangelo','juliano.miq@email.com.br','18622584336','54559804406','2026-05-17 21:56:09',1),(6,'Paciente de Teste','teste.paciente@gmail.com','98765432100','41999999999',NULL,1),(9,'Pacien4e de Teste','teste.pdaasçlfkciente@gmail.com','98365434100','45999999999',NULL,1),(10,'Pacien4e 4de Teste','teste.pda5asçlfkciente@gmail.com','98365437100','45995999999',NULL,1),(11,'Paci4en4e 4de Teste','teste.pda4445asçlfkciente@gmail.com','98365437170','45995999999',NULL,1),(12,'Paci4ewten4e 4de Teste','tessteda4445asçlfkciente@gmail.com','98365436170','45295999999',NULL,1),(14,'Maria Costa','maria.costa@email.com','11122233342','41288887777','2026-06-09 01:28:59',1),(15,'Matheus Ferreira De Melo Paciente','matheus.melo','10568019992','41995704404','2026-06-15 03:39:46',1);
/*!40000 ALTER TABLE `pacientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (2,'Vinicius','43215678922','Vinicius@email.com.br','123456789','Comum',1,'2026-05-17 20:45:56',NULL),(6,'Clarisvaldo Silva','51680384497','clarisvaldo.silva@email.com.br','15384675','Comum',1,'2026-05-17 21:57:40',NULL),(7,'Luciano Moreira','15868196475','luciano.moreira@email.com.br','51325848','Comum',1,'2026-05-17 21:59:04',NULL),(8,'Maria Eugenia','12345678910','maria.eugenia@email.com.br','41995704404','Comum',1,'2026-05-17 22:15:26',NULL),(9,'Luiza Clara','13548123588','luiza.calara@email.com.br','41995704404','Comum',1,'2026-05-17 22:16:38',NULL),(10,'Matheus Melo','10568019887','matheus@matheus.com.br','1234','AGENDADA',0,'2026-05-26 00:30:23',NULL),(12,'Matheus Melo2','10568319887','matheuw@matheus.com.br','1234','AGENDADA',1,'2026-05-26 01:14:15',NULL),(13,'Matheus Melo4','22568319887','matheuc@matheus.com.br','1234','AGENDADA',1,'2026-05-26 01:44:07',NULL),(15,'João Paulo','68495025559','joao.paulo@joao.com.br','1234','AGENDADA',1,'2026-05-26 01:49:51',NULL),(16,'Matheus Melo4','23568319887','matheuca@matheus.com.br','1234','AGENDADA',1,'2026-05-26 01:50:47',NULL),(18,'Matheus Melo','12345678900','matheus@matheuss.com','admin123','ADMIN',1,'2026-05-28 03:04:50',NULL),(20,'Matheus Melo','12345678990','matheus@matheusss.com','admin123','ADMIN',1,'2026-05-28 03:10:41',NULL),(21,'Matheus Melo 3','12345678992','matheus@matheuswss.com','admin123','ADMIN',1,'2026-05-28 03:13:21',NULL),(23,'Matheus Melo 3','12345378992','matheus@masadtheuswss.com','admin123','ADMIN',1,'2026-05-28 03:22:17',NULL),(24,'Matheus Melo 4','12345378992','matheus@masadtheuswssw.com','admin123','ADMIN',1,'2026-05-28 03:25:41',NULL),(26,'Jose arlindo3','12345358992','jose@arlindo.com','admin123','ADMIN',1,'2026-05-29 00:23:57',NULL),(28,'Jo2se arlindo3','12245358992','jos2@arlindo.com','admin123','ADMIN',1,'2026-05-29 00:25:36',NULL),(29,'Jo2se ar43lindo3','12245358692','jos2@a5lindo.com','admin123','ADMIN',1,'2026-05-29 00:30:33',NULL),(30,'Jo2se a443lindo3','12245352692','jos2@a56indo.com','admin123','ADMIN',1,'2026-05-29 00:31:35',NULL),(31,'Jo2se a4543lindo3','12245358692','jos2@356indo.com','admin123','ADMIN',1,'2026-05-29 00:56:25',NULL),(32,'ew a4543lindo3','12235358692','teste@356indo.com','admin123','ADMIN',1,'2026-05-29 01:05:26',NULL),(33,'Carlos Silva','99988877766','carlos.silva@wisesmile.com','$2a$12$WLzukFlPYzFVw1SpZyjK0.a7G6htcX0QZWphNRkjm1E9SUPCILtlS','Admin',1,'2026-05-30 21:27:49',NULL),(34,'Matfels','56845325598','Mat.Fels@wisesmile.com','$2a$10$VluGA3qMdgYeBkC1sdeEz.FZ0GOHqNm179pYv6XRBXFnXEYKq38nm','DENTISTA',1,'2026-05-30 21:39:38',NULL),(35,'Djuliane ','1596753149','djuliane@gmail.com','$2a$10$FO4Kf9bvOGitOsD3G9SmB.bqe9pQlmcHMdL/CoDa2QrIa1lFwScqS','Admin',1,'2026-06-16 13:43:45',NULL),(36,'','','','$2a$10$4SQEkeMo24ySRwgCDEwVrOUtZuLsKyuCvIZKAU8vujQqUvO8Q1lau','Dentista',0,'2026-06-16 13:53:37',NULL),(37,'Matheus Melo','10568019992','matheus.melo@hmpapafrancisco.com.br','$2a$10$tdKxJqc0Un2qqyo2t0j5BOgjljMx1wBMuNBg7WRjxSUL7hWNfTlnu','Admin',1,'2026-06-16 13:57:16',NULL),(38,'usuario','53698153364','usuario@teste.com.br','$2a$10$Uop0qK1iplvXpZXutMgRo.r5Lks4Hf6oo0TelIWxHo2GwNDj2yleO','Comum',1,'2026-06-16 17:17:20',NULL),(39,'Administrador Master','99999999999','admin@clinica.com.br','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy','ADMIN',1,'2026-06-16 19:06:16',NULL);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-16 17:52:24
