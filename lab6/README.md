# Docker & Docker Compose 

This project focuses on the containerization of a microservice-based architecture, consisting of an Angular frontend and two Spring Boot backend applications.

## Completed Tasks

1. **Angular Frontend (NGINX)**: 
   - Created a multi-stage `Dockerfile` for building the Angular app and serving it via **NGINX**.
   - Configured NGINX to act as a web server on port 80.
2. **Categories Microservice (Spring Boot)**:
   - Containerized using the **Eclipse Temurin** image.
   - Configured via environment variables for flexible deployment.
3. **Elements Microservice (Spring Boot)**:
   - Containerized using the **Eclipse Temurin** image.
   - Configured via environment variables and exposed on a dedicated port.
4. **Orchestration (Docker Compose)**:
   - Created a `docker-compose.yml` file to manage all three containers simultaneously.
   - Configured service networking, port mapping, and environment synchronization.


## Accessing the Services:
Frontend (Angular): http://localhost (Port 80)

Categories API: http://localhost:8081/api/genres

Elements API: http://localhost:8082/api/songs

## Deployment Instructions

To start the entire system with a single command, run:

```bash
docker-compose up --build
