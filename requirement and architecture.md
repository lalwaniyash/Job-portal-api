============================================================
AI-POWERED JOB PORTAL
PROJECT REQUIREMENTS & ARCHITECTURE
============================================================

PROJECT TYPE
------------
Monolithic Backend Application

Primary Technology:
- Java
- Spring Boot
- Spring MVC / REST
- Spring Data JPA / Hibernate
- MySQL
- Spring Security
- JWT Authentication
- Bean Validation
- Global Exception Handling
- Logging
- Swagger / OpenAPI
- Unit & Integration Testing
- AI / LLM API Integration

Architecture:
- Modular Monolith
- REST-based backend
- Single Spring Boot application
- Single relational database
- External LLM API for AI functionality

IMPORTANT:
------------
We are NOT using API Gateway, Microservices, Kafka, Redis,
Vector Database, Docker/Kubernetes etc. in the initial version.

Those can be introduced later as an architectural evolution.


============================================================
1. PROJECT OBJECTIVE
   ============================================================

Build a production-style Job Portal where:

1. Users can register and login.
2. Users can maintain their profile.
3. Users can upload and manage resumes.
4. Recruiters can create and manage jobs.
5. Users can search and view jobs.
6. Users can apply for jobs.
7. Users can track their applications.
8. Recruiters can view applications for their jobs.
9. The system can use AI to compare a candidate's resume
   with a job description.
10. AI can provide:
    - Match Score
    - Matching Skills
    - Missing Skills
    - Experience Match
    - AI Recommendation

The goal is not only to create CRUD APIs.

The project should demonstrate:
- OOP
- SOLID
- Clean separation of responsibilities
- REST API design
- Database design
- Authentication & Authorization
- Exception handling
- Validation
- Logging
- File handling
- External API integration
- AI integration
- Testing
- Production-style backend development


============================================================
2. HIGH-LEVEL ARCHITECTURE
   ============================================================

                    WEB / MOBILE CLIENT
                           |
                           | HTTP / HTTPS
                           ↓
              +-------------------------+
              |     SPRING BOOT APP     |
              |       MONOLITH          |
              +-------------------------+
                           |
       +-------------------+--------------------+
       |                   |                    |
       ↓                   ↓                    ↓
   USER MODULE         JOB MODULE        APPLICATION MODULE
   |                   |                    |
   ↓                   ↓                    ↓
   RESUME MODULE      JOB SEARCH          APPLICATION STATUS
   |                                        |
   +-------------------+--------------------+
   |
   ↓
   SERVICE LAYER
   |
   +------------+-------------+
   |                          |
   ↓                          ↓
   MySQL Database             AI INTEGRATION
   |
   ↓
   LLM API
   |
   ↓
   AI Response


============================================================
3. MODULAR MONOLITH STRUCTURE
   ============================================================

Spring Boot Application
|
+-- auth
|
+-- user
|
+-- resume
|
+-- job
|
+-- application
|
+-- ai
|
+-- common
|
+-- exception
|
+-- security


Each module should have clear responsibilities.

Example:

User Module
-----------
UserController
UserService
UserRepository
UserEntity
UserDTO


Job Module
----------
JobController
JobService
JobRepository
JobEntity
JobDTO


Application Module
------------------
ApplicationController
ApplicationService
ApplicationRepository
ApplicationEntity
ApplicationDTO


Resume Module
-------------
ResumeController
ResumeService
ResumeRepository
ResumeEntity
ResumeDTO
ResumeFileParser


AI Module
---------
AIController
AIService
LLMClient
ResumeJobMatcher
AIResponseDTO


============================================================
4. USER MODULE
   ============================================================

Purpose:
Manage users, authentication-related user information,
profile information and user roles.

User Types:
- CANDIDATE
- RECRUITER

User Entity should contain concepts such as:

- id
- name
- email
- password
- phone
- role
- createdAt
- updatedAt

Password:
- NEVER store plain-text passwords.
- Password must be hashed.

Authentication:
- JWT based authentication.

APIs:

POST   /api/auth/register
POST   /api/auth/login
POST   /api/auth/logout

GET    /api/users/me
PUT    /api/users/me
DELETE /api/users/me

The authenticated user should be identified from
the JWT instead of trusting userId sent by the client.


============================================================
5. AUTHENTICATION & AUTHORIZATION
   ============================================================

Authentication:
----------------
Verify:

