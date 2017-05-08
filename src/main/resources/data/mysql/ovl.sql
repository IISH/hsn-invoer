CREATE TABLE `ovlagv` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDNR` int(11) NOT NULL,
  `VLGNRAG` int(11) NOT NULL,
  `ANMAGV` varchar(50) NOT NULL,
  `TUSAGV` varchar(10) NOT NULL,
  `VRN1AGV` varchar(20) NOT NULL,
  `VRN2AGV` varchar(20) NOT NULL,
  `VRN3AGV` varchar(30) NOT NULL,
  `RAGVOVL` varchar(50) NOT NULL,
  `LFTAGV` int(11) NOT NULL,
  `BRPAGV` varchar(254) NOT NULL,
  `ADRAGV` varchar(50) NOT NULL,
  `HNDAGV` varchar(1) NOT NULL,
  `OPDRNR` varchar(3) NOT NULL,
  `DATUM` varchar(10) NOT NULL,
  `INIT` varchar(3) NOT NULL,
  `VERSIE` varchar(5) NOT NULL,
  `ONDRZKO` varchar(3) NOT NULL,
  `OPDRNRO` varchar(3) NOT NULL,
  `DATUMO` varchar(10) NOT NULL,
  `INITO` varchar(3) NOT NULL,
  `VERSIEO` varchar(5) NOT NULL,
  `OPDRNRI` varchar(3) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_hvjwjokbm9swnfrkimi5joxoj` (`IDNR`,`VLGNRAG`,`ONDRZKO`,`OPDRNRI`),
  KEY `UK_gd3gapg9i41v6yho2pg6rob7b` (`IDNR`),
  KEY `UK_2a5uqu3r1mmg65l3bnsq8eru9` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `ovlbyz` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDNR` int(11) NOT NULL,
  `BYZNR` int(11) NOT NULL,
  `BYZ` varchar(55) NOT NULL,
  `SCHERM` varchar(15) NOT NULL,
  `OPDRNR` varchar(3) NOT NULL,
  `DATUM` varchar(10) NOT NULL,
  `INIT` varchar(3) NOT NULL,
  `VERSIE` varchar(5) NOT NULL,
  `ONDRZKO` varchar(3) NOT NULL,
  `OPDRNRO` varchar(3) NOT NULL,
  `DATUMO` varchar(10) NOT NULL,
  `INITO` varchar(3) NOT NULL,
  `VERSIEO` varchar(5) NOT NULL,
  `OPDRNRI` varchar(3) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `UK_g01c8i05bbuc3454lx7umovip` (`IDNR`),
  KEY `UK_tr755jq0xr65v9tcpfxl1ia87` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `ovlech` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDNR` int(11) NOT NULL,
  `VLGECH` int(11) NOT NULL,
  `ANMEOVL` varchar(50) NOT NULL,
  `TUSEOVL` varchar(10) NOT NULL,
  `VRN1EOVL` varchar(20) NOT NULL,
  `VRN2EOVL` varchar(20) NOT NULL,
  `VRN3EOVL` varchar(30) NOT NULL,
  `LEVEOVL` varchar(1) NOT NULL,
  `LFTEOVL` int(11) NOT NULL,
  `BRPEOVL` varchar(254) NOT NULL,
  `ADREOVL` varchar(50) NOT NULL,
  `OPDRNR` varchar(3) NOT NULL,
  `DATUM` varchar(10) NOT NULL,
  `INIT` varchar(3) NOT NULL,
  `VERSIE` varchar(5) NOT NULL,
  `ONDRZKO` varchar(3) NOT NULL,
  `OPDRNRO` varchar(3) NOT NULL,
  `DATUMO` varchar(10) NOT NULL,
  `INITO` varchar(3) NOT NULL,
  `VERSIEO` varchar(5) NOT NULL,
  `OPDRNRI` varchar(3) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_g6lrwyiq2eui5fmuj079ni0h3` (`IDNR`,`VLGECH`,`ONDRZKO`,`OPDRNRI`),
  KEY `UK_6opis1mn3rkw1fvmp4e272f82` (`IDNR`),
  KEY `UK_b15fvtdq3iy81p6p86oyv2cv7` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `ovlknd` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDNR` int(11) NOT NULL,
  `OACGEMNR` int(11) NOT NULL,
  `OACGEMNM` varchar(50) NOT NULL,
  `OAKTENR` int(11) NOT NULL,
  `OAKTEUUR` int(11) NOT NULL,
  `OAKTEMIN` int(11) NOT NULL,
  `OAKTEDAG` int(11) NOT NULL,
  `OAKTEMND` int(11) NOT NULL,
  `OAKTEJR` int(11) NOT NULL,
  `LENGEB` int(11) NOT NULL,
  `AGVVADR` varchar(1) NOT NULL,
  `NAGVR` int(11) NOT NULL,
  `HNDTKV` varchar(1) NOT NULL,
  `GEGOVL` varchar(1) NOT NULL,
  `GEGVAD` varchar(1) NOT NULL,
  `GEGMOE` varchar(1) NOT NULL,
  `ANMOVL` varchar(50) NOT NULL,
  `TUSOVL` varchar(10) NOT NULL,
  `VRN1OVL` varchar(20) NOT NULL,
  `VRN2OVL` varchar(20) NOT NULL,
  `VRN3OVL` varchar(30) NOT NULL,
  `LAAUG` int(11) NOT NULL,
  `BRPOVL` varchar(254) NOT NULL,
  `GBPOVL` int(11) NOT NULL,
  `GGMOVL` varchar(50) NOT NULL,
  `ADROVL` varchar(50) NOT NULL,
  `BRGOVL` varchar(1) NOT NULL,
  `OVLSEX` varchar(1) NOT NULL,
  `OVLDAG` int(11) NOT NULL,
  `OVLMND` int(11) NOT NULL,
  `OVLJR` int(11) NOT NULL,
  `OVLUUR` int(11) NOT NULL,
  `OVLMIN` int(11) NOT NULL,
  `PLOOVL` varchar(50) NOT NULL,
  `LFTUOVL` int(11) NOT NULL,
  `LFTDOVL` int(11) NOT NULL,
  `LFTWOVL` int(11) NOT NULL,
  `LFTMOVL` int(11) NOT NULL,
  `LFTJOVL` int(11) NOT NULL,
  `MREOVL` int(11) NOT NULL,
  `ANMVOVL` varchar(50) NOT NULL,
  `TUSVOVL` varchar(10) NOT NULL,
  `VRN1VOVL` varchar(20) NOT NULL,
  `VRN2VOVL` varchar(20) NOT NULL,
  `VRN3VOVL` varchar(30) NOT NULL,
  `LEVVOVL` varchar(1) NOT NULL,
  `BRPVOVL` varchar(254) NOT NULL,
  `LFVROVL` int(11) NOT NULL,
  `ADRVOVL` varchar(50) NOT NULL,
  `ANMMOVL` varchar(50) NOT NULL,
  `TUSMOVL` varchar(10) NOT NULL,
  `VRN1MOVL` varchar(20) NOT NULL,
  `VRN2MOVL` varchar(20) NOT NULL,
  `VRN3MOVL` varchar(30) NOT NULL,
  `LEVMOVL` varchar(1) NOT NULL,
  `BRPMOVL` varchar(254) NOT NULL,
  `LFMROVL` int(11) NOT NULL,
  `ADRMOVL` varchar(50) NOT NULL,
  `EXTRACT` varchar(1) NOT NULL,
  `PROBLM` varchar(1) NOT NULL,
  `OPDRNR` varchar(3) NOT NULL,
  `DATUM` varchar(10) NOT NULL,
  `INIT` varchar(3) NOT NULL,
  `VERSIE` varchar(5) NOT NULL,
  `ONDRZKO` varchar(3) NOT NULL,
  `OPDRNRO` varchar(3) NOT NULL,
  `DATUMO` varchar(10) NOT NULL,
  `INITO` varchar(3) NOT NULL,
  `VERSIEO` varchar(5) NOT NULL,
  `OPDRNRI` varchar(3) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_p0rw3ucvnug9jh48i8a9yruy8` (`IDNR`,`ONDRZKO`,`OPDRNRI`),
  KEY `UK_2ek4vlfqb3btplgc9lhvs4fl` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
