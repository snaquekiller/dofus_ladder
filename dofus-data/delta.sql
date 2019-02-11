CREATE TABLE LN.dofus_player
(
  name VARCHAR(150),
  classe VARCHAR(150),
  serveur VARCHAR(150),
  niveau VARCHAR(11),
  xp BIGINT(20),
  creation_date DATETIME,
  number INT(11),
  id BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT
);


CREATE TABLE LN.dofus_succes
(
  id            BIGINT(20) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(150),
    classe VARCHAR(150),
    serveur VARCHAR(150),
    niveau INT,
    succes BIGINT,
    creation_date DATETIME,
    number INT
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

INSERT INTO LN.user (email, pseudo, nom, prenom, creation_date, update_date, deleted)
VALUES ('nic.guitton@gmail.com', 'snaquekiller', 'guitton', 'nicolas
', '2018-02-07 17:14:44', '2018-02-07 17:14:44', 0);
INSERT INTO LN.user (email, pseudo, nom, prenom, creation_date, update_date, deleted)
VALUES ('nicolas.guitton@kindle.com', 'snaquekiller', 'guitton', 'nicolas
', '2018-02-07 17:14:44', '2018-02-07 17:14:44', 0);
