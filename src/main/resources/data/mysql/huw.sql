CREATE TABLE `huwafk` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDNR` int(11) NOT NULL,
  `HDAG` int(11) NOT NULL,
  `HMAAND` int(11) NOT NULL,
  `HJAAR` int(11) NOT NULL,
  `HWAKNR` int(11) NOT NULL,
  `HWAKDG` int(11) NOT NULL,
  `HWAKMD` int(11) NOT NULL,
  `HWAKJR` int(11) NOT NULL,
  `HWAKGR` int(11) NOT NULL,
  `HWAKPL` varchar(50) NOT NULL,
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
  UNIQUE KEY `UK_3stoeb0qpbd49a2wkloetheul` (`IDNR`,`HDAG`,`HMAAND`,`HJAAR`,`HWAKNR`,`ONDRZKO`,`OPDRNRI`),
  KEY `UK_q0finurhrlvj6d3r8cqn45dyw` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `huwbyz` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDNR` int(11) NOT NULL,
  `HDAG` int(11) NOT NULL,
  `HMAAND` int(11) NOT NULL,
  `HJAAR` int(11) NOT NULL,
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
  KEY `UK_pjis9os0582y1x7eh1qr8yvtd` (`IDNR`),
  KEY `UK_fguaqmtk846w5nipc83bl9uum` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `huweer` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDNR` int(11) NOT NULL,
  `HDAG` int(11) NOT NULL,
  `HMAAND` int(11) NOT NULL,
  `HJAAR` int(11) NOT NULL,
  `VLGNREH` int(11) NOT NULL,
  `HUWER` varchar(1) NOT NULL,
  `ANMEH` varchar(50) NOT NULL,
  `TUSEH` varchar(10) NOT NULL,
  `VRN1EH` varchar(20) NOT NULL,
  `VRN2EH` varchar(20) NOT NULL,
  `VRN3EH` varchar(30) NOT NULL,
  `EINDEH` varchar(1) NOT NULL,
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
  UNIQUE KEY `UK_ed9qs6phpm3wy5wr0wj0s4s6w` (`IDNR`,`HDAG`,`HMAAND`,`HJAAR`,`VLGNREH`,`HUWER`,`ONDRZKO`,`OPDRNRI`),
  KEY `UK_cr1rw6qfr261xoh9gx79e4x7j` (`IDNR`,`HDAG`,`HMAAND`,`HJAAR`),
  KEY `UK_jkjfqcplsd736hq6ytov46vym` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `huwgtg` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDNR` int(11) NOT NULL,
  `HDAG` int(11) NOT NULL,
  `HMAAND` int(11) NOT NULL,
  `HJAAR` int(11) NOT NULL,
  `VLGNRGT` int(11) NOT NULL,
  `ANMGT` varchar(50) NOT NULL,
  `TUSGT` varchar(10) NOT NULL,
  `VRN1GT` varchar(20) NOT NULL,
  `VRN2GT` varchar(20) NOT NULL,
  `VRN3GT` varchar(30) NOT NULL,
  `LFTJGT` int(11) NOT NULL,
  `BRPGT` varchar(50) NOT NULL,
  `ADRGT` varchar(50) NOT NULL,
  `HNDGT` varchar(1) NOT NULL,
  `RELWIE` varchar(1) NOT NULL,
  `RELGT` varchar(50) NOT NULL,
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
  UNIQUE KEY `UK_c40hcvqexfr0t4v11u34m05sf` (`IDNR`,`HDAG`,`HMAAND`,`HJAAR`,`VLGNRGT`,`ONDRZKO`,`OPDRNRI`),
  KEY `UK_b2ddqsdldd5rqsg20fkv8ifr0` (`IDNR`,`HDAG`,`HMAAND`,`HJAAR`),
  KEY `UK_bts9crhheyv81ysynayid07sa` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `huwknd` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDNR` int(11) NOT NULL,
  `HGEMNR` int(11) NOT NULL,
  `HAKTENR` int(11) NOT NULL,
  `HPLTS` varchar(50) NOT NULL,
  `HUUR` int(11) NOT NULL,
  `HDAG` int(11) NOT NULL,
  `HMAAND` int(11) NOT NULL,
  `HJAAR` int(11) NOT NULL,
  `SCHEIDNG` varchar(1) NOT NULL,
  `SDAG` int(11) NOT NULL,
  `SMAAND` int(11) NOT NULL,
  `SJAAR` int(11) NOT NULL,
  `SPLTS` varchar(50) NOT NULL,
  `IDAG` int(11) NOT NULL,
  `IMAAND` int(11) NOT NULL,
  `IJAAR` int(11) NOT NULL,
  `IPLTS` varchar(50) NOT NULL,
  `LFTJHM` int(11) NOT NULL,
  `LFTJHV` int(11) NOT NULL,
  `GEBSEX` varchar(1) NOT NULL,
  `ANMHM` varchar(50) NOT NULL,
  `TUSHM` varchar(10) NOT NULL,
  `VRN1HM` varchar(20) NOT NULL,
  `VRN2HM` varchar(20) NOT NULL,
  `VRN3HM` varchar(30) NOT NULL,
  `BRPHM` varchar(50) NOT NULL,
  `GEBPLNHM` int(11) NOT NULL,
  `GEBPLHM` varchar(50) NOT NULL,
  `ADRHM` varchar(50) NOT NULL,
  `OADRHM` varchar(50) NOT NULL,
  `OADREHM` varchar(50) NOT NULL,
  `BSTHM` varchar(1) NOT NULL,
  `HNDHM` varchar(1) NOT NULL,
  `ANMHV` varchar(50) NOT NULL,
  `TUSHV` varchar(10) NOT NULL,
  `VRN1HV` varchar(20) NOT NULL,
  `VRN2HV` varchar(20) NOT NULL,
  `VRN3HV` varchar(30) NOT NULL,
  `BRPHV` varchar(50) NOT NULL,
  `GEBPLNHV` int(11) NOT NULL,
  `GEBPLHV` varchar(50) NOT NULL,
  `ADRHV` varchar(50) NOT NULL,
  `OADRHV` varchar(50) NOT NULL,
  `OADREHV` varchar(50) NOT NULL,
  `BSTHV` varchar(1) NOT NULL,
  `HNDHV` varchar(1) NOT NULL,
  `LEVVRHM` varchar(1) NOT NULL,
  `TOEVRHM` varchar(1) NOT NULL,
  `ANMVRHM` varchar(50) NOT NULL,
  `TUSVRHM` varchar(10) NOT NULL,
  `VRN1VRHM` varchar(20) NOT NULL,
  `VRN2VRHM` varchar(20) NOT NULL,
  `VRN3VRHM` varchar(30) NOT NULL,
  `BRPVRHM` varchar(50) NOT NULL,
  `ADRVRHM` varchar(50) NOT NULL,
  `PLOVVRHM` varchar(50) NOT NULL,
  `HNDVRHM` varchar(1) NOT NULL,
  `LFTJVRHM` int(11) NOT NULL,
  `LEVMRHM` varchar(1) NOT NULL,
  `TOEMRHM` varchar(1) NOT NULL,
  `ANMMRHM` varchar(50) NOT NULL,
  `TUSMRHM` varchar(10) NOT NULL,
  `VRN1MRHM` varchar(20) NOT NULL,
  `VRN2MRHM` varchar(20) NOT NULL,
  `VRN3MRHM` varchar(30) NOT NULL,
  `BRPMRHM` varchar(50) NOT NULL,
  `ADRMRHM` varchar(50) NOT NULL,
  `PLOVMRHM` varchar(50) NOT NULL,
  `HNDMRHM` varchar(1) NOT NULL,
  `LFTJMRHM` int(11) NOT NULL,
  `LEVVRHV` varchar(1) NOT NULL,
  `TOEVRHV` varchar(1) NOT NULL,
  `ANMVRHV` varchar(50) NOT NULL,
  `TUSVRHV` varchar(10) NOT NULL,
  `VRN1VRHV` varchar(20) NOT NULL,
  `VRN2VRHV` varchar(20) NOT NULL,
  `VRN3VRHV` varchar(30) NOT NULL,
  `BRPVRHV` varchar(50) NOT NULL,
  `ADRVRHV` varchar(50) NOT NULL,
  `PLOVVRHV` varchar(50) NOT NULL,
  `HNDVRHV` varchar(1) NOT NULL,
  `LFTJVRHV` int(11) NOT NULL,
  `LEVMRHV` varchar(1) NOT NULL,
  `TOEMRHV` varchar(1) NOT NULL,
  `ANMMRHV` varchar(50) NOT NULL,
  `TUSMRHV` varchar(10) NOT NULL,
  `VRN1MRHV` varchar(20) NOT NULL,
  `VRN2MRHV` varchar(20) NOT NULL,
  `VRN3MRHV` varchar(30) NOT NULL,
  `BRPMRHV` varchar(50) NOT NULL,
  `ADRMRHV` varchar(50) NOT NULL,
  `PLOVMRHV` varchar(50) NOT NULL,
  `HNDMRHV` varchar(1) NOT NULL,
  `LFTJMRHV` int(11) NOT NULL,
  `UGEBHUW` varchar(1) NOT NULL,
  `UOVLOUD` varchar(1) NOT NULL,
  `UOVLECH` varchar(1) NOT NULL,
  `CERTNATM` varchar(1) NOT NULL,
  `TOESTNOT` varchar(1) NOT NULL,
  `AKTEBEK` varchar(1) NOT NULL,
  `ONVERMGN` varchar(1) NOT NULL,
  `COMMAND` varchar(1) NOT NULL,
  `TOESTVGD` varchar(1) NOT NULL,
  `GEGHUW` varchar(1) NOT NULL,
  `GEGVR` varchar(1) NOT NULL,
  `GEGMR` varchar(1) NOT NULL,
  `PROBLM` varchar(1) NOT NULL,
  `NGTG` int(11) NOT NULL,
  `ERKEN` varchar(1) NOT NULL,
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
  UNIQUE KEY `UK_ehudt9cet0e5fwi68vc3s3ipj` (`IDNR`,`HDAG`,`HMAAND`,`HJAAR`,`ONDRZKO`,`OPDRNRI`),
  KEY `UK_8647f3nf773x1o41wvitsskty` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `huwttl` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDNR` int(11) NOT NULL,
  `HGEMNR` int(11) NOT NULL,
  `HAKTENR` int(11) NOT NULL,
  `HPLTS` varchar(50) NOT NULL,
  `HUUR` int(11) NOT NULL,
  `HDAG` int(11) NOT NULL,
  `HMAAND` int(11) NOT NULL,
  `HJAAR` int(11) NOT NULL,
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
  UNIQUE KEY `UK_7i9nhq2xmhqnl3wgrytv6dfbe` (`IDNR`,`HDAG`,`HMAAND`,`HJAAR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `huwvrknd` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDNR` int(11) NOT NULL,
  `HDAG` int(11) NOT NULL,
  `HMAAND` int(11) NOT NULL,
  `HJAAR` int(11) NOT NULL,
  `VLGNRVK` int(11) NOT NULL,
  `ANMVK` varchar(50) NOT NULL,
  `TUSVK` varchar(10) NOT NULL,
  `VRN1VK` varchar(20) NOT NULL,
  `VRN2VK` varchar(20) NOT NULL,
  `VRN3VK` varchar(30) NOT NULL,
  `GBDGVK` int(11) NOT NULL,
  `GBMDVK` int(11) NOT NULL,
  `GBJRVK` int(11) NOT NULL,
  `GESLVK` varchar(1) NOT NULL,
  `GBPLVK` varchar(50) NOT NULL,
  `ERVK` varchar(1) NOT NULL,
  `ERVKWIE` varchar(1) NOT NULL,
  `MEKDGVK` int(11) NOT NULL,
  `MEKMDVK` int(11) NOT NULL,
  `MEKJRVK` int(11) NOT NULL,
  `MEKPLVK` varchar(50) NOT NULL,
  `VEKDGVK` int(11) NOT NULL,
  `VEKMDVK` int(11) NOT NULL,
  `VEKJRVK` int(11) NOT NULL,
  `VEKPLVK` varchar(50) NOT NULL,
  `OPDRNR` varchar(3) NOT NULL,
  `DATUM` varchar(10) NOT NULL,
  `INIT` varchar(3) NOT NULL,
  `VERSIE` varchar(5) NOT NULL,
  `OPDRNRO` varchar(3) NOT NULL,
  `ONDRZKO` varchar(3) NOT NULL,
  `DATUMO` varchar(10) NOT NULL,
  `INITO` varchar(3) NOT NULL,
  `VERSIEO` varchar(5) NOT NULL,
  `OPDRNRI` varchar(3) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_111cqh12gbxv3n42s3i0t2w5x` (`IDNR`,`HDAG`,`HMAAND`,`HJAAR`,`VLGNRVK`,`ONDRZKO`,`OPDRNRI`),
  KEY `UK_fewk6w01wrdu53ar94ib9nfmm` (`IDNR`,`HDAG`,`HMAAND`,`HJAAR`),
  KEY `UK_cq4kxejmbt6qj9kepdydw3gjh` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;