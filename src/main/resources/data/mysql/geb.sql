CREATE TABLE `gebakte` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `GEMNR` int(11) NOT NULL,
  `JAAR` int(11) NOT NULL,
  `AKTENR` int(11) NOT NULL,
  `IDNR` int(11) NOT NULL,
  `GEBKODE` int(11) NOT NULL,
  `OVERSAMP` varchar(1) NOT NULL,
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
  UNIQUE KEY `UK_q930nm8po54g0j8wxt4fxjebo` (`IDNR`,`ONDRZKO`,`OPDRNRI`),
  KEY `UK_p57kr3fohb3yaqitf7li0mys5` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `gebbyz` (
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
  KEY `UK_8fwmc6vv0lcibtvj594bxh1uf` (`IDNR`),
  KEY `UK_jgc6m86s4de7s8h77kd7mcr1d` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `gebgtg` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDNR` int(11) NOT NULL,
  `VLGNRGT` int(11) NOT NULL,
  `ANMGT` varchar(50) NOT NULL,
  `TUSGT` varchar(10) NOT NULL,
  `VRN1GT` varchar(20) NOT NULL,
  `VRN2GT` varchar(20) NOT NULL,
  `VRN3GT` varchar(30) NOT NULL,
  `LFTGT` int(11) NOT NULL,
  `BRPGT` varchar(50) NOT NULL,
  `ADRGT` varchar(50) NOT NULL,
  `HNDGT` varchar(1) NOT NULL,
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
  UNIQUE KEY `UK_6bxasgsqhveb63ejnpqk72lgv` (`IDNR`,`ONDRZKO`,`OPDRNRI`,`VLGNRGT`),
  KEY `UK_pql9ey1vbykp6ja0gkm90vq2x` (`IDNR`),
  KEY `UK_t6vcl6om11l5d4o1o563svgm7` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `gebkant` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDNR` int(11) NOT NULL,
  `KANTTYPE` int(11) NOT NULL,
  `KANTDAG` int(11) NOT NULL,
  `KANTMND` int(11) NOT NULL,
  `KANTJR` int(11) NOT NULL,
  `KHUWDAG` int(11) NOT NULL,
  `KHUWMND` int(11) NOT NULL,
  `KHUWJR` int(11) NOT NULL,
  `KHUWGEM` varchar(50) NOT NULL,
  `KHUWANR` varchar(50) NOT NULL,
  `KANMVAD` varchar(50) NOT NULL,
  `KTUSVAD` varchar(10) NOT NULL,
  `KVRN1VAD` varchar(20) NOT NULL,
  `KVRN2VAD` varchar(20) NOT NULL,
  `KVRN3VAD` varchar(30) NOT NULL,
  `KWYZDAG` int(11) NOT NULL,
  `KWYZMND` int(11) NOT NULL,
  `KWYZJR` int(11) NOT NULL,
  `KWYZKB` int(11) NOT NULL,
  `KWYZSTBL` int(11) NOT NULL,
  `KGMRB` varchar(50) NOT NULL,
  `KGMERK` varchar(50) NOT NULL,
  `KWGMMR` varchar(50) NOT NULL,
  `KBRPMR` varchar(50) NOT NULL,
  `KANMGEB` varchar(50) NOT NULL,
  `KVRN1GEB` varchar(20) NOT NULL,
  `KVRN2GEB` varchar(20) NOT NULL,
  `KVRN3GEB` varchar(30) NOT NULL,
  `KTUSGEB` varchar(10) NOT NULL,
  `KSEXGEB` varchar(1) NOT NULL,
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
  UNIQUE KEY `UK_fd26yt8nw9tpi577804lvt9fl` (`IDNR`,`ONDRZKO`,`OPDRNRI`),
  KEY `UK_hf60g5w10prwxajniss657590` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `gebknd` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `GEMNR` int(11) NOT NULL,
  `JAAR` int(11) NOT NULL,
  `AKTENR` int(11) NOT NULL,
  `COHORTNR` int(11) NOT NULL,
  `IDNR` int(11) NOT NULL,
  `OVERSAMP` varchar(1) NOT NULL,
  `INVBEPER` varchar(1) NOT NULL,
  `AKTEUUR` int(11) NOT NULL,
  `AKTEDAG` int(11) NOT NULL,
  `AKTEMND` int(11) NOT NULL,
  `LENGEB` int(11) NOT NULL,
  `ANMAG` varchar(50) NOT NULL,
  `TUSAG` varchar(10) NOT NULL,
  `VRN1AG` varchar(20) NOT NULL,
  `VRN2AG` varchar(20) NOT NULL,
  `VRN3AG` varchar(30) NOT NULL,
  `LFTAG` int(11) NOT NULL,
  `BRPAG` varchar(50) NOT NULL,
  `ADRAG` varchar(50) NOT NULL,
  `HNDAG` varchar(1) NOT NULL,
  `GEBDAG` int(11) NOT NULL,
  `GEBMND` int(11) NOT NULL,
  `GEBJR` int(11) NOT NULL,
  `GEBUUR` int(11) NOT NULL,
  `GEBMIN` int(11) NOT NULL,
  `GEBSEX` varchar(1) NOT NULL,
  `GEBADR` varchar(50) NOT NULL,
  `ANMMR` varchar(50) NOT NULL,
  `TUSMR` varchar(10) NOT NULL,
  `VRN1MR` varchar(20) NOT NULL,
  `VRN2MR` varchar(20) NOT NULL,
  `VRN3MR` varchar(30) NOT NULL,
  `LFTMR` int(11) NOT NULL,
  `BRGSTMR` varchar(1) NOT NULL,
  `BRPMR` varchar(50) NOT NULL,
  `ADRMR` varchar(50) NOT NULL,
  `ANMGEB` varchar(50) NOT NULL,
  `TUSGEB` varchar(10) NOT NULL,
  `VRN1GEB` varchar(20) NOT NULL,
  `VRN2GEB` varchar(20) NOT NULL,
  `VRN3GEB` varchar(30) NOT NULL,
  `KANT` varchar(1) NOT NULL,
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
  UNIQUE KEY `UK_g68expdhfdv2fa2v8n5fkjwf3` (`IDNR`,`ONDRZKO`,`OPDRNRI`),
  KEY `UK_sgvnx6xwpgq0pgfwreov7f9el` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `gebvdr` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDNR` int(11) NOT NULL,
  `GEGVR` varchar(1) NOT NULL,
  `ANMVR` varchar(50) NOT NULL,
  `TUSVR` varchar(10) NOT NULL,
  `VRN1VR` varchar(20) NOT NULL,
  `VRN2VR` varchar(20) NOT NULL,
  `VRN3VR` varchar(30) NOT NULL,
  `LFTVR` int(11) NOT NULL,
  `BRPVR` varchar(50) NOT NULL,
  `ADRVR` varchar(50) NOT NULL,
  `G5OOSD` int(11) NOT NULL,
  `G5OOSM` int(11) NOT NULL,
  `G5OOSJ` int(11) NOT NULL,
  `G5OOGS` varchar(50) NOT NULL,
  `G5VOOD` int(11) NOT NULL,
  `G5VOOM` int(11) NOT NULL,
  `G5VOOJ` int(11) NOT NULL,
  `G5VOGO` varchar(50) NOT NULL,
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
  UNIQUE KEY `UK_9u5tpohpuijtfgxdwkygkipg6` (`IDNR`,`ONDRZKO`,`OPDRNRI`),
  KEY `UK_7ui91johm82v9pf8yjc1lm8p0` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `stpb` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DOELNR` int(11) NOT NULL,
  `GEMEENTE` varchar(50) NOT NULL,
  `GEMNR` int(11) NOT NULL,
  `JAAR` int(11) NOT NULL,
  `AKTENR` int(11) NOT NULL,
  `COHORTNR` int(11) NOT NULL,
  `IDNR` int(11) NOT NULL,
  `PROVNR` int(11) NOT NULL,
  `REGNR` int(11) NOT NULL,
  `SUBCOHNR` int(11) NOT NULL,
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
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE OR REPLACE VIEW geb AS
  SELECT G.ID, G.GEMNR, P.GEMNAAM, G.JAAR, G.AKTENR, G.IDNR, G.GEBKODE, G.OVERSAMP,
    G.ONDRZKO, G.OPDRNRI, G.OPDRNR, G.DATUM, G.INIT, G.VERSIE, G.OPDRNRO, G.DATUMO, G.INITO, G.VERSIEO
  FROM gebakte AS G
    LEFT JOIN plaats AS P
      ON G.GEMNR = P.GEMNR AND G.GEMNR > 0
  UNION ALL
  SELECT G.ID, G.GEMNR, P.GEMNAAM, G.JAAR, G.AKTENR, G.IDNR, 1 AS GEBKODE, G.OVERSAMP,
    G.ONDRZKO, G.OPDRNRI, G.OPDRNR, G.DATUM, G.INIT, G.VERSIE, G.OPDRNRO, G.DATUMO, G.INITO, G.VERSIEO
  FROM gebknd AS G
    LEFT JOIN plaats AS P
      ON G.GEMNR = P.GEMNR AND G.GEMNR > 0;
