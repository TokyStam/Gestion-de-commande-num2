-- --------------------------------------------------------
-- Hôte :                        localhost
-- Version du serveur:           5.7.19 - MySQL Community Server (GPL)
-- SE du serveur:                Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Export de la structure de la base pour javatest
DROP DATABASE IF EXISTS `javatest`;
CREATE DATABASE IF NOT EXISTS `javatest` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `javatest`;

-- Export de la structure de la table javatest. admin
DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `codeAdmin` int(11) NOT NULL AUTO_INCREMENT,
  `nomAdmin` varchar(50) DEFAULT '0',
  `password` varchar(50) DEFAULT '0',
  PRIMARY KEY (`codeAdmin`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Export de données de la table javatest.admin : ~1 rows (environ)
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` (`codeAdmin`, `nomAdmin`, `password`) VALUES
	(1, 'fetra', 'fetra');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;

-- Export de la structure de la table javatest. categorie
DROP TABLE IF EXISTS `categorie`;
CREATE TABLE IF NOT EXISTS `categorie` (
  `codeCateg` int(11) NOT NULL AUTO_INCREMENT,
  `designation` varchar(50) DEFAULT '0',
  `commentaire` varchar(50) DEFAULT '0',
  PRIMARY KEY (`codeCateg`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

-- Export de données de la table javatest.categorie : ~3 rows (environ)
/*!40000 ALTER TABLE `categorie` DISABLE KEYS */;
INSERT INTO `categorie` (`codeCateg`, `designation`, `commentaire`) VALUES
	(7, 'Sport', 'daafdsfa'),
	(8, 'Electronique', 'Produit electronique'),
	(9, 'Scolaire', 'tous les equipements scolaire');
/*!40000 ALTER TABLE `categorie` ENABLE KEYS */;

-- Export de la structure de la table javatest. itemeproduit
DROP TABLE IF EXISTS `itemeproduit`;
CREATE TABLE IF NOT EXISTS `itemeproduit` (
  `cVente` int(6) NOT NULL,
  `numPro` int(6) NOT NULL,
  `quantite` int(6) NOT NULL,
  `valeur` double DEFAULT NULL,
  KEY `cVente` (`cVente`),
  KEY `numPro` (`numPro`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Export de données de la table javatest.itemeproduit : ~14 rows (environ)
/*!40000 ALTER TABLE `itemeproduit` DISABLE KEYS */;
INSERT INTO `itemeproduit` (`cVente`, `numPro`, `quantite`, `valeur`) VALUES
	(2, 10, 1, 2000),
	(2, 2, 1, 12000),
	(3, 11, 1, 12000),
	(5, 6, 1, 500),
	(6, 2, 1, 12000),
	(8, 6, 7, 3500),
	(8, 2, 7, 84000),
	(8, 6, 1, 500),
	(9, 11, 1, 12000),
	(10, 6, 1, 500),
	(10, 10, 1, 2000),
	(10, 12, 1, 800),
	(10, 11, 1, 12000),
	(10, 2, 1, 12000);
/*!40000 ALTER TABLE `itemeproduit` ENABLE KEYS */;

-- Export de la structure de la table javatest. produits
DROP TABLE IF EXISTS `produits`;
CREATE TABLE IF NOT EXISTS `produits` (
  `numPro` int(6) NOT NULL AUTO_INCREMENT,
  `designation` text,
  `prix` double DEFAULT NULL,
  `codeCategorie` int(11) DEFAULT NULL,
  `qteEnStk` double DEFAULT NULL,
  `commentaire` text,
  `dateDebutStk` date DEFAULT NULL,
  PRIMARY KEY (`numPro`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

-- Export de données de la table javatest.produits : ~5 rows (environ)
/*!40000 ALTER TABLE `produits` DISABLE KEYS */;
INSERT INTO `produits` (`numPro`, `designation`, `prix`, `codeCategorie`, `qteEnStk`, `commentaire`, `dateDebutStk`) VALUES
	(2, 'Asus core i7, 9eme generation', 12000, 8, 194, 'bbbbbbbbbbs', '2018-08-16'),
	(6, 'hp1', 500, 8, 93, 'dsaf', '2018-07-30'),
	(10, 'tenis adidas', 2000, 7, 111, '', '2018-08-23'),
	(11, 'Clavier: qwerty, portable externe', 12000, 8, 10, 'trois couleurs different , noir, gris argentE, dorE', '2018-08-28'),
	(12, 'Stylo', 800, 7, 33, 'stylo de marque, En procenance de France', '2018-08-28');
/*!40000 ALTER TABLE `produits` ENABLE KEYS */;

-- Export de la structure de la table javatest. user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `chiffreDaffaire` double DEFAULT '0',
  `numTel` varchar(50) DEFAULT '0',
  `nom` varchar(50) DEFAULT NULL,
  `age` varchar(50) DEFAULT NULL,
  `dateNais` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- Export de données de la table javatest.user : ~6 rows (environ)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `chiffreDaffaire`, `numTel`, `nom`, `age`, `dateNais`) VALUES
	(1, 12323, '23432', 'fetra', '23', '2018-08-01'),
	(3, 0, '0', 'sfd', '343', '2015-06-24'),
	(6, 0, '0', 'mck', '0', '1995-08-12'),
	(9, 346143088, '2000.2', 'Ndrina', NULL, '2018-08-27'),
	(10, 23, '23', 'w', NULL, '2018-08-01'),
	(11, 0, 'sdsf', 'tt', NULL, '2018-07-30');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Export de la structure de la table javatest. vente
DROP TABLE IF EXISTS `vente`;
CREATE TABLE IF NOT EXISTS `vente` (
  `cVente` int(6) NOT NULL AUTO_INCREMENT,
  `valeur` double DEFAULT NULL,
  `payer` binary(5) DEFAULT NULL,
  `dateVente` date DEFAULT NULL,
  `codeUser` int(6) DEFAULT NULL,
  PRIMARY KEY (`cVente`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

-- Export de données de la table javatest.vente : ~10 rows (environ)
/*!40000 ALTER TABLE `vente` DISABLE KEYS */;
INSERT INTO `vente` (`cVente`, `valeur`, `payer`, `dateVente`, `codeUser`) VALUES
	(1, 500, '0\0\0\0\0', '2018-08-23', 6),
	(2, 14000, '0\0\0\0\0', '2017-08-04', 3),
	(3, 12000, '0\0\0\0\0', '2016-05-12', 9),
	(4, 12000, '0\0\0\0\0', '2016-10-16', 3),
	(5, 500, '0\0\0\0\0', '2016-10-06', 3),
	(6, 12000, '1\0\0\0\0', '2017-08-10', 1),
	(7, 12000, '0\0\0\0\0', '2018-08-10', 1),
	(8, 87500, '0\0\0\0\0', '2018-08-10', 1),
	(9, 12000, '0\0\0\0\0', '2018-06-12', 3),
	(10, 3300, '0\0\0\0\0', '2018-08-28', 3);
/*!40000 ALTER TABLE `vente` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
