# 03-USER-MGMT-APP
## This project is about User Management Functionality - Backend. This project was developed with the following tools:
* Spring Boot
* Mysql
* JPA
* Swagger
* Spring Boot Mail
* Lombok
* Maven
* Swagger
* Java
* SonarQube


## SonarQube in the project
1) Add SonarQube dependency in the pom.xml
```
    <properties>
        <java.version>17</java.version>
        <sonar.host.url>sonar_ip_address</sonar.host.url>
        <sonar.login>sonar_user</sonar.login>
        <sonar.password>sonar_password</sonar.password>
    </properties>
```
2) Install and create SonarQube account.
3) Type next command to create war file and connect to sonar
```
    mvn clean package
    mvn sonar:sonar
```
## Requirements about the project
## 1) Develop a screen for "User Registration".
New users will fill out the form below to create an account. After registration success, a random password will be sent to the user email in order to activate the account. User account will be inactive status.

![User Registration](https://github.com/felixala/03-USER-MGMT-APP/blob/master/src/main/resources/images/screenshots/createAccount.png)

## 2) Develop a screen for "Activate Account".
User will enter the random password that was sent in the step above and will enter new password and confirmation password to unlock the account. Status of the account will change to "Active".

![Activate Account](https://github.com/felixala/03-USER-MGMT-APP/blob/master/src/main/resources/images/screenshots/activateAccount.png)

## 3) Develop a screen to display all users registered.
This screen will display all users registered in the application. All users will have the following actions: Edit, Delete and Activate or Inactivate account.

![Display Users](https://github.com/felixala/03-USER-MGMT-APP/blob/master/src/main/resources/images/screenshots/viewAccount.png)

## 4) Develop a screen for "Login Page".
If user account is activated, so application will allow to the user to login using email and password.

![Login Page](https://github.com/felixala/03-USER-MGMT-APP/blob/master/src/main/resources/images/screenshots/forgotPassword.png)

## 5) Develop a screen for "Forgot Password".
This screen will recover the user password. If the user enter valid email, so the application will send the current password by email.

![Forgot Password](https://github.com/felixala/03-USER-MGMT-APP/blob/master/src/main/resources/images/screenshots/recoverPassword.png)