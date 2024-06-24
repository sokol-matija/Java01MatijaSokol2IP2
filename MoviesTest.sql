create database MOVIETEST03
go
use MOVIETEST03
go


CREATE TABLE Movies (
    IDMovie INT PRIMARY KEY IDENTITY,
    Title NVARCHAR(300),
    PublishedDate NVARCHAR(300),
    Description NVARCHAR(MAX),
    OriginalTitle NVARCHAR(300),
    Director NVARCHAR(300),
    Actors NVARCHAR(MAX),
    Duration NVARCHAR(300),
    Year NVARCHAR(300),
    ImageLink NVARCHAR(300),
    Rating NVARCHAR(300),
    Type NVARCHAR(300)
);
go

--select * from movies
--go

--delete from movies
--go

--Create Movie
CREATE PROCEDURE createMovie
    @Title NVARCHAR(300),
    @PublishedDate NVARCHAR(300),
    @Description NVARCHAR(MAX),
    @OriginalTitle NVARCHAR(300),
    @Director NVARCHAR(300),
    @Actors NVARCHAR(MAX),
    @Duration NVARCHAR(300),
    @Year NVARCHAR(300),
    @ImageLink NVARCHAR(300),
    @Rating NVARCHAR(300),
    @Type NVARCHAR(300),
    @IDMovie INT OUTPUT
AS
BEGIN
    INSERT INTO Movies (Title, PublishedDate, Description, OriginalTitle, Director, Actors, Duration, Year, ImageLink, Rating, Type)
    VALUES (@Title, @PublishedDate, @Description, @OriginalTitle, @Director, @Actors, @Duration, @Year, @ImageLink, @Rating, @Type);
    SET @IDMovie = SCOPE_IDENTITY();
END;
GO


/*
IDMovie INT PRIMARY KEY,
    Title NVARCHAR(300),
	Description NVARCHAR(MAX),
	OriginalTitle NVARCHAR(300),
	Duration NVARCHAR(300),
	Year NVARCHAR(300),
	ImageLink NVARCHAR(300),
	Rating NVARCHAR(300),
	Type NVARCHAR(300)
*/


--Selest Movie

CREATE PROCEDURE selectMovie
	@IDMovie INT
AS 
BEGIN 
	SELECT 
		* 
	FROM 
		Movies
	WHERE 
		IDMovie = @IDMovie
END
GO

CREATE PROCEDURE selectMovies
AS 
BEGIN 
	SELECT * FROM Movies
END
GO