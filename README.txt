# CRUD Application

# Employee CRUD Application

A Spring Boot application that performs basic CRUD operations using PostgreSQL.

## Features
- Create Employee
- Read Employee Details
- Update Employee
- Delete Employee

## Tech Stack
- Java
- Spring Boot
- Maven
- PostgreSQL

## Project Structure
controller → Handles REST APIs  
service → Business logic  
repository → Database interaction  
model → Entity classes

## How to Run
1. Clone the repository
2. Configure database in application.properties
3. Run the application

## API Endpoints
POST /employees
GET /employees
PUT /employees/{id}
DELETE /employees/{id}
