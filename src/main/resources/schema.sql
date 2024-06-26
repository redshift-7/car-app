CREATE TABLE `users` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE cars (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     make VARCHAR(255),
     model VARCHAR(255),
     numberplate VARCHAR(255),
     user_id BIGINT,
     FOREIGN KEY (user_id) REFERENCES users (id)
);
