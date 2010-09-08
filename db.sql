-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.51a-community-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema chris_test
--

CREATE DATABASE IF NOT EXISTS chris_test;
USE chris_test;

--
-- Definition of table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
CREATE TABLE `configuration` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `value` varchar(100) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `configuration`
--

/*!40000 ALTER TABLE `configuration` DISABLE KEYS */;
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES 
 (1,'title_font','Chiller'),
 (2,'title_size','124'),
 (3,'title','White Knuckle Ranch'),
 (4,'title_color','-65536'),
 (5,'title_background','-1381654');
/*!40000 ALTER TABLE `configuration` ENABLE KEYS */;


--
-- Definition of table `event`
--

DROP TABLE IF EXISTS `event`;
CREATE TABLE `event` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `date` varchar(10) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `event`
--

/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` (`id`,`name`,`date`) VALUES 
 (3,'New Test','2-2-2009'),
 (4,'Test Event','3-3-2009'),
 (5,'Race Jam','6-6-2009'),
 (10,'Thanksgiving Race','11-24-2009');
/*!40000 ALTER TABLE `event` ENABLE KEYS */;


--
-- Definition of table `event_classes`
--

DROP TABLE IF EXISTS `event_classes`;
CREATE TABLE `event_classes` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `event_id` int(10) unsigned NOT NULL,
  `name` varchar(45) NOT NULL,
  `laps` int(10) unsigned NOT NULL,
  `race_order` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `FK_event` (`event_id`),
  CONSTRAINT `FK_event` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `event_classes`
--

/*!40000 ALTER TABLE `event_classes` DISABLE KEYS */;
INSERT INTO `event_classes` (`id`,`event_id`,`name`,`laps`,`race_order`) VALUES 
 (4,3,'125A',0,0),
 (5,3,'250A',0,1),
 (7,5,'PeeWee',5,0),
 (8,4,'test class',6,0),
 (9,4,'the test',5,1),
 (10,10,'125A',5,0),
 (11,10,'125B',5,1),
 (12,10,'125C',5,2);
/*!40000 ALTER TABLE `event_classes` ENABLE KEYS */;


--
-- Definition of table `laps`
--

DROP TABLE IF EXISTS `laps`;
CREATE TABLE `laps` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `race_entry_id` int(10) NOT NULL,
  `moto` int(10) unsigned NOT NULL,
  `lap_timestamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `race_class_id` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `race_entry_id` USING BTREE (`race_entry_id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `laps`
--

/*!40000 ALTER TABLE `laps` DISABLE KEYS */;
INSERT INTO `laps` (`id`,`race_entry_id`,`moto`,`lap_timestamp`,`race_class_id`) VALUES 
 (1,5,0,'2009-11-04 15:31:05',5),
 (2,5,1,'2009-11-04 15:31:10',5),
 (3,4,0,'2009-11-04 15:31:15',5),
 (4,5,1,'2009-11-04 15:31:55',5),
 (5,4,1,'2009-11-04 15:34:17',5),
 (6,3,1,'2009-11-04 15:34:18',5),
 (7,4,1,'2009-11-04 15:38:42',5),
 (8,4,1,'2009-11-04 15:38:52',5),
 (9,5,1,'2009-11-04 15:39:37',5),
 (10,5,1,'2009-11-04 15:39:41',5),
 (11,2,0,'2009-11-04 15:45:10',4),
 (12,2,1,'2009-11-04 15:45:14',4),
 (13,9,0,'2009-11-05 06:41:52',4),
 (14,10,0,'2009-11-05 06:41:54',4),
 (15,10,0,'2009-11-05 06:41:58',4),
 (16,9,0,'2009-11-05 06:42:01',4),
 (17,2,0,'2009-11-05 06:42:03',4),
 (18,9,1,'2009-11-05 06:44:40',4),
 (19,6,0,'2009-11-05 08:15:37',7),
 (20,7,0,'2009-11-05 08:15:38',7),
 (21,8,0,'2009-11-05 08:15:39',7),
 (31,13,1,'2009-11-06 22:08:37',10),
 (32,11,1,'2009-11-06 22:08:39',10),
 (33,12,1,'2009-11-06 22:08:40',10),
 (34,12,1,'2009-11-06 22:08:45',10),
 (41,12,1,'2009-11-06 22:09:15',10),
 (42,12,1,'2009-11-06 22:09:16',10),
 (43,12,1,'2009-11-06 22:09:16',10),
 (44,13,1,'2009-11-06 22:09:21',10),
 (45,13,1,'2009-11-06 22:09:23',10),
 (46,13,1,'2009-11-06 22:09:27',10),
 (47,13,1,'2009-11-06 22:09:29',10),
 (48,11,1,'2009-11-06 22:09:30',10),
 (49,11,1,'2009-11-06 22:09:31',10),
 (50,11,1,'2009-11-06 22:09:32',10),
 (51,11,1,'2009-11-06 22:09:33',10),
 (52,5,0,'2009-11-17 09:01:43',5),
 (53,16,0,'2009-11-18 12:36:11',4),
 (54,16,0,'2009-11-18 12:36:15',4),
 (57,9,1,'2009-11-18 12:36:44',4),
 (58,2,1,'2009-11-18 12:36:46',4),
 (59,2,1,'2009-11-18 12:36:49',4),
 (60,9,1,'2009-11-18 12:36:51',4),
 (61,16,1,'2009-11-18 12:38:04',4),
 (62,13,0,'2009-11-18 13:25:51',10),
 (63,17,0,'2009-11-18 13:25:54',10),
 (64,14,0,'2009-11-18 13:25:58',10),
 (65,13,0,'2009-11-18 13:26:00',10),
 (66,17,0,'2009-11-18 13:26:01',10),
 (67,14,0,'2009-11-18 13:26:05',10),
 (68,17,0,'2009-11-18 13:26:07',10),
 (69,13,0,'2009-11-18 13:26:11',10),
 (70,14,0,'2009-11-18 13:26:14',10),
 (73,13,0,'2009-11-18 13:26:24',10),
 (74,14,0,'2009-11-18 13:26:26',10),
 (75,17,0,'2009-11-18 13:32:06',10);
/*!40000 ALTER TABLE `laps` ENABLE KEYS */;


--
-- Definition of table `race_delete`
--

DROP TABLE IF EXISTS `race_delete`;
CREATE TABLE `race_delete` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `date` date NOT NULL,
  `start_time` datetime NOT NULL,
  `laps` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `race_delete`
--

/*!40000 ALTER TABLE `race_delete` DISABLE KEYS */;
INSERT INTO `race_delete` (`id`,`date`,`start_time`,`laps`) VALUES 
 (1,'2009-10-02','2009-10-02 08:35:09',6);
/*!40000 ALTER TABLE `race_delete` ENABLE KEYS */;


--
-- Definition of table `race_entry`
--

DROP TABLE IF EXISTS `race_entry`;
CREATE TABLE `race_entry` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `event_class_id` int(10) unsigned NOT NULL,
  `racer_id` int(10) NOT NULL,
  `bike_mfg` varchar(45) NOT NULL,
  `size` varchar(45) NOT NULL,
  `number` varchar(45) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_event_class` (`event_class_id`),
  KEY `FK_racers` USING BTREE (`racer_id`),
  CONSTRAINT `FK_event_class` FOREIGN KEY (`event_class_id`) REFERENCES `event_classes` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_racers` FOREIGN KEY (`racer_id`) REFERENCES `racers` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `race_entry`
--

/*!40000 ALTER TABLE `race_entry` DISABLE KEYS */;
INSERT INTO `race_entry` (`id`,`event_class_id`,`racer_id`,`bike_mfg`,`size`,`number`) VALUES 
 (2,4,3,'Yamaha','250 2t','214'),
 (3,5,3,'Kawasaki','450','444'),
 (4,5,4,'Honda','450','555'),
 (5,5,1,'Suzuki','250 2t','119'),
 (6,7,3,'Yamaha','50','123'),
 (7,7,4,'Yamaha','50','321'),
 (8,7,1,'Yamaha','50','111'),
 (9,4,4,'Suzuki','125','111'),
 (10,4,1,'Suzuki','125','119'),
 (11,10,3,'Suzuki','125','222'),
 (12,10,4,'Yamaha','125','333'),
 (13,10,1,'Suzuki','125','119'),
 (14,10,5,'Honda','125','748'),
 (15,7,5,'Mine','50','222'),
 (16,4,5,'Honda','250f','119r'),
 (17,10,8,'Honda','250f','111');
/*!40000 ALTER TABLE `race_entry` ENABLE KEYS */;


--
-- Definition of table `racers`
--

DROP TABLE IF EXISTS `racers`;
CREATE TABLE `racers` (
  `id` int(11) NOT NULL auto_increment,
  `last_name` varchar(30) default NULL,
  `first_name` varchar(30) default NULL,
  `birthday` varchar(10) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `racers`
--

/*!40000 ALTER TABLE `racers` DISABLE KEYS */;
INSERT INTO `racers` (`id`,`last_name`,`first_name`,`birthday`) VALUES 
 (1,'Shaw','Chris','5-3-1983'),
 (3,'Hobart','Lans','1-1-1965'),
 (4,'Shaw','Amie','11-09-1982'),
 (5,'Tores','Chris','1-1-1985'),
 (6,'Kowis','David','22-11-1982'),
 (8,'Lewis','Justin','1-1-1987');
/*!40000 ALTER TABLE `racers` ENABLE KEYS */;


--
-- Definition of table `score`
--

DROP TABLE IF EXISTS `score`;
CREATE TABLE `score` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `race_entry_id` int(10) unsigned NOT NULL,
  `race_class_id` int(10) unsigned NOT NULL,
  `moto1_place` int(10) unsigned NOT NULL default '99',
  `moto2_place` int(10) unsigned NOT NULL default '99',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `unique` (`race_entry_id`,`race_class_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `score`
--

/*!40000 ALTER TABLE `score` DISABLE KEYS */;
INSERT INTO `score` (`id`,`race_entry_id`,`race_class_id`,`moto1_place`,`moto2_place`) VALUES 
 (1,5,5,1,1),
 (2,4,5,2,2),
 (3,3,5,99,3),
 (4,2,4,3,1),
 (5,9,4,2,2),
 (6,10,4,1,99),
 (7,6,7,1,99),
 (8,7,7,2,99),
 (9,8,7,3,99),
 (10,12,10,2,1),
 (11,11,10,3,3),
 (12,13,10,1,2),
 (13,16,4,4,3),
 (14,17,10,3,99),
 (15,14,10,2,99);
/*!40000 ALTER TABLE `score` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
