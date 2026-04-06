# EECS 3311 Project 

## Setup Instructions

### Prerequisites
- Java 21+
- Maven
- Docker Desktop
- Gemini API Key

---

## Running with Docker (Recommended)

### 1. Create your `.env` file in the project root
Create a file named exactly `.env` (not `.env.properties`) with the following contents:
```env
DB_NAME=servicebooking
DB_USER=appuser
DB_PASSWORD=apppassword
GEMINI_API_KEY=your_gemini_api_key_here
```

### 2. Start all services with a single command
```bash
docker-compose up --build
```

This will start:
| Service  | URL                         |
|----------|-----------------------------|
| Frontend | http://localhost:3000       |
| Backend  | http://localhost:8080       |
| Database | localhost:5432              |

### 3. To stop all services
```bash
docker-compose down
```

To stop and also delete the database volume (full reset):
```bash
docker-compose down -v
```

---

## Running Locally (Without Docker)

### Prerequisites
- Ensure you have a `.env.properties` file in `backend/servicebooking/` with your Gemini API key:
```properties
GEMINI_API_KEY=your_gemini_api_key_here
```

### To run the Backend
```bash
cd backend/servicebooking
mvnw clean install
mvnw spring-boot:run
```

Test if the backend is working by visiting `http://localhost:8080/hello` in your browser.

### To run the Frontend
```bash
cd frontend
npm install
npm run dev
```

Test if the frontend works by visiting `http://localhost:5173/`.

---

## Contributions

- Yamin: Use Case Diagram, Class Diagram, Model (all classes)
- Emmanuel: SpringBoot initialization, Admin use cases, Database & Docker setup
- Fahmid: Class Diagram, Controllers (Payment, Booking)
- Hasan: Class Diagram, Model (Booking, Service)

---

## Phase 1 Submission

GitHub Repository URL: https://github.com/yamintayyar/eecs3311-project

- I confirm that the repository is public and accessible.
- I understand that the state of the **main** branch at the time of submission will be considered the official Phase 1 submission.