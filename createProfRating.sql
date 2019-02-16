CREATE TABLE profRatings (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    tid INT NOT NULL,
    tFname VARCHAR(20) DEFAULT '',
    tLname VARCHAR(20) DEFAULT '',
    tNumRating INT NOT NULL,
    overall_Rate FLOAT(8),
    take_again INT,
    difficulty FLOAT(8)
);


# INSERT INTO movies VALUES('tt0455805','Then She Found Me',2007,'Helen Hunt');

# INSERT INTO stars (id, name, birthYear) VALUES('nm2053036','Alistair MacLeod',1936);