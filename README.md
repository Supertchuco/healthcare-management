# healthCare-managementApplication
	This application is a REST API to take control of a healthcare institution registration as well as the exams ingest.

Functional requirements
    Each new healthcare institution must receive 20 pixeon coins to save exams and retrieve them.
    Every exam successfully created must charge 1 pixeon coin from the healthcare institution's bugdet
    You must charge 1 pixeon coin from the budget when one healthcare institution retrieves an exame but if the institution retrieves the same exame more than once you must not charge it, which means you have to charge only the first access to the exam.
    A healthcare institution must not have access to an exam that belongs to other healthcare institution.
    A healthcare institution is not allowed to create or get an exam when running out of budget.
    We are expecting you to build the solution using Spring Framework and we also do not care about the database or any other tool that you might choose.

## Technologies(Frameworks and Plugins)
This project I have developed using Intellij IDE and these technologies and frameworks:

	-Java 8
    -Springboot,
    -Spring Security
    -Gradle,
    -H2 (memory database),
    -Swagger,
    -Lombok,
    -Actuator,
    -Docker,
	-PMD plugin,
	-Checkstyle,
    -Spring rest.

## About project	
	This project is formed per one SpringBoot Application.
        Notes about application:
            -It is configured to listen 8090 port;
            -It is configured to use Spring Security because that, when the application is started the class DatabaseLoader.java is configured to insert an admin user with e-mail: "test" and password: "test", use these credentials to access Swagger and do requests for this API (Basic Authentication);
            -In this Swagger url: http://localhost:8090/healthcare-management/swagger-ui.html you can check all documentation about rest endpoints;
            -It is configured to use Swagger, using it you can check the endpoints and payloads using an internet browser. To access Swagger interface use this url: http://localhost:8090/swagger-ui.html, just a detail, if you are using Docker in Windowns machine maybe you need to change the localhost to a -specific ip (I need to that because I have used Docker toolbox)
			-Basic the API has these endpoints:
				post - http://localhost:8090/healthcare-management/exam/ - create new exam in database;
                Get - http://localhost:8090/healthcare-management/exam/{examId} - retrieve an exam in database;
				Put - http://localhost:8090/healthcare-management/exam/{examId} - update an exam in database;
				Delete - http://localhost:8090/healthcare-management/exam/{examId} - delete an exam in database;
				post - http://localhost:8090/healthcare-management//healthCareInstitution/ - create new health care institution in database;
				post - http://localhost:8090/healthcare-management/user/ - create new user in database;

			-There are unit tests for service layer,
			-There are integration tests for API that simulate the complete flows:
				These tests are configured to use H2 database, I mean the H2 is created just to use in integration tests, after it the H2 database instance will be destroyed.
			-This project is using PMD (https://maven.apache.org/plugins/maven-pmd-plugin/) and Checkstyle (https://maven.apache.org/plugins/maven-checkstyle-plugin/) plugins to keep a good quality in -the code.
			-During every build process, these process are executed:
				Execute unit tests for service layer
				Execute integration tests
				Execute Checkstyle verification
				Execute PMD verification	
				build jar file

## Run 
To run application without an IDE you need to follow these steps:
```bash
-Execute the Gradle build;
-To running this application you can running it without 
	 a profile, in this way the application will use H2 database,
	 if you want use Mysql database you just need to choose Mysql profile
	 (application-mysql.properties), to do that you need to configure the
	 application to use this profile (spring.profiles.active=mysql)
	 and configure the database connection in application-mysql.properties file,
	 if you want to use docker you need to configure it in profile file, you can find this configuration in docker-compose file (SPRING_PROFILES_ACTIVE);
	 -The application can create the database table structure every time that the application are started (spring.jpa.hibernate.ddl-auto=update), it is only configured in mysql profile.
```
Inside Intellij IDE:
```bash
-Import the project;
-Execute Gradle import;
-Check Enable annotation processing field in Intellij options
-If necessary configure Mysql database in profile file our if you want to use H2 you can pass to next step;
-Start application using the Intellij IDE.
```

## Docker
 To use docker you need follow these steps:
 ```bash
	-Build the application,
	-Build docker image with this command: docker build -t healthcare-management . or docker build -t healthcare-management . (you need to run this command in root project that you want to *create the docker image);
    -Execute the docker-compose file with this command: docker-compose up (you need to run this command in root project). You can -check if applications are running using the actuator feature, to do do that you need to access this url: http://localhost:8090/healthcare-management/health;
```

## Points to Improve 
 Major points to improve in this API:
```bash
	- Put Roles in database;
	- Api to Delete User and Institution;
	- Create more integration and unit tests.
```

If you have questions, please feel free to contact me.