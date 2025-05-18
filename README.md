# 📚 BookWorm - Your Personal Reading Companion

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)
![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)

BookWorm is a full-stack web application that helps readers track their reading progress, join book clubs, and discover new books. Built with Spring Boot and React, it provides a modern and intuitive interface for book enthusiasts.

## ✨ Features

- 📖 **Reading Lists**: Create and manage custom reading lists
- 📊 **Progress Tracking**: Track your reading progress with page counts and status updates
- 👥 **Book Clubs**: Join or create book clubs for group discussions
- 💬 **Discussions**: Engage in book-related discussions within clubs
- ⭐ **Reviews**: Rate and review books you've read
- 🔍 **Book Discovery**: Find new books through recommendations
- 👤 **User Profiles**: Customize your profile with reading preferences and bio

## 🛠️ Tech Stack

### Backend
- Java 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- MySQL 8
- Maven

### Frontend
- React 18
- Tailwind CSS
- React Router
- Axios
- Context API for state management

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Node.js 16 or higher
- MySQL 8
- Maven
- npm or yarn

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/bookworm.git
   cd bookworm
   ```

2. **Set up the database**
   ```sql
   CREATE DATABASE bookworm_db;
   ```

3. **Configure the backend**
   - Navigate to `src/main/resources/application.properties`
   - Update the database credentials:
     ```properties
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

4. **Start the backend server**
   ```bash
   cd backend
   mvn spring-boot:run
   ```

5. **Start the frontend development server**
   ```bash
   cd frontend
   npm install
   npm start
   ```

The application will be available at:
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080

## 📁 Project Structure

```
bookworm/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/bookworm/
│   │   │   │       ├── config/
│   │   │   │       ├── controller/
│   │   │   │       ├── model/
│   │   │   │       ├── repository/
│   │   │   │       ├── service/
│   │   │   │       └── security/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
└── frontend/
    ├── src/
    │   ├── components/
    │   ├── contexts/
    │   ├── pages/
    │   ├── services/
    │   └── utils/
    ├── package.json
    └── README.md
```

## 🔐 API Documentation

The API documentation is available at `/swagger-ui.html` when running the backend server.

### Key Endpoints

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - User login
- `GET /api/books` - Get all books
- `GET /api/reading-lists` - Get user's reading lists
- `POST /api/book-clubs` - Create a new book club
- `GET /api/reviews` - Get book reviews

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is free to use for students who are looking to build similar projects and upgrade my project.

## 👥 Authors

- Swastik Sengupta - AIML student - [My GitHub](https://github.com/Swastik-51)

## 🙏 Acknowledgments

- Spring Boot team for the amazing framework
- React team for the frontend library
- All contributors who have helped shape this project

## 📞 Support

If you have any questions or need help, please open an issue in the GitHub repository.

---

⭐ Star this repository if you find it helpful! 
