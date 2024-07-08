create database MOVIETEST04
go

USE MOVIETEST04; -- Use the database where you want to create the schema
go

-- Drop the Movies table if it exists
IF OBJECT_ID('dbo.Movies', 'U') IS NOT NULL
    DROP TABLE dbo.Movies;
GO

-- Drop the createMovie stored procedure if it exists
IF OBJECT_ID('dbo.createMovie', 'P') IS NOT NULL
    DROP PROCEDURE dbo.createMovie;
GO

-- Drop the selectMovie stored procedure if it exists
IF OBJECT_ID('dbo.selectMovie', 'P') IS NOT NULL
    DROP PROCEDURE dbo.selectMovie;
GO

-- Drop the selectMovies stored procedure if it exists
IF OBJECT_ID('dbo.selectMovies', 'P') IS NOT NULL
    DROP PROCEDURE dbo.selectMovies;
GO

--User and Role Table
CREATE TABLE roles (
    IDRoles INT PRIMARY KEY IDENTITY,
    Name NVARCHAR(50) UNIQUE NOT NULL
);
go

CREATE TABLE users (
    IDUser INT PRIMARY KEY IDENTITY,
    Username NVARCHAR(255) UNIQUE NOT NULL,
    Password NVARCHAR(255) NOT NULL,
    Role_id INT,
    FOREIGN KEY (Role_id) REFERENCES roles(IDRoles)
);
go


-- Insert roles
INSERT INTO roles (name) VALUES ('USER'), ('ADMIN');
go
-- Insert an admin user
INSERT INTO users (username, password, role_id) VALUES ('admin', 'admin', (SELECT IDRoles FROM roles WHERE name='ADMIN'));
go
-- Insert a regular user
INSERT INTO users (username, password, role_id) VALUES ('user', 'user', (SELECT IDRoles FROM roles WHERE name='USER'));
go
 -- Show users
 SELECT * FROM users
 go

 
 -- Testing prepared java statement
 select u.Username, u.Password, roles.Name 
 from users as u
 join roles on u.Role_id = roles.IDRoles
 where u.Username = 'user' and u.Password = 'user'
 go

-- Create the Movies table
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
GO

-- Create the createMovie stored procedure
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

-- Create the selectMovie stored procedure
CREATE PROCEDURE dbo.selectMovie
    @IDMovie INT
AS 
BEGIN 
    SELECT * 
    FROM Movies
    WHERE IDMovie = @IDMovie;
END;
GO

-- Create the selectMovies stored procedure
CREATE PROCEDURE dbo.selectMovies
AS 
BEGIN 
    SELECT * 
    FROM Movies;
END;
GO


--Select all Movies
SELECT * FROM MOVIES

delete from movies

