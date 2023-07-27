CREATE TABLE person (
  id binary(16) NOT NULL DEFAULT (UUID()),
  id_person_type binary(16) NOT NULL,
  name varchar(70) NOT NULL,
  document varchar(20) NOT NULL,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY id_UNIQUE (id),
  UNIQUE KEY document_UNIQUE (document),
  KEY fk_person_type_idx (id_person_type),
  CONSTRAINT fk_person_type FOREIGN KEY (id_person_type) REFERENCES person_type (id) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO person (id, id_person_type, name, document, created_at) VALUES (
UUID_TO_BIN('581c2c14-f5f4-11ed-9216-7085c2be6d69'),
UUID_TO_BIN('0015dc51-05d5-11ee-900d-7085c2be6d69'),
'FERNANDO BRAGA MATHEUS',
'67780886050',
CURRENT_TIMESTAMP
);