"Who is the user?"

Flow:

Client
|
| email + password
↓
POST /api/auth/login
|
↓
Spring Security
|
↓
Validate credentials
|
↓
Generate JWT
|
↓
Client receives JWT


For protected APIs:

Client
|
| Authorization: Bearer <JWT>
↓
Spring Security Filter
|
↓
Validate JWT
|
↓
Identify User + Role
|
↓
Controller


Authorization:
--------------

Candidate:
- Manage own profile
- Manage own resume
- View jobs
- Apply for jobs
- View own applications
- Use AI resume-job matching

Recruiter:
- Create jobs
- Update own jobs
- Delete/close own jobs
- View applications for own jobs
- View candidate information relevant to applications


============================================================
6. PROFILE MODULE
   ============================================================

Candidate profile may contain:

- Full Name
- Phone
- Location
- Bio
- Skills
- Education
- Experience
- LinkedIn URL
- GitHub URL
- Portfolio URL

Profile should be associated with a User.

Relationship:

User
|
| 1 : 1
↓
Profile


============================================================
7. RESUME MODULE
   ============================================================

Purpose:
Allow candidates to upload and manage resumes.

Requirements:

1. Candidate can upload resume.
2. Candidate can view resume metadata.
3. Candidate can update/replace resume.
4. Candidate can delete resume.
5. Candidate can have one or multiple resumes
   depending on final business decision.
6. Resume should belong to a candidate.
7. Resume file should NOT be stored directly inside
   normal database columns as raw content initially.

Resume metadata can contain:

- id
- userId
- fileName
- fileType
- filePath / storageReference
- extractedText
- createdAt
- updatedAt

Supported initial formats:
- PDF
- DOCX

Resume Processing:

Resume File
|
↓
File Validation
|
↓
Text Extraction
|
↓
Extracted Resume Text
|
↓
Stored / Used for AI analysis


IMPORTANT:
The LLM should ideally receive meaningful text rather than
blindly receiving a binary PDF file.

Therefore:

PDF/DOCX
↓
Text Extraction
↓
Resume Text
↓
LLM


============================================================
8. JOB MODULE
   ============================================================

Purpose:
Recruiters create jobs and candidates discover jobs.

Job Entity:

- id
- recruiterId
- title
- description
- companyName
- location
- employmentType
- experienceRequired
- salaryMin
- salaryMax
- skillsRequired
- status
- createdAt
- updatedAt

Possible employment types:

- FULL_TIME
- PART_TIME
- CONTRACT
- INTERNSHIP

Possible status:

- OPEN
- CLOSED
- DRAFT

Recruiter APIs:

POST   /api/jobs
GET    /api/jobs/{id}
PUT    /api/jobs/{id}
DELETE /api/jobs/{id}

Candidate/Public APIs:

GET    /api/jobs
GET    /api/jobs/{id}

Search:

GET /api/jobs?keyword=java
GET /api/jobs?location=Chennai
GET /api/jobs?skill=Spring Boot

Later:

- Pagination
- Sorting
- Filtering
- Multiple skill filtering


============================================================
9. APPLICATION MODULE
   ============================================================

Purpose:
Allow candidates to apply for jobs and track applications.

Application Entity:

- id
- jobId
- candidateId
- resumeId
- status
- appliedAt
- updatedAt

Possible application statuses:

- APPLIED
- UNDER_REVIEW
- SHORTLISTED
- REJECTED
- HIRED
- WITHDRAWN

Relationship:

User/Candidate
|
| 1 : N
↓
Application
|
| N : 1
↓
Job


Basic flow:

Candidate
|
| Apply
↓
POST /api/jobs/{jobId}/applications
|
↓
Create Application
|
↓
Save in MySQL


Candidate APIs:

POST /api/jobs/{jobId}/applications
GET  /api/applications/me
GET  /api/applications/{id}
PATCH /api/applications/{id}/withdraw


Recruiter APIs:

GET   /api/recruiter/applications
GET   /api/jobs/{jobId}/applications
PATCH /api/applications/{id}/status


Business Rules:

- Only authenticated candidates can apply.
- Candidate cannot apply to the same job multiple times.
- Candidate must have a valid resume if resume is required.
- Candidate cannot apply to a closed job.
- Recruiter cannot modify another recruiter's job/application.
- Application status changes should follow valid business rules.


============================================================
10. AI MODULE
    ============================================================

