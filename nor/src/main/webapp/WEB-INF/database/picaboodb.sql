-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        10.4.8-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- picaboodb 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `picaboodb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `picaboodb`;

-- 테이블 picaboodb.address 구조 내보내기
CREATE TABLE IF NOT EXISTS `address` (
  `address_no` varchar(50) NOT NULL,
  `road_ful_addr` varchar(500) NOT NULL COMMENT '전체 도로명 주소',
  `addr_detail` varchar(500) NOT NULL COMMENT '상세정보',
  KEY `FK_address_franchisee` (`address_no`),
  CONSTRAINT `FK_address_customer` FOREIGN KEY (`address_no`) REFERENCES `customer` (`customer_no`),
  CONSTRAINT `FK_address_franchisee` FOREIGN KEY (`address_no`) REFERENCES `franchisee` (`franchisee_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 picaboodb.admin 구조 내보내기
CREATE TABLE IF NOT EXISTS `admin` (
  `admin` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 picaboodb.customer 구조 내보내기
CREATE TABLE IF NOT EXISTS `customer` (
  `customer_no` varchar(50) NOT NULL,
  `customer_id` varchar(50) NOT NULL,
  `customer_pw` varchar(50) NOT NULL,
  `customer_name` varchar(50) NOT NULL,
  `customer_birth` varchar(50) NOT NULL,
  `customer_gender` varchar(50) NOT NULL,
  `customer_email` varchar(50) DEFAULT NULL,
  `customer_phone` varchar(50) DEFAULT NULL,
  `customer_type` enum('C','O') NOT NULL,
  PRIMARY KEY (`customer_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 picaboodb.franchisee 구조 내보내기
CREATE TABLE IF NOT EXISTS `franchisee` (
  `franchisee_no` varchar(50) NOT NULL,
  `franchisee_crn` varchar(50) NOT NULL COMMENT '사업자 등록증',
  `franchisee_name` varchar(50) NOT NULL COMMENT '상호명',
  `franchisee_phone` varchar(50) NOT NULL,
  PRIMARY KEY (`franchisee_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 picaboodb.seat 구조 내보내기
CREATE TABLE IF NOT EXISTS `seat` (
  `franchisee_no` varchar(50) NOT NULL,
  `seat_no` int(11) NOT NULL,
  `seat_row` int(11) DEFAULT NULL,
  `seat_cols` int(11) DEFAULT NULL,
  `seat_use` enum('Y','N') NOT NULL DEFAULT 'N',
  PRIMARY KEY (`franchisee_no`,`seat_no`),
  CONSTRAINT `FK_seat_franchisee` FOREIGN KEY (`franchisee_no`) REFERENCES `franchisee` (`franchisee_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
