CREATE TABLE contact (
  id binary(16) NOT NULL DEFAULT (UUID()),
  id_person binary(16)NOT NULL,
  phone varchar(20) NOT NULL,
  email varchar(200) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY id_UNIQUE (id),
  UNIQUE KEY id_person_UNIQUE (id_person),
  UNIQUE KEY phone_UNIQUE (phone),
  UNIQUE KEY email_UNIQUE (email),
  CONSTRAINT fk_person_contact FOREIGN KEY (id_person) REFERENCES person (id) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO contact (id, id_person, phone, email) VALUES (UUID_TO_BIN('8541944c-05d5-11ee-900d-7085c2be6d69'), UUID_TO_BIN('581c2c14-f5f4-11ed-9216-7085c2be6d69'), '21986731552', 'fernando.matheuss@hotmail.com');

