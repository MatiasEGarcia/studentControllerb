CREATE TABLE IF NOT EXISTS students (
	id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
	lastname VARCHAR(45) NOT NULL,
	email VARCHAR(45) NOT NULL UNIQUE,
    age TINYINT NOT NULL,
    admission_date DATE NOT NULL,
    favorite_language VARCHAR(45),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS teachers (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(45) NOT NULL,
	lastname VARCHAR(45) NOT NULL,
	email VARCHAR(45) NOT NULL UNIQUE,
    age TINYINT NOT NULL,
    qualification VARCHAR(45) NOT NULL,
    nationality VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS courses(
	id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(45) NOT NULL,
    teacher INT NOT NULL,
    shift VARCHAR(45) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY(teacher) REFERENCES teachers(id)
);

CREATE TABLE IF NOT EXISTS qualifications(
	id INT NOT NULL AUTO_INCREMENT,
    qualification TINYINT NOT NULL,
    student INT NOT NULL,
    course INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY(student) REFERENCES students(id),
    FOREIGN KEY(course) REFERENCES courses(id)
);