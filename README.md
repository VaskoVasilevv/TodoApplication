
## Todo Application
Simple Java console Todo application for managing User Tasks 
## Features

- User management 
- Task management 

## Tech

The project uses the following dependencies:

- [GSON] - open-source Java library to serialize and deserialize Java objects to JSON
- [BCrypt] - password-hashing library
- [Apache-Commons-FileUtils] - File manipulating Java Library

## Installation

The application requeres [Java] and  [Maven](https://maven.apache.org/) to install.

Installing and running the application:

```sh
mvn clean install
cd ToDoApp 
java -jar TodoApp.jar
```

## First time run:

Starting the application will require username/password

Username: admin
Password: adminpass
```bash
Login
Username: admin
Password: adminpass
```
Admin panel:
```bash
Please choose from the following options
1) Create new user - create new app user 
2) List all users - list all registered user in the app
3) Edit user - edit selected user
4) Delete user - delete selected user
5) Task management - manages all registered tasks in the app 
6) Exit - exit the application
```

User panel:
```bash
1) List all Tasks - created by the user or shared with him
2) Create Taks - creates a new task
3) Edit Taks - edit the selected task
4) Change Tasks Status - changes the status of the taskt In Progress / Completed
5) Delete Taks - delete the selected task
6) Share Taks - shares the selected task with other app users
7) Exit - exit the program

After the first start the application will create the following files:
[users.json] - file where the users info will be stored
[tasks.json] - file where the tasks info will be stored
```


