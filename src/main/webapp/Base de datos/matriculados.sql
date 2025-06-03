-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-06-2025 a las 02:37:02
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `matriculados`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `matriculados`
--

CREATE TABLE `matriculados` (
  `codiMedi` int(11) NOT NULL,
  `ndniMedi` varchar(8) NOT NULL,
  `appaMedi` varchar(50) NOT NULL,
  `apmaMedi` varchar(50) NOT NULL,
  `nombMedi` varchar(50) NOT NULL,
  `fechNaciMedi` date NOT NULL,
  `logiMedi` varchar(100) NOT NULL,
  `passMedi` varchar(500) NOT NULL,
  `secretKey` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `matriculados`
--

INSERT INTO `matriculados` (`codiMedi`, `ndniMedi`, `appaMedi`, `apmaMedi`, `nombMedi`, `fechNaciMedi`, `logiMedi`, `passMedi`, `secretKey`) VALUES
(1, '12345678', 'NOSE', 'NOSE', 'NOSE', '2025-06-05', 'NOSE', '$2a$10$0tOFKYUug1mU9Z968e0L5u2GRQ0YNlEREHpNnOa6J21wAo1nmOliS', NULL),
(2, '87456321', 'ESON', 'ESON', 'GAAA', '2025-06-12', 'ESON', '$2a$10$Wqz8TegsXrUbMNpohC687uH5uBqEbzRAe4nqRjLZzthgNi7633xHu', NULL);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `matriculados`
--
ALTER TABLE `matriculados`
  ADD PRIMARY KEY (`codiMedi`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `matriculados`
--
ALTER TABLE `matriculados`
  MODIFY `codiMedi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
