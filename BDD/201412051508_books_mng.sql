# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# H�te: 127.0.0.1 (MySQL 5.6.20)
# Base de donn�es: books_mng
# Temps de g�n�ration: 2014-12-05 14:07:04 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Affichage de la table account
# ------------------------------------------------------------

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `crypted_key` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `account_user` (`user_id`),
  CONSTRAINT `account_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;

INSERT INTO `account` (`id`, `crypted_key`, `user_id`)
VALUES
	(3,'0000',1);

/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;


# Affichage de la table author
# ------------------------------------------------------------

DROP TABLE IF EXISTS `author`;

CREATE TABLE `author` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `last_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;

INSERT INTO `author` (`id`, `first_name`, `last_name`)
VALUES
	(1,'Lynn','Flewelling'),
	(2,'Agatha','Christie');

/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;


# Affichage de la table book
# ------------------------------------------------------------

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `isbn` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `title` varchar(255) CHARACTER SET utf8 DEFAULT '',
  `type_id` int(11) unsigned NOT NULL,
  `author_id` int(11) unsigned NOT NULL,
  `summary` varchar(8000) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`isbn`),
  KEY `book_author` (`author_id`),
  KEY `fk_book_type` (`type_id`),
  CONSTRAINT `book_author` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_book_type` FOREIGN KEY (`type_id`) REFERENCES `type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;

INSERT INTO `book` (`isbn`, `title`, `type_id`, `author_id`, `summary`)
VALUES
	('0-062-07350-8','Murder on the orient express',2,2,'Hercule Poirot, private detective and retired Belgian police officer, boards the Taurus Express train to Stamboul (Istanbul). On the train there are two other passengers, Mary Debenham and Colonel Arbuthnot. The two act as if they are strangers, but Poirot observes behavior that suggests that they are not. Poirot is suspicious of the couple. The train arrives in Stamboul and Poirot checks in at the Tokatlian Hotel. As soon as Poirot arrives he receives a telegram summoning him back to London. While waiting at the hotel for the next train, Poirot bumps into an old friend, M. Bouc, head of the Wagon Lit. M. Bouc arranges a space for Poirot on the Orient Express. In the dining room of the Tokatlian Hotel, Poirot first spots Ratchett and Hector McQueen eating dinner. Poirot know that Ratchett is an evil man and he describes him to M. Bouc as an animal.'),
	('0-345-52230-3','Casket of Souls - Nightrunner',1,1,'More than the dissolute noblemen they appear to be, Alec and Seregil are skillful spies, dedicated to serving queen and country. But when they stumble across evidence of a plot pitting Queen Phoria against Princess Klia, the two Nightrunners will find their loyalties torn as never before. Even at the best of times, the royal court at Rhíminee is a serpents\' nest of intrigue, but with the war against Plenimar going badly, treason simmers just below the surface. And that\'s not all that poses a threat: A mysterious plague is spreading through the crowded streets of the city, striking young and old alike. Now, as panic mounts and the body count rises, hidden secrets emerge. And as Seregil and Alec are about to learn, conspiracies and plagues have one thing in common: the cure can be as deadly as the disease.'),
	('0-345-52231-1','Shards Of Time - Nightrunner',1,1,'The last novel in the series. The governor of the sacred island of Kouros and his mistress have been killed inside a locked and guarded room. The sole witnesses to the crime guards who broke down the door, hearing the screams from within have gone mad with terror, babbling about ghosts... and things worse than ghosts. Dispatched to Kouros by the queen, master spies Alec and Seregil find all the excitement and danger they could want and more. For an ancient evil has been awakened there, a great power that will not rest until it has escaped its prison and taken revenge on all that lives. And our heroes must find a way to stop it and save those they hold most dear... or die trying.'),
	('0-553-57542-2','Luck in the Shadows - Nightrunner',1,1,'Seregil stumbles into the rescue of Alec, a poor, orphaned hunter. After hiring Alec to guide him through the Northern Lands, Seregil notes Alecs quick learning ability and fast hands, and offers him a job as his apprentice. Alec, though wary of Seregil at first due to a distressing amount of secrecy and suspicion that he\'s becoming a thief or spy, accepts the offer. They fall into a mystery that involves the fast deterioration of Seregil\'s mind and sanity, and Alec must find a way to save his new teacher and friend. Alec manages to deliver Seregil into the hands of Nysander, a wizard of Skala, but the mystery seems to only deepen. At the same time, a traitorous plot against the Queen seems to be unfolding, and Seregil must solve it quickly; before he is found guilty of treason himself.'),
	('0-553-57543-0','Stalking Darkness - Nightrunner',1,1,'The seemingly harmless wooden disc that nearly caused Seregil\'s death and loss of sanity in Luck in the Shadows is revealed to be part of a broken, evil, helm belonging to the Eater of Death, a forsaken god named Seriamaius. A plan to retrieve all the pieces of the helm is attempted by a Plenimarine, Mardus, who wishes to use it to conquer Skala and Mycena and rule over the three lands. A prophecy long foretold takes place, and Seregil has to kill his mentor, Nysander, in order to destroy the helm. In the last paragraphs of the book, Seregil and Alec admit their feelings for one another.'),
	('0-553-57725-5','Traitor\'s Moon - Nightrunner',1,1,'Seregil and Alec are sent to Aurënen, Seregil\'s homeland, with Princess Klia, in a Skalan delegation to ask for open ports, warriors and supplies in the deepening war between Skala and Plenimar, but the attempted murder of the Princess means trouble. Seregil and Alec must unravel the mystery before all chance of a treaty is ruined. At the same time, Seregil must readjust himself to the country he was exiled from more than thirty years previously.'),
	('0-553-59008-1','Shadows Return - Nightrunner',1,1,'Seregil and Alec are kidnapped by Zengati slavetraders, and bought by a Plenimaran alchemist. Using Alec?s unique blood as a half-northerner, half-hâzadriëlfaie, the alchemist intends to create a creature called a rhekaro, who appears to be a young child, and yet is most definitely not human at all. Seregil eventually helps Alec and Sebrahn escape, coming to terms with his own past as he reunites with an old lover and enemy.'),
	('0-553-59009-X','The White Road - Nightrunner',1,1,'Having escaped death and slavery in Plenimar, Seregil and Alec want nothing more than to go back to their nightrunning life in Rhíminee. Instead they find themselves saddled with Sebrahn, a strange, alchemically created creature - the prophesied \"child of no woman.\" Its moon-white skin and frightening powers make it a danger to all whom Seregil and Alec come into contact with, leaving them no choice but to learn more about Sebrahn\'s true nature. But what then With the help of old friends and Seregil\'s clan, the pair sets out to discover the truth about this living homunculus - a journey that can lead only to danger... or death. For Seregil\'s old nemesis Ulan í Sathil of Virèsse and Alec\'s own long-lost kin (Hâzadrielfaie) are after them, intent on possessing both Sebrahn and Alec. On the run and hunted, Alec and his friends must fight against time to accomplish their most personal mission ever.');

