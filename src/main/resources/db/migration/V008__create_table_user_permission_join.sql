CREATE TABLE user_permission_join (
  id_user binary(16) NOT NULL,
  id_permission binary(16) NOT NULL,
  PRIMARY KEY (id_user,id_permission)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO user_permission_join (id_user, id_permission) VALUES (UUID_TO_BIN('ae46dc08-2c64-11ee-a204-581122c7752d'), UUID_TO_BIN('5a86bd48-2c66-11ee-a204-581122c7752d'));
