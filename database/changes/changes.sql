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

