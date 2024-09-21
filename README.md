# Trading-Platform-API-Backend (In Development)

This is the backend for a **Bitcoin Trading Platform** built as an **API** using **Java** and **Spring Boot**. The platform allows users to securely trade Bitcoin, manage their wallets, and view transaction history. It includes authentication with OTP (One-Time Password) verification, with data stored in **MySQL**.

## Features (Planned)

- **User Authentication**: Secure JWT-based registration and login.
- **OTP Verification**: An additional layer of security for transactions.
- **Bitcoin Trading**: APIs for buying and selling Bitcoin.
- **Transaction History**: APIs to retrieve detailed transaction history.
- **Wallet Management**: Manage Bitcoin balance and view transactions through APIs.
- **MySQL Integration**: Persistent data storage for users and transactions.

## Technologies

- **Java 17**
- **Spring Boot**
  - **Spring Security** for authentication.
  - **JWT** for secure API access.
  - **Spring Data JPA** for data persistence.
- **MySQL** as the database.
- **Maven** for dependency management.
  
## API Backend Status

🚧 **This API backend is currently in development**. Expect regular updates, and features will be added progressively.

## How to Run (Development Setup)

1. Clone the repository:
    ```bash
    git clone https://github.com/VeekeshKumar2408/Trading-Platform-API-Backend.git
    cd Trading-Platform-API-Backend
    ```

2. Set up MySQL and create a database:
    ```sql
    CREATE DATABASE bitcoin_trading;
    ```

3. Update `application.properties` with your MySQL credentials:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/bitcoin_trading
    spring.datasource.username=your_mysql_username
    spring.datasource.password=your_mysql_password
    spring.jpa.hibernate.ddl-auto=update
    ```

4. Run the application:
    ```bash
    mvn spring-boot:run
    ```

5. The API will be available at `http://localhost:5455`.

## Current Progress

- [x] Initial project setup
- [x] JWT-based user authentication
- [ ] OTP Verification (In Progress)
- [ ] Bitcoin trading API
- [ ] Wallet and transaction management API
