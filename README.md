# 📚 Book Management System
> A modern full-stack digital library platform with AI-powered features

<div align="center">

![Book Management System](https://via.placeholder.com/800x400/4f46e5/ffffff?text=Book+Management+System)

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18-61DAFB?style=for-the-badge&logo=react&logoColor=black)](https://reactjs.org/)
[![MySQL](https://img.shields.io/badge/MySQL-Aiven_Cloud-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)](LICENSE)

**[🌐 Live Demo](https://ptn-book-storage.netlify.app/)** •
**[📖 API Docs](https://book-management-backend-d481.onrender.com/swagger-ui/index.html)** •
**[🎥 Demo Video](https://www.youtube.com/watch?v=uis-1R07yUM)**

</div>

---

## 🌟 Highlights
| 🚀 **Modern Architecture**                     | 🔒 **Security**                                             | 💬 **AI & Smart Features**                           | 🎯 **Performance & UX**                                |
|:-----------------------------------------------| :---------------------------------------------------------- | :--------------------------------------------------- | :----------------------------------------------------- |
| **Spring Boot 3 + React 18** full-stack design | **JWT Authentication** + Role-based access (Admin/Customer) | **Gemini AI Integration** for smart search & chatbot | **Optimized SQL queries** and **efficient pagination** |
| 3 tier Layered RESTful API architecture        | Secured REST endpoints                                      | AI-powered book discovery & chatbot                  | Clean, modern UI with TailwindCSS                      |
| MySQL (Aiven Cloud) + Cloudinary               | Input validation & exception handling                       | Context-aware responses                              | Smooth navigation via React Router                     |
| Render + Netlify cloud deployment              | Token refresh & secure sessions                             | Natural language query support                       | Real-time feedback experience                          |
---

## ✨ Key Features

<div align="center">

|     📚 Book Management     |     👤 User System    |       💬 Blog & Social       |     🤖 AI Assistant    |
| :------------------------: | :-------------------: | :--------------------------: | :--------------------: |
| CRUD + search + pagination | JWT Auth + Role-based | Blog posts & nested comments | Chatbot & Smart search |
|      Favorites system      |   Profile management  |     Like / reply features    | Gemini API integration |

</div>

---

## 🏗️ System Architecture

![System Architecture](https://res.cloudinary.com/duipncbaq/image/upload/v1759678920/diagram-export-10-5-2025-10_38_25-PM_hanweu.jpg)

---

## 🛠️ Technology Stack

- Backend: Java 17 · Spring Boot 3 · Spring Security (JWT) · JPA/Hibernate · MySQL · Swagger/OpenAPI
- Frontend: React 18 · TailwindCSS · Axios · React Router DOM · Vite
- Cloud & DevOps: Render (Backend) · Netlify (Frontend) · Aiven MySQL · Cloudinary · Gemini API
- Testing & Build: · Maven
---

## 👥 Role-Based Access Control

<div align="center">

### 🔐 Permission Matrix

<details>
<summary>📜 <strong>View Full Role Permission Table</strong></summary>

| Feature / Action                 | 👑 Admin | 👤 Customer |
|----------------------------------| :------: | :---------: |
| **📚 Book Management**           |          |             |
| View/Search/Filter/Sort books    |     ✅    |      ✅      |
| View book details                |     ✅    |      ✅      |
| Add/Edit/Delete books            |     ✅    |      ❌      |
| **👤 User Management**           |          |             |
| Register / Login / Logout        |     ✅    |      ✅      |
| View user list                   |     ✅    |      ❌      |
| Manage users (users info, roles) |     ✅    |      ❌      |
| View/Update own profile          |     ✅    |      ✅      |
| **❤️ Favorites**                 |          |             |
| Add/Remove favorites             |     ✅    |      ✅      |
| View own favorites               |     ✅    |      ✅      |
| **📝 Blog System**               |          |             |
| View all posts                   |     ✅    |      ✅      |
| Create/Edit/Delete own posts     |     ✅    |      ✅      |
| Manage all posts/comments        |     ✅    |      ❌      |
| Like/Unlike posts                |     ✅    |      ✅      |
| Comment / Reply (nested)         |     ✅    |      ✅      |
| **🤖 AI Features**               |          |             |
| Use chatbot                      |     ✅    |      ✅      |
| AI-powered book search           |     ✅    |      ✅      |
| **⚙️ System & Tools**            |          |             |
| Access API Docs (Swagger)        |     ✅    |      ✅      |
| Handle validation & exceptions   |     ✅    |      ✅      |

</details>

</div>

---

## 🚀 Quick Start Guide

### 🔧 Backend Setup
```bash
# Clone the repository
git clone https://github.com/SmaugTHEDrag/book_management_backend.git
cd book_management_backend

# Install dependencies
mvn clean install

# Configure database in application.properties
# Database Configuration (MySQL)
spring.datasource.url=jdbc:mysql://localhost:3306/book_management
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

# Gemini API Configuration 
gemini.api.key=your_gemini_api_key
gemini.api.url=https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent

# Cloudinary Configuration 
cloudinary.cloud_name=your_cloud_name
cloudinary.api_key=your_cloudinary_api_key
cloudinary.api_secret=your_cloudinary_api_secret

# Run the application
mvn spring-boot:run

# 🎉 Backend running at http://localhost:8080
# 📖 Swagger UI: http://localhost:8080/swagger-ui/index.html
```

### 🎨 Frontend Setup
```bash
# Clone the frontend repository
git clone https://github.com/SmaugTHEDrag/book_management_frontend.git
cd book_management_frontend

# Install dependencies
npm install

# Start development server
npm run dev

# 🎉 Frontend running at http://localhost:5173
```

---

## 🎥 Demo & Live Links

<div align="center">

### 🌐 **Live Application**
[![Frontend](https://img.shields.io/badge/Frontend-Netlify-00C7B7?style=for-the-badge&logo=netlify)](https://ptn-book-storage.netlify.app/)
[![Backend](https://img.shields.io/badge/Backend-Render-46E3B7?style=for-the-badge&logo=render)](https://book-management-backend-d481.onrender.com)
[![API Docs](https://img.shields.io/badge/API%20Docs-Swagger-85EA2D?style=for-the-badge&logo=swagger)](https://book-management-backend-d481.onrender.com/swagger-ui/index.html)


### 🎬 **Project Walkthrough**
[![Watch the demo](https://img.youtube.com/vi/uis-1R07yUM/0.jpg)](https://www.youtube.com/watch?v=uis-1R07yUM)  
[![Watch on YouTube](https://img.shields.io/badge/▶️%20Watch%20on%20YouTube-FF0000?style=for-the-badge&logo=youtube&logoColor=white)](https://www.youtube.com/watch?v=uis-1R07yUM)
</div>

## 👨‍💻 About the Developer

<div align="center">

**Pham Thai Nguyen**

[![GitHub](https://img.shields.io/badge/GitHub-SmaugTHEDrag-181717?style=for-the-badge&logo=github)](https://github.com/SmaugTHEDrag)
[![Email](https://img.shields.io/badge/Email-thainguyen122004@gmail.com-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:thainguyen122004@gmail.com)

*Java Backend developer passionate about creating modern, scalable applications*

</div>

---

<div align="center">

**⭐ If you found this project helpful, please give it a star! ⭐**

Made with ❤️ and ☕ by [Pham Thai Nguyen](https://github.com/SmaugTHEDrag)

</div>