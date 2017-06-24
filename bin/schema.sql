DROP TABLE IF EXISTS people;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS followers;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS user_roles;

-- Feel free to augment or modify these schemas (and the corresponding data) as you see fit!
CREATE TABLE people (
    id IDENTITY,
    handle VARCHAR,
    name VARCHAR
);

CREATE TABLE messages (
    id IDENTITY,
    person_id NUMBER REFERENCES people (id),
    content VARCHAR
);

CREATE TABLE followers (
    id IDENTITY,
    person_id NUMBER REFERENCES people (id),
    follower_person_id NUMBER REFERENCES people (id)
);


CREATE  TABLE user (
  person_id number REFERENCES People(id),
  username VARCHAR NOT NULL ,
  password VARCHAR NOT NULL ,
  enabled INT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (username));
   
CREATE TABLE user_roles (
  id identity,
  username varchar NOT NULL references user(username),
  role varchar NOT NULL);