Purpose:
Provide intelligent resume-to-job matching.

Initial AI feature:

AI Resume ↔ Job Description Matching


Input:

1. Candidate Resume Text
2. Job Description

Flow:

Candidate
|
| Select Resume + Job
↓
Spring Boot
|
+---- Get Resume
|
+---- Get Job
|
+---- Extract Resume Text
|
↓
AI Service
|
↓
LLM API
|
↓
Structured AI Response
|
↓
Spring Boot
|
↓
Client


============================================================
11. AI MATCHING API
    ============================================================

Example endpoint:

POST /api/ai/job-match


Request:

{
"resumeId": 10,
"jobId": 25
}


Spring Boot internally performs:

1. Authenticate user.
2. Verify resume belongs to user.
3. Fetch resume.
4. Fetch job.
5. Extract/get resume text.
6. Get job description.
7. Construct AI prompt.
8. Send request to LLM API.
9. Receive AI response.
10. Validate AI response.
11. Convert response into DTO.
12. Return response to frontend.


Example Response:

{
"matchScore": 82,
"matchingSkills": [
"Java",
"Spring Boot",
"REST API",
"MySQL"
],
"missingSkills": [
"Kafka",
"AWS"
],
"experienceMatch": true,
"recommendation": "Good match for this position."
}


============================================================
12. AI PROMPT RESPONSIBILITY
    ============================================================

The AI should NOT make arbitrary decisions.

We will give it a clear structured instruction.

Conceptually:

SYSTEM:
You are a job matching assistant.

USER:
Analyze the candidate resume against the job description.

Resume:
<resume text>

Job Description:
<job description>

Return:
- Match score from 0 to 100
- Matching skills
- Missing skills
- Experience match
- Short recommendation

Return the result in the required JSON structure.


IMPORTANT:
AI output should be treated as a recommendation.

The application should NOT blindly trust AI output for
security, authorization or critical business decisions.


============================================================
13. LLM INTEGRATION ARCHITECTURE
    ============================================================

Spring Boot
|
↓
AIService
|
↓
LLMClient
|
↓
External LLM API
|
↓
LLM Response
|
↓
AIService
|
↓
AIResponseDTO


We should NOT call the external LLM API directly
from Controller.

BAD:

Controller
↓
LLM API


GOOD:

Controller
↓
AIService
↓
LLMClient
↓
External LLM API


This keeps the application loosely coupled and testable.


============================================================
14. AI ERROR HANDLING
    ============================================================

Possible failures:

- LLM API unavailable
- Timeout
- Invalid API response
- Rate limit
- Invalid resume text
- Empty job description
- Token/input too large
- Authentication failure with LLM provider

Application should handle these gracefully.

Example:

LLM unavailable
↓
AIService catches failure
↓
Application Exception
↓
Global Exception Handler
↓
Proper HTTP response


Example:

{
"status": 503,
"message": "AI service is temporarily unavailable"
}


============================================================
15. DATABASE ARCHITECTURE
    ============================================================

Initial database:

MySQL

Main tables:

users
profiles
resumes
jobs
applications


Conceptual relationships:

users
|
+---- profiles
|
+---- resumes
|
+---- applications
|
+---- jobs


More explicitly:

USER
|
+---- 1 : 1 ---- PROFILE
|
+---- 1 : N ---- RESUME
|
+---- 1 : N ---- APPLICATION
|
N : 1
|
JOB
|
N : 1
|
RECRUITER


Foreign Keys:

profiles.user_id
resumes.user_id
jobs.recruiter_id
applications.candidate_id
applications.job_id
applications.resume_id


Important constraints:

- users.email should be UNIQUE.
- application(candidate_id, job_id) should be UNIQUE.
- Foreign key relationships should be enforced.
- Required fields should be NOT NULL where appropriate.


============================================================
16. API LAYER ARCHITECTURE
    ============================================================

Controller Layer
----------------
Responsible for:
- HTTP requests
- Request validation
- Calling services
- Returning HTTP responses

Controller should NOT contain business logic.


Service Layer
-------------
Responsible for:
- Business logic
- Transaction management
- Orchestration
- Calling repositories
- Calling external services


Repository Layer
----------------
Responsible for:
- Database access
- Queries
- Persistence


External Integration Layer
--------------------------
Responsible for:
- LLM API communication
- Future external integrations


Architecture:

