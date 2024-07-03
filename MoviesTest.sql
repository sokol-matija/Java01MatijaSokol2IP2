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


--Select all Movies7
SELECT * FROM MOVIES

delete from movies

