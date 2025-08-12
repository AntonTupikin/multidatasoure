# Multidatasoure

Demo Spring Boot application showing how to configure and use multiple data sources.

## Overview

- **Primary data source** stores application users and their roles.
- **Secondary data source** stores log entries for auditing operations.
- JWT-based authentication protects the API and allows login and user management.

## Running the project

```bash
mvn spring-boot:run
```

Connection details for both databases are configured in `src/main/resources/application.yml`.

