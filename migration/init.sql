-- Create the database if it does not exist
CREATE DATABASE IF NOT EXISTS sevenify_soap;

-- Switch to the database
USE sevenify_soap;

-- Table for storing API keys
CREATE TABLE IF NOT EXISTS api_keys (
    `key` VARCHAR(255) PRIMARY KEY,
    client VARCHAR(255) NOT NULL
);

-- Table for storing followings
CREATE TABLE IF NOT EXISTS followings (
    premium_id INT NOT NULL,
    follower_id INT NOT NULL,
    status ENUM('pending', 'accepted') NOT NULL,
    PRIMARY KEY (premium_id, follower_id)
);

-- Table for logging
CREATE TABLE IF NOT EXISTS logging (
    id INT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(255) NOT NULL,
    ip VARCHAR(50) NOT NULL,
    endpoint VARCHAR(255) NOT NULL,
    `timestamp` DATETIME NOT NULL
);

INSERT INTO api_keys VALUES ('postman', 'postman'), ('browser', 'browser'), ('rest', 'rest'), ('php', 'php')