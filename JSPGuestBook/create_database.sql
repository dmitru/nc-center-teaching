CREATE DATABASE guestbook;
USE guestbook;

CREATE TABLE messages 
(id INT PRIMARY KEY AUTO_INCREMENT,
 author TEXT,
 content TEXT,
 timelog TIMESTAMP);
 