Controller
↓
Service
↓
Repository
↓
Database


For AI:

Controller
↓
AIService
↓
LLMClient
↓
External LLM API


============================================================
17. DTO ARCHITECTURE
    ============================================================

Do NOT expose JPA entities directly from every API.

Use DTOs.

Example:

UserRequestDTO
UserResponseDTO

JobCreateRequest
JobResponse

ApplicationResponseDTO

ResumeResponseDTO

AIJobMatchRequest
AIJobMatchResponse


Benefits:

- API contract is independent of database entity.
- Prevent accidental exposure of sensitive fields.
- Easier validation.
- Easier future changes.


============================================================
18. VALIDATION
    ============================================================

Use Bean Validation.

Examples:

@Email
@NotBlank
@NotNull
@Size
@Pattern
@Min
@Max


Examples:

Registration:
- Valid email
- Strong password
- Name required

Job:
- Title required
- Description required
- Valid salary range

Resume:
- Valid file type
- File size limit

Application:
- Valid job
- Valid resume


============================================================
19. GLOBAL EXCEPTION HANDLING
    ============================================================

Use:

@RestControllerAdvice


Handle common exceptions:

- ResourceNotFoundException
- InvalidRequestException
- UnauthorizedException
- ForbiddenException
- DuplicateApplicationException
- InvalidFileException
- AIServiceException
- ValidationException


Standard error response:

{
"timestamp": "...",
"status": 400,
"error": "Bad Request",
"message": "...",
"path": "..."
}


============================================================
20. SECURITY REQUIREMENTS
    ============================================================

Security should include:

- Password hashing
- JWT authentication
- Role-based authorization
- Protected endpoints
- Ownership checks
- Input validation
- File upload validation
- No sensitive information in logs


Important:

Authentication:
"Who are you?"

Authorization:
"What are you allowed to do?"


Example:

Candidate cannot:

DELETE /api/jobs/10

if job 10 belongs to another recruiter.


Recruiter cannot:

GET another recruiter's private application data
unless business rules allow it.


============================================================
21. FILE UPLOAD SECURITY
    ============================================================

Resume upload must validate:

- File extension
- MIME type
- File size
- Empty file
- Filename handling

Do NOT blindly trust user-provided filenames.

The application should generate a safe storage name.

Example:

Original:
Yash_Resume.pdf

Stored internally:
UUID-generated-name.pdf


============================================================
22. LOGGING
    ============================================================

Use structured application logging.

Log:

- Application startup
- Important business events
- Authentication failures
- Application creation
- Job creation
- AI request success/failure
- External API failures
- Unexpected exceptions


DO NOT log:

- Passwords
- JWT tokens
- LLM API keys
- Sensitive resume content unnecessarily


============================================================
23. TRANSACTION MANAGEMENT
    ============================================================

Use @Transactional where business operations
require atomicity.

Example:

Applying for a job:

Validate candidate
↓
Validate job
↓
Validate resume
↓
Check duplicate application
↓
Create application
↓
Commit transaction


If a critical database operation fails,
the transaction should roll back.


============================================================
24. API DOCUMENTATION
    ============================================================

Use Swagger / OpenAPI.

Document:

- Authentication APIs
- User APIs
- Job APIs
- Resume APIs
- Application APIs
- AI APIs

For every API document:

- Endpoint
- HTTP method
- Request
- Response
- Authentication requirement
- Possible errors


============================================================
25. TESTING STRATEGY
    ============================================================

Unit Tests:

- UserService
- JobService
- ApplicationService
- ResumeService
- AIService


Integration Tests:

- Authentication
- Job creation
- Job retrieval
- Application creation
- Database interaction


Security Tests:

- Unauthorized request
- Invalid JWT
- Candidate accessing recruiter API
- Recruiter accessing candidate-only API
- Resource ownership checks


AI Tests:

Do NOT depend on the real LLM API for every test.

Mock LLMClient.

Example:

AIService
↓
Mock LLMClient
↓
Fake AI response


This makes tests:
- Faster
- Cheaper
- Deterministic


============================================================
26. IMPORTANT BUSINESS FLOWS
    ============================================================

FLOW 1 — USER REGISTRATION
--------------------------

Client
↓
POST /api/auth/register
↓
Validate request
↓
Check email
↓
Hash password
↓
Create user
↓
Save DB
↓
Return response


FLOW 2 — LOGIN
--------------

