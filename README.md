# 💳 E-Banking Backend API

Backend d’une application bancaire moderne développé avec **Spring Boot**.  
Cette API REST permet la gestion des clients, comptes bancaires, opérations bancaires et l’authentification sécurisée avec **JWT**.

---

# 🚀 Technologies utilisées

- Java 17
- Spring Boot 3
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- H2 Database
- Maven
- Lombok
- Swagger OpenAPI

---

# 📌 Fonctionnalités

## 👤 Authentification & Sécurité

- Authentification avec JWT
- Spring Security
- Gestion des rôles et autorisations
- BCrypt Password Encoder
- Stateless Authentication

---

## 🏦 Gestion Bancaire

- Gestion des clients
- Gestion des comptes bancaires
- Dépôt d’argent
- Retrait d’argent
- Virement bancaire
- Historique des opérations
- Pagination des opérations

---

## 🤖 Chatbot AI

- Endpoint chatbot sécurisé
- Contrôle d’accès avec JWT

---

## 📖 Documentation API

- Swagger UI intégré
- OpenAPI Documentation

---

# 📂 Architecture du projet

```bash
ebanking-backend/
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/ebankingbackend/
│   │   │       ├── controllers/
│   │   │       ├── dto/
│   │   │       ├── entities/
│   │   │       ├── repositories/
│   │   │       ├── security/
│   │   │       ├── services/
│   │   │       └── mapper/
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   └── data.sql
│── pom.xml
│── README.md
```

---

# ⚙️ Configuration

## application.properties

```properties
server.port=8085

spring.datasource.url=jdbc:h2:mem:bank
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=true

spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true

jwt.secret=mySecretKey123456789
jwt.expiration=86400000
```

---

# ▶️ Installation et exécution

## 1️⃣ Cloner le projet

```bash
git clone https://github.com/MohamedSAIFI0/ebanking-backend.git
```

---

## 2️⃣ Accéder au dossier

```bash
cd ebanking-backend
```

---

## 3️⃣ Compiler le projet

```bash
mvn clean install
```

---

## 4️⃣ Lancer l’application

```bash
mvn spring-boot:run
```

---

# 🌐 Accès à l’application

## API Backend

```bash
http://localhost:8085
```

---

## Swagger UI

```bash
http://localhost:8085/swagger-ui/index.html
```

---

## H2 Console

```bash
http://localhost:8085/h2-console
```

### Paramètres H2

```text
JDBC URL : jdbc:h2:mem:bank
User Name : sa
Password :
```

---

# 🔐 Authentification JWT

## Endpoint Login

```http
POST /auth/login
```

### Exemple Request

```json
{
  "username": "admin",
  "password": "admin0000"
}
```

---

## Exemple Response

```json
{
  "access-token": "eyJhbGciOiJIUzUxMiJ9..."
}
```

---

# 👥 Comptes utilisateurs par défaut

| Username | Password | Role |
|---|---|---|
| user1 | user1111 | USER |
| admin | admin0000 | ADMIN |

---

# 🛡️ Sécurité Spring

Le projet utilise :

- JWT Authentication
- OAuth2 Resource Server
- BCrypt Password Encoder
- Stateless Session Management
- CORS Configuration

---

# 📌 Endpoints sécurisés

Certaines routes nécessitent un token JWT :

```http
Authorization: Bearer <token>
```

---

# 🧪 Tests API

Vous pouvez tester l’API avec :

- Postman
- Swagger UI
- Insomnia

---

# 📚 Technologies Backend modernes utilisées

- REST API
- DTO Pattern
- Repository Pattern
- Service Layer
- Dependency Injection
- Layered Architecture
- Stateless Authentication

---

# 👨‍💻 Auteur

## Mohamed SAIFI

- Développeur Backend Java & Spring Boot
- Administrateur Réseaux & Sécurité
- Passionné par les architectures backend modernes

GitHub :  
https://github.com/MohamedSAIFI0

---

# ⭐ Objectif pédagogique

Ce projet permet de comprendre :

- Spring Boot
- Spring Security
- JWT Authentication
- Architecture REST
- Gestion bancaire backend
- Sécurisation d’API
- JPA & Hibernate
- Gestion des rôles et permissions