/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;


# Affichage de la table review
# ------------------------------------------------------------

DROP TABLE IF EXISTS `review`;

CREATE TABLE `review` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `book_id` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `user_id` int(11) unsigned NOT NULL,
  `mark` int(11) DEFAULT NULL,
  `comment` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `review_book` (`book_id`),
  KEY `review_user` (`user_id`),
  CONSTRAINT `review_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`isbn`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `review_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;

INSERT INTO `review` (`id`, `book_id`, `user_id`, `mark`, `comment`)
VALUES
	(5,'0-553-57542-2',1,5,'AWESOME !');

/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;


# Affichage de la table type
# ------------------------------------------------------------

DROP TABLE IF EXISTS `type`;

CREATE TABLE `type` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `type` WRITE;
/*!40000 ALTER TABLE `type` DISABLE KEYS */;

INSERT INTO `type` (`id`, `name`)
VALUES
	(1,'Fantasy'),
	(2,'Crime Novel');

/*!40000 ALTER TABLE `type` ENABLE KEYS */;
UNLOCK TABLES;


# Affichage de la table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(11) CHARACTER SET utf8 DEFAULT NULL,
  `last_name` varchar(11) CHARACTER SET utf8 DEFAULT NULL,
  `nickname` varchar(11) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `birth_date` varchar(11) CHARACTER SET utf8 DEFAULT NULL,
  `email` varchar(11) CHARACTER SET utf8 NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `first_name`, `last_name`, `nickname`, `birth_date`, `email`)
VALUES
	(1,'Simon','Jacquemin','Mekiis','29/01/91','aa@bb.com');

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
