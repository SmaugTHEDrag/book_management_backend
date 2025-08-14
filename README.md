# 📚 Book Management System
[![Java](https://img.shields.io/badge/Java-17-red?logo=openjdk)]()
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot)]()
[![React](https://img.shields.io/badge/React-18-blue?logo=react)]()
[![MySQL](https://img.shields.io/badge/MySQL-Aiven_Cloud-orange?logo=mysql)]()
[![License](https://img.shields.io/badge/License-MIT-yellow)]()

Book Management System is a full-stack web application for efficient book management, featuring:
- 🔍 Advanced search, sorting, and pagination for easy navigation
- ❤️ Favorites per user to save preferred books
- 🛡 Role-based access control (Admin & Customer) for secure operations
---
## ✨ Features
- 📖 **Book Management**: CRUD, search, sort, pagination
- ❤️ **Favorites**: Add/remove favorites per user
- 👤 **User Management**: JWT auth, role-based access (Admin/Customer)
- 🔍 **Advanced Filtering** with Specification API
- 📜 **Swagger API Docs** for backend endpoints
- 🌐 **Cloud Deployment**: Backend on Render, Frontend on Netlify
---
## 🛡 Role Permissions
| Feature / Action            | Admin | Customer |
| --------------------------- | :---: | :------: |
| View books                  |  ✅   |    ✅    |
| Search, sort, paginate      |  ✅   |    ✅    |
| Add book                    |  ✅   |    ❌    |
| Edit book                   |  ✅   |    ❌    |
| Delete book                 |  ✅   |    ❌    |
| View user list              |  ✅   |    ❌    |
| Manage users (CRUD)         |  ✅   |    ❌    |
| Add to favorites            |  ✅   |    ✅    |
| Remove from favorites       |  ✅   |    ✅    |
| View own favorites          |  ✅   |    ✅    |

---

## 🛠 Tech Stack
**Backend**: Java 17, Spring Boot 3.x, Spring Security (JWT), JPA, MySQL, Swagger  
**Frontend**: ReactJS, TailwindCSS, Axios, React Router DOM  
**Database**: MySQL hosted on Aiven Cloud  
**Testing**: JUnit 5, Mockito

---

## 🏗 Architecture
[ReactJS + TailwindCSS] --> [Spring Boot REST API] --> [MySQL (Aiven Cloud)]
(Frontend) (Backend + Security) (Database)

---

## 🚀 Quick Start
### Backend
```bash

git clone https://github.com/your-username/book-management.git
cd backend
mvn clean install
mvn spring-boot:run
# Swagger: http://localhost:8080/swagger-ui/index.html
```
### Frontend
```bash

cd frontend
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
| **Frontend**     | [Netlify Deployment](https://your-frontend.netlify.app)               |
| **Backend**      | [Render Deployment](https://your-backend.onrender.com)                |
| **Swagger Docs** | [Swagger UI](https://your-backend.onrender.com/swagger-ui/index.html) |

--- 

## 📬 Contact
- Name: Nguyen Pham
- mail: your.email@example.com
- LinkedIn: Your LinkedIn
- GitHub: Your GitHub