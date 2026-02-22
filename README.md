# MedLink Backend API

MedLink is a hospital appointment and bed booking web platform that allows users to search for nearby hospitals, check real-time bed availability, and book appointments with doctors. This repository contains the backend service, built using Spring Boot and **MySQL**, providing a robust REST API for the frontend client.

Developed by **Piyush Kumar, Sairam Sharan, Anadi Mehta, Satyam Gupta, Sweta Kumari, Sanchita, and Mayank**, students at **IIIT Lucknow** (Beta).

---

## 🚀 Technology Stack

- **Framework:** Spring Boot 3.5.11 (Java 25)
- **Database:** MySQL 8+
- **ORM:** Spring Data JPA (Hibernate)
- **Authentication:** JWT (jjwt 0.11)
- **Email:** Spring Mail (SMTP)
- **Build Tool:** Maven

---

## 🛠️ Running Locally

### Prerequisites

1. **Java Development Kit (JDK) 25** installed.
2. **MySQL Server** installed and running on your local machine.
3. Configure `application.properties` with your MySQL credentials, database URI, and Gmail SMTP credentials (for sending OTPs).

### Setup and Execution

Clone the repository and run the application using the Maven wrapper:

```bash
cd medlink_backend/medlink
./mvnw clean install
./mvnw spring-boot:run
```

The application will start on **`http://localhost:4000`**.

---

## 📡 API Endpoints

**Base URL:** `http://localhost:4000`

### Authentication

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/register` | Register a new user account. Sends OTP email for verification. | `{ "firstName", "lastName", "email", "password" }` | `{ "statusCode": 200, "message": "..." }` |
| `PUT` | `/verify-account` | Verify account with OTP sent to email. | Query params: `email`, `otp` | `{ "token": "<JWT>", "statusCode": 200 }` |
| `POST` | `/login` | Login with credentials. Returns JWT token. | `{ "email", "password" }` | `{ "token": "<JWT>", "statusCode": 200 }` |

### Hospitals

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `GET` | `/hospitals/{location}` | Get hospitals by city/location. | N/A | `[ { "id", "name", "address", "location", "contactInfo", "beds" } ]` |
| `POST` | `/hospitals/upload` | Bulk upload hospitals (admin utility). | `[ { "name", "address", "location", "contactInfo", "beds" } ]` | Saved hospitals list |
| `GET` | `/hospitals/id/{id}` | Get hospital by ID. | N/A | `{ "id", "name", "address", "location", "contactInfo", "beds" }` |

### Appointments

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/appointment` | Create a new appointment. | `{ "patientId", "fullName", "email", "phone", "date", "time", "message" }` | Saved appointment object |
| `GET` | `/appointment/{id}` | Get all appointments for a user by user ID. | N/A | `[ { "id", "patientId", "fullName", "email", "phone", "date", "time", "message" } ]` |

### Contact

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/contact` | Submit a contact form message. | `{ "fullName", "email", "message" }` | Saved contact object |

---

## 🔒 Authentication Flow

1. **Register** - User submits name, email, password to `POST /register` and the backend sends OTP to email
2. **Verify OTP** - User enters 6-digit OTP via `PUT /verify-account?email=...&otp=...` and receives a JWT token
3. **Login** - User submits email, password to `POST /login` and receives a JWT token
4. **JWT Security** - The frontend must include the JWT Token in the `Authorization` header (`Bearer <token>`) for protected routes.

---

## 🗄️ Data Models

### UserModel
```json
{
  "id": "long",
  "email": "string",
  "password": "string (hashed)",
  "firstName": "string",
  "lastName": "string",
  "otp": "string",
  "active": "boolean",
  "otpGeneratedTime": "LocalDateTime"
}
```

### HospitalModel
```json
{
  "id": "long",
  "name": "string",
  "address": "string",
  "location": "string (city)",
  "contactInfo": "string",
  "beds": "int (total bed count)"
}
```

### PatientInfo (Appointment)
```json
{
  "id": "long",
  "patientId": "string (user ID)",
  "fullName": "string",
  "email": "string",
  "phone": "string",
  "date": "string (YYYY-MM-DD)",
  "time": "string (HH:MM)",
  "message": "string (Department: X | Doctor: Y | Symptoms: Z)"
}
```

### ContactModel
```json
{
  "id": "long",
  "email": "string",
  "fullName": "string",
  "message": "string"
}
```

---

## 🌐 CORS Configuration

The backend is configured to allow all origins (`*`) for cross-origin requests, supporting the following HTTP methods: `GET`, `POST`, `PUT`, `DELETE`, `OPTIONS`.