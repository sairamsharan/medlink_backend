# MedLink — Microservices Architecture

## Overview
MedLink is a healthcare platform backend built with a **microservices architecture** using **Spring Boot** and **Spring Cloud Netflix**. The system manages hospitals, doctors, patients, and appointments through independent, scalable services.

## Architecture

```
                        React Frontend (port 3000)
                              │
                              ▼
                    ┌── API Gateway ──────┐
                    │    (port 4000)       │
                    │  Spring Cloud Gateway│
                    └─────────┬───────────┘
                              │
                    ┌── Eureka Server ────┐
                    │    (port 8761)       │
                    │  Service Discovery   │
                    └─────────┬───────────┘
                              │
            ┌─────────────────┼─────────────────┐
            ▼                 ▼                  ▼
    ┌──────────────┐ ┌──────────────┐ ┌──────────────────┐
    │ Auth Service  │ │Hospital Svc  │ │Appointment Svc   │
    │  (port 4001)  │ │ (port 4002)  │ │   (port 4003)    │
    │ JWT + OTP     │ │ Hospital CRUD│ │ Booking + Contact│
    │ Login/Register│ │              │ │ Email Confirm    │
    └──────────────┘ └──────────────┘ └──────────────────┘
            │                 │                  │
            └─────────────────┼──────────────────┘
                              ▼
                    PostgreSQL (Neon Cloud)
```

## Services

| Service | Port | Description |
|---|---|---|
| **Eureka Server** | 8761 | Netflix Eureka service discovery and registry |
| **API Gateway** | 4000 | Spring Cloud Gateway — routes requests to services |
| **Auth Service** | 4001 | JWT authentication, OTP verification, email service |
| **Hospital Service** | 4002 | Hospital CRUD operations |
| **Appointment Service** | 4003 | Appointment booking, contact form, email confirmations |

## Technologies Used
- **Spring Boot 3.2.4** — Application framework
- **Spring Cloud Netflix Eureka** — Service discovery and registration
- **Spring Cloud Gateway** — API gateway for request routing
- **Spring Data JPA** — Database access with Hibernate ORM
- **Spring Security** — Authentication and authorization
- **JWT (jjwt)** — Stateless token-based authentication
- **PostgreSQL** — Database (Neon Cloud)
- **Lombok** — Boilerplate code reduction

## Getting Started

### Prerequisites
- Java 17+
- Maven
- Docker (optional)

### Run Individually
```bash
# 1. Start Eureka Server first
cd eureka-server && ./mvnw spring-boot:run

# 2. Start API Gateway
cd api-gateway && ./mvnw spring-boot:run

# 3. Start Services (in any order)
cd auth-service && ./mvnw spring-boot:run
cd hospital-service && ./mvnw spring-boot:run
cd appointment-service && ./mvnw spring-boot:run
```

### Verify
- Eureka Dashboard: http://localhost:8761
- API Gateway: http://localhost:4000

## API Endpoints

All requests go through the API Gateway (`http://localhost:4000`):

| Method | Endpoint | Service | Description |
|---|---|---|---|
| POST | `/register` | auth-service | Register new user |
| PUT | `/verify-account` | auth-service | Verify OTP |
| POST | `/login` | auth-service | Login and get JWT |
| GET | `/hospitals/{location}` | hospital-service | Get hospitals by location |
| GET | `/hospitals/id/{id}` | hospital-service | Get hospital by ID |
| POST | `/hospitals/upload` | hospital-service | Bulk upload hospitals |
| POST | `/appointment` | appointment-service | Book appointment |
| GET | `/appointment/{id}` | appointment-service | Get appointments |
| POST | `/contact` | appointment-service | Submit contact form |

## Contributing
Contributions are welcome! Fork the repository, make your changes, and submit a pull request.