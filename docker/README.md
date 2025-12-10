# Docker setup for java-web-app

This repository includes a simple Docker setup to run the application together with a PostgreSQL database.

Files:
- `docker/Dockerfile` - builds the production JVM image (expects `build/libs/*.jar`).
- `docker-compose.dev.yml` - starts Postgres + the app for local development.

How it works
1. The Postgres container uses `/docker-entrypoint-initdb.d` to run any `*.sql` files on first startup. We mount `src/main/resources/db.migration` there so the schema and seed data are applied automatically.
2. The `app` service uses the included `docker/Dockerfile` which is now a multi-stage build: the first stage runs the Gradle wrapper with a JDK to build the fat jar, and the second stage copies the built jar into a lightweight JRE image for runtime. Because of that, you don't have to run Gradle on the host — `docker compose build` will perform the build inside the container.

Run locally (development)

Option A — let Docker build the jar (recommended for portability)

1. Ensure Docker Engine is running and your user has permission to use Docker.

2. Start containers and let Docker build everything:

   docker compose -f docker-compose.dev.yml up --build

Option B — build the jar locally (faster iteration when developing)

1. Build the fat jar with Gradle locally (this skips doing the build inside the image):

   ./gradlew clean bootJar

2. Start containers (Dockerfile will copy the jar from build/libs into the image):

   docker compose -f docker-compose.dev.yml up --build

3. The app will be available at http://localhost:8080 (server.port defined in application-dev.yml)

