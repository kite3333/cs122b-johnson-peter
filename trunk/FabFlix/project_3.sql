create table employees(email varchar(50) primary key,
					password varchar(20) NOT NULL,
					fullname varchar(100));

DELIMITER //
CREATE PROCEDURE tester()
	BEGIN
		DECLARE test_id VARCHAR(100) DEFAULT NULL;
		CALL add_movie('Juntao', '2008', 'm shalaman', test_id, 'cool', 'horror-fried');
	END //
DELIMITER ;
					
DELIMITER //
CREATE PROCEDURE add_movie(m_title VARCHAR(100), m_year INTEGER, m_director VARCHAR(100),
							star_fname VARCHAR(50), star_lname VARCHAR(50), genre VARCHAR(32))
main: BEGIN
		DECLARE m_id INT DEFAULT 0; # Movie ID
		DECLARE s_id INT DEFAULT 0; # Star ID
		DECLARE g_id INT DEFAULT 0; # Genre ID
		IF (m_title IS NULL OR m_year IS NULL OR m_director IS NULL OR star_fname IS NULL OR
			star_lname IS NULL OR genre IS NULL) # Are any of the parameters NULL?
		THEN
			SELECT 'One or more of the parameters is a NULL.' AS 'FAIL';
			LEAVE main;
		END IF;
		IF (SELECT COUNT(*) FROM movies WHERE title = m_title AND m_year = year AND 
			director = m_director) # Does a record for the movie exist?
		THEN # Stop, because the movie already exists.
			SELECT 'The movie already exists' as 'FAIL';
			LEAVE main;
		END IF;
		SELECT 'All initial checks passed' AS 'SUCCESS';
		INSERT INTO movies (title, year, director) VALUES(m_title, m_year, m_director);
		SELECT 'The movie record was successfully created' AS 'SUCCESS';
		SELECT id INTO m_id FROM movies WHERE title = m_title AND m_year = year AND 
			director = m_director; # Get id for relational tables.
		IF (SELECT(SELECT COUNT(*) FROM stars WHERE first_name = star_fname AND 
			last_name = star_lname) IS FALSE) # Does a record for the star not exist?
		THEN # Make a new record for the star.
			INSERT INTO stars (first_name, last_name) VALUES(star_fname, star_lname);
			SELECT 'A record for the star was created' AS 'NOTE';
		END IF;
		SELECT id INTO s_id FROM stars WHERE first_name = star_fname AND 
			last_name = star_lname; # Get id for relational tables.
		IF (SELECT(SELECT COUNT(*) FROM genres WHERE name = genre) IS FALSE)
		THEN # Make a new record for the genre.
			INSERT INTO genres (name) VALUES(genre);
			SELECT 'A record for the genre was created' AS 'NOTE';
		END IF;
		SELECT id INTO g_id FROM genres WHERE name = genre;
		INSERT INTO stars_in_movies (star_id, movie_id) VALUES(s_id, m_id);
		INSERT INTO genres_in_movies (genre_id, movie_id) VALUES(g_id, m_id);
		SELECT 'Related star and genre successfully connected to movie' AS 'SUCCESS';
	END //
DELIMITER ;
drop procedure add_movie;