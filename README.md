Job Tracker Application V2 (WIP)

A full-stack job tracking application designed to help you manage your job search efficiently — from applications to interviews, contacts, notes, and documents — all in one place.

 **This project is still a Work In Progress (WIP).**
> Features, structure, and APIs may change as development continues.

<img width="1920" height="1258" alt="screencapture-localhost-5173-companies-2026-04-26-11_17_34" src="https://github.com/user-attachments/assets/9b48733b-4b1c-40ac-8e6e-b8f0d9b7d508" />
<img width="1920" height="991" alt="screencapture-localhost-5173-contact-persons-2026-04-26-11_17_40" src="https://github.com/user-attachments/assets/d1de2386-ddbd-4c3c-b7e2-e1451d46a517" />
<img width="1920" height="991" alt="screencapture-localhost-5173-documents-2026-04-26-11_17_50" src="https://github.com/user-attachments/assets/5b08d7e6-e6ed-4f91-b8e0-a265a4ca78e7" />
<img width="1920" height="1920" alt="screencapture-localhost-5173-resume-cv-job-matches-2026-04-26-11_17_59" src="https://github.com/user-attachments/assets/93a8b24d-d7ab-4244-9743-94255964932b" />
<img width="1920" height="1042" alt="screencapture-localhost-5173-2026-04-26-11_16_28" src="https://github.com/user-attachments/assets/d80447b4-4508-455b-a055-332fdb9ce3f8" />
<img width="1920" height="991" alt="screencapture-localhost-5173-2026-04-26-11_16_39" src="https://github.com/user-attachments/assets/8690a56d-0e66-43d9-b3ad-8af90eff630d" />
<img width="1920" height="991" alt="screencapture-localhost-5173-job-applications-2026-04-26-11_16_52" src="https://github.com/user-attachments/assets/6efeb866-5455-43db-906e-7e6f0d0d0bed" />
<img width="1920" height="1267" alt="screencapture-localhost-5173-job-applications-4-2026-04-26-11_17_06" src="https://github.com/user-attachments/assets/ee53276c-d314-4da3-91ed-64005601cb39" />
<img width="1905" height="1127" alt="screencapture-localhost-5173-job-applications-4-2026-04-26-11_17_19" src="https://github.com/user-attachments/assets/4dd3cbb4-2bdf-4693-9fbc-2eaf07a168cf" />


### Overview

This application helps you organize and track your job search lifecycle with structured data and insightful dashboards.

Instead of juggling spreadsheets or notes, this app gives you a centralized system to:

* Track job applications
* Monitor progress and response rates
* Manage interviews and contacts
* Store documents like CVs/resumes
* Keep notes for each opportunity

---

### Features

### Dashboard

* View job application statistics:

  * Total applications
  * Active applications
  * Response rate
  * Applications needing follow-up
* Quick overview of your job search progress

---

### Job Applications

* Core entity of the system
* Required fields:

  * Title, Job Source, Status, Type, Work Mode, Priority
  * Dates (Applied, Posted, Deadline)
  * Company
  
* Optional fields:

  * Department
  * Salary range (min/max)
  * Currency
  * Job URL

---

### Interviews

* Track interview stages per job application
* Includes:

  * Interviewer details
  * Date & duration
  * Interview type & stage
  * Location or meeting link
  * Notes

---

###  Contact Persons

* Multiple contacts per job application
* Store:

  * Full name
  * Email
  * Phone number
  * LinkedIn URL
  * Role
  * Notes

---

###  Notes

* Add comments or observations per job application
* Useful for:

  * Interview feedback
  * Reminders
  * Personal insights

---

###  Documents

* Upload and manage:

  * Resume / CV
* Features:

  * Versioning support
  * Replace existing or add new documents

---


##  Getting Started (Docker)

### 1. Clone the repository

```bash
git clone https://github.com/tamani-percy/you_jobo.git
cd job-tracker
```

---

### 2. Run the application using Docker Compose

```bash
docker compose up --build
```

This will:

* Spin up MySQL
* Build and run the Spring Boot backend
* Build and serve the Vue frontend

---

### 3. Access the application

* Frontend: `http://localhost:5173`
* Backend API: `http://localhost:8080`

---

### 4. AI-Powered Insights

The application includes AI-assisted features designed to help users make better decisions during their job search.

#### Resume/CV & Job Match

* Analyze how well a resume/CV matches a specific job application
* Generate a match score
* Identify candidate strengths
* Highlight missing skills or gaps
* Suggest keywords to improve resume tailoring

#### Interview Pattern Analysis

* Analyze interview notes across multiple interview rounds
* Detect repeated strengths and weaknesses
* Identify recurring skill gaps
* Surface behavioral or communication patterns
* Recommend focus areas for future interviews

#### Pre-Interview Preparation

* Generate likely interview questions based on:

  * Job title
  * Company
  * Job description
  * Interview stage
  * Interview type
  * Resume/CV content

* Provide role-specific preparation tips
* Suggest technical topics to revise
* Generate confidence and communication tips

#### Post-Interview Reflection

* Analyze personal post-interview notes
* Identify what went well
* Highlight weaknesses and missed opportunities
* Suggest concrete next steps
* Help users improve after each interview

#### Job Application Notes Analysis

* Analyze job application notes written by the user
* Extract strengths, concerns, next steps, and patterns
* Summarize unstructured notes into clear insights
* Help users track lessons learned per opportunity

#### Daily Focus

* Generate a prioritized daily focus list
* Rank job applications using:

  * Priority
  * Date applied
  * AI match score
  * Application status

* Recommend which jobs deserve attention today
* Help users decide where to focus follow-ups, preparation, or resume tailoring

## Known Limitations

* Some features may be incomplete or unstable
* APIs and database schema may change
* UI/UX is still evolving

---

## Contributing

Contributions, ideas, and feedback are welcome!

---

## License

This project is currently unlicensed (add a license if needed).
