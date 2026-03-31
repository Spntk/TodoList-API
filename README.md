# TaskFlow - Todo Management Application

A full-stack todo management application built with Spring Boot and Next.js.

## 🚀 Tech Stack

**Backend**
- Java 21 + Spring Boot 3
- Spring Security + JWT Authentication
- PostgreSQL
- Docker

**Frontend**
- Next.js 14 + TypeScript
- Tailwind CSS + shadcn/ui
- Axios

## ✨ Features

- 🔐 JWT Authentication with configurable session duration
- ✅ Todo management with priority, status, category
- 🗑️ Soft delete with 7-day recovery window
- ⏰ Scheduled auto hard-delete after 7 days
- 📊 Dashboard with task overview
- 🏷️ Category management
- 🔍 Search, filter, sort, pagination
- ☑️ Bulk select and delete
- ⚙️ Profile & password settings

## 🐳 Run with Docker

### Prerequisites
- Docker Desktop

### Steps

\`\`\`bash
# 1. Clone repository
git clone https://github.com/username/taskflow.git
cd taskflow

# 2. Build backend image
cd todolist_API
docker build -t todolist-backend .

# 3. Build frontend image
cd ../todolist_mini_project
docker build -t todolist-frontend .

# 4. Start all services
cd ..
docker compose up -d
\`\`\`

Open http://localhost:3000

## 📁 Project Structure

\`\`\`
taskflow/
├── todolist_API/          # Spring Boot Backend
├── todolist_Frontend/ # Next.js Frontend
└── docker-compose.yml
\`\`\`

## 🔑 Environment Variables

| Variable | Description |
|---|---|
| SPRING_DATASOURCE_URL | PostgreSQL connection URL |
| SPRING_DATASOURCE_USERNAME | Database username |
| SPRING_DATASOURCE_PASSWORD | Database password |
| JWT_SECRET | JWT signing secret |
