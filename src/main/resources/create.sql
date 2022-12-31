-- 创建数据库
CREATE DATABASE IF NOT EXISTS file_locker DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 切换数据库
USE file_locker;

-- 创建表
CREATE TABLE IF NOT EXISTS upload_file
(
    file_code   VARCHAR(256) NOT NULL PRIMARY KEY,
    file_name   VARCHAR(256) NOT NULL,
    file_type   VARCHAR(128)  NOT NULL,
    file_size   BIGINT,
    save_time   BIGINT,
    upload_time BIGINT,
    delete_time BIGINT
);

CREATE TABLE IF NOT EXISTS global_info
(
    pickup_code VARCHAR(6)   NOT NULL PRIMARY KEY,
    file_code   VARCHAR(128) NOT NULL
);