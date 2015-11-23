#
# tutaj będziemy umieszczać zapytania SQL tworzące tabele
#
#

USE data;

SET foreign_key_checks = 0;
-- Drop tables
drop table `Users`;
drop table `Contacts`;
drop table `Presence`;
drop table `Presence_Users`;
drop table `CourseDates`;
drop table `Presence_CourseDates`;
drop table `Courses`;
drop table `Users_Courses`;
drop table `Users_Subjects`;
drop table `Departments`;
drop table `Subjects`;
SET foreign_key_checks = 1;


CREATE TABLE `data`.`Users` 
(
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`login` VARCHAR(20) NOT NULL,
`firstName` VARCHAR(25) NOT NULL,
`lastName` VARCHAR(25) NOT NULL,
`index` INT UNSIGNED NOT NULL,
`type` VARCHAR(15) NOT NULL,
`password` VARCHAR(100) NOT NULL,
`lastLogin` DATETIME NULL DEFAULT NULL,
`Status` VARCHAR(10),
PRIMARY KEY (`ID`),
UNIQUE index `ID_UNIQUE` (`ID` ASC),
UNIQUE index `index_UNIQUE` (`index` ASC),
UNIQUE index `login_UNIQUE` (`login` ASC)
-- FOREIGN KEY (`ID`) REFERENCES Presence_Users(`studentID`)
);

CREATE TABLE `data`.`Contacts` 
(
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`userID` INT UNSIGNED NOT NULL,
`Email` VARCHAR(50),
`PESEL` VARCHAR(11),
`phone` VARCHAR(15),
`street` VARCHAR(100),
`city` VARCHAR(30),
PRIMARY KEY (`ID`),
UNIQUE index `ID_UNIQUE` (`ID` ASC),
UNIQUE index `Email_UNIQUE` (`Email` ASC),
UNIQUE index `PESEL_UNIQUE` (`PESEL` ASC),
UNIQUE index `phone_UNIQUE` (`phone` ASC)
-- FOREIGN KEY (`userID`) REFERENCES Users(`ID`)
);

CREATE TABLE `data`.`Presence` 
(
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`Status` VARCHAR(10) NOT NULL,
PRIMARY KEY (`ID`),
UNIQUE index `ID_UNIQUE` (`ID` ASC)
-- FOREIGN KEY (`studentID`) REFERENCES UsersPresence(`studentID`)
);

CREATE TABLE `data`.`Presence_Users` 
(
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`studentID` INT UNSIGNED NOT NULL,
`presenceID` INT UNSIGNED NOT NULL,
PRIMARY KEY (`ID`, `studentID`, `presenceID`),
UNIQUE index `ID_UNIQUE` (`ID` ASC),
FOREIGN KEY (`studentID`) REFERENCES Users(`ID`),
FOREIGN KEY (`presenceID`) REFERENCES Presence(`ID`)
);

CREATE TABLE `data`.`CourseDates` 
(
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`courseID` INT UNSIGNED NOT NULL,
`startTime` TIME,
`finishTime` TIME,
`date` DATE,
PRIMARY KEY (`ID`),
UNIQUE index `ID_UNIQUE` (`ID` ASC),
FOREIGN KEY (`courseID`) REFERENCES Courses(`ID`)
);

CREATE TABLE `data`.`Presence_CourseDates` 
(
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`presenceID` INT UNSIGNED,
`courseDatesID` INT UNSIGNED,
PRIMARY KEY (`ID`),
UNIQUE index `ID_UNIQUE` (`ID` ASC),
FOREIGN KEY (`presenceID`) REFERENCES Presence(`ID`),
FOREIGN KEY (`courseDatesID`) REFERENCES CourseDates(`ID`)
);

CREATE TABLE `data`.`Courses` 
(
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`subjectID` INT UNSIGNED NOT NULL,
`type` VARCHAR(15),
`coursesQuantity` INT UNSIGNED,
`minPresence` INT UNSIGNED,
PRIMARY KEY (`ID`),
UNIQUE index `ID_UNIQUE` (`ID` ASC),
FOREIGN KEY (`subjectID`) REFERENCES Subjects(`ID`)
);

CREATE TABLE `data`.`Users_Courses` 
(
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`userID` INT UNSIGNED,
`courseID` INT UNSIGNED,
PRIMARY KEY (`ID`),
UNIQUE index `ID_UNIQUE` (`ID` ASC),
FOREIGN KEY (`subjectID`) REFERENCES Subjects(`ID`),
FOREIGN KEY (`courseID`) REFERENCES Courses(`ID`)
);

CREATE TABLE `data`.`Users_Subjects` 
(
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`teacherID` INT UNSIGNED,
`courseID` INT UNSIGNED,
PRIMARY KEY (`ID`),
UNIQUE index `ID_UNIQUE` (`ID` ASC),
FOREIGN KEY (`teacherID`) REFERENCES Users(`ID`),
FOREIGN KEY (`courseID`) REFERENCES Courses(`ID`)
);

CREATE TABLE `data`.`Departments` 
(
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`Name` VARCHAR(100) NOT NULL,
`description` VARCHAR(1000),
PRIMARY KEY (`ID`)
);

CREATE TABLE `data`.`Subjects` 
(
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`Name` VARCHAR(45) NOT NULL,
`departmentID` INT UNSIGNED NOT NULL,
`description` VARCHAR(500) NULL,
PRIMARY KEY (`ID`),
FOREIGN KEY (`departmentID`) REFERENCES Departments(`ID`)
);

CREATE TABLE `data`.`StudentCourses` 
(
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `courseID` INT UNSIGNED NULL,
  `studentID` INT UNSIGNED NULL,
  `saveTime` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
  FOREIGN KEY (`courseID`) REFERENCES Courses(`ID`),
  FOREIGN KEY (`studentID`) REFERENCES Users(`ID`)
);


