CREATE DATABASE  IF NOT EXISTS `chris_test` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `chris_test`;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
DROP TABLE IF EXISTS event_classes;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE event_classes (
  id int(10) unsigned NOT NULL,
  event_id int(10) unsigned NOT NULL,
  `name` varchar(45) NOT NULL,
  laps int(10) unsigned NOT NULL,
  race_order int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (id),
  KEY FK_event (event_id),
  CONSTRAINT FK_event FOREIGN KEY (event_id) REFERENCES event (id) ON DELETE CASCADE ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES event_classes WRITE;
INSERT INTO event_classes (id, event_id, name, laps, race_order) VALUES (4,3,'125A',0,0),(5,3,'250A',0,1),(7,5,'PeeWee',5,0),(8,4,'test class',6,0),(9,4,'the test',5,1),(10,10,'125A',5,0),(11,10,'125B',5,1),(12,10,'125C',5,2),(13,13,'A',6,0);
UNLOCK TABLES;
DROP TABLE IF EXISTS race_delete;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE race_delete (
  id int(10) unsigned NOT NULL,
  `date` date NOT NULL,
  start_time datetime NOT NULL,
  laps int(10) unsigned NOT NULL,
  PRIMARY KEY (id)
);
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES race_delete WRITE;
INSERT INTO race_delete (id, date, start_time, laps) VALUES (1,'2009-10-02','2009-10-02 08:35:09',6);
UNLOCK TABLES;
DROP TABLE IF EXISTS laps;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE laps (
  id int(10) unsigned NOT NULL,
  race_entry_id int(10) NOT NULL,
  moto int(10) unsigned NOT NULL,
  lap_timestamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  race_class_id int(10) unsigned NOT NULL,
  PRIMARY KEY (id),
  KEY race_entry_id (race_entry_id)
);
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES laps WRITE;
INSERT INTO laps (id, race_entry_id, moto, lap_timestamp, race_class_id) VALUES (1,5,0,'2009-11-04 21:31:05',5),(2,5,1,'2009-11-04 21:31:10',5),(3,4,0,'2009-11-04 21:31:15',5),(4,5,1,'2009-11-04 21:31:55',5),(5,4,1,'2009-11-04 21:34:17',5),(6,3,1,'2009-11-04 21:34:18',5),(7,4,1,'2009-11-04 21:38:42',5),(8,4,1,'2009-11-04 21:38:52',5),(9,5,1,'2009-11-04 21:39:37',5),(10,5,1,'2009-11-04 21:39:41',5),(11,2,0,'2009-11-04 21:45:10',4),(12,2,1,'2009-11-04 21:45:14',4),(13,9,0,'2009-11-05 12:41:52',4),(14,10,0,'2009-11-05 12:41:54',4),(15,10,0,'2009-11-05 12:41:58',4),(16,9,0,'2009-11-05 12:42:01',4),(17,2,0,'2009-11-05 12:42:03',4),(18,9,1,'2009-11-05 12:44:40',4),(19,6,0,'2009-11-05 14:15:37',7),(20,7,0,'2009-11-05 14:15:38',7),(21,8,0,'2009-11-05 14:15:39',7),(31,13,1,'2009-11-07 04:08:37',10),(32,11,1,'2009-11-07 04:08:39',10),(33,12,1,'2009-11-07 04:08:40',10),(34,12,1,'2009-11-07 04:08:45',10),(41,12,1,'2009-11-07 04:09:15',10),(42,12,1,'2009-11-07 04:09:16',10),(43,12,1,'2009-11-07 04:09:16',10),(44,13,1,'2009-11-07 04:09:21',10),(45,13,1,'2009-11-07 04:09:23',10),(46,13,1,'2009-11-07 04:09:27',10),(47,13,1,'2009-11-07 04:09:29',10),(48,11,1,'2009-11-07 04:09:30',10),(49,11,1,'2009-11-07 04:09:31',10),(50,11,1,'2009-11-07 04:09:32',10),(51,11,1,'2009-11-07 04:09:33',10),(52,5,0,'2009-11-17 15:01:43',5),(53,16,0,'2009-11-18 18:36:11',4),(54,16,0,'2009-11-18 18:36:15',4),(57,9,1,'2009-11-18 18:36:44',4),(58,2,1,'2009-11-18 18:36:46',4),(59,2,1,'2009-11-18 18:36:49',4),(60,9,1,'2009-11-18 18:36:51',4),(61,16,1,'2009-11-18 18:38:04',4),(62,13,0,'2009-11-18 19:25:51',10),(63,17,0,'2009-11-18 19:25:54',10),(64,14,0,'2009-11-18 19:25:58',10),(65,13,0,'2009-11-18 19:26:00',10),(66,17,0,'2009-11-18 19:26:01',10),(67,14,0,'2009-11-18 19:26:05',10),(68,17,0,'2009-11-18 19:26:07',10),(69,13,0,'2009-11-18 19:26:11',10),(70,14,0,'2009-11-18 19:26:14',10),(73,13,0,'2009-11-18 19:26:24',10),(74,14,0,'2009-11-18 19:26:26',10),(75,17,0,'2009-11-18 19:32:06',10),(76,6,1,'2010-06-01 14:46:26',7),(77,6,1,'2010-06-01 14:46:27',7),(78,6,1,'2010-06-01 14:46:28',7),(79,6,1,'2010-06-01 14:46:33',7),(80,6,1,'2010-06-01 14:46:36',7),(81,18,0,'2010-06-01 20:09:46',11),(82,22,0,'2010-06-01 20:09:47',11),(83,23,0,'2010-06-01 20:09:48',11),(84,21,0,'2010-06-01 20:09:51',11),(85,19,0,'2010-06-01 20:09:57',11),(86,20,0,'2010-06-01 20:09:59',11),(87,20,1,'2010-06-01 20:10:02',11),(88,19,1,'2010-06-01 20:10:06',11),(89,18,1,'2010-06-01 20:10:13',11),(90,22,1,'2010-06-01 20:10:17',11),(91,23,1,'2010-06-01 20:10:20',11),(92,21,1,'2010-06-01 20:10:27',11),(93,24,0,'2010-06-01 20:15:45',12),(94,24,1,'2010-06-01 20:15:47',12),(95,24,0,'2010-06-02 13:53:54',12),(96,24,0,'2010-06-02 13:53:57',12),(97,24,0,'2010-06-02 13:53:59',12),(98,24,0,'2010-06-02 13:54:01',12),(99,24,1,'2010-06-02 13:54:30',12),(100,24,1,'2010-06-02 13:54:31',12),(101,24,1,'2010-06-02 13:54:39',12),(102,24,1,'2010-06-02 13:55:03',12),(103,6,0,'2010-09-08 21:36:38',7),(104,6,0,'2010-09-08 21:36:39',7),(105,6,0,'2010-09-08 21:36:40',7),(106,25,0,'2010-09-08 21:37:51',13),(107,25,0,'2010-09-08 21:37:53',13),(108,25,0,'2010-09-08 21:37:53',13),(109,25,0,'2010-09-08 21:37:55',13),(110,25,0,'2010-09-08 21:38:00',13),(111,25,0,'2010-09-08 21:38:02',13),(112,25,1,'2010-09-08 21:38:06',13),(113,25,1,'2010-09-08 21:38:08',13),(114,25,1,'2010-09-08 21:38:12',13),(115,25,1,'2010-09-08 21:38:15',13),(116,25,1,'2010-09-08 21:38:19',13),(117,25,1,'2010-09-08 21:38:22',13);
UNLOCK TABLES;
DROP TABLE IF EXISTS racers;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE racers (
  id int(11) NOT NULL,
  last_name varchar(30) DEFAULT NULL,
  first_name varchar(30) DEFAULT NULL,
  birthday varchar(10) DEFAULT NULL,
  PRIMARY KEY (id)
);
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES racers WRITE;
INSERT INTO racers (id, last_name, first_name, birthday) VALUES (1,'Shaw','Chris','5-3-1983'),(3,'Hobart','Lans','1-1-1965'),(4,'Shaw','Amie','11-09-1982'),(5,'Tores','Chris','1-1-1985'),(6,'Kowis','David','22-11-1982'),(8,'Lewis','Justin','1-1-1987');
UNLOCK TABLES;
DROP TABLE IF EXISTS score;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE score (
  id int(10) unsigned NOT NULL,
  race_entry_id int(10) unsigned NOT NULL,
  race_class_id int(10) unsigned NOT NULL,
  moto1_place int(10) unsigned NOT NULL DEFAULT '99',
  moto2_place int(10) unsigned NOT NULL DEFAULT '99',
  PRIMARY KEY (id),
  UNIQUE KEY `unique` (race_entry_id,race_class_id)
);
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES score WRITE;
INSERT INTO score (id, race_entry_id, race_class_id, moto1_place, moto2_place) VALUES (1,5,5,1,1),(2,4,5,2,2),(3,3,5,99,3),(4,2,4,3,1),(5,9,4,2,2),(6,10,4,1,99),(7,6,7,1,1),(8,7,7,2,99),(9,8,7,3,99),(10,12,10,2,1),(11,11,10,3,3),(12,13,10,1,2),(13,16,4,4,3),(14,17,10,3,99),(15,14,10,2,99),(16,18,11,1,3),(17,22,11,2,4),(18,23,11,3,5),(19,21,11,4,6),(20,19,11,5,2),(21,20,11,6,1),(22,24,12,1,1),(36,25,13,1,1);
UNLOCK TABLES;
DROP TABLE IF EXISTS race_entry;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE race_entry (
  id int(10) unsigned NOT NULL,
  event_class_id int(10) unsigned NOT NULL,
  racer_id int(10) NOT NULL,
  bike_mfg varchar(45) NOT NULL,
  size varchar(45) NOT NULL,
  number varchar(45) NOT NULL,
  PRIMARY KEY (id),
  KEY FK_event_class (event_class_id),
  KEY FK_racers (racer_id),
  CONSTRAINT FK_event_class FOREIGN KEY (event_class_id) REFERENCES event_classes (id) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT FK_racers FOREIGN KEY (racer_id) REFERENCES racers (id) ON DELETE CASCADE ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES race_entry WRITE;
INSERT INTO race_entry (id, event_class_id, racer_id, bike_mfg, size, number) VALUES (2,4,3,'Yamaha','250 2t','214'),(3,5,3,'Kawasaki','450','444'),(4,5,4,'Honda','450','555'),(5,5,1,'Suzuki','250 2t','119'),(6,7,3,'Yamaha','50','123'),(7,7,4,'Yamaha','50','321'),(8,7,1,'Yamaha','50','111'),(9,4,4,'Suzuki','125','111'),(10,4,1,'Suzuki','125','119'),(11,10,3,'Suzuki','125','222'),(12,10,4,'Yamaha','125','333'),(13,10,1,'Suzuki','125','119'),(14,10,5,'Honda','125','748'),(15,7,5,'Mine','50','222'),(16,4,5,'Honda','250f','119r'),(17,10,8,'Honda','250f','111'),(18,11,3,'Kawasaki','250','121'),(19,11,6,'Suzuki','250','122'),(20,11,8,'Honda','250','123'),(21,11,4,'Yamaha','250','124'),(22,11,1,'Suzuki','250','119'),(23,11,5,'Honda','250','111'),(24,12,3,'Kawasaki','250','121'),(25,13,3,'Suzuki','250','121');
UNLOCK TABLES;
DROP TABLE IF EXISTS configuration;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE configuration (
  id int(10) unsigned NOT NULL,
  `name` varchar(45) NOT NULL,
  `value` varchar(100) NOT NULL,
  PRIMARY KEY (id)
);
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES configuration WRITE;
INSERT INTO configuration (id, name, value) VALUES (1,'title_font','Chiller'),(2,'title_size','124'),(3,'title','White Knuckle Ranch'),(4,'title_color','-65536'),(5,'title_background','-1381654');
UNLOCK TABLES;
DROP TABLE IF EXISTS event;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event` (
  id int(10) unsigned NOT NULL,
  `name` varchar(45) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (id)
);
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES event WRITE;
INSERT INTO event (id, name, date) VALUES (3,'New Test','2009-02-02'),(4,'Test Event','2009-03-03'),(5,'Race Jam','2009-06-06'),(10,'Thanksgiving Race','2009-11-24'),(11,'Weekly Race','2010-06-10'),(12,'Weekly Race','2010-06-22'),(13,'Labor Day Race','2010-09-06');
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

