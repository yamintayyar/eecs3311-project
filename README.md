# EECS 3311 Project 

## Setup Instructions

### Prerequisites
- Java 17+
- Gemini API Key
---

### To run the Backend

```bash
cd backend/servicebooking
mvnw clean install
mvnw spring-boot:run
```

Test if the backend endpoints are working correctly by running the project then go to the url `http://localhost:8080/hello` in your browser.

--- 

### To run the Frontend

Make sure to install the dependencies first by running the following command:

```bash (in the frontend directory)
npm install
```

Then run the following command:

```bash (in the frontend directory)
npm run dev
```

Test if the frontend works by going to `http://localhost:5173/`.

--- 

### To run the chatbot, you must provide an API key.
1. Create a '.env.properties' file in the root directory similiar to '.env.example'
2. Place your Gemini API key in here.

## Contributions

- Yamin: Use Case Diagram, Class Diagram, Model(all classes), Client Frontend view 
- Emmanuel: SpringBoot initialization
- Fahmid: Class Diagram, Controllers(Payment, Booking)
- Hasan: Class Diagram, Model(Booking, Service)
---

## Phase 1 Submission

GitHub Repository URL: https://github.com/yamintayyar/eecs3311-project

- I confirm that the repository is public and accessible.
- I understand that the state of the **main** branch at the time of submission will be considered the official Phase 1 submission.
