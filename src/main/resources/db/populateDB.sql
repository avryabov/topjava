DELETE
FROM meals;
DELETE
FROM user_roles;
DELETE
FROM users;
ALTER SEQUENCE global_seq
  RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2015-05-1 14:00:00', 'User dinner', 510),
       (100000, '2015-05-1 21:00:00', 'User supper', 1500),
       (100001, '2015-05-1 14:00:00', 'Admin dinner', 510),
       (100001, '2015-05-1 20:20:20', 'Admin supper', 500);
