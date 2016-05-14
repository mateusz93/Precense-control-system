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

ALTER TABLE `data`.`Users`
DROP COLUMN `index`,
DROP INDEX `index_UNIQUE`;

ALTER TABLE `data`.`Contacts`
CHANGE COLUMN `PESEL` `group` VARCHAR(11) NULL DEFAULT NULL ,
DROP INDEX `PESEL_UNIQUE`;

ALTER TABLE `data`.`Users`
AUTO_INCREMENT = 100000 ;

ALTER TABLE `data`.`Users`
CHANGE COLUMN `login` `login` VARCHAR(60) NOT NULL ;





ALTER TABLE `data`.`AppLog`
ADD INDEX `fk_AppLog_1_idx` (`eventDictionaryID` ASC);
ALTER TABLE `data`.`AppLog`
ADD CONSTRAINT `fk_AppLog_1`
  FOREIGN KEY (`eventDictionaryID`)
  REFERENCES `data`.`EventDictionary` (`ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_AppLog_2`
  FOREIGN KEY (`teacherID`)
  REFERENCES `data`.`Users` (`ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_AppLog_3`
  FOREIGN KEY (`studentID`)
  REFERENCES `data`.`Users` (`ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `data`.`User`
ADD CONSTRAINT `fk_contact`
  FOREIGN KEY (`contactID`) REFERENCES `data`.`Contact` (`ID`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `data`.`Subject`
ADD CONSTRAINT `fk_department`
  FOREIGN KEY (`departmentID`)
  REFERENCES `data`.`Department` (`ID`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

