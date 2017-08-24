CREATE TABLE topic(
   id uuid NOT NULL,
   created_date TIMESTAMP NOT NULL,
   latest_message_date TIMESTAMP,
   name VARCHAR(255) NOT NULL,
   PRIMARY KEY(id)
);
