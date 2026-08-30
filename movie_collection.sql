CREATE DATABASE movie_collection;

USE movie_collection;

CREATE TABLE movies (
    movie_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100),
    genre VARCHAR(50),
    director VARCHAR(100),
    release_year INT,
    language VARCHAR(50),
    rating DOUBLE
);