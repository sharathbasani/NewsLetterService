# NewsLetterService

A Spring Boot microservice for creating topics, managing subscribers and subscriptions, scheduling content delivery, and sending emails. It uses JWT-based authentication, Spring Data JPA for persistence, and a scheduled task to dispatch content to subscribers at configured times.

## Instructions
- If the service is not used for a longer time. It will be become inactive (Render free tier restriction).
- To use it, please visit https://newsletterservice.onrender.com first to initiate re-deployment.
- Time to be stored in DB is UTC (Render uses UTC timezone during deployment)

## Table of contents
- Project overview
- Open Source tools used
- Features
- Technology stack
- Quick start (build and run)
- Configuration
- Authentication (JWT)
  - Register
  - Login
  - Using the token
  - Token lifetime and rotation
- API reference (high level)
  - Auth
  - Topic
  - Subscriber
  - Subscription
  - Content
  - Email
- Scheduling
- Error handling & logging
- Database / JPA
- Development notes
- Security recommendations
- Contributing
- License


## Project overview
NewsLetterService provides the back-end for a newsletter system where administrators can create topics and content, subscribers can be managed, subscription link subscribers to topics, and scheduled content is automatically emailed to subscribed users.

The service exposes REST endpoints, requires authentication for protected routes, and sends emails. For the Content scheduled for a specific time is dispatched by a scheduled job.

Swagger/OpenAPI: available under `/api/swagger-ui/index.html` and OpenAPI JSON at `/api/v3/api-docs`

## Open Source tools used
- Deployment: Render (Hobby Tier)
- Sql: Railway (30-day free trial)
- SMTP Provider: Brevo (Free Tier - 300 Emails per day)

## Features
- User registration and login
- JWT authentication (Bearer tokens)
- CRUD operations for Topics, Subscribers, Subscriptions, and Content
- Send ad-hoc emails via `/api/email/send` (ad-hoc sends are synchronous)
- Scheduler that runs every minute and sends scheduled content to subscribers of a topic (scheduled sends are dispatched asynchronously)
- Validation and centralized exception handling with structured JSON error responses

## Technology stack
- Java 17+
- Spring Boot
  - Spring Web
  - Spring Security
  - Spring Data JPA
  - Spring Scheduling
- JJWT (io.jsonwebtoken) for JWT creation and verification
- Lombok (for boilerplate reduction)
- MapStruct (mappers exist in the project)
- SLF4J logging
- MySQL (configurable via `application.properties`, can be replaced by any other JDBC database)


## Quick start (build and run)
1. Configure your database and any required environment variables (see Configuration).
2. Build the project:

```bash
./mvnw -DskipTests clean package
```

3. Run the application:

```bash
./mvnw -DskipTests spring-boot:run
```

The service listens on port `8080` by default and uses a servlet context path of `/api`, so endpoints are rooted under `http://localhost:8080/api`.


## Configuration
Runtime configuration is in `src/main/resources/application.properties` and environment variables are used for assigning these values.

Important properties:

- `spring.datasource.*` - JDBC URL, username and password for the database
- `security.jwt.secret` - Base64-encoded secret used to sign JWTs (HS256). Keep this secret safe.
- `security.jwt.expiration-ms` - Token expiry in milliseconds (default: `3600000` = 1 hour)

## Authentication (JWT)

This service uses JWT bearer tokens. Authentication flow:

1. Register a new user (POST `/api/auth/register`).
2. Login with username/password (POST `/api/auth/login`).
3. Use returned token on the following requests in the `Authorization` header as `Bearer <token>`.

### Register

Endpoint: `POST /api/auth/register`

Request body (JSON):
```json
{
  "username": "alice",
  "password": "<PASSWORD>"
}
```

Responses:
- `201 Created` — user registered successfully (a message returned asking to log in)
- `409 Conflict` — username already exists
- `400 Bad Request` — validation errors

### Login

Endpoint: `POST /api/auth/login`

Request body (JSON):
```json
{
  "username": "alice",
  "password": "<PASSWORD>"
}
```

Response (200 OK):
```json
{
  "token": "<JWT_TOKEN>"
}
```

Use this token for protected endpoints:

Header example:
```
Authorization: Bearer <JWT_TOKEN>
```

