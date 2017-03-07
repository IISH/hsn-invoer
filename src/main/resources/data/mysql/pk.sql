CREATE TABLE `p7` (

`ID` int(11) NOT NULL AUTO_INCREMENT,
`IDNR` int(11) NOT NULL,
`IDNRP` int(11) NOT NULL,
`P7IDBG` int(11) NOT NULL,
`P7OPOG` varchar(255) NOT NULL,
`P7OPOL` varchar(255) NOT NULL,
`P7OPOR` varchar(255) NOT NULL,
`P7OPOB` varchar(255) NOT NULL,
`P7OPIO` varchar(255) NOT NULL,
`P7OPPG` varchar(60) NOT NULL,
`P7OPPC` varchar(60) NOT NULL,
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
  UNIQUE KEY `UK_fgm2n358y1pl3s23fxnx2jhh3` (`IDNR`,`ONDRZKO`,`OPDRNRI`),
  KEY `UK_h546yp3h6shgek69ugtgsdfr3` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `p8` (

`ID` int(11) NOT NULL AUTO_INCREMENT,
`IDNR` int(11) NOT NULL,
`IDNRP` int(11) NOT NULL,
`P8TPNR` int(11) NOT NULL,
`DGADRP` int(11) NOT NULL,
`MDADRP` int(11) NOT NULL,
`JRADRP` int(11) NOT NULL,
`PLADRP` varchar(50) NOT NULL,
`P8OPPD` int(11) NOT NULL,
`P8OPPM` int(11) NOT NULL,
`P8OPPJ` int(11) NOT NULL,
`P8OPWF` varchar(255) NOT NULL,
`P8OPWL` varchar(255) NOT NULL,
`P8OPWS` varchar(255) NOT NULL,
`P8OPWH` varchar(255) NOT NULL,
`P8OPWR` varchar(255) NOT NULL,
`P8OPWT` varchar(255) NOT NULL,
`P8OPWP` varchar(255) NOT NULL,
`P8OPWB` varchar(255) NOT NULL,
`P8OPIL` varchar(50) NOT NULL,
`P8OPIJ` int(11) NOT NULL,
`P8OPIM` int(11) NOT NULL,
`P8OPID` int(11) NOT NULL,
`P8OPAG` varchar(255) NOT NULL,
`P8OPZJ` int(11) NOT NULL,
`P8OPZM` int(11) NOT NULL,
`P8OPZD` int(11) NOT NULL,
`P8OPIO` varchar(255) NOT NULL,
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
  UNIQUE KEY `UK_cnrdkj9v8bepq01k6h5onm6ok` (`IDNR`,`P8TPNR`,`ONDRZKO`,`OPDRNRI`),
  KEY `UK_2payahmjo9b9t0x8ilgi0vn95` (`IDNR`),
  KEY `UK_c6lwpv7b00vt9vuncgtahw9do` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `pkadres` (

`ID` int(11) NOT NULL AUTO_INCREMENT,
`IDNR` int(11) NOT NULL,
`VGNRADP` int(11) NOT NULL,
`DGADRP` int(11) NOT NULL,
`MDADRP` int(11) NOT NULL,
`JRADRP` int(11) NOT NULL,
`VERNUM` varchar(1) NOT NULL,
`STRADRP` varchar(50) NOT NULL,
`PLADRP` varchar(50) NOT NULL,
`LNDADRP` varchar(50) NOT NULL,
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
  UNIQUE KEY `UK_8wvj8mb1yj23s67tlwlwwh7xo` (`IDNR`,`VGNRADP`,`ONDRZKO`,`OPDRNRI`),
  KEY `UK_j29sdchqdn44idsqcvj0we90x` (`IDNR`),
  KEY `UK_c92yl9a157a9yggom66tf6m1s` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `pkbrp` (

`ID` int(11) NOT NULL AUTO_INCREMENT,
`IDNR` int(11) NOT NULL,
`VGNRBRP` int(11) NOT NULL,
`BRPPOSP` varchar(1) NOT NULL,
`BEROEPP` varchar(254) NOT NULL,
`DHBRPP` varchar(1) NOT NULL,
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
  UNIQUE KEY `UK_f6t0py8vdpldug0o82iodvxhj` (`IDNR`,`VGNRBRP`,`ONDRZKO`,`OPDRNRI`),
  KEY `UK_3o863beqgvg1ln7j85cm1y29u` (`IDNR`),
  KEY `UK_7oswfjahgrpbhp70fdjobx0m2` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `pkbyz` (

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

  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `pkeigknd` (

`ID` int(11) NOT NULL AUTO_INCREMENT,
`IDNR` int(11) NOT NULL,
`VGNRKDP` int(11) NOT NULL,
`ANMKNDP` varchar(50) NOT NULL,
`TUSKNDP` varchar(10) NOT NULL,
`VN1KNDP` varchar(20) NOT NULL,
`VN2KNDP` varchar(20) NOT NULL,
`VN3KNDP` varchar(30) NOT NULL,
`GDGKNDP` int(11) NOT NULL,
`GMDKNDP` int(11) NOT NULL,
`GJRKNDP` int(11) NOT NULL,
`GDGKNDPCR` int(11) NOT NULL,
`GMDKNDPCR` int(11) NOT NULL,
`GJRKNDPCR` int(11) NOT NULL,
`GPLKNDP` varchar(50) NOT NULL,
`GLNKNDP` varchar(255) NOT NULL,
`RELKNDP` varchar(50) NOT NULL,
`HDGKNDP` int(11) NOT NULL,
`HMDKNDP` int(11) NOT NULL,
`HJRKNDP` int(11) NOT NULL,
`HPLKNDP` varchar(50) NOT NULL,
`VNMPTNP` varchar(20) NOT NULL,
`TUSPTNP` varchar(10) NOT NULL,
`ANMPTNP` varchar(50) NOT NULL,
`ODGKNDP` int(11) NOT NULL,
`OMDKNDP` int(11) NOT NULL,
`OJRKNDP` int(11) NOT NULL,
`OPLKNDP` varchar(50) NOT NULL,
`ADGKNDP` int(11) NOT NULL,
`AMDKNDP` int(11) NOT NULL,
`AJRKNDP` int(11) NOT NULL,
`APLKNDP` varchar(50) NOT NULL,
`AANTEK` varchar(60) NOT NULL,
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
  UNIQUE KEY `UK_1suqo8rohshobs4724y5hgc9` (`IDNR`,`VGNRKDP`,`ONDRZKO`,`OPDRNRI`),
  KEY `UK_mfwlgabhej3ae37ry05k4d63f` (`IDNR`),
  KEY `UK_4eweoa75t9ijau8ittwtd5uhx` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `pkhuw` (

`ID` int(11) NOT NULL AUTO_INCREMENT,
`IDNR` int(11) NOT NULL,
`VNRHUWP` int(11) NOT NULL,
`ANMHUWP` varchar(50) NOT NULL,
`TUSHUWP` varchar(10) NOT NULL,
`VN1HUWP` varchar(20) NOT NULL,
`VN2HUWP` varchar(20) NOT NULL,
`VN3HUWP` varchar(30) NOT NULL,
`BRPHUWP` varchar(254) NOT NULL,
`GDGHUWP` int(11) NOT NULL,
`GMDHUWP` int(11) NOT NULL,
`GJRHUWP` int(11) NOT NULL,
`GDGHUWPCR` int(11) NOT NULL,
`GMDHUWPCR` int(11) NOT NULL,
`GJRHUWPCR` int(11) NOT NULL,
`GPLHUWP` varchar(50) NOT NULL,
`HDGHUWP` int(11) NOT NULL,
`HMDHUWP` int(11) NOT NULL,
`HJRHUWP` int(11) NOT NULL,
`HPLHUWP` varchar(50) NOT NULL,
`ODGHUWP` int(11) NOT NULL,
`OMDHUWP` int(11) NOT NULL,
`OJRHUWP` int(11) NOT NULL,
`ORDHUWP` int(11) NOT NULL,
`OPLHUWP` varchar(50) NOT NULL,
`ADGHUWP` int(11) NOT NULL,
`AMDHUWP` int(11) NOT NULL,
`AJRHUWP` int(11) NOT NULL,
`APLHUWP` varchar(50) NOT NULL,
`SRTHUWP` varchar(50) NOT NULL,
`DDGHUWP` int(11) NOT NULL,
`DMDHUWP` int(11) NOT NULL,
`DJRHUWP` int(11) NOT NULL,
`OPDGHUWP` int(11) NOT NULL,
`OPMDHUWP` int(11) NOT NULL,
`OPJRHUWP` int(11) NOT NULL,
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
  UNIQUE KEY `UK_sqa1haf626houq3b2tbse44k` (`IDNR`,`VNRHUWP`,`ONDRZKO`,`OPDRNRI`),
  KEY `UK_6ng9yc5sjc3d5m18qs95aog89` (`IDNR`),
  KEY `UK_dyr06p3hh4ugaoqe8wwfihldy` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `pkknd` (

`ID` int(11) NOT NULL AUTO_INCREMENT,
`IDNR` int(11) NOT NULL,
`IDNRP` int(11) NOT NULL,
`GAKTNRP` varchar(8) NOT NULL,
`PKTYPE` int(11) NOT NULL,
`EINDAGPK` int(11) NOT NULL,
`EINMNDPK` int(11) NOT NULL,
`EINJARPK` int(11) NOT NULL,
`CTRDGP` int(11) NOT NULL,
`CTRMDP` int(11) NOT NULL,
`CTRJRP` int(11) NOT NULL,
`CTRPARP` varchar(1) NOT NULL,
`GZNVRMP` varchar(50) NOT NULL,
`ANMPERP` varchar(50) NOT NULL,
`TUSPERP` varchar(10) NOT NULL,
`VNM1PERP` varchar(20) NOT NULL,
`VNM2PERP` varchar(20) NOT NULL,
`VNM3PERP` varchar(30) NOT NULL,
`GDGPERP` int(11) NOT NULL,
`GMDPERP` int(11) NOT NULL,
`GJRPERP` int(11) NOT NULL,
`GDGPERPCR` int(11) NOT NULL,
`GMDPERPCR` int(11) NOT NULL,
`GJRPERPCR` int(11) NOT NULL,
`GPLPERP` varchar(50) NOT NULL,
`NATPERP` varchar(40) NOT NULL,
`GDSPERP` varchar(20) NOT NULL,
`GSLPERP` varchar(1) NOT NULL,
`ANMVDRP` varchar(50) NOT NULL,
`TUSVDRP` varchar(10) NOT NULL,
`VNM1VDRP` varchar(20) NOT NULL,
`VNM2VDRP` varchar(20) NOT NULL,
`VNM3VDRP` varchar(30) NOT NULL,
`GDGVDRP` int(11) NOT NULL,
`GMDVDRP` int(11) NOT NULL,
`GJRVDRP` int(11) NOT NULL,
`GDGVDRPCR` int(11) NOT NULL,
`GMDVDRPCR` int(11) NOT NULL,
`GJRVDRPCR` int(11) NOT NULL,
`GPLVDRP` varchar(50) NOT NULL,
`ANMMDRP` varchar(50) NOT NULL,
`TUSMDRP` varchar(10) NOT NULL,
`VNM1MDRP` varchar(20) NOT NULL,
`VNM2MDRP` varchar(20) NOT NULL,
`VNM3MDRP` varchar(30) NOT NULL,
`GDGMDRP` int(11) NOT NULL,
`GMDMDRP` int(11) NOT NULL,
`GJRMDRP` int(11) NOT NULL,
`GDGMDRPCR` int(11) NOT NULL,
`GMDMDRPCR` int(11) NOT NULL,
`GJRMDRPCR` int(11) NOT NULL,
`GPLMDRP` varchar(50) NOT NULL,
`ODGPERP` int(11) NOT NULL,
`OMDPERP` int(11) NOT NULL,
`OJRPERP` int(11) NOT NULL,
`OPLPERP` varchar(50) NOT NULL,
`OAKPERP` varchar(10) NOT NULL,
`ODOPERP` varchar(50) NOT NULL,
`GEGPERP` varchar(1) NOT NULL,
`GEGVDRP` varchar(1) NOT NULL,
`GEGMDRP` varchar(1) NOT NULL,
`PROBLMP` varchar(1) NOT NULL,
`PSBDGP` int(11) NOT NULL,
`PSBMDP` int(11) NOT NULL,
`PSBJRP` int(11) NOT NULL,
`PSBNRP` varchar(12) NOT NULL,
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
  UNIQUE KEY `UK_h615ebgx8cbvry3ji85fi4w44` (`IDNR`,`ONDRZKO`,`OPDRNRI`),
  KEY `UK_98vafeo4vqp4ajn3g1hgh3vl5` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
