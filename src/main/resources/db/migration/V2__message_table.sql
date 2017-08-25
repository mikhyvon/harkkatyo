CREATE TABLE message(
   id uuid NOT NULL,
   topic_id uuid,
   creator_id uuid NOT NULL,
   created_date TIMESTAMP NOT NULL,
   text VARCHAR(255) NOT NULL,
   PRIMARY KEY(id)
);
