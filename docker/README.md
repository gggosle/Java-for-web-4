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

1. Ensure Docker Engine is running and your user has permission to use Docker (see Troubleshooting below).

2. Start containers and let Docker build everything:

   docker compose -f docker-compose.dev.yml up --build

Option B — build the jar locally (faster iteration when developing)

1. Build the fat jar with Gradle locally (this skips doing the build inside the image):

   ./gradlew clean bootJar

2. Start containers (Dockerfile will copy the jar from build/libs into the image):

   docker compose -f docker-compose.dev.yml up --build

3. The app will be available at http://localhost:8080 (server.port defined in application-dev.yml)

Notes & troubleshooting
- Docker permission denied: if you see "permission denied while trying to connect to the Docker daemon socket at unix:///var/run/docker.sock", it means your user cannot talk to the Docker daemon. Fix options:

  - Run Docker commands with sudo:

    sudo docker compose -f docker-compose.dev.yml up --build

  - Or add your user to the `docker` group (then log out and back in or reboot):

    sudo usermod -aG docker $USER

  - Ensure Docker Engine is installed and running (e.g., systemd service):

    sudo systemctl start docker
    sudo systemctl enable docker

- Buildx warning: you may see "Docker Compose is configured to build using Bake, but buildx isn't installed" — this is only a warning; the build will still proceed using classic build if buildx is unavailable. To remove the warning, install Docker Buildx or use the standard docker buildx plugin supported by your Docker installation.

- SQL migrations re-run only on fresh DB initialization. To force re-run remove the `db-data` volume:

  docker compose -f docker-compose.dev.yml down -v

- If the app tries to connect to `localhost:5432` instead of the `db` service, ensure the `SPRING_DATASOURCE_URL` environment variable in `docker-compose.dev.yml` is set (compose overrides application-dev.yml with env vars).

- If you prefer Flyway-managed migrations, add `org.flywaydb:flyway-core` to `build.gradle` and configure Flyway in `application.yml`.
