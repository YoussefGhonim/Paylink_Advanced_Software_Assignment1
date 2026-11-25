
# PayLink – Digital Wallet and Bill Payment System

## 1. Project Overview

PayLink is a backend Digital Wallet and Bill Payment System that allows users to manage their wallets and pay different bills through a centralized platform.

The system is designed to support payments for services such as:
- Electricity
- Water
- Mobile services
- Internet services

It also considers payment failures, refunds, transaction tracking, wallet balance management, and settlement operations.

This project is developed as part of the Advanced Software Engineering course.

## 2. Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Backend programming language |
| Spring Boot 4.2.0 | Backend application framework |
| PostgreSQL 17 | Relational database |
| Spring Data JPA | Database access |
| Maven | Dependency and build management |
| JUnit 5 | Testing |
| Docker | Application containerization |
| Docker Compose | Running the application and database |
| GitHub Actions | Continuous Integration (CI) |
| Checkstyle | Code style validation |

## 3. Project Architecture

PayLink follows a Layered Modular Monolith architecture.

The system is organized around separate business modules with defined responsibilities and interfaces.

The planned modules include:
- User Management
- Wallet Management
- Payment Processing
- Bill Payment
- Refund Management
- Settlement and Reconciliation

The current Spring Boot health controller is a placeholder used to verify that the application starts correctly. Business modules will be implemented in the following development phases.

For more details, see:
- `docs/architecture.md`
- `docs/database-erd.mmd`

## 4. Prerequisites

Before running the project, make sure you have:

- Git installed.
- Docker Desktop installed and running (Windows/macOS), or Docker Engine with Docker Compose (Linux).

You do NOT need to install Java or Maven separately when running through Docker Compose.

## 5. Running the Project

### Step 1: Clone the repository

```bash
git clone <REPOSITORY_URL>
cd paylink
```

Replace `<REPOSITORY_URL>` with the actual GitHub repository URL.

### Step 2: Build and start the containers

```bash
docker compose up --build
```

Docker Compose will:
1. Build the Spring Boot application.
2. Pull and start the PostgreSQL database.
3. Wait for the database health check.
4. Start the application.

### Step 3: Verify the application

Open the following URL in your browser:

http://localhost:8080/health

Expected response:

```text
PayLink is running!
```

### Step 4: Stop the containers

Press `Ctrl + C` in the terminal, then run:

```bash
docker compose down
```

To remove the database volume as well (this permanently deletes the local database data):

```bash
docker compose down -v
```

## 6. Running Tests Locally

If Java 21 is installed, tests and code-style checks can be executed using Maven Wrapper.

On Windows:

```powershell
.\mvnw clean verify
```

On Linux/macOS:

```bash
./mvnw clean verify
```

This command performs:
- Project compilation
- Unit and integration tests
- Checkstyle validation
- Maven verification

## 7. Continuous Integration

PayLink uses GitHub Actions for automated validation.

The CI pipeline runs on:
- Pushes to branches.
- Pushes of `phase-*` tags.
- Pull requests targeting `main` or `develop`.

The pipeline:
1. Checks out the repository.
2. Sets up Java 21.
3. Builds the project.
4. Runs tests.
5. Executes Checkstyle validation.

A successful workflow indicates that the project passes the configured build, test, and code-style checks.

## 8. Team Responsibilities

| Member | Responsibility |
|---|---|
| Yahiaa | Requirements Engineering and Documentation |
| Sandra | UML Diagrams and Behavioral Design |
| Menna | Database Design and System Architecture |
| Youssef | Backend Skeleton, GitHub, Docker, CI/CD and Integration |

## 9. Project Documentation

Project documentation is maintained in the `docs/` directory.

Expected documentation includes:
- Requirements specification
- AI prompt log
- UML diagrams
- Database ERD
- System architecture

## 10. Development Status

**Phase 1 – Foundation and Design**

The current phase establishes the project requirements, architecture, database design, backend skeleton, containerization, and CI pipeline.

Business functionality and additional modules will be implemented in subsequent phases.
