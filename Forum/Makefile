enter: 
	mysql
forum:	enter
	CREATE DATABASE forum;
	USE forum;
messages: forum

all:
	mysql
	CREATE TABLE IF NOT EXISTS messages(
	message_id INT AUTO_INCREMENT
	message_text TEXT NOT NULL
	message date TIMESTAMP NOT NULL
	PRIMARY KEY(message_id)	

all: forum

clean:
	drop database forum
