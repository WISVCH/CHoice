# CHoice

## How to run

Prerequisites:

- JDK 11
- PostgreSQL

Setup:

1. Create a Postgres user named `choice` with a password.
2. Create a database named `choice` and give the user from the previous step access to it.
3. In `application.yml` replace `<database>` in `spring.datasource.url` with `choice` and `<ldap-group>` in `wisvch.connect.admin-groups` .
4. Set the username and password options below that to `choice` and the password that you picked respectively.
5. Set `wisvch.connect.admin-groups` to `hoothub`.
6. Run the `bootRun` Gradle task with `./gradlew bootRun` or `gradlew.bat bootRun` if you're on Windows.
7. Go to `localhost:8080/choice2/dashboard/`, you'll be redirected to a CH Connect page where you have to grant access. You may have to log in first.
8. If everything went well you should now be able to access CHoice at the same URL.

## Built with

- Gradle - Build and dependency management tool
- Spring Boot - Web framework
- Thymeleaf - HTML templating
- PostgreSQL - Database
