CREATE TABLE teeny_url (
  id SERIAL,
  teenyKey varchar(2083) DEFAULT NULL,
  url varchar(2083) NOT NULL,
  created_on bigint NOT NULL,
  PRIMARY KEY (id)
)