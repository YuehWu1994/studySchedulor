DELIMITER $$
CREATE PROCEDURE insertUserInput (IN firstName VARCHAR(20), IN lastName VARCHAR(20), IN profName VARCHAR(40), IN gradeWant INT , IN currentGrade INT, IN finalTime DATETIME, IN profStyle INT, IN topic INT, IN gradeW INT) 
BEGIN

	# check whether we should insert student
	SET @st = (SELECT s.id from students s WHERE s.firstName = firstName and s.lastName = lastName);
	IF (@st IS NULL) THEN
		INSERT INTO students (firstName, lastName) VALUES(firstName,lastName);
		SET @st = (SELECT MAX(id) from students);
	END IF;

	select @st AS '';

	# split professor name
	SET @fN =  SUBSTRING_INDEX(SUBSTRING_INDEX(profName, ' ', 1), ' ', -1);
	SET @lN =  TRIM(SUBSTR(profName, LOCATE(' ', profName)));
	select @fN AS '';
	select @lN AS '';
	SET @pId = (SELECT p.id from profRatings p WHERE p.tFname = @fN and p.tLname = @lN);
	select @pId AS '';

	INSERT INTO student_in_course VALUES(@st, @pId, gradeWant, currentGrade, finalTime, profStyle, topic, gradeW);
END
$$
DELIMITER ;


# call insertUserInput('Yueh','Wu','Christophe Magnan','100','80','1212-12-12T00:12','1','2','3');

UPDATE profRatings p
SET p.tFname = 'Christophe'
WHERE p.id = 261