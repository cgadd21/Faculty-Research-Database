DROP DATABASE IF EXISTS project;
CREATE DATABASE project;
use project;

-- ALL USERS ARE IN THIS TABLE
-- THIS TABLE IS USED TO DEFINE WHAT TYPE OF USER THEY ARE IE. STUDENT FACULTY GUEST
-- typeID DEFINES THE TYPE OF USER 
-- typeID IS ONE OF 3 VALUES F - Faculty, G - Guest, S - Student

CREATE TABLE users (
	userID INT NOT NULL auto_increment,
	typeID ENUM ('F','G','S') NOT NULL,
    username VARCHAR(30) UNIQUE,
    password VARCHAR(30),
	constraint user_pk primary key (userID)
) AUTO_INCREMENT = 1; 
-- GUEST INFORMATION START
CREATE TABLE guest(
    guestID INT NOT NULL,
    business VARCHAR(30),
    fname VARCHAR(30),
    lname VARCHAR(30),
    contactinfo VARCHAR(30),
    CONSTRAINT guest_pk PRIMARY KEY (guestID),
    CONSTRAINT guest_id_fk FOREIGN KEY (guestID) REFERENCES users(userID)
    ON DELETE CASCADE
	ON UPDATE CASCADE
);

-- GUEST INFORMATION END

CREATE TABLE student (
	studentID int,
    fname VARCHAR(30),
    lname VARCHAR(30),
    email VARCHAR(30),
    phonenumber VARCHAR(30), 
    CONSTRAINT student_pk PRIMARY KEY (studentID),
    CONSTRAINT student_id_fk FOREIGN KEY (studentID) REFERENCES users(userID)
    ON DELETE CASCADE
	ON UPDATE CASCADE
);

CREATE TABLE interestList (
	interestID INT NOT NULL auto_increment,
    intDesc VARCHAR(30),
    constraint int_pk PRIMARY KEY (interestID)
);
CREATE TABLE studentInterests (
	studentID int,
    interestID int,
    CONSTRAINT studentInterests_pk PRIMARY KEY (studentID, interestID),
    CONSTRAINT studentInterests_id_fk FOREIGN KEY (studentID) REFERENCES student(studentID)
    ON DELETE CASCADE
	ON UPDATE CASCADE,
	CONSTRAINT studentInterests_int_fk FOREIGN KEY (interestID) REFERENCES interestList(interestID)
    ON DELETE CASCADE
	ON UPDATE CASCADE
);

CREATE TABLE faculty (
	facultyID INT NOT NULL,
    fname VARCHAR(30),
    lname VARCHAR(30),
    email VARCHAR(30),
    phonenumber VARCHAR(30),
    location VARCHAR(30),
    CONSTRAINT faculty_pk PRIMARY KEY (facultyID),
    CONSTRAINT faculty_id_fk FOREIGN KEY (facultyID) REFERENCES users(userID)
    ON DELETE CASCADE
	ON UPDATE CASCADE
);

CREATE TABLE facultyInterests (
    facultyID INT,
    interestID INT,
    CONSTRAINT facultyInterests_pk PRIMARY KEY (facultyID, interestID),
    CONSTRAINT facultyInterests_faculty_fk FOREIGN KEY (facultyID) REFERENCES faculty(facultyID)
    ON DELETE CASCADE
	ON UPDATE CASCADE,
    CONSTRAINT facultyInterests_int_fk FOREIGN KEY (interestID) REFERENCES interestList(interestID)
    ON DELETE CASCADE
	ON UPDATE CASCADE
);



CREATE TABLE abstractList (
	abstractID int,
	professorAbstract TEXT,
	CONSTRAINT absractList_pk PRIMARY KEY (abstractID)
);
CREATE TABLE facultyAbstract (
	facultyID INT,
	abstractID INT,
	CONSTRAINT facultyAbstract_faculty_fk FOREIGN KEY (facultyID) REFERENCES faculty(facultyID)
    ON DELETE CASCADE
	ON UPDATE CASCADE,
    CONSTRAINT facultyAbstract_abstract_fk FOREIGN KEY (abstractID) REFERENCES abstractList(abstractID)
    ON DELETE CASCADE
	ON UPDATE CASCADE,
    CONSTRAINT facultyAbstract_pk PRIMARY KEY (facultyID, abstractID)
);

INSERT INTO Users (userID, typeID, username, password) VALUES 
(1,'F','Jimhab','FacultyPass'), 
(2,'S','jmd4173','StudentPass'), 
(3,'G','Wegmans','GuestPass'),
(4,'S','ak7221','StudentPass');

INSERT INTO guest (guestID, business, fname, lname, contactinfo) VALUES
(3, 'Wegmans','Mr','Wegman','wegmanscontact@gmail.com');
INSERT INTO student (studentID, fname, lname, email, phonenumber) VALUES
(2, 'John', 'DAngelo','jmd4173@rit.edu', '203-427-5637'),
(4,'Aathif','Khan','ak7221@rit.edu','585-287-1098');

INSERT INTO interestList  VALUES (1, 'Pascal');
INSERT INTO interestList  VALUES (2, 'Java');
INSERT INTO interestList  VALUES (3, 'JDBC');
INSERT INTO interestList  VALUES (4, 'MySQL');
INSERT INTO interestList  VALUES (5, 'Python');
INSERT INTO interestList  VALUES (6, 'COBOL');
INSERT INTO interestList  VALUES (7, 'C');
INSERT INTO interestList  VALUES (8, 'CyberSecurity');
INSERT INTO interestList  VALUES (9, 'C++');
INSERT INTO interestList  VALUES (10, 'C#');
INSERT INTO interestList  VALUES (11, 'JavaScript');
INSERT INTO interestList  VALUES (12, 'PHP');
INSERT INTO interestList  VALUES (13, 'Ada');
INSERT INTO interestList  VALUES (14, "Ruby/Ruby on Rails");

INSERT INTO studentInterests (studentID, interestID) VALUES
(2,2),
(2,3),
(2,4),
(4,10),
(4,11),
(4,12);

INSERT INTO faculty (facultyID, fname, lname, email, phonenumber, location) VALUES
(1, 'Jim', 'Habermas','email','123-456-7890', 'Golisano');


INSERT INTO abstractList (abstractID, professorAbstract) VALUES
(1,'Im an abstract!');



INSERT INTO facultyAbstract (facultyID, abstractID) VALUES
(1,1);

INSERT INTO facultyInterests (facultyID, interestID) VALUES
(1,1),
(1,2),
(1,3),
(1,4),
(1,5),
(1,6),
(1,7),
(1,8);
