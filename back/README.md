# Back
This project is developed in Java version 17 with Spring Boot. 

## Tools

- IDE (IntelliJ)
- Postman
- MySql Workbench (or a MySql database installed)

## Build

Open the back folder with your IDE.
Connect to MySql Workbench for example, create a database and if needed, import the Database.sql file currently in the folder.
Execute the SQL script to create the tables and datas.

To avoid displaying sensitive datas, create a .env file in the back folder. 

In the .env file, adapt the DATABASE_USERNAME and DATABASE_PASSWORD variables (as shown in the application.properties file). 

These are the variables that allow you to connect to MySql.
- DATABASE_USERNAME= your_username
- DATABASE_PASSWORD= your_password

Run your application with your IDE. With IntelliJ, right-click on the BackApplication file. Intellij will install automatically maven dependencies
After these steps, you can go on http://localhost:8080/documentation for more information on this API.

## Warning
Build and run the back-end before consulting the API documentation.