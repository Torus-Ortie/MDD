# Monde de Dev
![Monde de Dev](https://github.com/Torus-Ortie/MDD/blob/main/front/public/logoMDD.png)

## Description
Monde de Dev is a social networking application for developers, built with Spring Boot 3.3.5 and Angular 18.2.0

## Prerequisites
Before you begin, ensure you have met the following requirements:
- You have installed the latest version of Java and Maven.
- You have a MySQL server running. If not, you can [download it from here](https://dev.mysql.com/downloads/installer/).
- [Git](https://docs.github.com/en/get-started/quickstart/set-up-git)
- [NodeJS (**version 20.18.0 - LTS**)](https://nodejs.org/en/) - includes NPM 10.8.1
- [NPM (**version 10.8.1**)](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm)
- [Angular CLI](https://github.com/angular/angular-cli)

## Test User

For your convenience, a test user will be initialized once you complete the backend installation steps. 
You can then use these credentials to access the Swagger Documentation and the frontend of the application.

- email : `test@mdd.com`
- username : `Test`
- password : `Test123*`

You may also choose to register a new user with your own credentials.

## Backend installation and launch

### 1. Clone the repository

```bash
git clone https://github.com/Torus-Ortie/MDD.git
```

### 2. Navigate to the backend directory

```bash
cd back
```

### 3. Create a new database in your MySQL server

   Open a terminal and run the command

  ```bash
  mysql -u mysql_username -p < mdd.sql
  ```

Important Note: Replace `mysql_username` with your actual MySQL username.

### 4. Update the application.properties file

  You need to update the application properties file with the correct server, database, and security variables.

  You may use a .env file (recommended), or replace the variables in the resources/application.properties file directly.
  
  The following variables need to updated with your own values:
  
  ```properties
  #use the username, and password from your MySQL server
  spring.datasource.username=${DB_USERNAME}
  spring.datasource.password=${DB_PASSWORD}

  jwt.secret=${JWT_SECRET} #a secure, random string (UUID recommended)
  ```

  *Note* - You can optionally change the server.url via the application properties file. The default url is `http://localhost:3001/api/`
  
  If you change the server url, be sure to change it in the frontend environments directory as well.

### 5. Build the project and install its dependencies

```bash
mvn clean install
```

### 6. Launch the backend of the application

```bash
mvn spring-boot:run
```

Once the server is running locally, you can access the API endpoints at `http://localhost:3001/api/`.

## Frontend installation and launch

### 1. Navigate to the frontend directory

```bash
cd front
```

### 2. Install the frontend dependencies

```bash
npm install
```

### 3. Launch the frontend of the application

```bash
npm run start
```

The application will launch in your browser at `http://localhost:4200`.

### 4. Connect to Monde de Dev

You may now register a new user and then login.
