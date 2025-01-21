# Spring Boot Security with JWT

This project demonstrates the implementation of Spring Boot security with JSON Web Tokens (JWT). It includes user authentication, registration, and role-based access control.

## Features
- User registration
- User login to obtain a JWT token
- Role-based access control (e.g., access to managers or users)
- H2 in-memory database for quick development and testing

---

## Endpoints

### 1. **Register a User**
**URL:** `POST http://localhost:8080/users`

**Description:** Create a new user by providing their details.

**Payload Example:**
```json
{
  "username": "exampleUser",
  "password": "password123",
  "roles": ["USERS", "MANAGERS"]
}
```

---

### 2. **Access H2 Console**
**URL:** `http://localhost:8080/h2-console/`

**Description:** View and interact with the H2 in-memory database. Ensure the database connection properties in `application.properties` match the console settings.

---

### 3. **Login to Obtain JWT Token**
**URL:** `POST http://localhost:8080/login`

**Description:** Authenticate a user and receive a JWT token.

**Payload Example:**
```json
{
  "username": "exampleUser",
  "password": "password123"
}
```

**Response Example:**
```json
{
  "login": "exampleUser",
  "token": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

### 4. **Get Managers (Requires Token)**
**URL:** `GET http://localhost:8080/managers`

**Description:** Fetch data accessible only to users with the appropriate role (e.g., `ROLE_MANAGER`).

**Headers Example:**
```
Authorization: Bearer <JWT_TOKEN>
```

---

### 5. **Get Users (Requires Token)**
**URL:** `GET http://localhost:8080/users`

**Description:** Fetch data accessible to authenticated users.

**Headers Example:**
```
Authorization: Bearer <JWT_TOKEN>
```

---

## Prerequisites

1. **Java 17** or later
2. **Maven** (to build and run the project)

---

## Setup and Running the Project

1. Clone the repository:
   ```bash
   git clone <repository_url>
   cd <repository_directory>
   ```

2. Build the project using Maven:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Access the application endpoints using a tool like [Postman](https://www.postman.com/) or [cURL](https://curl.se/).

---

### Roles and Permissions
- **ROLE_USER**: Grants access to basic user endpoints.
- **ROLE_MANAGER**: Grants access to managerial endpoints.

---

## H2 Database Configuration
Default configurations for the H2 database:
- **URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** `sa`

Access the H2 console at `http://localhost:8080/h2-console/`.

---

## Testing Endpoints
### Example with `curl`:

#### Register a User:
```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"username":"exampleUser","password":"password123","roles":["USERS", "MANAGERS"]}'
```

#### Login:
```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"username":"exampleUser","password":"password123"}'
```

#### Access Protected Endpoints:
```bash
curl -X GET http://localhost:8080/users \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

