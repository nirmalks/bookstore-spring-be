## Bookstore BE service using Spring Boot

- Backend Stack: Spring Boot, Spring Security (JWT), Spring Data JPA, PostgreSQL, Flyway, Redis, Elasticsearch
- Tools & Libraries: Swagger, Jakarta Validation, AOP Logging

### Key Features

- Clean architecture with feature-based folder structure
- RESTful APIs for managing books, authors, users, orders, carts, and genres
- JWT-based authentication and role-based authorization (user, admin)
- Request validation using Jakarta annotations and centralized exception handling
- Advanced book search with filtering and pagination using JPA Specifications
- Redis caching for improved performance
- Elasticsearch integration for full-text search
- Swagger documentation for API testing
- AOP-based structured logging for API request tracing as example
- Flyway integration for consistent DB migrations

### Admin Capabilities

- Add or update books, authors, and genres

### User Capabilities

- Register, log in, and manage account profile
- Browse and search books
- Add items to cart and place orders
- View orders

### credentials
* admin cred - admin/admin123
* customer cred - john_doe/admin123

### Swagger
http://localhost:8080/swagger-ui/index.html
