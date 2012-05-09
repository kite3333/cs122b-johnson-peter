create table stars(id integer primary key NOT NULL AUTO_INCREMENT, 
					first_name varchar(50) NOT NULL, 
					last_name varchar(50) NOT NULL, 
					dob date, 
					photo_url varchar(200));

create table movies (id integer primary key NOT NULL AUTO_INCREMENT, 
				title varchar(100) NOT NULL, 
				year integer NOT NULL, 
				director varchar(100) NOT NULL, 
				banner_url varchar(200), 
				trailer_url varchar(200));

create table stars_in_movies (star_id integer NOT NULL, 
				FOREIGN KEY (star_id) REFERENCES stars(id), 
				movie_id integer NOT NULL, 
				FOREIGN KEY (movie_id) REFERENCES movies(id));

create table genres (id integer primary key NOT NULL AUTO_INCREMENT, 
				name varchar(32) NOT NULL);

create table genres_in_movies (genre_id integer NOT NULL, 
				FOREIGN KEY (genre_id) REFERENCES genres(id), 
				movie_id integer NOT NULL, 
				FOREIGN KEY (movie_id) REFERENCES movies(id));

create table creditcards (id varchar(20) primary key NOT NULL, 
				first_name varchar(50) NOT NULL, 
				last_name varchar(50) NOT NULL, 
				expiration date NOT NULL);
				
create table customers (id integer primary key NOT NULL AUTO_INCREMENT, 
				first_name varchar(50) NOT NULL, 
				last_name varchar(50) NOT NULL, 
				cc_id varchar(20), 
				FOREIGN KEY (cc_id) REFERENCES creditcards(id), 
				address varchar(200) NOT NULL, 
				email varchar(50) NOT NULL, 
				password varchar(20) NOT NULL);

create table sales (id integer primary key NOT NULL AUTO_INCREMENT, 
				customer_id integer NOT NULL, 
				FOREIGN KEY (customer_id) REFERENCES customers(id), 
				movie_id integer NOT NULL, 
				FOREIGN KEY (movie_id) REFERENCES movies(id), 
				sale_date date NOT NULL);

