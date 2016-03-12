CREATE TABLE `adrestp` (
  `RecordID` int(11) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(2) NOT NULL,
  `TYPE` varchar(40) NOT NULL,
  `ONDRZKO` varchar(3) NOT NULL,
  `OPDRNRI` varchar(3) NOT NULL,
  PRIMARY KEY (`RecordID`),
  KEY `UK_m1g766jc72xqy9bgdv9athpxg` (`CODE`),
  KEY `UK_ewfps6k0bdohyrgkdgifb7d6i` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `beroep` (
  `RecordID` int(11) NOT NULL AUTO_INCREMENT,
  `BERPNAAM` varchar(50) NOT NULL,
  `NWINLST` varchar(1) NOT NULL,
  `ONDRZKO` varchar(3) NOT NULL,
  `OPDRNRI` varchar(3) NOT NULL,
  PRIMARY KEY (`RecordID`),
  KEY `UK_4t58wpcf0msw39dc1dyv19cxh` (`BERPNAAM`),
  KEY `UK_tahjyd9clsppqws0ytcl0tkoq` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `kg` (
  `RecordID` int(11) NOT NULL AUTO_INCREMENT,
  `KODE` varchar(2) NOT NULL,
  `KERKGENO` varchar(50) NOT NULL,
  `AFGEKORT` varchar(20) NOT NULL,
  `OMSCHRIJ` varchar(100) NOT NULL,
  `NWINLST` varchar(1) NOT NULL,
  `ONDRZKO` varchar(3) NOT NULL,
  `OPDRNRI` varchar(3) NOT NULL,
  PRIMARY KEY (`RecordID`),
  KEY `UK_6w9n2g0mps5jpiue431do6ti2` (`KERKGENO`),
  KEY `UK_mqx8urvj86ssulm5i5nud0rh1` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `plaats` (
  `RecordID` int(11) NOT NULL AUTO_INCREMENT,
  `GEMNR` int(11) NOT NULL,
  `PROVNR` int(11) NOT NULL,
  `REGNR` int(11) NOT NULL,
  `GEMNAAM` varchar(50) NOT NULL,
  `NWINLST` varchar(1) NOT NULL,
  `REGIO` varchar(20) NOT NULL,
  `WAGENINGEN` int(11) NOT NULL,
  `EUCLIDMIDX` int(11) NOT NULL,
  `EUCLIDMIDY` int(11) NOT NULL,
  `ONDRZKO` varchar(3) NOT NULL,
  `OPDRNRI` varchar(3) NOT NULL,
  PRIMARY KEY (`RecordID`),
  KEY `UK_fvlrn1wcsdkdv6r5jt4h330cd` (`GEMNR`),
  KEY `UK_oy3rtmpqquf5ae5ujxm0lwuew` (`GEMNAAM`),
  KEY `UK_gqfgsox4i79hjaege4ehg5b1` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `relatie` (
  `RecordID` int(11) NOT NULL AUTO_INCREMENT,
  `RELKODE` int(11) NOT NULL,
  `RELATIE` varchar(50) NOT NULL,
  `NWINLST` varchar(1) NOT NULL,
  `ONDRZKO` varchar(3) NOT NULL,
  `OPDRNRI` varchar(3) NOT NULL,
  PRIMARY KEY (`RecordID`),
  KEY `UK_r4aoi8w5oapvucu4nmmh6uvs8` (`RELKODE`),
  KEY `UK_lqixv4lpo2wdm0x481v3r5kh2` (`RELATIE`),
  KEY `UK_16d3juhgmf5tkd7sva7f3opsp` (`ONDRZKO`,`OPDRNRI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
