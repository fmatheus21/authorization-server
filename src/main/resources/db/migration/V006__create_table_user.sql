CREATE TABLE user (
  id binary(16) NOT NULL,
  id_person binary(16) NOT NULL,
  username varchar(50) NOT NULL,
  password varchar(200) NOT NULL,
  active tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (id),
  UNIQUE KEY id_UNIQUE (id),
  UNIQUE KEY id_person_UNIQUE (id_person),
  UNIQUE KEY username_UNIQUE (username),
  CONSTRAINT fk_person_user FOREIGN KEY (id_person) REFERENCES person (id) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO user (id, id_person, username, password, active) VALUES (
UUID_TO_BIN('ae46dc08-2c64-11ee-a204-581122c7752d'),
UUID_TO_BIN('581c2c14-f5f4-11ed-9216-7085c2be6d69'),
'67780886050',
'$2a$10$04MQf8gTMmbC1aosZajxeO.r1fCtjLbvpVfbZQJD7XbR0oFQ9X1AC',
1);
