CREATE TABLE `regione` (
  `regione_id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(80) DEFAULT NULL,
  `nose` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`regione_id`),
  KEY `regione_nome` (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;


CREATE TABLE `provincia` (
  `provincia_id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(80) DEFAULT NULL,
  `sigla` varchar(5) DEFAULT NULL,
  `regione_id` int(11) DEFAULT NULL,
  `capoluogo` int(11) DEFAULT NULL,
  PRIMARY KEY (`provincia_id`),
  KEY `provincia_nome` (`nome`),
  KEY `reg` (`regione_id`),
  CONSTRAINT `provincia_ibfk_1` FOREIGN KEY (`regione_id`) REFERENCES `regione` (`regione_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;


CREATE TABLE `comune` (
  `comune_id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(80) DEFAULT NULL,
  `codice_Catastale` varchar(4) DEFAULT NULL,
  `abitanti` int(11) DEFAULT NULL,
  `capoluogo` tinyint(1) DEFAULT NULL,
  `provincia_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`comune_id`),
  KEY `comune_nome` (`nome`),
  KEY `prov` (`provincia_id`),
  CONSTRAINT `comune_ibfk_1` FOREIGN KEY (`provincia_id`) REFERENCES `provincia` (`provincia_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
