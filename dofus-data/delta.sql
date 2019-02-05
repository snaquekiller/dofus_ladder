CREATE TABLE LN.dofus_player
(
  id            BIGINT(20) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(150),
    classe VARCHAR(150),
    serveur VARCHAR(150),
    niveau INT,
    xp BIGINT,
    creation_date DATETIME,
    number INT
);


CREATE TABLE LN.dofus_xp
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