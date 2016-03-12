CREATE TABLE `users` (
  `RecordID` int(11) NOT NULL AUTO_INCREMENT,
  `inlognaam` varchar(30) NOT NULL,
  `wachtwoord` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`RecordID`),
  UNIQUE KEY `UK_1amlfo22066a4m40tclmdouha` (`inlognaam`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