### Token lifetime and expiration
- Default lifetime: `security.jwt.expiration-ms` 1 hour
- After expiration, the token is invalid and protected endpoints will return `401 Unauthorized`.

### What happens on invalid/expired token
- If a Bearer token is present but invalid or expired, the JWT provider's validation fails. The filter will not set the authentication and the configured AuthenticationEntryPoint returns `401 Unauthorized`.
- If no Authorization header is provided for protected routes, the service returns `401 Unauthorized` (configured via `SecurityConfig`).


## API Reference (High level)
All endpoints are rooted under `/api`

All the endpoints can be viewed under `/api/swagger-ui/index.html`.

### Auth
- `POST /api/auth/register` — register a new user.
- `POST /api/auth/login` — login and receive JWT.

### Topic (requires authentication)
- `POST /api/topic/createTopic` — Create a topic.
  - Body: `{ "name": "Topic name", "description": "..." }`
  - Returns: created topic response (201)

- `GET /api/topic/getAllTopics` — List topics (200)
- `GET /api/topic/getTopic/{id}` — Get a topic by id (200)
- `PATCH /api/topic/updateTopic/{id}` — Update topic (200)
- `DELETE /api/topic/deleteTopic/{id}` — Delete topic (204)

### Subscriber (requires authentication)
- `POST /api/subscriber/createSubscriber` — Create a subscriber
  - Body: `{ "name":"John Doe", "email":"john@example.com" }` (201)
- `GET /api/subscriber/getAllSubscribers` — List subscribers (200)
- `GET /api/subscriber/getSubscriber/{id}` — Get subscriber (200)
- `PATCH /api/subscriber/updateSubscriber/{id}` — Update subscriber (200)
- `DELETE /api/subscriber/deleteSubscriber/{id}` — Delete subscriber (204)
- `GET /api/subscriber/getAllSubscriptions/{id}` — Get all subscriptions for a subscriber (200)

### Subscription (requires authentication)
- `POST /api/subscription/createSubscription` — Create subscription linking subscriber and topic
  - Body: `{ "subscriberId": 1, "topicId": 2 }` (201)
- `GET /api/subscription/getAllSubscriptions` — Get all subscriptions (200)
- `GET /api/subscription/getSubscription/{id}` — Get subscription (200)
- `PATCH /api/subscription/updateSubscription/{id}` — Update subscription (200)
- `DELETE /api/subscription/deleteSubscription/{id}` — Delete subscription (204)

### Content (requires authentication)
- `POST /api/content/createContent` — Create content and optionally schedule it
  - Body example:
  ```json
  {
    "topicId": 2,
    "title": "Weekly update",
    "body": "Hello subscribers...",
    "scheduledAt": "14:30:00"
  }
  ```
  - `scheduledAt` is a `LocalTime` value (HH:mm:ss) and is used by the scheduler to decide when to send the content.
- `GET /api/content/getAllContents` — List content (200)
- `GET /api/content/getContent/{id}` — Get content by id (200)
- `PATCH /api/content/updateContent/{id}` — Update content (200)
- `DELETE /api/content/deleteContent/{id}` — Delete content (204)

### Email (requires authentication)
- `POST /api/email/send` — Send an ad-hoc email (200)
  - Body example:
  ```json
  {
    "toEmail": "john@example.com",
    "subject": "Hello",
    "body": "This is a test"
  }
  ```


## Scheduling
The service contains a `ContentSchedulerService` which runs every minute (cron: `0 * * * * *`).

Behavior:
1. At each scheduler tick (every minute) the service looks for `Content` entries whose `scheduledAt` time equals the current server time (minutes/seconds normalized).
2. For each matching `Content`, the service queries `SubscriptionRepo.findSubscriberEmailsByTopic(topicId)` to collect subscriber emails.
3. The `EmailService` sends the content `title` and `body` to each subscriber email.

Notes about asynchronous sending:
- Scheduled sends use an asynchronous method `EmailService.sendEmailAsync(...)` annotated with Spring's `@Async`. The project enables async processing via `AsyncConfig` (`@EnableAsync`) and registers `GlobalAsyncExceptionHandler` to capture uncaught exceptions from async methods.
- Ad-hoc sends invoked via the `/api/email/send` endpoint currently call the synchronous `EmailService.sendEmail(...)` method.

