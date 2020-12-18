DROP TABLE IF EXISTS `transaction`;
DROP TABLE IF EXISTS `compte`;


CREATE TABLE `compte` (
  `id_compte` bigint NOT NULL,
  `solde` double DEFAULT NULL,
  PRIMARY KEY (`id_compte`)
) ENGINE=InnoDB;

CREATE TABLE `transaction` (
  `id_transaction` bigint NOT NULL,
  `date_debit` datetime(6) DEFAULT NULL,
  `date_transaction` datetime(6) DEFAULT NULL,
  `montant` double DEFAULT NULL,
  `id_compte` bigint DEFAULT NULL,
  PRIMARY KEY (`id_transaction`),
  KEY `FK41hs9ufmm78trxw62c7s0q9xw` (`id_compte`),
  CONSTRAINT `FK41hs9ufmm78trxw62c7s0q9xw` FOREIGN KEY (`id_compte`) REFERENCES `compte` (`id_compte`)
) ENGINE=InnoDB;