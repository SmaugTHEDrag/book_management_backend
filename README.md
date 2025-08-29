# 📚 Book Management System

[![Java](https://img.shields.io/badge/Java-17-red?logo=openjdk)]()  
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot)]()  
[![React](https://img.shields.io/badge/React-18-blue?logo=react)]()  
[![MySQL](https://img.shields.io/badge/MySQL-Aiven_Cloud-orange?logo=mysql)]()  
[![License](https://img.shields.io/badge/License-MIT-yellow)]()

A **full-stack digital library platform** featuring book management, user favorites, blog posts, likes, nested comments, and AI-powered search assistance.  
Built with **Spring Boot (Backend)**, **ReactJS (Frontend)**, and **MySQL (Cloud)**.

---

## 📝 Project Overview
- **Developed** a full-stack platform supporting:
    - User registration & login
    - Book search & favorites
    - Blog posts with likes & nested comments
- **Engineered** secure RESTful APIs using **Spring Boot** & **Spring Security**, with **JWT-based authentication** and **role-based access control** (Admin/Customer).
- **Designed & optimized** a relational database schema to efficiently handle nested comments and likes, mitigating N+1 query issues.
- **Integrated AI features** via **Gemini API** for a chatbot that can answer user queries and perform **real-time keyword-based database searches** across book titles, authors, and categories.
- **Implemented** dynamic filtering, sorting, and pagination for efficient data retrieval.
- **Followed** a **3-tier architecture** in Spring Boot (**Controller → Service → Entity/Repository/DTO**) for clean separation of concerns and maintainability.

---

## ✨ Features
- 📖 **Book Management** – CRUD operations, search, sorting, and pagination
- ❤️ **Favorites** – Add/remove favorite books per user
- 📝 **Blog System** – Create blog posts, like posts, and comment (with nested replies)
- 🤖 **AI Chatbot** – Powered by Gemini API for intelligent search & Q&A
- 👤 **User Management** – JWT authentication, role-based access
- 🔍 **Advanced Filtering** – Powered by Spring Data Specification API
- 📜 **API Documentation** – Swagger UI integration
- ☁ **Cloud Deployment** – Backend on Render, Frontend on Netlify

---

## 🛡 Role Permissions
| Feature / Action                       | Admin | Customer |
|----------------------------------------|:-----:|:--------:|
| View books                             | ✅    | ✅       |
| Search/sort/paginate                   | ✅    | ✅       |
| Add book                               | ✅    | ❌       |
| Edit book                              | ✅    | ❌       |
| Delete book                            | ✅    | ❌       |
| View user list                         | ✅    | ❌       |
| Manage users (CRUD)                    | ✅    | ❌       |
| Add to favorites                       | ✅    | ✅       |
| Remove from favorites                  | ✅    | ✅       |
| View own favorites                     | ✅    | ✅       |
| Create own blog posts                  | ✅    | ✅       |
| Like blog posts                        | ✅    | ✅       |
| Comment on blog posts (nested replies) | ✅    | ✅       |
| Use AI Chatbot                         | ✅    | ✅       |
| Search via AI Chatbot                  | ✅    | ✅       |

---

## 🛠 Tech Stack
**Backend:** Java 17, Spring Boot 3.x, Spring Security (JWT), JPA, MySQL, Swagger  
**Frontend:** React 18, TailwindCSS, Axios, React Router DOM  
**Database:** MySQL (Aiven Cloud)  
**AI Integration:** Gemini API  
**Testing:** JUnit 5, Mockito

---

## 🏗 Architecture

[ReactJS + TailwindCSS] --> [Spring Boot REST API] --> [MySQL (Aiven Cloud)]
(Frontend) (Backend + Security) (Database) --> [Gemini API]

---

## 🚀 Quick Start
### Backend
```bash

git clone https://github.com/SmaugTHEDrag/book_management_backend.git
mvn clean install
mvn spring-boot:run
# Swagger: http://localhost:8080/swagger-ui/index.html
```
### Frontend
```bash

git clone https://github.com/SmaugTHEDrag/book_management_frontend.git
npm install
npm run dev
# Default: http://localhost:5173
```

---

## 🎥 Demo Video
[![Watch the demo](https://img.youtube.com/vi/YOUTUBE_VIDEO_ID/0.jpg)](https://www.youtube.com/watch?v=YOUTUBE_VIDEO_ID)
### 🌐 Live Demo
| Service          | Link                                                                  |
| ---------------- | --------------------------------------------------------------------- |
| **Frontend**     | [Netlify Deployment](https://ptn-book-storage.netlify.app/)               |
| **Backend**      | [Render Deployment](https://book-management-backend-d481.onrender.com)                |
| **Swagger Docs** | [Swagger UI](https://book-management-backend-d481.onrender.com/swagger-ui/index.html) |

--- 

## 📬 Contact
- Name: Pham Thai Nguyen
- mail: thainguyen122004@gmail.com
- GitHub: https://github.com/SmaugTHEDrag