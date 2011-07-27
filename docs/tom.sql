-- phpMyAdmin SQL Dump
-- version 2.11.6
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 26, 2009 at 07:14 PM
-- Server version: 5.0.51
-- PHP Version: 5.2.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `tom`
--

-- --------------------------------------------------------

--
-- Table structure for table `actor`
--

CREATE TABLE IF NOT EXISTS `actor` (
  `idItem` int(10) unsigned NOT NULL,
  `goals` text,
  PRIMARY KEY  (`idItem`),
  KEY `actor_FKIndex1` (`idItem`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `actor`
--


-- --------------------------------------------------------

--
-- Table structure for table `author`
--

CREATE TABLE IF NOT EXISTS `author` (
  `identifier` int(11) NOT NULL,
  `idItem` int(11) NOT NULL,
  `login` varchar(255) default NULL,
  `dataHora` varchar(255) default NULL,
  `version` varchar(255) default NULL,
  PRIMARY KEY  (`identifier`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `author`
--


-- --------------------------------------------------------

--
-- Table structure for table `classItem`
--

CREATE TABLE IF NOT EXISTS `classItem` (
  `idItem` int(10) unsigned NOT NULL,
  `type` enum('NOR','SUP','SUB','ABS','FIN') default NULL,
  PRIMARY KEY  (`idItem`),
  KEY `classItem_FKIndex1` (`idItem`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `classItem`
--


-- --------------------------------------------------------

--
-- Table structure for table `collaboration`
--

CREATE TABLE IF NOT EXISTS `collaboration` (
  `login` varchar(255) NOT NULL default '',
  `idItem` int(10) NOT NULL,
  `dataHora` datetime default NULL,
  `version` varchar(255) default NULL,
  PRIMARY KEY  (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `collaboration`
--


-- --------------------------------------------------------

--
-- Table structure for table `history`
--

CREATE TABLE IF NOT EXISTS `history` (
  `idHistory` int(10) NOT NULL auto_increment,
  `operation` varchar(255) NOT NULL,
  `directory` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `idProject` int(10) NOT NULL,
  PRIMARY KEY  (`idHistory`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `history`
--


-- --------------------------------------------------------

--
-- Table structure for table `project`
--

CREATE TABLE IF NOT EXISTS `project` (
  `idProject` int(10) NOT NULL auto_increment,
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text,
  `status` enum('U','S') NOT NULL default 'U' COMMENT 'U - Unstructured, S - Structured',
  `obs` text,
  PRIMARY KEY  (`idProject`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `project`
--

INSERT INTO `project` (`idProject`, `id`, `name`, `description`, `status`, `obs`) VALUES
(1, '', 'Configure this', '', 'U', '');

-- --------------------------------------------------------

--
-- Table structure for table `project_version`
--

CREATE TABLE IF NOT EXISTS `project_version` (
  `project_idProject` int(11) NOT NULL,
  `versions_idVersion` int(11) NOT NULL,
  UNIQUE KEY `versions_idVersion` (`versions_idVersion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `project_version`
--


-- --------------------------------------------------------

--
-- Table structure for table `requeriment`
--

CREATE TABLE IF NOT EXISTS `requeriment` (
  `idItem` int(10) unsigned NOT NULL,
  `benefit` int(10) unsigned default NULL,
  `penalty` int(10) unsigned default NULL,
  `cost` int(10) unsigned default NULL,
  `risk` int(10) unsigned default NULL,
  PRIMARY KEY  (`idItem`),
  KEY `requeriment_FKIndex1` (`idItem`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `requeriment`
--


-- --------------------------------------------------------

--
-- Table structure for table `status`
--

CREATE TABLE IF NOT EXISTS `status` (
  `idStatus` int(11) NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY  (`idStatus`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `status`
--


-- --------------------------------------------------------

--
-- Table structure for table `typeItem`
--

CREATE TABLE IF NOT EXISTS `typeItem` (
  `idTypeItem` int(10) unsigned NOT NULL auto_increment,
  `description` varchar(255) default NULL,
  `subType` enum('I','A') default 'I',
  PRIMARY KEY  (`idTypeItem`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `typeItem`
--

INSERT INTO `typeItem` (`idTypeItem`, `description`, `subType`) VALUES
(1, 'Need', 'I'),
(2, 'Feature', 'I'),
(3, 'Actor', 'I'),
(4, 'Use Case', 'I'),
(5, 'Class', 'I'),
(6, 'Test Case', 'I'),
(7, 'Vision', 'A'),
(8, 'Use Case Specification', 'A'),
(9, 'Use Case Diagram', 'A'),
(10, 'Class Diagram', 'A'),
(11, 'Test Case Specification', 'A'),
(12, 'Requeriment', 'I'),
(13, 'SubSystem', 'I');

-- --------------------------------------------------------

--
-- Table structure for table `typeLink`
--

CREATE TABLE IF NOT EXISTS `typeLink` (
  `idTypeLink` int(10) unsigned NOT NULL auto_increment,
  `description` varchar(255) default NULL,
  `group` enum('GEN','DEP') NOT NULL default 'GEN' COMMENT 'GEN - generic, DEP - Dependency, ...',
  PRIMARY KEY  (`idTypeLink`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `typeLink`
--

INSERT INTO `typeLink` (`idTypeLink`, `description`, `group`) VALUES
(1, 'Dependency', 'GEN'),
(2, 'Evolution', 'GEN'),
(3, 'Abstraction', 'GEN'),
(4, 'Contribution', 'GEN'),
(5, 'Belongs', 'GEN');

-- --------------------------------------------------------

--
-- Table structure for table `useCase`
--

CREATE TABLE IF NOT EXISTS `useCase` (
  `idItem` int(10) unsigned NOT NULL,
  `mainFlow` text,
  PRIMARY KEY  (`idItem`),
  KEY `useCase_FKIndex1` (`idItem`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `useCase`
--


-- --------------------------------------------------------

--
-- Table structure for table `u_actor`
--

CREATE TABLE IF NOT EXISTS `u_actor` (
  `identifier` int(10) NOT NULL auto_increment,
  `id` varchar(100) default NULL,
  `name` varchar(255) NOT NULL,
  `artifact` text NOT NULL,
  `version` varchar(100) default NULL,
  `rule` varchar(255) default NULL,
  `description` text,
  `goals` text,
  PRIMARY KEY  (`identifier`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `u_actor`
--


-- --------------------------------------------------------

--
-- Table structure for table `u_item`
--

CREATE TABLE IF NOT EXISTS `u_item` (
  `identifier` int(11) NOT NULL auto_increment,
  `id` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `artfact` varchar(255) default NULL,
  `version` varchar(255) default NULL,
  `rule` varchar(255) default NULL,
  PRIMARY KEY  (`identifier`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `u_item`
--


-- --------------------------------------------------------

--
-- Table structure for table `u_requeriment`
--

CREATE TABLE IF NOT EXISTS `u_requeriment` (
  `identifier` int(20) NOT NULL auto_increment,
  `id` varchar(100) default NULL,
  `name` varchar(255) NOT NULL,
  `artifact` text NOT NULL,
  `version` varchar(100) default NULL,
  `rule` varchar(255) NOT NULL,
  `description` text,
  `useCases` text,
  PRIMARY KEY  (`identifier`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `u_requeriment`
--


-- --------------------------------------------------------

--
-- Table structure for table `u_subSystem`
--

CREATE TABLE IF NOT EXISTS `u_subSystem` (
  `identifier` int(20) NOT NULL auto_increment,
  `id` varchar(100) default NULL,
  `name` varchar(255) NOT NULL,
  `artifact` text NOT NULL,
  `version` varchar(100) default NULL,
  `rule` varchar(255) NOT NULL,
  `description` text,
  `requeriments` text,
  PRIMARY KEY  (`identifier`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `u_subSystem`
--


-- --------------------------------------------------------

--
-- Table structure for table `version`
--

CREATE TABLE IF NOT EXISTS `version` (
  `idVersion` int(10) NOT NULL auto_increment,
  `id` varchar(255) NOT NULL,
  `description` text,
  `phase` varchar(255) default NULL,
  `dateTime` datetime NOT NULL,
  `status` tinyint(1) NOT NULL default '1',
  `idProject` int(10) NOT NULL,
  PRIMARY KEY  (`idVersion`),
  KEY `FK14F51CD81FF7DF1F` (`idProject`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `version`
--

INSERT INTO `version` (`idVersion`, `id`, `description`, `phase`, `dateTime`, `status`, `idProject`) VALUES
(1, 'First Version', 'Created automatically', 'Plan', '2009-10-20 20:00:00', 1, 1);
