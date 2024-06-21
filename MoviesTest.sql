CREATE TABLE Movies (
    IDMovie INT PRIMARY KEY IDENTITY,
    Title NVARCHAR(300),
	Description NVARCHAR(MAX),
	OriginalTitle NVARCHAR(300),
	Duration NVARCHAR(300),
	Year NVARCHAR(300),
	ImageLink NVARCHAR(300),
	Rating NVARCHAR(300),
	Type NVARCHAR(300)
);
GO
use MOVIESTEST
go

select * from movies
go

drop table Movies
go


INSERT INTO Movies (IDMovie, Title, Description, OriginalTitle, Duration, Year, ImageLink, Rating, Type)
VALUES (1, 'Family Guy', 'Once a good tv show', 'Family Guy', 22, 1999, 'no link', 4, 'Normal');

INSERT INTO Movies VALUES(@Title,@Description,@OriginalTitle, @Duration, @Year, @ImageLink, @Rating, @Type)
	SET @IDMovie = SCOPE_IDENTITY();
	go

--Create Movie
CREATE PROCEDURE createMovie
    @Title NVARCHAR(300),
	@Description NVARCHAR(MAX),
	@OriginalTitle NVARCHAR(300),
	@Duration NVARCHAR(300),
	@Year NVARCHAR(300),
	@ImageLink NVARCHAR(300),
	@Rating NVARCHAR(300),
	@Type NVARCHAR(300),
    @IDMovie INT OUTPUT
AS
BEGIN
    INSERT INTO Movies VALUES(@Title,@Description,@OriginalTitle,@Duration,@Year,@ImageLink,@Rating,@Type)
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