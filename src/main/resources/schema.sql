DROP TABLE IF EXISTS tasks;

CREATE TABLE tasks (
    `id` IDENTITY,
    `title` VARCHAR(100),
    `description` VARCHAR(1024),
    `author` VARCHAR(100),
    `created_at` TIMESTAMP
);

CREATE TABLE attachments (
    `file_name` VARCHAR(100) UNIQUE,
    `task` NUMERIC,
    FOREIGN KEY (`task`) REFERENCES tasks (`id`)
);