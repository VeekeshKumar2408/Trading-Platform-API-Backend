# Trading-Platform-API-Backend (In Development)

This is the backend for a **Bitcoin Trading Platform** built as an **API** using **Java** and **Spring Boot**. The platform allows users to securely trade Bitcoin, manage their wallets, and view transaction history. It integrates with third-party APIs like **CoinGecko** to fetch real-time Bitcoin data and includes payment gateway integrations with **Razorpay** and **Stripe** for seamless transactions.  

## Features (Planned and Implemented)

- **User Authentication**: Secure JWT-based registration and login.
- **OTP Verification**: An additional layer of security for transactions.
- **Bitcoin Trading**: APIs for buying and selling Bitcoin.
- **Transaction History**: APIs to retrieve detailed transaction history.
- **Wallet Management**: Manage Bitcoin balance and view transactions through APIs.
- **Real-Time Bitcoin Data**: Integration with **CoinGecko API** to fetch live Bitcoin price and market trends.
- **Payment Gateway Integration**:  
  - **Razorpay** and **Stripe** support for wallet top-ups and payments.  
- **MySQL Integration**: Persistent data storage for users and transactions.

## Technologies

- **Java 17**
- **Spring Boot**
  - **Spring Security** for authentication.
  - **JWT** for secure API access.
  - **Spring Data JPA** for data persistence.
  - **Spring RestTemplate** for third-party API integration.
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

4. Add your API keys for third-party services in `application.properties`:
    ```properties
    # CoinGecko API
    coingecko.api.base-url=https://api.coingecko.com/api/v3

    # Razorpay API
    razorpay.api.key=your_razorpay_key
    razorpay.api.secret=your_razorpay_secret

    # Stripe API
    stripe.api.key=your_stripe_api_key
    ```

5. Run the application:
    ```bash
    mvn spring-boot:run
    ```

6. The API will be available at `http://localhost:5455`.

## Current Progress

- [x] Initial project setup
- [x] JWT-based user authentication
- [x] OTP Verification (In Progress)
- [x] Bitcoin trading API
- [x] Wallet and transaction management API
- [x] Integration with CoinGecko for real-time Bitcoin data
- [x] Payment gateway integration with Razorpay and Stripe

## Upcoming Features

- Advanced analytics for trading performance.
- Notifications for transaction status and Bitcoin price updates.
- Multi-currency wallet support.
