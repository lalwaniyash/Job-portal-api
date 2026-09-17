# 🚀 AI-Powered Job Portal API

<p align="center">
  <a href="https://github.com/lalwaniyash/Job-portal-api">
    <img src="https://img.shields.io/badge/Java-17%20%2F%2025-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" />
    <img src="https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot" />
    <img src="https://img.shields.io/badge/Spring_Security-6.x-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white" alt="Spring Security" />
    <img src="https://img.shields.io/badge/MySQL-8.x%20%2F%209.x-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL" />
    <img src="https://img.shields.io/badge/JWT-Stateless_Auth-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white" alt="JWT" />
    <img src="https://img.shields.io/badge/Google_Gemini-AI_LLM-4285F4?style=for-the-badge&logo=google&logoColor=white" alt="Google Gemini" />
    <img src="https://img.shields.io/badge/OpenAPI-Swagger_3.0-85EA2D?style=for-the-badge&logo=swagger&logoColor=black" alt="Swagger" />
  </a>
</p>

A production-grade, modular monolith backend REST API for an **AI-Powered Job Portal** built with **Spring Boot 3**, **Spring Security 6**, **Spring Data JPA (Hibernate)**, and **Google Gemini AI**. 

It handles everything from multi-role authentication (Candidates & Recruiters), dynamic multi-criteria job searching, resume document parsing (PDF/DOCX), to intelligent LLM-driven candidate-job compatibility scoring.

---

## 🌟 Key Features

- 🔐 **Stateless JWT Security & RBAC**: Role-Based Access Control (`ROLE_CANDIDATE`, `ROLE_RECRUITER`, `ROLE_ADMIN`) with stateless session management, BCrypt password hashing, and custom `OncePerRequestFilter`.
- 📄 **Resume Parsing Engine**: Automated text extraction from PDF (`Apache PDFBox`) and DOCX (`Apache POI`) files with local storage isolation.
- 🤖 **AI-Powered Job Matcher**: Deep LLM integration using Spring `RestClient` with Google Gemini to calculate candidate-job match score percentage, matching strengths, missing skills, and personalized recommendations.
- 🔍 **Dynamic Multi-Criteria Search**: Dynamic database queries using Spring Data JPA `Specification` (Criteria API) for filtering jobs by keyword, location, employment type, and skills with pagination & sorting.
- 💼 **Recruiter & Candidate Workflows**: End-to-end job posting lifecycle, candidate application submission with duplicate protection (`@Transactional`), and recruiter status management.
- 🛡️ **Enterprise Best Practices**: Declarative Bean Validation (`jakarta.validation`), Centralized RFC 7807 compliant error handling (`@RestControllerAdvice`), JPA Auditing (`@CreatedDate`, `@LastModifiedDate`), and 12-factor `.env` secret management.
- 📖 **Interactive API Documentation**: Live Swagger UI & OpenAPI 3.0 with integrated JWT Bearer authentication support.

---

## 🏛️ System Architecture

```
                       CLIENT (Web / Mobile / Postman)
                                      |
                                      ↓ [HTTP / HTTPS]
                       +-------------------------------+
                       |     SPRING BOOT MONOLITH      |
                       +-------------------------------+
                                      |
       +--------------+---------------+---------------+---------------+
       ↓              ↓               ↓               ↓               ↓
  [Auth Mod]     [User Mod]      [Job Mod]     [Resume Mod]       [AI Mod]
       |              |               |               |               |
       |              ↓               |               ↓               ↓
  (JWT Filter) (Profile Mgmt)   (Criteria API) (PDF/DOCX Parser) (Gemini Client)
       |              |               |               |               |
       +--------------+---------------+---------------+---------------+
                                      |
                                      ↓
                            [SERVICE LAYER (@Transactional)]
                                      |
                                      ↓
                           [SPRING DATA JPA REPOSITORIES]
                                      |
                                      ↓
                               MySQL Database
```

---

## 📂 Project Package Structure

```
com.jobportal
├── JobPortalApiApplication.java       # Main entrypoint with @EnableJpaAuditing
│
├── common                             # Shared domain layer
│   ├── config/                        # Global configs (Security, CORS, OpenAPI)
│   ├── dto/                           # Standard API response wrappers & Error responses
│   ├── entity/                        # Audited BaseEntity (id, createdAt, updatedAt)
│   └── exception/                     # Global @RestControllerAdvice & Custom Exceptions
│
├── auth                               # Authentication & Authorization module
│   ├── controller/                    # /api/auth/register, /api/auth/login
│   ├── dto/                           # AuthRequest, AuthResponse, RegisterRequest
│   ├── security/                      # JwtService, JwtFilter, CustomUserDetailsService
│   └── service/                       # AuthService
│
├── user                               # User & Candidate/Recruiter Profile module
│   ├── controller/                    # /api/users/me, /api/users/me/profile
│   ├── dto/                           # ProfileRequest, ProfileResponse
│   ├── entity/                        # User, Profile (@OneToOne)
│   ├── repository/                    # UserRepository, ProfileRepository
│   └── service/                       # UserService, ProfileService
│
├── resume                             # Document management & Text extraction
│   ├── controller/                    # /api/resumes/upload, /api/resumes/{id}
│   ├── entity/                        # Resume entity
│   ├── parser/                        # Strategy Pattern: PDFBox & Apache POI parsers
│   └── service/                       # ResumeService, FileStorageService
│
├── job                                # Job posting & Dynamic filtering module
│   ├── controller/                    # Public & Recruiter Job REST APIs
│   ├── entity/                        # Job entity, EmploymentType, JobStatus
│   ├── specification/                 # Dynamic JPA Criteria Specifications
│   └── service/                       # JobService
│
├── application                        # Job Application workflow & Tracking
│   ├── controller/                    # Apply, Withdraw, Review, Update Status APIs
│   ├── entity/                        # JobApplication, ApplicationStatus
│   └── service/                       # ApplicationService (@Transactional)
│
└── ai                                 # LLM Job Matching & Scoring
    ├── client/                        # Spring RestClient Gemini API integration
    ├── dto/                           # AI Match Request/Response & Prompts
    └── service/                       # AIService
```

