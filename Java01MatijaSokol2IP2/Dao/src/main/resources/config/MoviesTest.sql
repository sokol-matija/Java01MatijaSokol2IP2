--CREATE DATABASE IF NOT EXISTS MOVIETEST04;
--create database MOVIETEST05;
--GO

-- TODO: Procedure is it redundant to remove every time

USE MOVIETEST05;
GO

--use master

--Drop database  MOVIETEST05


-- Drop and create roles table if not exists
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'roles')
BEGIN
    CREATE TABLE roles (
        IDRoles INT PRIMARY KEY IDENTITY,
        name NVARCHAR(50) UNIQUE NOT NULL
    );
END
GO

-- Drop and create users table if not exists
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'users')
BEGIN
    CREATE TABLE users (
        IDUSER INT PRIMARY KEY IDENTITY,
        username NVARCHAR(255) UNIQUE NOT NULL,
        password NVARCHAR(255) NOT NULL,
        role_id INT,
        FOREIGN KEY (role_id) REFERENCES roles(IDRoles)
    );
END
GO

-- Insert roles if they do not exist
INSERT INTO roles (name)
SELECT 'USER' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'USER');
INSERT INTO roles (name)
SELECT 'ADMIN' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ADMIN');
GO

-- Insert admin user if it does not exist
INSERT INTO users (username, password, role_id)
SELECT 'admin', 'admin', (SELECT IDRoles FROM roles WHERE name = 'ADMIN')
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');
GO

-- Insert regular user if it does not exist
INSERT INTO users (username, password, role_id)
SELECT 'user', 'user', (SELECT IDRoles FROM roles WHERE name = 'USER')
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'user');
GO

-- Drop and create Movies table if not exists
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Movies')
BEGIN
    CREATE TABLE dbo.Movies (
        IDMovie INT PRIMARY KEY IDENTITY,
        Title NVARCHAR(300),
        PublishedDate NVARCHAR(300),
        Description NVARCHAR(MAX),
        OriginalTitle NVARCHAR(300),
        Director NVARCHAR(300),
        Actors NVARCHAR(MAX),
        Duration NVARCHAR(300),
        Year NVARCHAR(300),
        Genres NVARCHAR(MAX),
        ImageLink NVARCHAR(300),
        Rating NVARCHAR(300),
        Type NVARCHAR(300),
        PicturePath NVARCHAR(300)
    );
END
GO

-- Drop and create createMovie procedure if not exists
IF EXISTS (SELECT * FROM sys.procedures WHERE name = 'createMovie')
BEGIN
    DROP PROCEDURE dbo.createMovie;
END
GO

CREATE PROCEDURE dbo.createMovie
    @Title NVARCHAR(300),
    @PublishedDate NVARCHAR(300),
    @Description NVARCHAR(MAX),
    @OriginalTitle NVARCHAR(300),
    @Director NVARCHAR(300),
    @Actors NVARCHAR(MAX),
    @Duration NVARCHAR(300),
    @Year NVARCHAR(300),
    @Genres NVARCHAR(MAX), 
    @ImageLink NVARCHAR(300),
    @Rating NVARCHAR(300),
    @Type NVARCHAR(300),
    @PicturePath NVARCHAR(300),
    @IDMovie INT OUTPUT
AS
BEGIN
    INSERT INTO Movies (Title, PublishedDate, Description, OriginalTitle, Director, Actors, Duration, Year, Genres, ImageLink, Rating, Type, PicturePath)
    VALUES (@Title, @PublishedDate, @Description, @OriginalTitle, @Director, @Actors, @Duration, @Year, @Genres, @ImageLink, @Rating, @Type, @PicturePath);
    SET @IDMovie = SCOPE_IDENTITY();
END;
GO

-- Drop and create selectMovie procedure if not exists
IF EXISTS (SELECT * FROM sys.procedures WHERE name = 'selectMovie')
BEGIN
    DROP PROCEDURE dbo.selectMovie;
END
GO

CREATE PROCEDURE dbo.selectMovie
    @IDMovie INT
AS 
BEGIN 
    SELECT * 
    FROM Movies
    WHERE IDMovie = @IDMovie;
END;
GO

-- Drop and create selectMovies procedure if not exists
IF EXISTS (SELECT * FROM sys.procedures WHERE name = 'selectMovies')
BEGIN
    DROP PROCEDURE dbo.selectMovies;
END
GO

CREATE PROCEDURE dbo.selectMovies
AS 
BEGIN 
    SELECT * 
    FROM Movies;
END;
GO

--Select all Movies
--SELECT * FROM MOVIES

--delete from movies
