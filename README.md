
![](https://github.com/Proud-Sachinda/Segfault/blob/master/Extra%20Resources/images/Logo.PNG) 


[![Build Status](https://travis-ci.org/Proud-Sachinda/Segfault.svg?branch=master)](https://travis-ci.org/Proud-Sachinda/Segfault)


[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com%3Aqbank&metric=coverage)](https://sonarcloud.io/dashboard?id=com%3Aqbank)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=com%3Aqbank&metric=ncloc)](https://sonarcloud.io/dashboard?id=com%3Aqbank)

[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=com%3Aqbank&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=com%3Aqbank)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=com%3Aqbank&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=com%3Aqbank)

[![Releases](https://img.shields.io/badge/Releases-Latest-blue.svg)](https://github.com/Proud-Sachinda/Segfault/releases)

[![contributors](https://img.shields.io/badge/Contributors-5-blueviolet.svg)](https://github.com/Proud-Sachinda/Segfault/graphs/contributors)

qbank
==============

Template for a simple Vaadin application that only requires a Servlet 3.0 container to run.


Workflow
========

To compile the entire project, run "mvn install".

To run the application, run "mvn jetty:run" and open http://localhost:8080/ .

To produce a deployable production mode WAR:
- change productionMode to true in the servlet class configuration (nested in the UI class)
- run "mvn clean package"
- test the war file with "mvn jetty:run-war"

Client-Side compilation
-------------------------

The generated maven project is using an automatically generated widgetset by default. 
When you add a dependency that needs client-side compilation, the maven plugin will 
automatically generate it for you. Your own client-side customizations can be added into
package "client".

Debugging client side code
  - run "mvn vaadin:run-codeserver" on a separate console while the application is running
  - activate Super Dev Mode in the debug window of the application

Developing a theme using the runtime compiler
-------------------------

When developing the theme, Vaadin can be configured to compile the SASS based
theme at runtime in the server. This way you can just modify the scss files in
your IDE and reload the browser to see changes.

To use the runtime compilation, open pom.xml and comment out the compile-theme 
goal from vaadin-maven-plugin configuration. To remove a possibly existing 
pre-compiled theme, run "mvn clean package" once.

When using the runtime compiler, running the application in the "run" mode 
(rather than in "debug" mode) can speed up consecutive theme compilations
significantly.

It is highly recommended to disable runtime compilation for production WAR files.

Using Vaadin pre-releases
-------------------------

If Vaadin pre-releases are not enabled by default, use the Maven parameter
"-P vaadin-prerelease" or change the activation default value of the profile in pom.xml .

Run the following SQL Commands
----------------------------
*Lecturer Table*
```sql
CREATE TABLE lecturer (
  lecturer_id varchar(12) NOT NULL,
  lecturer_fname varchar(50) NOT NULL,
  lecturer_lname varchar(50) NOT NULL,
  PRIMARY KEY (lecturer_id));
```
*Course Table*
```sql
CREATE TABLE course (
   course_id int(11) NOT NULL,
   course_name varchar(50) DEFAULT NULL,
   course_code varchar(12) DEFAULT NULL,
   PRIMARY KEY (course_id));
 ```
*Role Table*
```sql
CREATE TABLE role (
  role_id int(11) NOT NULL,
  role_description varchar(45) DEFAULT NULL,
  PRIMARY KEY (role_id));
```
*Tag Table*
```sql
CREATE TABLE tag(
  tag_id SERIAL PRIMARY KEY,
  tag_name VARCHAR NOT NULL,
  question_id INTEGER,
  FOREIGN KEY (question_id)
    REFERENCES question(question_id));
```

=======================================================================


![](https://github.com/Proud-Sachinda/Segfault/blob/master/Extra%20Resources/images/logo1.PNG)
