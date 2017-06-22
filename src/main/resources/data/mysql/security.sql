CREATE TABLE `users` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `inlognaam` varchar(30) NOT NULL,
  `wachtwoord` varchar(60) DEFAULT NULL,
  `triple` varchar(3) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_1amlfo22066a4m40tclmdouha` (`inlognaam`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `user_workorders` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `triple` int(11) NOT NULL,
  `ONDRZKO` varchar(3) NOT NULL,
  `OPDRNRI` varchar(3) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_c5bimtg9kl766vmy6aued6cvg` (`triple`,`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
