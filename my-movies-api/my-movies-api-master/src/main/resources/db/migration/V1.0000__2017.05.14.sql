-- V1.0000__2017.05.14.sql

CREATE TABLE "user" (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  "name" VARCHAR NOT NULL,
  email VARCHAR NOT NULL,
  "password" TEXT NOT NULL
);

CREATE TABLE "role" (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  "name" VARCHAR NOT NULL
);

CREATE TABLE users_roles (
  user_id BIGINT REFERENCES "user"(id) NOT NULL,
  role_id BIGINT REFERENCES "role"(id) NOT NULL
);

CREATE TABLE movie (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  genre VARCHAR NOT NULL,
  image_url VARCHAR NOT NULL,
  title VARCHAR NOT NULL,
  "year" VARCHAR NOT NULL
);

CREATE TABLE favorite_movie (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  movie BIGINT NOT NULL REFERENCES movie (id),
  "user" BIGINT NOT NULL REFERENCES "role" (id)
);

INSERT INTO "role" VALUES((SELECT nextval('role_id_seq')), 'ROLE_ADMIN');
INSERT INTO "role" VALUES((SELECT nextval('role_id_seq')), 'ROLE_USER');