---

## 🛠️ Tech Stack

| Component | Technology | Version | Purpose |
|---|---|---|---|
| **Language** | Java | 17 / 21+ | Core programming language |
| **Framework** | Spring Boot | 3.x / 4.x | REST API & Inversion of Control |
| **Security** | Spring Security + JJWT | 6.x / 0.12.6 | Stateless JWT Authentication & RBAC |
| **Database** | MySQL | 8.x / 9.x | Relational database persistence |
| **ORM** | Hibernate / Spring Data JPA | 6.x+ | Entity-relational mapping & Auditing |
| **Document Parsing** | Apache PDFBox & Apache POI | 3.0.3 / 5.3.0 | PDF & DOCX text extraction |
| **AI Integration** | Google Gemini 1.5 Flash | REST API | Candidate-job match analysis & feedback |
| **Documentation** | Springdoc OpenAPI | 2.6.0 | Swagger UI interactive documentation |
| **Build Tool** | Apache Maven | 3.9+ | Build lifecycle & dependency management |

---

## 🚀 Getting Started & Local Setup

### 1. Prerequisites
- **Java 17+** (JDK 17, 21 or 25 LTS installed)
- **MySQL 8.0+** running locally on port `3306`
- **Maven 3.8+** (or use included `./mvnw` wrapper)
- **Git**

### 2. Clone the Repository
```bash
git clone https://github.com/lalwaniyash/Job-portal-api.git
cd Job-portal-api/job-portal-api
```

### 3. Create the MySQL Database
Log in to MySQL terminal or MySQL Workbench:
```sql
CREATE DATABASE IF NOT EXISTS job_portal_db;
```

### 4. Configure Environment Variables (`.env`)
Create a `.env` file in the `job-portal-api/` directory (or copy from `.env.example`):
```bash
cp .env.example .env
```
Edit `.env` with your actual database and API credentials:
```env
# Database Configuration
DB_USERNAME=root
DB_PASSWORD=your_mysql_password

# JWT Authentication Configuration (Generate with: openssl rand -hex 32)
JWT_SECRET=your_256_bit_hex_jwt_secret_key_here
JWT_EXPIRATION_MS=86400000

# File Upload Directory
UPLOAD_DIR=./uploads/resumes

# Google Gemini AI API Key
GEMINI_API_KEY=your_google_gemini_api_key
```

### 5. Build and Run the Application
```bash
# Using Maven Wrapper (macOS / Linux)
./mvnw clean spring-boot:run

# Using Maven Wrapper (Windows)
mvnw.cmd clean spring-boot:run
```

The application will start on: `http://localhost:8080`

---

## 📑 API Documentation & Swagger UI

Once the application is running, open your browser to explore and test all REST endpoints interactively:

👉 **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)  
👉 **OpenAPI JSON Spec**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 📋 Key REST API Endpoints Overview

| Module | Method | Endpoint | Access | Description |
|---|---|---|---|---|
| **Auth** | `POST` | `/api/auth/register` | Public | Register a new user (`CANDIDATE` or `RECRUITER`) |
| **Auth** | `POST` | `/api/auth/login` | Public | Authenticate and obtain JWT Bearer token |
| **User** | `GET` | `/api/users/me` | Authenticated | Get current authenticated user profile |
| **Profile** | `POST` | `/api/users/me/profile` | Candidate/Recruiter | Create or update user profile details |
| **Resume** | `POST` | `/api/resumes/upload` | Candidate | Upload PDF/DOCX resume & extract text |
| **Resume** | `GET` | `/api/resumes/{id}` | Candidate / Recruiter | View resume details and extracted text |
| **Job** | `GET` | `/api/jobs` | Public | Paginated job search with dynamic filters |
| **Job** | `POST` | `/api/jobs` | Recruiter | Post a new job vacancy |
| **Job** | `PUT` | `/api/jobs/{id}` | Recruiter (Owner) | Update job vacancy details |
| **Application** | `POST` | `/api/applications/apply` | Candidate | Apply for a job with uploaded resume |
| **Application** | `GET` | `/api/applications/my` | Candidate | View all submitted job applications |
| **Application** | `PATCH`| `/api/applications/{id}/status`| Recruiter | Update application status (`ACCEPTED`, `REJECTED`, etc.) |
| **AI Matching** | `POST` | `/api/ai/job-match` | Authenticated | Trigger Gemini AI match scoring & analysis |

---

## 👤 Author

- **Yash Lalwani** — [GitHub (@lalwaniyash)](https://github.com/lalwaniyash)
