CREATE TABLE user(
   id uuid NOT NULL,
   name VARCHAR(255) NOT NULL,
   role VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   PRIMARY KEY(id)
);