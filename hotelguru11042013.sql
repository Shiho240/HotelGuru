-- phpMyAdmin SQL Dump
-- version 4.0.3
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 04, 2013 at 09:55 PM
-- Server version: 5.6.12-log
-- PHP Version: 5.5.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `hotelguru`
--
CREATE DATABASE IF NOT EXISTS `hotelguru` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `hotelguru`;

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE IF NOT EXISTS `comments` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `Ship_Name` varchar(32) NOT NULL,
  `Room_Num` int(11) NOT NULL,
  `Comment` text NOT NULL,
  `Username` varchar(16) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`cid`, `Ship_Name`, `Room_Num`, `Comment`, `Username`) VALUES
(1, 'Queen Mary 2', 12002, 'This room was totally Baller', 'MasterShihoChief'),
(2, 'Queen Mary 2', 12002, 'Room Sucked Dick', 'test'),
(3, 'Queen Mary 2', 12002, 'it was ok....', 'test7');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(16) NOT NULL,
  `Password` varchar(64) NOT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `Username` (`Username`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`uid`, `Username`, `Password`) VALUES
(1, 'MasterShihoChief', 'LiMqtuFxWacLi7hM5d5kbw==\n'),
(2, 'test', 'test'),
(3, 'Wraith240', 'rOlNSCfSqZh8aZ9nowJiGw==\n'),
(4, 'blah', 'CVi59blb2gGSOm5H7YWhuw==\n'),
(7, 'blah1', 'M32grfFkoZc3I3Nx0m0Qvw==\n'),
(9, 'blah2', 'WDOBqw5P73SzWE55BY3n3A==\n'),
(10, 'test3', 'iRLnfVKd4LAoMlXkvjtd1A==\n'),
(11, 'test4', 'yg0fHPNJ2Eel+xp0OJAHVQ==\n'),
(12, 'test5', '6X/a5uJbkg7r8RH9V5pwmA==\n'),
(14, 'test6', 's2RhwvB3PBaKeQ0/Lu+rTg==\n'),
(15, 'test7', 'Bjxs19r6idzelxUpztFCqg==\n');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
