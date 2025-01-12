-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 12, 2025 at 11:21 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `managment`
--

-- --------------------------------------------------------

--
-- Table structure for table `tasks`
--

CREATE TABLE `tasks` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `completed` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tasks`
--

INSERT INTO `tasks` (`id`, `name`, `description`, `status`, `user_id`, `completed`) VALUES
(1, 'Zadatak', 'test', NULL, 19, 1),
(2, 'Zadatak', 'azuriraj plate', NULL, 22, 1),
(3, 'Zadatak', 'test', NULL, 19, 1),
(4, 'Zadatak', 'test', NULL, 22, 1),
(5, 'Zadatak', 'zavrsi task', NULL, 13, 1),
(6, 'Zadatak', 'zavrsi novi task', NULL, 23, 1),
(7, 'Zadatak', 'reste', NULL, 22, 1),
(8, 'Zadatak', 'novi task', NULL, 46, NULL),
(9, 'Zadatak', 'test', NULL, 13, 1),
(10, 'Zadatak', 'novi task', NULL, 24, NULL),
(11, 'Zadatak', 'novi task', NULL, 48, NULL),
(12, 'Zadatak', 'task', NULL, 13, 1),
(13, 'Zadatak', 'task', NULL, 35, 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(10) NOT NULL,
  `name` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL,
  `phone` varchar(200) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `password` varchar(200) NOT NULL,
  `role` enum('admin','user') DEFAULT 'user',
  `isAdmin` tinyint(1) DEFAULT 0,
  `salary` decimal(10,2) NOT NULL DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `phone`, `address`, `password`, `role`, `isAdmin`, `salary`) VALUES
(2, 'super admin', 'superadmin@gmail.com', 'super', 'super', 'super', 'admin', 2, 1199.00),
(13, 'Esmir', 'esmir@gmail.com', '11-22-44', 'banovici 166', 'esmir123', 'user', 0, 2000.00),
(19, 'Eldar', 'eldar@gmail.com', '22-22-22', 'banovici 17', 'eldar123', 'user', 0, 11000.00),
(21, 'Adel', 'adel@gmail.com', '11-11-11', 'brezovaca 21', 'admin', 'admin', 1, 9000.00),
(22, 'Enis', 'manager@yahoo.com', '00-11-22', 'trestenica', 'manager', 'admin', 3, 3000.00),
(23, 'Dženisa', 'dzenisa@gmail.com', '11-11-22', 'Srebrenik', 'dzenisa123', 'user', 0, 1500.00),
(24, 'Senada', 'senada@icloud.com', '22-22-11', 'Tuzla 11', 'senada123', 'user', 0, 0.00),
(31, 'Edi', 'edi@gmail.com', '22-33-11', 'Sarajevo', 'edi123', 'user', 0, 969.00),
(32, 'Sasa', 'sasa@gmail.com', '22-22-22', 'Mostar', 'sasa123', 'user', 0, 1200.00),
(33, 'Ana', 'ana@icloud.com', '11-11-33', 'Sarajevo', 'ana123', 'user', 0, 0.00),
(34, 'Sinisa', 'sinisa@gmail.com', '11-33-44', 'Sarajevo', 'sinisa123', 'user', 0, 13000.00),
(35, 'Zinedin', 'zinedin@gmail.com', '44-44-44', 'Banovici 223', 'zinedin123', 'user', 0, 3000.00),
(40, 'John Doe', 'john.doe@example.com', '1234567890', '123 Main St', 'password123', 'user', 0, 75000.00),
(41, 'Jane Smith', 'jane.smith@example.com', '2345678901', '456 Oak Ave', 'password456', 'user', 0, 65000.00),
(42, 'Bob Johnson', 'bob.johnson@example.com', '3456789012', '789 Pine Rd', 'password789', 'user', 0, 45000.00),
(43, 'Alice Brown', 'alice.brown@example.com', '4567890123', '321 Maple Dr', 'password321', 'user', 0, 80000.00),
(44, 'Charlie White', 'charlie.white@example.com', '5678901234', '654 Elm St', 'password654', 'user', 0, 70000.00),
(45, 'Diana Green', 'diana.green@example.com', '6789012345', '987 Cedar Ln', 'password987', 'user', 0, 42000.00),
(46, 'Eve Black', 'eve.black@example.com', '7890123456', '159 Birch Ct', 'password159', 'user', 0, 40000.00),
(47, 'Frank Blue', 'frank.blue@example.com', '8901234567', '753 Spruce Way', 'password753', 'user', 0, 68000.00),
(48, 'novi korisnik', 'novikorisnik', 'novi korisnik', 'novi korisnik', 'novi korisnik', 'user', 0, 1200.00);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tasks`
--
ALTER TABLE `tasks`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tasks`
--
ALTER TABLE `tasks`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