Client
↓
POST /api/auth/login
↓
Validate credentials
↓
Generate JWT
↓
Return JWT


FLOW 3 — CREATE JOB
-------------------

Recruiter
↓
POST /api/jobs
↓
JWT validation
↓
Role validation
↓
Validate request
↓
Create Job
↓
Save DB


FLOW 4 — APPLY TO JOB
---------------------

Candidate
↓
POST /api/jobs/{jobId}/applications
↓
Authenticate
↓
Verify Candidate
↓
Fetch Job
↓
Check Job is OPEN
↓
Check duplicate application
↓
Fetch Resume
↓
Create Application
↓
Save DB


FLOW 5 — AI JOB MATCHING
------------------------

Candidate
↓
Select Resume
↓
Select Job
↓
POST /api/ai/job-match
↓
Authenticate
↓
Verify Resume Ownership
↓
Fetch Resume
↓
Fetch Job
↓
Extract Resume Text if required
↓
Create AI Prompt
↓
LLM API
↓
Receive AI Response
↓
Validate/Parse Response
↓
Return Match Result


============================================================
27. INITIAL API LIST
    ============================================================

AUTH
----

POST   /api/auth/register
POST   /api/auth/login
POST   /api/auth/logout


USER / PROFILE
-------------

GET    /api/users/me
PUT    /api/users/me
DELETE /api/users/me

GET    /api/users/me/profile
PUT    /api/users/me/profile


RESUME
------

POST   /api/resumes
GET    /api/resumes
GET    /api/resumes/{id}
PUT    /api/resumes/{id}
DELETE /api/resumes/{id}


JOB
---

POST   /api/jobs
GET    /api/jobs
GET    /api/jobs/{id}
PUT    /api/jobs/{id}
DELETE /api/jobs/{id}


APPLICATION
-----------

POST   /api/jobs/{jobId}/applications

GET    /api/applications/me
GET    /api/applications/{id}

PATCH  /api/applications/{id}/withdraw

GET    /api/recruiter/applications
GET    /api/jobs/{jobId}/applications

PATCH  /api/applications/{id}/status


AI
--

POST   /api/ai/job-match


============================================================
28. PROJECT PACKAGE STRUCTURE
    ============================================================

com.jobportal
|
+-- auth
|   +-- controller
|   +-- service
|   +-- dto
|
+-- user
|   +-- controller
|   +-- service
|   +-- repository
|   +-- entity
|   +-- dto
|
+-- resume
|   +-- controller
|   +-- service
|   +-- repository
|   +-- entity
|   +-- dto
|   +-- parser
|
+-- job
|   +-- controller
|   +-- service
|   +-- repository
|   +-- entity
|   +-- dto
|
+-- application
|   +-- controller
|   +-- service
|   +-- repository
|   +-- entity
|   +-- dto
|
+-- ai
|   +-- controller
|   +-- service
|   +-- client
|   +-- dto
|
+-- security
|   +-- JwtAuthenticationFilter
|   +-- JwtService
|   +-- SecurityConfig
|
+-- exception
|   +-- GlobalExceptionHandler
|   +-- CustomExceptions
|
+-- common
+-- enums
+-- util


============================================================
29. INITIAL ARCHITECTURE DECISION
    ============================================================

We are intentionally choosing:

MODULAR MONOLITH

NOT:

Microservices


Reason:

- Easier development
- Easier debugging
- Easier deployment
- Suitable for current project scope
- Allows us to focus on Spring Boot fundamentals
- Still demonstrates good architecture


Future evolution can be:

CURRENT:

Client
↓
Spring Boot Monolith
↓
MySQL


FUTURE:

Client
↓
API Gateway
↓
User Service
Job Service
Application Service
Resume Service
AI Service
↓
Separate Databases / Messaging


But this is OUT OF INITIAL SCOPE.


============================================================
30. AI ARCHITECTURE DECISION
    ============================================================

Initial AI architecture:

Spring Boot
|
↓
AI Service
|
↓
LLM Client
|
↓
External LLM API


We are NOT initially implementing:

- Vector Database
- RAG
- Embeddings
- Fine-tuning
- AI Microservice
- Agentic workflows
- Multiple LLM providers
- Kafka-based AI processing


These may be considered later if they solve a real requirement.


============================================================
31. FUTURE AI ENHANCEMENTS
    ============================================================

