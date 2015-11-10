#
# tutaj będziemy umieszczać zapytania SQL tworzące tabele
#
#

USE data;

SET foreign_key_checks = 0;
-- Drop tables
drop table `Użytkownicy`;
drop table `Kontakty`;
drop table `Obecności`;
drop table `ObecnościUżytkownicy`;
drop table `Terminy_zajęć`;
drop table `ObecnościTerminy_zajęć`;
drop table `Kursy`;
drop table `UżytkownicyKursy`;
drop table `UżytkownicyPrzedmioty`;
drop table `Wydziały`;
drop table `Przedmioty`;
SET foreign_key_checks = 1;


CREATE TABLE `data`.`Użytkownicy` 
(
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`Imię` VARCHAR(25) NOT NULL,
`Nazwisko` VARCHAR(25) NOT NULL,
`Index` INT UNSIGNED NOT NULL,
`Typ` VARCHAR(15) NOT NULL,
`Hasło` VARCHAR(100) NOT NULL,
`Ostatnie_logowanie` DATETIME NULL DEFAULT NULL,
`Status` VARCHAR(10),
PRIMARY KEY (`ID`),
UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
UNIQUE INDEX `Index_UNIQUE` (`Index` ASC)
-- FOREIGN KEY (`ID`) REFERENCES UżytkownicyObecności(`Student_ID`)
);

CREATE TABLE `data`.`Kontakty` 
(
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`Użytkownik_ID` INT UNSIGNED NOT NULL,
`Email` VARCHAR(50),
`PESEL` VARCHAR(11),
`Telefon` VARCHAR(15),
`Ulica` VARCHAR(100),
`Miasto` VARCHAR(30),
PRIMARY KEY (`ID`),
UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
UNIQUE INDEX `Email_UNIQUE` (`Email` ASC),
UNIQUE INDEX `PESEL_UNIQUE` (`PESEL` ASC),
UNIQUE INDEX `Telefon_UNIQUE` (`Telefon` ASC)
-- FOREIGN KEY (`Użytkownik_ID`) REFERENCES Użytkownicy(`ID`)
);

CREATE TABLE `data`.`Obecności` 
(
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`Status` VARCHAR(10) NOT NULL,
PRIMARY KEY (`ID`),
UNIQUE INDEX `ID_UNIQUE` (`ID` ASC)
-- FOREIGN KEY (`Student_ID`) REFERENCES UżytkownicyObecności(`Student_ID`)
);

CREATE TABLE `data`.`ObecnościUżytkownicy` 
(
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`Student_ID` INT UNSIGNED NOT NULL,
`Obecność_ID` INT UNSIGNED NOT NULL,
PRIMARY KEY (`ID`, `Student_ID`, `Obecność_ID`),
UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
FOREIGN KEY (`Student_ID`) REFERENCES Użytkownicy(`ID`),
FOREIGN KEY (`Obecność_ID`) REFERENCES Obecności(`ID`)
);

CREATE TABLE `data`.`Terminy_zajęć` 
(
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`Kurs_ID` INT UNSIGNED NOT NULL,
`Czas_start` TIME,
`Czas_koniec` TIME,
`Data` DATE,
PRIMARY KEY (`ID`),
UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
FOREIGN KEY (`Kurs_ID`) REFERENCES Kursy(`ID`)
);

CREATE TABLE `data`.`ObecnościTerminy_zajęć` 
(
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`Obecność_ID` INT UNSIGNED,
`Termin_zajęć_ID` INT UNSIGNED,
PRIMARY KEY (`ID`),
UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
FOREIGN KEY (`Obecność_ID`) REFERENCES Obecności(`ID`),
FOREIGN KEY (`Termin_zajęć_ID`) REFERENCES Terminy_zajęć(`ID`)
);

CREATE TABLE `data`.`Kursy` 
(
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`Przedmiot_ID` INT UNSIGNED NOT NULL,
`Typ` VARCHAR(15),
`Ilość_zajęć` INT UNSIGNED,
`Obecność_min` INT UNSIGNED,
PRIMARY KEY (`ID`),
UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
FOREIGN KEY (`Przedmiot_ID`) REFERENCES Przedmioty(`ID`)
);

CREATE TABLE `data`.`UżytkownicyKursy` 
(
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`Przedmiot_ID` INT UNSIGNED,
`Kurs_ID` INT UNSIGNED,
PRIMARY KEY (`ID`),
UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
FOREIGN KEY (`Przedmiot_ID`) REFERENCES Przedmioty(`ID`),
FOREIGN KEY (`Kurs_ID`) REFERENCES Kursy(`ID`)
);

CREATE TABLE `data`.`UżytkownicyPrzedmioty` 
(
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`Prowadzący_ID` INT UNSIGNED,
`Kurs_ID` INT UNSIGNED,
PRIMARY KEY (`ID`),
UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
FOREIGN KEY (`Prowadzący_ID`) REFERENCES Użytkownicy(`ID`),
FOREIGN KEY (`Kurs_ID`) REFERENCES Kursy(`ID`)
);

CREATE TABLE `data`.`Wydziały` 
(
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`Nazwa` VARCHAR(100) NOT NULL,
`Opis` VARCHAR(1000),
PRIMARY KEY (`ID`)
);

CREATE TABLE `data`.`Przedmioty` 
(
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`Nazwa` VARCHAR(45) NOT NULL,
`Wydział_ID` INT UNSIGNED NOT NULL,
`Opis` VARCHAR(500) NULL,
PRIMARY KEY (`ID`),
FOREIGN KEY (`Wydział_ID`) REFERENCES Wydziały(`ID`)
);
