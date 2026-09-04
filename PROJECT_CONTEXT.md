# 🎯 AI-Powered Job Portal — Project Context & Learning Tracker

> **Workspace**: `/Users/yash/Documents/Personal/Java/Project`  
> **Primary Objective**: Build a production-grade, modular monolith AI Job Portal while mastering Spring Boot, Spring Security, JPA/Hibernate, clean architecture, and external LLM integrations.

---

## 📌 1. Project Overview & Architecture

### High-Level Summary
- **App Type**: Modular Monolith Backend REST API
- **Tech Stack**:
  - **Language**: Java 17/21+ (Environment: OpenJDK 25 LTS)
  - **Framework**: Spring Boot 3.x (Spring Web MVC, Spring Data JPA, Spring Security 6)
  - **Database**: MySQL 8.x/9.x (Hibernate ORM)
  - **Security**: Stateless JWT Authentication + Role-Based Access Control (RBAC)
  - **Document Parsing**: Apache PDFBox / Apache POI for PDF & DOCX resume text extraction
  - **AI Integration**: Spring `RestClient` with External LLM API (Google Gemini / OpenAI)
  - **Validation & Docs**: Bean Validation (`jakarta.validation`), Springdoc OpenAPI (Swagger UI)
  - **Testing**: JUnit 5, Mockito, MockMvc

---

## 🧠 2. Core Spring Boot Concepts to Master (Learning Objectives)

During this project, we will cover every foundational and advanced concept:

| Phase | Spring Boot / Backend Concepts Mastered |
|---|---|
| **Phase 1** | Maven multi-dependency management, Spring Boot Auto-configuration, `application.yml` profiles, JPA connection pooling (HikariCP), `@Entity` & `BaseEntity` JPA auditing. |
| **Phase 2** | Spring Security 6 Filter Chain, DelegatingFilterProxy, Custom `OncePerRequestFilter`, Stateless Session Management, JWT Claims & Signatures, `BCryptPasswordEncoder`, `UserDetailsService`. |
| **Phase 3** | `@OneToOne` & `@ManyToOne` JPA Relationships, FetchType (LAZY vs EAGER), DTO pattern vs Entity leaking, `@AuthenticationPrincipal` for current user extraction. |
| **Phase 4** | Multipart file handling (`MultipartFile`), File stream management, Strategy Design Pattern in Spring (`DocumentParserFactory`), Custom File Storage Service. |
| **Phase 5** | Spring Data JPA Specifications (`JpaSpecificationExecutor`), Dynamic Criteria Builder queries, Pagination & Sorting (`Pageable`, `Page<T>`), Domain Enums in JPA. |
| **Phase 6** | `@Transactional` propagation and isolation, database unique constraints, atomic state transitions, complex domain validation. |
| **Phase 7** | Spring 3.x `RestClient` / `WebClient`, HTTP client timeouts and connection pools, Structured Prompt Engineering, Resilient LLM failure handling. |
| **Phase 8** | Declarative validation (`@Valid`, custom validators), `@RestControllerAdvice` & `@ExceptionHandler`, centralized RFC 7807 compliant error format, SLF4J structured logging. |
| **Phase 9** | Springdoc OpenAPI / Swagger annotations, `@WebMvcTest` (slice testing), `@SpringBootTest` (full integration test), Mockito unit tests (`@Mock`, `@InjectMocks`). |
| **Phase 10** | Environment variable management, 12-factor app design, production logging, performance optimizations (indexes, N+1 query prevention). |

---

## 🚦 3. Implementation Progress Tracker

### 📊 Overall Status
- **Current Phase**: **Phase 2 — Authentication & Authorization (Spring Security 6 + JWT) [Next Up]**
- **Completion**: `[█░░░░░░░░░] 10%`

---

### Detailed Phase Checklist

- [x] **Phase 1: Project Setup & Database Configuration**
  - [x] Initialize Spring Boot 3.x Maven project (`pom.xml`)
  - [x] Configure `application.yml` and `.env` (MySQL Datasource, JPA, Hibernate, JWT, LLM config)
  - [x] Set up project package structure (`com.jobportal.*`)
  - [x] Create `BaseEntity` with JPA auditing (`@CreatedDate`, `@LastModifiedDate`)
  - [x] Verify database connectivity and server startup (`mvn clean spring-boot:run`)

- [ ] **Phase 2: Authentication & Authorization (Spring Security 6 + JWT)**
  - [ ] Create `Role` enum (`ROLE_CANDIDATE`, `ROLE_RECRUITER`, `ROLE_ADMIN`)
  - [ ] Create `User` entity and `UserRepository`
  - [ ] Configure `BCryptPasswordEncoder` & `CustomUserDetailsService`
  - [ ] Implement `JwtService` (token creation, validation, claim extraction)
  - [ ] Implement `JwtAuthenticationFilter`
  - [ ] Configure `SecurityFilterChain` in `SecurityConfig`
  - [ ] Build `AuthService` and `AuthController` (`/api/auth/register`, `/api/auth/login`)
  - [ ] Verify JWT issue and protected route access with cURL / Postman