After the basic AI feature works, possible additions:

1. AI Job Recommendations

Resume
↓
Extract Skills
↓
Compare against Jobs
↓
Recommended Jobs


2. AI Resume Improvement

Resume
↓
LLM
↓
Suggestions:
- Missing skills
- Weak bullet points
- Better descriptions


3. AI Cover Letter Generation

Resume + Job Description
↓
LLM
↓
Personalized Cover Letter


4. AI Career Assistant

Candidate
↓
Career Question
↓
LLM
↓
Career Guidance


5. Embedding-based Job Search

Resume / Job
↓
Embedding
↓
Vector Database
↓
Similarity Search


These are FUTURE features, not mandatory for V1.


============================================================
32. NON-FUNCTIONAL REQUIREMENTS
    ============================================================

Performance:
- Paginated job listing
- Efficient database queries
- Avoid unnecessary DB calls
- Proper indexes

Security:
- Password hashing
- JWT
- Authorization
- Input validation
- Secure file handling
- Secrets stored outside source code

Reliability:
- Global exception handling
- External API timeout handling
- Transaction management

Maintainability:
- SOLID principles
- Layered architecture
- DTOs
- Clear module boundaries
- Meaningful naming

Observability:
- Logging
- Error tracking
- Request tracing where useful

Testability:
- Dependency injection
- Mock external integrations
- Unit tests
- Integration tests


============================================================
33. WHAT THIS PROJECT WILL DEMONSTRATE ON RESUME
    ============================================================

Java
Spring Boot
REST API Development
MySQL
JPA/Hibernate
Spring Security
JWT
Role-Based Authorization
File Upload
PDF/DOCX Processing
Validation
Global Exception Handling
Logging
Swagger/OpenAPI
Unit Testing
Integration Testing
External API Integration
LLM/AI Integration
Modular Monolith Architecture
Clean Architecture Principles
SOLID Principles


============================================================
34. FINAL V1 ARCHITECTURE
    ============================================================

                         CLIENT
                           |
                           ↓
                    REST API / HTTPS
                           |
                           ↓
              +-------------------------+
              |    SPRING BOOT APP      |
              |     MODULAR MONOLITH    |
              +-------------------------+
                           |
    +----------+---------+---------+----------+
    |          |                   |          |
    ↓          ↓                   ↓          ↓
    AUTH      USER                JOB     APPLICATION
    |
    ↓
    RESUME
    |
    ↓
    TEXT EXTRACTION
    |
    ↓
    AI SERVICE
    |
    ↓
    LLM CLIENT
    |
    ↓
    EXTERNAL LLM API


                           |
                           ↓
                    SERVICE LAYER
                           |
                           ↓
                  REPOSITORY LAYER
                           |
                           ↓
                       MySQL


============================================================
35. DEVELOPMENT ORDER
    ============================================================

We should NOT build everything at once.

Phase 1:
- Spring Boot project
- Project structure
- Database configuration
- Basic entities

Phase 2:
- User registration
- Login
- JWT
- Spring Security
- Roles

Phase 3:
- Profile APIs

Phase 4:
- Resume upload
- Resume storage
- PDF/DOCX text extraction

Phase 5:
- Job CRUD
- Job search
- Filtering
- Pagination

Phase 6:
- Application APIs
- Application status
- Candidate/recruiter authorization

Phase 7:
- Validation
- Global exception handling
- Logging

Phase 8:
- Swagger
- Unit tests
- Integration tests

Phase 9:
- LLM integration
- AIService
- Resume + Job matching
- Structured AI response
- AI error handling

Phase 10:
- Improve architecture
- Refactor
- Add production-quality features
- Prepare README
- Prepare system architecture diagram
- Prepare resume description
- Prepare interview explanation


============================================================
FINAL GOAL
============================================================

Build a realistic AI-powered Job Portal using:

JAVA
+
SPRING BOOT
+
REST APIs
+
MYSQL
+
SPRING SECURITY
+
JWT
+
FILE PROCESSING
+
AI / LLM API
+
VALIDATION
+
EXCEPTION HANDLING
+
LOGGING
+
TESTING
+
MODULAR MONOLITH ARCHITECTURE


The project should be simple enough to understand deeply,
but realistic enough to discuss in a backend/system-design
interview.

Most importantly:

We will understand and implement every component ourselves.

We will NOT add a technology merely because it looks good
on a resume.