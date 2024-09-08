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

-- Create User Procedure
--SELECT * From Users

IF EXISTS (SELECT * FROM sys.procedures WHERE name = 'createUser')
BEGIN
    DROP PROCEDURE dbo.createUser;
END
GO

CREATE PROCEDURE dbo.createUser
    @Username NVARCHAR(255),
    @Password NVARCHAR(255),
    @RoleId INT
AS
BEGIN
    IF NOT EXISTS (SELECT 1 FROM users WHERE username = @Username)
    BEGIN
        INSERT INTO users (username, password, role_id)
        VALUES (@Username, @Password, @RoleId);
    END
    ELSE
    BEGIN
        RAISERROR ('Username already exists', 16, 1);
        RETURN;
    END
END;
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

-- Drop and create updateMovie procedure if not exists
IF EXISTS (SELECT * FROM sys.procedures WHERE name = 'updateMovie')
BEGIN
    DROP PROCEDURE dbo.updateMovie;
END
GO

CREATE PROCEDURE dbo.updateMovie
    @IDMovie INT,
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
    @PicturePath NVARCHAR(300)
AS
BEGIN
    UPDATE Movies
    SET 
        Title = @Title,
        PublishedDate = @PublishedDate,
        Description = @Description,
        OriginalTitle = @OriginalTitle,
        Director = @Director,
        Actors = @Actors,
        Duration = @Duration,
        Year = @Year,
        Genres = @Genres,
        ImageLink = @ImageLink,
        Rating = @Rating,
        Type = @Type,
        PicturePath = @PicturePath
    WHERE IDMovie = @IDMovie;
END;
GO

--Drop and Create Delete Movie Procedure if not exitst 
IF EXISTS (SELECT * FROM sys.procedures WHERE name = 'deleteMovie')
BEGIN
    DROP PROCEDURE dbo.deleteMovie;
END
GO

CREATE PROCEDURE dbo.deleteMovie
    @IDMovie INT
AS 
BEGIN 
    DELETE 
    FROM Movies
    WHERE IDMovie = @IDMovie;
END;
GO



--Testing
select users.Username, users.Password, roles.Name
from users
join roles on users.Role_id = roles.IDRoles
where users.Username = 'USER' and users.Password = 'user';

select users.Username, users.Password, roles.Name from users join roles on users.Role_id = roles.IDRoles where users.Username = 'USER' and users.Password = 'user'

-- Drop and create authenticateUser procedure if not exists
IF EXISTS (SELECT * FROM sys.procedures WHERE name = 'authenticateUser')
BEGIN
    DROP PROCEDURE dbo.authenticateUser;
END
GO

-- Autentification 
CREATE PROCEDURE dbo.authenticateUser
    @Username NVARCHAR(300),
    @Password NVARCHAR(300),
    @Role NVARCHAR(300) OUTPUT
AS
BEGIN
    SELECT @Role = roles.Name
    FROM users
    JOIN roles ON users.Role_id = roles.IDRoles
    WHERE users.Username = @Username AND users.Password = @Password;
END;
GO

-- Create FavoriteMovies table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'FavoriteMovies')
BEGIN
    CREATE TABLE FavoriteMovies (
        IDFavorite INT PRIMARY KEY IDENTITY,
        Username NVARCHAR(255),	
        IDMovie INT,
        FOREIGN KEY (Username) REFERENCES users(username),
        FOREIGN KEY (IDMovie) REFERENCES Movies(IDMovie),
        UNIQUE (Username, IDMovie)
    );
END
GO

-- Create SaveFavoriteForUser procedure
IF EXISTS (SELECT * FROM sys.procedures WHERE name = 'SaveFavoriteForUser')
BEGIN
    DROP PROCEDURE dbo.SaveFavoriteForUser;
END
GO

CREATE PROCEDURE dbo.SaveFavoriteForUser
    @Username NVARCHAR(255),
    @IDMovie INT
AS
BEGIN
    IF NOT EXISTS (SELECT 1 FROM FavoriteMovies WHERE Username = @Username AND IDMovie = @IDMovie)
    BEGIN
        INSERT INTO FavoriteMovies (Username, IDMovie)
        VALUES (@Username, @IDMovie);
    END
END;
GO

-- Create RemoveFavoriteForUser procedure
IF EXISTS (SELECT * FROM sys.procedures WHERE name = 'RemoveFavoriteForUser')
BEGIN
    DROP PROCEDURE dbo.RemoveFavoriteForUser;
END
GO

CREATE PROCEDURE dbo.RemoveFavoriteForUser
    @Username NVARCHAR(255),
    @IDMovie INT
AS
BEGIN
    DELETE FROM FavoriteMovies
    WHERE Username = @Username AND IDMovie = @IDMovie;
END;
GO

-- Create GetFavoriteMoviesForUser procedure
IF EXISTS (SELECT * FROM sys.procedures WHERE name = 'GetFavoriteMoviesForUser')
BEGIN
    DROP PROCEDURE dbo.GetFavoriteMoviesForUser;
END
GO

CREATE PROCEDURE dbo.GetFavoriteMoviesForUser
    @Username NVARCHAR(255)
AS
BEGIN
    SELECT m.*
    FROM Movies m
    INNER JOIN FavoriteMovies fm ON m.IDMovie = fm.IDMovie
    WHERE fm.Username = @Username;
END;
GO

-- Create IsMovieInFavorites procedure
IF EXISTS (SELECT * FROM sys.procedures WHERE name = 'IsMovieInFavorites')
BEGIN
    DROP PROCEDURE dbo.IsMovieInFavorites;
END
GO
CREATE PROCEDURE IsMovieInFavorites
    @MovieID INT,
    @IsInFavorites BIT OUTPUT
AS
BEGIN
    IF EXISTS (SELECT 1 FROM FavoriteMovies WHERE IDMovie = @MovieID)
        SET @IsInFavorites = 1
    ELSE
        SET @IsInFavorites = 0
END
GO


-- Create deleteAllMovies procedure
IF EXISTS (SELECT * FROM sys.procedures WHERE name = 'deleteAllMovies')
BEGIN
    DROP PROCEDURE dbo.deleteAllMovies;
END
GO
CREATE PROCEDURE deleteAllMovies
AS
BEGIN
	DELETE FROM FavoriteMovies
    DELETE FROM Movies;
END
GO

--Safe from running - Common queries 

--USE MOVIETEST05;

--SELECT * FROM MOVIES

--SELECT * FROM FavoriteMovies

--delete from movies