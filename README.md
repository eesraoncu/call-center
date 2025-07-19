# 🎯 Call Center Management System

[![Java](https://img.shields.io/badge/Java-24-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0+-green.svg)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-19.1.0-blue.svg)](https://reactjs.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13+-blue.svg)](https://www.postgresql.org/)
[![JWT](https://img.shields.io/badge/JWT-Authentication-yellow.svg)](https://jwt.io/)

> **A comprehensive call center management system with real-time analytics, customer relationship management, and advanced reporting capabilities.**

## 🌟 Features

### 📊 **Real-Time Analytics Dashboard**
- **Call Volume Tracking**: Monitor incoming and outgoing calls with detailed metrics
- **Performance Analytics**: Track operator performance and customer satisfaction scores
- **Interactive Charts**: Beautiful visualizations using Recharts library
- **Custom Reports**: Generate weekly and monthly performance reports

### 👥 **Customer Relationship Management**
- **Customer Database**: Comprehensive customer information management
- **Call History**: Detailed call records with timestamps and outcomes
- **Customer Segmentation**: Organize customers by location and service type
- **Address Management**: Multi-level address system (City → District → Township → Neighborhood)

### 🔐 **Advanced Security & Authentication**
- **JWT Token Authentication**: Secure API access with token-based authentication
- **Role-Based Access Control**: Admin and Operator role management
- **Password Security**: Encrypted password storage and validation
- **Session Management**: Secure user sessions with automatic logout

### 📞 **Call Management System**
- **Call Recording**: Track call details, duration, and outcomes
- **Operator Assignment**: Assign customers to specific operators
- **Call Statistics**: Monitor call volume, duration, and resolution rates
- **Service Tracking**: Link calls to specific services and categories

### 🏢 **Location Management**
- **Hierarchical Address System**: City → District → Township → Neighborhood
- **Geographic Analytics**: Location-based reporting and statistics
- **Address Validation**: Comprehensive address validation and formatting

### 📈 **Advanced Reporting**
- **Weekly Reports**: Call volume, average call duration, first-call resolution
- **Customer Satisfaction**: CSAT/NPS score tracking and visualization
- **Operator Performance**: Individual operator statistics and rankings
- **Wait Time Analysis**: Customer wait time tracking and optimization

## 🛠️ Technology Stack

### Backend
- **Java 24**: Latest Java features and performance
- **Spring Boot 3.0+**: Rapid application development framework
- **Spring Security**: Comprehensive security framework
- **Spring Data JPA**: Database abstraction layer
- **PostgreSQL**: Robust relational database
- **JWT**: Stateless authentication tokens
- **Maven**: Dependency management and build tool

### Frontend
- **React 19.1.0**: Modern UI library with hooks
- **Vite**: Fast build tool and development server
- **React Router DOM**: Client-side routing
- **Recharts**: Beautiful and responsive charts
- **Axios**: HTTP client for API communication
- **JWT Decode**: Token parsing and validation

### Development Tools
- **Git**: Version control

## 🚀 Quick Start

### Prerequisites
- Java 24 or higher
- Node.js 18+ and npm
- PostgreSQL 13+
- Maven 3.6+

### Backend Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/callcenter-project.git
   cd callcenter-project
   ```

2. **Configure PostgreSQL**
   ```sql
   CREATE DATABASE callcenter_db;
   CREATE USER callcenter_user WITH PASSWORD 'your_password';
   GRANT ALL PRIVILEGES ON DATABASE callcenter_db TO callcenter_user;
   ```

3. **Update application.properties**
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/callcenter_db
   spring.datasource.username=callcenter_user
   spring.datasource.password=your_password
   ```

4. **Run the backend**
   ```bash
   cd callcenter_backend/callcenter3/callcenter1/callcenter1
   mvn spring-boot:run
   ```

### Frontend Setup

1. **Install dependencies**
   ```bash
   cd callcenter_frontend/deneme
   npm install
   ```

2. **Start development server**
   ```bash
   npm run dev
   ```

3. **Build for production**
   ```bash
   npm run build
   ```

## 📋 API Documentation

### Authentication Endpoints
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration
- `GET /api/auth/profile` - Get user profile

### Customer Management
- `GET /api/customers` - Get all customers
- `POST /api/customers` - Create new customer
- `PUT /api/customers/{id}` - Update customer
- `DELETE /api/customers/{id}` - Delete customer

### Call Management
- `GET /api/calls` - Get all call records
- `POST /api/calls` - Create new call record
- `GET /api/call-details` - Get detailed call information

### Location Management
- `GET /api/locations/cities` - Get all cities
- `GET /api/locations/districts/{cityId}` - Get districts by city
- `GET /api/locations/townships/{districtId}` - Get townships by district

## 🏗️ Project Structure

```
callcenter_project/
├── callcenter_backend/          # Spring Boot Backend
│   └── callcenter3/
│       └── callcenter1/
│           └── callcenter1/
│               ├── src/main/java/com/example/callcenter1/
│               │   ├── config/          # Configuration classes
│               │   ├── controller/       # REST API controllers
│               │   ├── dto/             # Data Transfer Objects
│               │   ├── exception/       # Custom exceptions
│               │   ├── model/           # Entity classes
│               │   ├── repository/      # Data access layer
│               │   ├── security/        # Security configuration
│               │   ├── service/         # Business logic
│               │   └── validation/      # Custom validators
│               └── src/main/resources/
│                   └── application.properties
└── callcenter_frontend/         # React Frontend
    └── deneme/
        ├── src/
        │   ├── components/      # Reusable UI components
        │   ├── context/         # React context providers
        │   ├── pages/           # Page components
        │   └── services/        # API service layer
        ├── public/              # Static assets
        └── package.json
```

## 🔧 Configuration

### Environment Variables
```bash
# Database Configuration
DB_HOST=localhost
DB_PORT=5432
DB_NAME=callcenter_db
DB_USER=callcenter_user
DB_PASSWORD=your_password

# JWT Configuration
JWT_SECRET=your_jwt_secret_key
JWT_EXPIRATION=86400000

# Server Configuration
SERVER_PORT=8080
SERVER_ADDRESS=0.0.0.0
```

### Security Configuration
- CORS enabled for frontend communication
- JWT token validation on protected endpoints
- Role-based access control (ADMIN/OPERATOR)
- Input validation and sanitization

## 📊 Key Features in Detail

### Real-Time Analytics
- **Call Volume Tracking**: Monitor daily/weekly call volumes
- **Performance Metrics**: Track operator efficiency and customer satisfaction
- **Interactive Dashboards**: Beautiful charts and visualizations
- **Custom Reports**: Generate detailed performance reports

### Customer Management
- **Comprehensive Profiles**: Store customer details, contact info, and preferences
- **Call History**: Track all customer interactions and outcomes
- **Location Tracking**: Multi-level address management system
- **Service Assignment**: Link customers to specific services

### Operator Management
- **Performance Tracking**: Monitor individual operator metrics
- **Customer Assignment**: Assign customers to specific operators
- **Role Management**: Admin and operator role differentiation
- **Activity Logging**: Track all operator activities



## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 👥 Collaborators

### Backend Team
- [**Ahmet Yunus Demirci**](https://github.com/AhmetYunusDemirci)
- [**Aysel Sündük**](https://github.com/aysel-sunduk)
- [**Beyza Nur Bostancıoğlu**](https://github.com/beyzanurbostancioglu)
- [**Esra Öncü**](https://github.com/eesraoncu)
- [**Hikmet Alanlı**](https://github.com/hikmetdev)
- [**İrem Karaca**](https://github.com/karacairem)
- [**Mustafa Bosnalı**](https://github.com/mustafabsnl)
- [**Zeynep Nur Karabay**](https://github.com/ZNurk)

### Frontend Team
- [**Pınar Çağla Kurt**](https://github.com/pinarcagla)
- [**Mert Polat**](https://github.com/MertP06)
- [**Evla Yorulmaz**](https://github.com/evoli04)
- [**Elif Buse Holozlu**](https://github.com/elifbuseh)

---

**⭐ Star this repository if you find it helpful!** 
