DROP TABLE IF EXISTS tbl_genres CASCADE;
CREATE TABLE tbl_genres (
	id  INTEGER  NOT NULL AUTO_INCREMENT,
	genre_name   VARCHAR(20),
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS tbl_people CASCADE;
CREATE TABLE tbl_people (
	id   INTEGER  NOT NULL AUTO_INCREMENT,
	name         VARCHAR(61) CHARACTER SET utf8,
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS tbl_booktitle CASCADE;
CREATE TABLE tbl_booktitle  (
	id  INTEGER   NOT NULL AUTO_INCREMENT,
	title        VARCHAR(300) CHARACTER SET utf8,
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS tbl_publisher CASCADE;
CREATE TABLE tbl_publisher (
	id   INTEGER    NOT NULL AUTO_INCREMENT,
	publisher_name varchar(300),
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS tbl_dblp_document CASCADE;
CREATE TABLE tbl_dblp_document (
	id   INTEGER  NOT NULL AUTO_INCREMENT,
	title         VARCHAR(380) CHARACTER SET utf8,
	start_page    INTEGER,
	end_page      INTEGER,
	year          INTEGER,
	volume        INTEGER,
	number        INTEGER,
	url           VARCHAR(200),
	ee            VARCHAR(100),
	cdrom         VARCHAR(75),
	cite          VARCHAR(75),
	crossref      VARCHAR(75),
	isbn          VARCHAR(21),
	series        VARCHAR(100), 
	editor_id     INTEGER REFERENCES tbl_people(id),
	booktitle_id  INTEGER REFERENCES tbl_booktitle(id),
	genre_id      INTEGER REFERENCES tbl_genres(id),
	publisher_id  INTEGER REFERENCES tbl_publisher(id),
	PRIMARY KEY (id)
);


DROP TABLE IF EXISTS tbl_author_document_mapping CASCADE;
CREATE TABLE tbl_author_document_mapping (
    id   INTEGER   NOT NULL AUTO_INCREMENT,
    doc_id        INTEGER REFERENCES tbl_dblp_document(id),
    author_id     INTEGER REFERENCES tbl_people(id) ,
	PRIMARY KEY (id)
);
