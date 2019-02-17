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


CREATE TABLE students (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    firstName VARCHAR(20) DEFAULT '',
    lastName VARCHAR(20) DEFAULT ''
);


CREATE TABLE student_in_course (
	studentId INT NOT NULL,
    profId INT NOT NULL,
    gradeWant INT NOT NULL,
    currentGrade INT NOT NULL,
    finalTime DATETIME NOT NULL,
    profStyle INT NOT NULL,
    topic INT NOT NULL,
    gradeW INT NOT NULL,
    FOREIGN KEY (studentId) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (profId) REFERENCES profRatings(id) ON DELETE CASCADE
);


# select * from student_in_course sc, students s, profRatings pf  WHERE sc.profId = pf.id and sc.studentId = s.id and s.firstName = 'Yueh' and s.lastName = 'Wu';



# INSERT INTO movies VALUES('tt0455805','Then She Found Me',2007,'Helen Hunt');

# INSERT INTO stars (id, name, birthYear) VALUES('nm2053036','Alistair MacLeod',1936);