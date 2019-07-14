-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jul 24, 2017 at 08:38 AM
-- Server version: 5.6.36-cll-lve
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gridmepc_vehicledetails`
--

-- --------------------------------------------------------

--
-- Table structure for table `FineData`
--

CREATE TABLE `FineData` (
  `id` int(255) NOT NULL,
  `vehicle_num` varchar(1000) NOT NULL,
  `vehicle_type` varchar(1000) NOT NULL,
  `fine_type` varchar(1000) NOT NULL,
  `fine_charge` varchar(1000) NOT NULL,
  `datetime` varchar(1000) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `FineData`
--

INSERT INTO `FineData` (`id`, `vehicle_num`, `vehicle_type`, `fine_type`, `fine_charge`, `datetime`) VALUES
(114, 'KA 2 F 1234', '2 Wheeler', 'Helmet Fine', '500', '1500698990716'),
(115, 'KA 3 J 6989', '2 Wheeler', 'Helmet Fine', '500', '1500699834338'),
(113, 'KA 2 F 1234', '2 Wheeler', 'Helmet Fine', '500', '1500698972239'),
(112, 'KA 2 F 1234', '2 Wheeler', 'Helmet Fine', '500', '1500698963042'),
(108, 'KA 2 D 3098', '2 Wheeler', 'Helmet Fine', '500', '1500565645030'),
(109, 'KA 2 D 1234', '2 Wheeler', 'Over speed', '500', '1500680002226'),
(110, 'KA 2 D 1234', '2 Wheeler', 'Over speed', '500', '1500680029080'),
(111, 'KA 2 F 1234', '2 Wheeler', 'Helmet Fine', '500', '1500698936873'),
(107, 'KA 43 D 4567', '2 Wheeler', 'Helmet Fine', '500', '1500559576265'),
(103, 'KA 11 D 1234', '2 Wheeler', 'Parking Fine', '100', '1500517855939'),
(104, 'KA 43 D 4567', '2 Wheeler', 'Parking Fine', '200', '1500556977537'),
(105, 'KA 2 D 4567', '2 Wheeler', 'Traffic Fine', '100', '1500558264288'),
(106, 'KA 2 D 4567', '2 Wheeler', 'Parking Fine', '250', '1500558311695'),
(102, 'KA 01 A 3333', 'Light Motor Vehicle', 'Traffic Fine', '100', '1500484224196'),
(97, 'KA 01 A 1111', '2 Wheeler', 'Parking Fine', '100', '1500484136832'),
(98, 'KA 01 A 1111', '2 Wheeler', 'Helmet Fine', '500', '1500484148574'),
(99, 'KA 01 A 2222', '3 Wheeler', 'Parking Fine', '100', '1500484179237'),
(100, 'KA 01 A 2222', '3 Wheeler', 'Over speed', '500', '1500484183254'),
(101, 'KA 01 A 3333', 'Light Motor Vehicle', 'Seat Belt Fine', '500', '1500484198477');

-- --------------------------------------------------------

--
-- Table structure for table `FinerateData`
--

CREATE TABLE `FinerateData` (
  `id` int(255) NOT NULL,
  `vehicle_type` varchar(1000) NOT NULL,
  `fine_type` varchar(1000) NOT NULL,
  `Charge` varchar(1000) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `FinerateData`
--

INSERT INTO `FinerateData` (`id`, `vehicle_type`, `fine_type`, `Charge`) VALUES
(56, '2 Wheeler', 'Parking Fine', '1000'),
(14, '3 Wheeler', 'Parking Fine', '100'),
(12, '2 Wheeler', 'Over speed', '500'),
(21, '2 Wheeler', 'Traffic Fine', '100'),
(22, '3 Wheeler', 'Traffic Fine', '200'),
(23, '3 Wheeler', 'Over speed', '500'),
(32, '2 Wheeler', 'Helmet Fine', '5000'),
(36, 'Light Motor Vehicle', 'Parking Fine', '100'),
(37, 'Light Motor Vehicle', 'Traffic Fine', '100'),
(38, 'Light Motor Vehicle', 'Over speed', '500'),
(39, 'Heavy Motor Vehicle', 'Parking Fine', '500'),
(40, 'Heavy Motor Vehicle', 'Overload Fine', '1000'),
(41, 'Heavy Motor Vehicle', 'Traffic Fine', '1000'),
(42, 'Heavy Motor Vehicle', 'Over speed', '1000'),
(75, 'Light Motor Vehicle', 'Seat Belt Fine', '500');

-- --------------------------------------------------------

--
-- Table structure for table `VehicleData`
--

CREATE TABLE `VehicleData` (
  `id` int(255) NOT NULL,
  `vehicle_type` varchar(1000) NOT NULL,
  `vehicle_num` varchar(1000) NOT NULL,
  `rc_name` varchar(1000) NOT NULL,
  `address_1` varchar(1000) NOT NULL,
  `address_2` varchar(1000) NOT NULL,
  `district` varchar(1000) NOT NULL,
  `state` varchar(1000) NOT NULL,
  `country` varchar(1000) NOT NULL,
  `pin_code` varchar(1000) NOT NULL,
  `phone_num` varchar(1000) NOT NULL,
  `email_id` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `VehicleData`
--

INSERT INTO `VehicleData` (`id`, `vehicle_type`, `vehicle_num`, `rc_name`, `address_1`, `address_2`, `district`, `state`, `country`, `pin_code`, `phone_num`, `email_id`) VALUES
(64, '2 Wheeler', 'KA 01 A 1111', 'Peter', 'Koramangala', '', 'Bangalore', 'Karnataka', 'India', '560095', '9526122211', 'peterparker@gmail.com'),
(65, '3 Wheeler', 'KA 01 A 2222', 'Sunil', 'Madiwala', '', 'Bangalore', 'Karnataka', 'India', '560068', '9141414131', 'sunilbhatt@gmail.com'),
(66, 'Light Motor Vehicle', 'KA 01 A 3333', 'Rahul', 'BTM', '', 'Bangalore', 'Karnataka', 'India', '560029', '9456112474', 'rahulraghav@gmail.com'),
(67, '2 Wheeler', 'KA 11 D 1234', 'Nimisha', 'Bangalore', '', 'Bangalore', 'Karnataka', 'India', '560068', '9778466444', 'nimisha@gmail.com'),
(68, '2 Wheeler', 'KA 43 1235', 'sreerag', 'banglore', '', 'banglore', 'Kerala', 'India', '236963', '3696963963', 'sff@gamil.com'),
(69, '2 Wheeler', 'KA 22 D 2345', 'raj', 'tamilnadu', '', 'kolar', 'Goa', 'India', '363235', '363636363636', 'rtf@gmail.com'),
(70, '2 Wheeler', 'KA 43 D 4567', 'ghjjj', 'rttgg', 'sfff', 'dfgh', 'Bihar', 'India', '236258', '3636362514', 'ertf@gmail.com'),
(71, '2 Wheeler', 'KA 2 D 4567', 'dfgh', 'rtt', 'rty', 'dgg', 'Andaman and Nicobar Islands', 'India', '236523', '1233214563', 'wedd@gmail.com'),
(72, 'Light Motor Vehicle', 'HR 12 G 3456', 'mmu', 'ssdf', 'ssddd', 'asssdaas', 'Andaman and Nicobar Islands', 'India', '123456', '1234567889', 'asd@gmail.com'),
(73, '2 Wheeler', 'HR 2 B 4567', 'ssdd', 'qww', 'asd', 'asd', 'Andaman and Nicobar Islands', 'India', '123456', '1234123456', 'asd@gmail.com'),
(74, '2 Wheeler', 'KA 2 D 1235', 'asd', 'sdd', 'sdd', 'asd', 'Andaman and Nicobar Islands', 'India', '123456', '2345678909', 'asd@gmail.com'),
(75, '2 Wheeler', 'KA 2 D 3098', 'fgh', 'wrty', 'rhh', 'erg', 'Andaman and Nicobar Islands', 'India', '236541', '1236547852', 'adfg@gmail.com'),
(76, '2 Wheeler', 'KA 2 D 1234', 'nim', 'wer', 'erty', 'wefg', 'Andaman and Nicobar Islands', 'India', '258258', '2582582581', 'erfg@gmail.com'),
(77, '2 Wheeler', 'KA 2 D 1254', 'jim', 'banglr', 'banglr', 'kadugidi', 'Karnataka', 'India', '369258', '3636366936', 'nis@gmail.com'),
(78, '2 Wheeler', 'KA 2 F 1234', 'jooo', 'malleswara', 'banglr', 'kadugodi', 'Karnataka', 'India', '363699', '636936936966', 'sedff@gmail.con'),
(79, '2 Wheeler', 'KA 02 RR 1234', 'nimisha', 'malleswaram', 'banglr', 'kadugodi', 'Karnataka', 'India', '363639', '363636363636', 'rftg@gmail.com'),
(80, '2 Wheeler', 'KA 3 J 6989', 'mallesh', 'Whitefield', 'kr puram', 'b south', 'Karnataka', 'India', '560066', '1234567891', 'abc@gmail.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `FineData`
--
ALTER TABLE `FineData`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `FinerateData`
--
ALTER TABLE `FinerateData`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `VehicleData`
--
ALTER TABLE `VehicleData`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `vehicle_num` (`vehicle_num`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `FineData`
--
ALTER TABLE `FineData`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=116;
--
-- AUTO_INCREMENT for table `FinerateData`
--
ALTER TABLE `FinerateData`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=76;
--
-- AUTO_INCREMENT for table `VehicleData`
--
ALTER TABLE `VehicleData`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
