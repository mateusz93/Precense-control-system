ALTER TABLE `data`.`Courses` 
ADD COLUMN `teacherID` INT(10) NULL AFTER `subjectID`,
ADD COLUMN `description` VARCHAR(500) NULL AFTER `minPresence`, RENAME TO  `data`.`TeacherCourses`;

DROP TABLE `data`.`Users_Subjects`;
DROP TABLE `data`.`Users_Courses`;

TRUNCATE `data`.`Presence_CourseDates`;
DROP TABLE `data`.`Presence_CourseDates`;

TRUNCATE `data`.`Presence`;
DROP TABLE `data`.`Presence`;

TRUNCATE `data`.`Presence_Users`;
DROP TABLE `data`.`Presence_Users`;

CREATE TABLE `data`.`StudentPrecenses` 
(
  `ID` INT UNSIGNED NOT NULL,
  `studentID` INT UNSIGNED NOT NULL,
  `courseDateID` INT UNSIGNED NOT NULL,
  `status` VARCHAR(20) NULL,
PRIMARY KEY (`ID`),
FOREIGN KEY (`studentID`) REFERENCES Users(`ID`),
FOREIGN KEY (`courseDateID`) REFERENCES CourseDates(`ID`)
);

ALTER TABLE `data`.`CourseDates` 
DROP FOREIGN KEY `CourseDates_ibfk_1`;

ALTER TABLE `data`.`CourseDates` 
ADD CONSTRAINT `fk_CourseDates_1`
  FOREIGN KEY (`courseID`) REFERENCES `data`.`TeacherCourses` (`ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `data`.`StudentPrecenses` 
CHANGE COLUMN `ID` `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ;


