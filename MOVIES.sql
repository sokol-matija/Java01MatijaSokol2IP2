CREATE DATABASE MOVIES
GO
USE MOVIES
GO

use MOVIESTEST 


--Directors
CREATE TABLE Directors (
    ID INT PRIMARY KEY,
    FirstName NVARCHAR(255),
    LastName NVARCHAR(255)
);
GO

--Actors
CREATE TABLE Actors(
    ID INT PRIMARY KEY,
    FirstName NVARCHAR(255),
    LastName NVARCHAR(255)
);
GO

--Genres
CREATE TABLE Genres (
    ID INT PRIMARY KEY,
    Type NVARCHAR(255)
);


--Moviese
CREATE TABLE Movies (
    ID INT PRIMARY KEY,
    Title NVARCHAR(255),
    PublishedDate DATETIME,
    Description NVARCHAR(MAX),
    OriginalTitle NVARCHAR(255),
    DirectorID INT FOREIGN KEY REFERENCES Directors(ID),
    Duration INT,
    Link NVARCHAR(MAX),
    Year INT,
    PicturePath NVARCHAR(MAX),
    Rating INT,
    Type NVARCHAR(50),
    CONSTRAINT FK_Director FOREIGN KEY (DirectorID) REFERENCES Directors(ID)
);
GO

--MoviGenres
CREATE TABLE MovieGenres (
    MovieID INT FOREIGN KEY REFERENCES Movies(ID),
    GenreID INT FOREIGN KEY REFERENCES Genres(ID),
    PRIMARY KEY (MovieID, GenreID)
);
GO

--MovieActors
CREATE TABLE MovieActors (
    MovieID INT FOREIGN KEY REFERENCES Movies(ID),
    ActorID INT FOREIGN KEY REFERENCES Actors(ID),
    PRIMARY KEY (MovieID, ActorID)
);
GO

--Procedures Movies----------------------------------

--Create Movie
CREATE PROCEDURE CreateMovie
    @Title NVARCHAR(255),
    @PublishedDate DATETIME,
    @Description NVARCHAR(MAX),
    @OriginalTitle NVARCHAR(255),
    @DirectorID INT,
    @Duration INT,
    @Link NVARCHAR(MAX),
    @Year INT,
    @PicturePath NVARCHAR(MAX),
    @Rating INT,
    @Type NVARCHAR(50)
AS
BEGIN
    INSERT INTO Movies (Title, PublishedDate, Description, OriginalTitle, DirectorID, Duration, Link, Year, PicturePath, Rating, Type)
    VALUES (@Title, @PublishedDate, @Description, @OriginalTitle, @DirectorID, @Duration, @Link, @Year, @PicturePath, @Rating, @Type);
END;
GO

--Update Movie
CREATE PROCEDURE UpdateMovie
    @ID INT,
    @Title NVARCHAR(255),
    @PublishedDate DATETIME,
    @Description NVARCHAR(MAX),
    @OriginalTitle NVARCHAR(255),
    @DirectorID INT,
    @Duration INT,
    @Link NVARCHAR(MAX),
    @Year INT,
    @PicturePath NVARCHAR(MAX),
    @Rating INT,
    @Type NVARCHAR(50)
AS
BEGIN
    UPDATE Movies 
    SET Title = @Title, 
        PublishedDate = @PublishedDate,
        Description = @Description,
        OriginalTitle = @OriginalTitle,
        DirectorID = @DirectorID,
        Duration = @Duration,
        Link = @Link,
        Year = @Year,
        PicturePath = @PicturePath,
        Rating = @Rating,
        Type = @Type
    WHERE ID = @ID;
END;
GO

--Delete Movie
CREATE PROCEDURE DeleteMovie
    @ID INT
AS
BEGIN
    DELETE FROM Movies WHERE ID = @ID;
END;
GO

--Selest Movie
CREATE PROCEDURE SelectMovie
    @ID INT
AS
BEGIN
    SELECT * FROM Movies WHERE ID = @ID;
END;
GO

--Select All Movies
CREATE PROCEDURE SelectAllMovies
AS
BEGIN
    SELECT * FROM Movies;
END;
GO

--Autors Procedures -----------------------

--Create Actor 
CREATE PROCEDURE CreateActor
    @FirstName NVARCHAR(255),
    @LastName NVARCHAR(255)
AS
BEGIN
    INSERT INTO Actors (FirstName, LastName)
    VALUES (@FirstName, @LastName);
END;
GO

--Update Actor 
CREATE PROCEDURE UpdateActor
    @ID INT,
    @FirstName NVARCHAR(255),
    @LastName NVARCHAR(255)
AS
BEGIN
    UPDATE Actors 
    SET FirstName = @FirstName, 
        LastName = @LastName
    WHERE ID = @ID;
END;
GO

--Delete Actor
CREATE PROCEDURE DeleteActor
    @ID INT
AS
BEGIN
    DELETE FROM Actors WHERE ID = @ID;
END;
GO


--Select Autor 
CREATE PROCEDURE SelectActor
    @ID INT
AS
BEGIN
    SELECT * FROM Actors WHERE ID = @ID;
END;
GO

--Select all Autors
CREATE PROCEDURE SelectAllActors
AS
BEGIN
    SELECT * FROM Actors;
END;
GO

--Genres Procedures----------------------
--Create Genre
CREATE PROCEDURE CreateGenre
    @Type NVARCHAR(255)
AS
BEGIN
    INSERT INTO Genres (Type)
    VALUES (@Type);
END;
GO

--Update Genre 
CREATE PROCEDURE UpdateGenre
    @ID INT,
    @Type NVARCHAR(255)
AS
BEGIN
    UPDATE Genres 
    SET Type = @Type
    WHERE ID = @ID;
END;
GO
--Delete Genre
CREATE PROCEDURE DeleteGenre
    @ID INT
AS
BEGIN
    DELETE FROM Genres WHERE ID = @ID;
END;
GO



--Select Genre
CREATE PROCEDURE SelectGenre
    @ID INT
AS
BEGIN
    SELECT * FROM Genres WHERE ID = @ID;
END;
GO

--Select all Genres 
CREATE PROCEDURE SelectAllGenres
AS
BEGIN
    SELECT * FROM Genres;
END;
GO
--Director Procedure------------------------
--Create Director
CREATE PROCEDURE CreateDirector
    @FirstName NVARCHAR(255),
    @LastName NVARCHAR(255)
AS
BEGIN
    INSERT INTO Directors (FirstName, LastName)
    VALUES (@FirstName, @LastName);
END;
GO

--Update Director
CREATE PROCEDURE UpdateDirector
    @ID INT,
    @FirstName NVARCHAR(255),
    @LastName NVARCHAR(255)
AS
BEGIN
    UPDATE Directors 
    SET FirstName = @FirstName, 
        LastName = @LastName
    WHERE ID = @ID;
END;
GO

--Delete Director
CREATE PROCEDURE DeleteDirector
    @ID INT
AS
BEGIN
    DELETE FROM Directors WHERE ID = @ID;
END;
GO

--Select Director
CREATE PROCEDURE SelectDirector
    @ID INT
AS
BEGIN
    SELECT * FROM Directors WHERE ID = @ID;
END;
GO

--Select Directors
CREATE PROCEDURE SelectAllDirectors
AS
BEGIN
    SELECT * FROM Directors;
END;
Go
--------------------------------------------
