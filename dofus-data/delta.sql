CREATE TABLE IF NOT EXISTS LN.dofus_player (
  `name` varchar(150) DEFAULT NULL,
  `classe` varchar(150) DEFAULT NULL,
  `serveur` varchar(150) DEFAULT NULL,
  `niveau` varchar(11) DEFAULT NULL,
  `xp` bigint(20) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
);


CREATE TABLE LN.dofus_succes
(
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(150) DEFAULT NULL,
  `classe` varchar(150) DEFAULT NULL,
  `serveur` varchar(150) DEFAULT NULL,
  `niveau` int(11) DEFAULT NULL,
  `succes` bigint(20) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS LN.user
(
  id            BIGINT(20) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  email         VARCHAR(200)                        NOT NULL UNIQUE,
  pseudo        VARCHAR(100),
  nom           VARCHAR(1000),
  prenom        VARCHAR(1000),
  password      VARCHAR(1000),
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  update_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  deleted       TINYINT(1)                      DEFAULT 0
);


create index if NOT EXISTS dofus_player_name_index on dofus_player(name);
create index if NOT EXISTS dofus_player_date_index on dofus_player(creation_date);


INSERT INTO LN.user (email, pseudo, nom, prenom, creation_date, update_date, deleted)
VALUES ('nic.guitton@gmail.com', 'snaquekiller', 'guitton', 'nicolas
', '2018-02-07 17:14:44', '2018-02-07 17:14:44', 0);
INSERT INTO LN.user (email, pseudo, nom, prenom, creation_date, update_date, deleted)
VALUES ('nicolas.guitton@kindle.com', 'snaquekiller', 'guitton', 'nicolas
', '2018-02-07 17:14:44', '2018-02-07 17:14:44', 0);

ALTER TABLE LN.user add role VARCHAR(100);