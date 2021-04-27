-- Find the most popular bad movie

	--load data
-- Create a relation with movies.data dataset with schema (userID:int, movieID:int, rating:int, ratingTime:int) and name it ratings
ratings = LOAD '../data/movies.data' AS (userID:int, movieID:int, rating:int, ratingTime:int);
-- Create a relation with movies.item dataset with schema (movieID:int, movieTitle:chararray, releaseDate:chararray, videoRelease:chararray, imdbLink:chararray) and name it movie_data. Use the pipe " | " character with PigStorage to parse the file correctly.
movie_data = LOAD LOAD '../data/movies.item' USING PigStorage('|') AS (movieID:int, movieTitle:chararray, releaseDate:chararray, videoRelease:chararray, imdbLink:chararray);

--Find all the movies with an average rating of less than 2.0
nameLookup = FOREACH movie_data GENERATE movieID, movieTitle;

groupedRatings = GROUP ratings BY movieID;
averageRatings = FOREACH groupedRatings GENERATE group AS movieID,AVG(ratings.rating) AS avgRating, COUNT(ratings.rating) AS numRatings;
badMovies = FILTER averageRatings BY avgRating < 2.0;
DUMP badMovies; 
namedBadMovies = JOIN badMovies BY movieID, nameLookup BY movieID;
DUMP namedBadMovies; 

finalResults = FOREACH namedBadMovies GENERATE nameLookup::movieTitle AS movieName, badMovies::avgRating AS avgRating, badMovies::numRatings AS numRatings;
DUMP finalResults;

finalResultsSorted = ORDER finalResults BY numRatings DESC;
DUMP finalResultsSorted;
 
STORE finalResultsSorted INTO 'PopularBadMovies';

MostPopular = limit finalResultsSorted 1; 
DUMP MostPopular;
