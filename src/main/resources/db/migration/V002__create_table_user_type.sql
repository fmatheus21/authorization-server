CREATE TABLE user_type (
  id binary(16) NOT NULL DEFAULT (UUID()),
  name varchar(30) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY id_UNIQUE (id),
  UNIQUE KEY name_UNIQUE (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO user_type (id, name) VALUES (UUID_TO_BIN('1f7ad73d-05d5-11ee-900d-7085c2be6d69'), 'CORPORATIVO');
INSERT INTO user_type (id, name) VALUES (UUID_TO_BIN('21f75dd8-05d5-11ee-900d-7085c2be6d69'), 'CLIENTE');