## Error handling & logging
- Centralized exception handling is in `GlobalExceptionHandler`.
  - Validation errors produce `400 Bad Request` with `ValidationError` details.
  - `Custom Exceptions` map to `404 Not Found`, `409 Conflict`, `503 Service Unavailable`, or `400 Bad Request` depending on the exception type.
  - Unhandled exceptions return `500 Internal Server Error`.
- SLF4J logging is used throughout the project. `GlobalExceptionHandler` logs validation errors (WARN) and application/framework/unhandled exceptions (ERROR) with request path and error details.


## Database / JPA
- The project uses Spring Data JPA. Repositories are in `src/main/java/.../repositories`.
- Entities include `User`, `Topic`, `Subscriber`, `Subscription`, and `Content`.
- JPA is configured to `update` the schema by default in `application.properties` (`spring.jpa.hibernate.ddl-auto=update`) — change this for production.


## Development notes
- Lombok and MapStruct are used — ensure your IDE has Lombok support enabled.

- To run the application with a different JWT secret (recommended for local testing):

```bash
export SECURITY_JWT_SECRET=$(openssl rand -base64 48)
./mvnw spring-boot:run
```

- To change JWT expiration to 24 hours (example):

```properties
security.jwt.expiration-ms=86400000
```


## Security recommendations
- Rotate keys periodically and have a plan to re-issue tokens or invalidate old ones.
- Ensure HTTPS is used in production so tokens are not exposed in transit.


## Troubleshooting
- If protected endpoints return `401 Unauthorized`:
  - Ensure you provided `Authorization: Bearer <token>` header.
  - Confirm token has not expired (default 1 hour).
  - Confirm `security.jwt.secret` matches the secret used to sign the token.

- If scheduler does not run:
  - Check application timezone and scheduled times stored on Content entities.


## Areas for Improvement

Below are concise recommendations to make the service more robust, scalable, and production-ready.

#### Asynchronous Email Sending
- Move email sending to an asynchronous pipeline using a message queue.
- Producers publish email tasks; consumers or workers process them.
- Add retries with exponential backoff and a dead-letter queue (DLQ).

#### Scheduling Improvements
- Replace `@Scheduled` polling with:
  - Cloud schedulers (Cloud Scheduler, Azure Timer), or
  - A clustered scheduler like Quartz with JDBC store.
- Prevents duplicate executions across multiple service instances.

#### Transaction Management
- Use the **SAGA pattern** for reliability in distributed microservice transactions.

#### Caching
- Cache frequently accessed data (subscriber lists, topics, templates) using Redis or Memcached.
- Reduces database load and speeds up sending operations.

#### Batching & Rate Limiting
- Add rate limiting (per user/IP) to prevent DDOS attacks.
- Batch email sends to improve throughput and reduce provider cost.
- Apply provider-specific rate limits.

#### Resilience & Provider Fallback
- Use retries and circuit breakers (Resilience4j).
- Add fallback email providers to improve reliability.

#### Observability & Monitoring
- Add metrics (Micrometer), structured JSON logs, and distributed tracing (OpenTelemetry).
- Track queue depth, email failures, delivery latency, and scheduler runs.
- Add dashboards and alerts (Prometheus, Grafana, cloud monitoring).

#### Idempotency & Deduplication
- Use idempotency keys (e.g., `contentId + subscriberId`) to avoid duplicate emails on retries.

#### Email Templates & Localization
- Use a templating engine (Thymeleaf, Freemarker, Handlebars).
- Support variables, conditional content, and multi-language templates.

#### Security & JWT Enhancements
- Consider asymmetric JWT signing (RS256).
- Add refresh tokens and token revocation/blacklisting.

#### Database Improvements
- Use Flyway or Liquibase for schema migrations.
- Add the necessary indexes and consider partitioning large tables.

#### Testing & CI/CD
- Add integration tests for auth flow and email-processing pipeline.
- Improve CI pipelines with automated tests and static code checks.

#### Performance & Scalability
- Ensure scheduled tasks don't run multiple times in multi-instance deployments.
- Horizontally scale consumers and partition workloads for large-volume sending.

#### Privacy & Compliance
- Implement unsubscribe links and suppression lists.
- Store consent metadata and manage user preferences (GDPR/anti-spam compliance).

#### Operational Readiness
- Add readiness/liveness health checks for container deployments.
- Configure alerts for queue backlogs, failed sends, and provider outages.
