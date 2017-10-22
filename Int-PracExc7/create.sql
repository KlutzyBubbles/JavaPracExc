DROP TABLE IF EXISTS tblUsers, tblBank, tblEmployee;

CREATE TABLE IF NOT EXISTS tblUsers (
	userName	VARCHAR(10) NOT NULL PRIMARY KEY,
	firstName	VARCHAR(20)	NOT NULL,
	lastName	VARCHAR(20)	NOT NULL,
	txtPassword	VARCHAR(10)	NOT NULL,
	AccessLevel	INT			NOT NULL,
	Enabled		INT			NOT NULL
) Engine=InnoDB;

INSERT INTO tblUsers VALUES ('admin', 'Manager', 'Admin', 'tafe123', 2, 1);
INSERT INTO tblUsers VALUES ('jimt', 'Jim', 'Tresinits', 'tafe123', 1, 1);
INSERT INTO tblUsers VALUES ('fred', 'Fred', 'Roberts', 'tafe123', 1, 1);
INSERT INTO tblUsers VALUES ('sams', 'Sam', 'Smith', 'tafe123', 1, 1);


CREATE TABLE IF NOT EXISTS tblEmployee (
	employeeID	INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	firstName	VARCHAR(20)	NOT NULL,
	lastName	VARCHAR(20)	NOT NULL
) Engine=InnoDB;

INSERT INTO tblEmployee VALUES (1, 'John', 'Doe');
INSERT INTO tblEmployee VALUES (2, 'Lee', 'Tzilantonis');
INSERT INTO tblEmployee VALUES (3, 'Michael', 'Schwiimmer');
INSERT INTO tblEmployee VALUES (4, 'Jason', 'Joker');
INSERT INTO tblEmployee VALUES (5, 'Some', 'Guy\'g');

CREATE TABLE IF NOT EXISTS tblBank (
	employeeID	INT(11) NOT NULL,
	bank		VARCHAR(30)	NOT NULL,
	bsb			VARCHAR(6)	NOT NULL,
	accountNo	VARCHAR(11)	NOT NULL,
	PRIMARY KEY (employeeID, bsb, accountNo),
	FOREIGN KEY (employeeID) REFERENCES tblEmployee(employeeID)
) Engine=InnoDB;

INSERT INTO tblBank VALUES (1, 'Westpac', '654321', '1234567');
INSERT INTO tblBank VALUES (1, 'ANZ', '123456', '1234567');
INSERT INTO tblBank VALUES (2, 'Commenwealth', '123789', '1234567');
INSERT INTO tblBank VALUES (3, 'Westpac Express', '765432', '1234567');
INSERT INTO tblBank VALUES (5, 'Westpac', '654321', '7654321');