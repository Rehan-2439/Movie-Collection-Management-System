# Movie Collection Management System

## Project Description

The Movie Collection Management System is a Java application used to manage a collection of movies. The project uses MySQL as the database and JDBC to connect the Java application with the database.

## Features

- Add Movie
- View Movies
- Edit Movie
- Update Movie
- Delete Movie
- Exit Application

## Technologies Used

- Java
- Eclipse IDE
- MySQL
- MySQL Workbench
- JDBC
- GitHub

## Database

Database Name:

movie_collection

Table Name:

movies

The movies table contains the following details:

- Movie ID
- Title
- Genre
- Director
- Release Year
- Language
- Rating

## Database Setup

Run the `movie_collection.sql` file in MySQL Workbench to create the database and movies table.

## JDBC Setup

Add the MySQL Connector/J `.jar` file to the Java project's build path before running the application.

## How to Run

1. Open the project in Eclipse.
2. Create the MySQL database using the SQL file.
3. Add the MySQL Connector/J JDBC driver to the project.
4. Enter your MySQL username and password in the Java program.
5. Run `MovieCollectionManagement.java`.

## Functionalities

The application provides the following options:

1. Add Movie
2. View Movies
3. Edit Movie
4. Update Movie
5. Delete Movie
6. Exit