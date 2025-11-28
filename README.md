# :video_game: Video Game Play Tracker :video_game:
*A Kotlin console application for managing and tracking video game play sessions.*

---

## Project Overview

This project was developed as part of the **Software Development Tools** module.
The goal of this project is to demonstrate proficiency in:
- Programming with Kotlin  
- Applying software development tools (Git, Gradle, CI/CD, TDD)  
- Building and testing console-based applications with good coding practices  

---

## Purpose

The **Video Game Play Tracker** allows users to manage a collection of video games and record their individual play sessions.  
Features:
- Manage Video Games
  - Add, Update, List, Delete Games
  - Store Attributes Such As
    - Title
    - Genre
    - Release Year
    - Price
    - Public Rating
- Track Play Sessions
  - Add Play Sessions to Your Games
  - Store Attributes Such As:
    - Day Played
    - Session Duration
    - Personal Rating of the Session
- Save and Load Data
  - Users can Save and Load Data in **JSON or XML** Format
  - Persistence Option can be Selected at Runtime Using the Built-In Menu

---

## Tools and Technologies Used

| Tool                               | Purpose                     |
|------------------------------------|-----------------------------|
| **Kotlin**                         | Main Programming Language   |
| **Gradle**                         | Build Automation            |
| **JUnit 5**                        | Unit Testing Framework      |
| **Git & GitHub**                   | Version Control and Hosting |
| **IntelliJ**                       | IDE                         |
| **Jacoco**                         | Code Coverage Reporting     |
| **Kotlin Serialiser / XML Parser** | Data Persistence            |

---

## Testing

### ✔ Unit Tests
Full JUnit5 test coverage for:

- Utility functions
    - Range validation
    - Positive-number validation
    - Enum validation (genres + days)
- GameAPI
- PlaySessionAPI

### ✔ Input Validation System
The project includes a complete validation suite:

- `validIntRange`, `validDoubleRange`
- Positive number validation
- Rating validation (0.1–10)
- Enum validation for:
    - Genres
    - Days of the week

All validated with TDD.

---

## Running the Application  

### Clone the Repository
```bash
git clone https://github.com/your-username/sw-sdt-assignment.git
cd sw-sdt-assignment
