CREATE TABLE `b2` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `B1IDBG` int(11) NOT NULL,
  `B2DIBG` int(11) NOT NULL,
  `B2MIBG` int(11) NOT NULL,
  `B2JIBG` int(11) NOT NULL,
  `IDNR` int(11) NOT NULL,
  `B2RNBG` int(11) NOT NULL,
  `B2FCBG` int(11) NOT NULL,
  `B2RDNR` int(11) NOT NULL,
  `B2RMNR` int(11) NOT NULL,
  `B2RJNR` int(11) NOT NULL,
  `B2RDCR` int(11) NOT NULL,
  `B2RMCR` int(11) NOT NULL,
  `B2RJCR` int(11) NOT NULL,
  `B2ANNR` varchar(50) NOT NULL,
  `B2VNNR` varchar(50) NOT NULL,
  `B2GSNR` varchar(1) NOT NULL,
  `B2GDNR` int(11) NOT NULL,
  `B2GMNR` int(11) NOT NULL,
  `B2GJNR` int(11) NOT NULL,
  `B2GDCR` int(11) NOT NULL,
  `B2GMCR` int(11) NOT NULL,
  `B2GJCR` int(11) NOT NULL,
  `B2GNNR` varchar(50) NOT NULL,
  `B2ODNR` int(11) NOT NULL,
  `B2OMNR` int(11) NOT NULL,
  `B2OJNR` int(11) NOT NULL,
  `B2ODCR` int(11) NOT NULL,
  `B2OMCR` int(11) NOT NULL,
  `B2OJCR` int(11) NOT NULL,
  `B2ONNR` varchar(50) NOT NULL,
  `B2NANR` varchar(40) NOT NULL,
  `B2WDNR` varchar(50) NOT NULL,
  `B2VWNR` varchar(2) NOT NULL,
  `B2AANR` varchar(100) NOT NULL,
  `B2AAN` varchar(128) NOT NULL,
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
  KEY `UK_dicjtkptfhehv3ujmgcw1ajen` (`IDNR`,`B1IDBG`,`B2DIBG`,`B2MIBG`,`B2JIBG`,`B2RNBG`),
  KEY `UK_qb9sapnkimuneip3q8dev0fjv` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `b3` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `B1IDBG` int(11) NOT NULL,
  `B2DIBG` int(11) NOT NULL,
  `B2MIBG` int(11) NOT NULL,
  `B2JIBG` int(11) NOT NULL,
  `IDNR` int(11) NOT NULL,
  `B2RNBG` int(11) NOT NULL,
  `B3TYPE` int(11) NOT NULL,
  `B3VRNR` int(11) NOT NULL,
  `B3KODE` int(11) NOT NULL,
  `B3RGLN` int(11) NOT NULL,
  `B2FCBG` int(11) NOT NULL,
  `B3MDNR` int(11) NOT NULL,
  `B3MMNR` int(11) NOT NULL,
  `B3MJNR` int(11) NOT NULL,
  `B3MDCR` int(11) NOT NULL,
  `B3MMCR` int(11) NOT NULL,
  `B3MJCR` int(11) NOT NULL,
  `B3GEGEVEN` varchar(50) NOT NULL,
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
  KEY `UK_lv516kgn8ylqj0furwaktn24a` (`IDNR`,`B1IDBG`,`B2DIBG`,`B2MIBG`,`B2JIBG`,`B2RNBG`),
  KEY `UK_sl7i9jpuungusgmqmnlchoxfj` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `b4` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `B1IDBG` int(11) NOT NULL,
  `B2DIBG` int(11) NOT NULL,
  `B2MIBG` int(11) NOT NULL,
  `B2JIBG` int(11) NOT NULL,
  `IDNR` int(11) NOT NULL,
  `B2FDBG` int(11) NOT NULL,
  `B2FMBG` int(11) NOT NULL,
  `B2FJBG` int(11) NOT NULL,
  `B2PGBG` varchar(20) NOT NULL,
  `B2VHBG` int(11) NOT NULL,
  `B4GKBG` varchar(50) NOT NULL,
  `B4SPBG` varchar(50) NOT NULL,
  `B4AAN` varchar(255) NOT NULL,
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
  KEY `UK_o3gdrhily3trco1liwhan0eix` (`IDNR`,`B1IDBG`,`B2DIBG`,`B2MIBG`,`B2JIBG`),
  KEY `UK_kjn6pmvcfevbq599287g1stvg` (`ONDRZKO`,`OPDRNRI`),
  KEY `UK_50a1m82wv7ehvqj8hk9v7wyq5` (`B2FDBG`,`B2FMBG`,`B2FJBG`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `b6` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `B1IDBG` int(11) NOT NULL,
  `B2DIBG` int(11) NOT NULL,
  `B2MIBG` int(11) NOT NULL,
  `B2JIBG` int(11) NOT NULL,
  `IDNR` int(11) NOT NULL,
  `B2RNBG` int(11) NOT NULL,
  `B6VRNR` int(11) NOT NULL,
  `B6MDNR` int(11) NOT NULL,
  `B6MMNR` int(11) NOT NULL,
  `B6MJNR` int(11) NOT NULL,
  `B6MDCR` int(11) NOT NULL,
  `B6MMCR` int(11) NOT NULL,
  `B6MJCR` int(11) NOT NULL,
  `B6TPNR` varchar(2) NOT NULL,
  `B6SINR` int(11) NOT NULL,
  `B6STNR` varchar(40) NOT NULL,
  `B6NRNR` varchar(9) NOT NULL,
  `B6TVNR` varchar(15) NOT NULL,
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
  KEY `UK_hqqfcia9v7ow3qgx7y4t7m438` (`IDNR`,`B1IDBG`,`B2DIBG`,`B2MIBG`,`B2JIBG`),
  KEY `UK_546waj78jau43cvq8ek1lgrtv` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;