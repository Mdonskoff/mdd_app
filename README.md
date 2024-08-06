# MDD App (Monde de dev)

## Context
Create an MVP application that allows you to :

- Register and login
- Subscribe or unsubscribe to a topic
- Create and open articles
- A personal feed view
- Comment an article
- Modify your profile info

## Front
This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 14.2.13.

Don't forget to install your node_modules before starting (`npm install`).

## Back
This project is developed in Java version 17.

Don't forget to download maven dependencies.

### Development front server
Run `ng serve` for a dev server. Navigate to http://localhost:4200/. The application will automatically reload if you change any of the source files.

### Development back server
Run `BackApplication` with your IDE command or maven command. Go to http://localhost:8080/documentation for API documentation. 

Please note that the application must be installed and running to access the documentation.

## Where to start
Clone this repository with git clone command and follow the steps in the back folder /back/README.md and the front folder /front/README.md
Install the back end first, then the front end.

### WARNING
The application is configured to update the database.
If you want to delete once shut down, you have to change this line :
`spring.jpa.hibernate.ddl-auto=update` to `spring.jpa.hibernate.ddl-auto=create-drop`
in the /back/.../application.properties file.

## Author
Marushka LABORDE-DONSKOFF