- [ ] **Phase 3: User & Profile Management**
  - [ ] Create `Profile` entity (`@OneToOne` with User)
  - [ ] Implement `ProfileRepository` and `ProfileService`
  - [ ] Build `UserController` & `ProfileController` (`/api/users/me`, `/api/users/me/profile`)
  - [ ] Verify profile creation & update flows

- [ ] **Phase 4: Resume Management & Document Parsing**
  - [ ] Build `FileStorageService` (upload validation, sanitization, disk storage)
  - [ ] Implement `DocumentParser` Strategy (PDFBox for PDF, POI for DOCX)
  - [ ] Create `Resume` entity & `ResumeRepository`
  - [ ] Implement `ResumeService` & `ResumeController` (`POST /api/resumes`, `GET /api/resumes/{id}`)
  - [ ] Verify file upload & plain-text extraction

- [ ] **Phase 5: Job Posting & Advanced Search Module**
  - [ ] Create `EmploymentType`, `JobStatus` enums
  - [ ] Create `Job` entity & `JobRepository`
  - [ ] Implement `JobSpecification` for dynamic multi-criteria filtering
  - [ ] Build `JobService` and `JobController` (Recruiter CRUD + Public paginated search)
  - [ ] Test filtering by keyword, location, skills, pagination & sorting

- [ ] **Phase 6: Application Workflow & Tracking**
  - [ ] Create `ApplicationStatus` enum
  - [ ] Create `JobApplication` entity with composite unique key constraint
  - [ ] Implement `ApplicationService` with atomic business rules (`@Transactional`)
  - [ ] Build `ApplicationController` (Candidate apply/withdraw + Recruiter review/status update)
  - [ ] Test status transitions and duplicate prevention

- [ ] **Phase 7: AI Job Matching & LLM Integration**
  - [ ] Create `LLMClient` interface and Spring `RestClient` implementation
  - [ ] Build `PromptBuilder` for structured prompt generation
  - [ ] Implement `AIService` (fetch resume + job -> call LLM -> parse JSON)
  - [ ] Build `AIController` (`POST /api/ai/job-match`)
  - [ ] Test AI matching and error resilience

- [ ] **Phase 8: Validation, Global Exception Handling & Logging**
  - [ ] Add `@Valid` annotations and constraints on all DTOs
  - [ ] Create custom business exceptions (`ResourceNotFoundException`, `AIServiceException`, etc.)
  - [ ] Build `@RestControllerAdvice` for standard error JSON formatting
  - [ ] Add SLF4J structured logging for key lifecycle events

- [ ] **Phase 9: API Documentation & Automated Testing**
  - [ ] Configure Springdoc OpenAPI (Swagger UI with Bearer JWT support)
  - [ ] Write Unit Tests with JUnit 5 & Mockito (`AuthServiceTest`, `JobServiceTest`, etc.)
  - [ ] Write Integration Tests with MockMvc (`@SpringBootTest`)
  - [ ] Verify test suite passes (`mvn test`)

- [ ] **Phase 10: Production Readiness & System Design Notes**
  - [ ] Environment variable secret management (`.env` / system env)
  - [ ] Create `README.md` with setup guide, API guide, architecture diagrams
  - [ ] Document System Design & Interview talking points

---

## 🏛️ 4. Architecture Decisions Log (ADR)

1. **Modular Monolith vs Microservices**:
   - *Decision*: Build a single Spring Boot application divided cleanly into domain packages (`auth`, `user`, `resume`, `job`, `application`, `ai`).
   - *Rationale*: Avoids distributed systems complexity (network latency, distributed transactions, service discovery) while maintaining clean separation of concerns and high maintainability.

2. **Stateless JWT Security**:
   - *Decision*: Stateless JWT authentication using Spring Security 6 `SecurityFilterChain` and `SessionCreationPolicy.STATELESS`.
   - *Rationale*: Scalable, avoids server-side session state, standard for REST APIs.

3. **Resume Text Extraction Before LLM**:
   - *Decision*: Extract clean text from PDF/DOCX files locally using Apache PDFBox/POI and persist `extractedText` in MySQL before calling LLM.
   - *Rationale*: Drastically lowers token usage, avoids uploading heavy binary files to LLM endpoints, and allows fast text reuse across multiple job applications.

---

## 📝 5. Session Log & Next Steps

| Date | Phase / Milestone | Notes |
|---|---|---|
| *Start* | Initialization | Created `PROJECT_CONTEXT.md`, verified Java 25 & Maven 3.9 & MySQL. Ready to initialize Spring Boot application. |
