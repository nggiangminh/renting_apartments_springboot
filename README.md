## Renting Apartments - Spring Boot

A web application for apartment rental/management built with Spring Boot, Thymeleaf, Spring Security, and MySQL.

### Tech Stack
- **Java**: 17 (recommended for Spring Boot 3)
- **Spring Boot**: 3.2.5
- **Spring MVC + Thymeleaf**: server-side rendering
- **Spring Data JPA (Hibernate)**: data access
- **Spring Security**: authentication/authorization
- **MySQL**: database

### Project Structure
```
src/main/java/fit/se2/springboot
  ├─ config/
  │   └─ WebSecurityConfig.java
  ├─ controller/
  │   ├─ ApartmentController.java
  │   ├─ AuthController.java
  │   ├─ HomeController.java
  │   ├─ UserController.java
  │   └─ WishlistController.java
  ├─ model/
  │   ├─ Apartment.java
  │   ├─ ChangePasswordForm.java
  │   ├─ CustomUserDetails.java
  │   ├─ User.java
  │   └─ Wishlist.java
  ├─ repository/
  │   ├─ ApartmentRepository.java
  │   ├─ UserRepository.java
  │   └─ WishlistRepository.java
  └─ service/
      ├─ ApartmentService.java
      ├─ CustomUserDetailsService.java
      ├─ UserService.java
      └─ WishlistService.java

src/main/resources
  ├─ application.properties
  ├─ static/
  │   └─ css/ (styles and static assets)
  └─ templates/ (Thymeleaf views: Home, login, signup, apartment*, wishlist, ...)
```

### Setup
1) Requirements
- Java 17 (JDK)
- Maven 3.9+ (or use the included `mvnw` wrapper)
- MySQL 8.x

2) Create MySQL database
```
CREATE DATABASE final_pj CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3) Configure connection (file `src/main/resources/application.properties`)
- Default JDBC URL: `jdbc:mysql://localhost:3306/final_pj`
- Update your MySQL username/password accordingly (avoid committing real passwords).

Notable properties:
- `server.port=9001` (app runs on port 9001)
- `spring.jpa.hibernate.ddl-auto=update` (auto schema update from entities)

### Run (Development)
Using Maven Wrapper (no global Maven install required):
```
./mvnw spring-boot:run
```
On Windows PowerShell:
```
./mvnw.cmd spring-boot:run
```
The app will be available at: `http://localhost:9001`

### Build (Jar)
```
./mvnw clean package
java -jar target/springboot-0.0.1-SNAPSHOT.jar
```

### Accounts & Security
- Uses Spring Security; see `config/WebSecurityConfig.java` and `service/CustomUserDetailsService.java`.
- Sign in/sign up views: `templates/login.html`, `templates/signup.html`.
- Create users via UI or seed data manually in the DB.

### Key Features (overview)
- Apartment management: create, update, details, listing
- User management, change password, update profile
- Apartment wishlist
- Thymeleaf UI with base layout `_layout.html`

### Additional Configuration (optional)
- Change server port: update `server.port` in `application.properties`
- Improve DB secrets security: prefer environment variables/Spring profiles over hardcoding

### Contributing
- PRs/Issues are welcome. Follow code style, keep commits focused, and update README when configs change.

