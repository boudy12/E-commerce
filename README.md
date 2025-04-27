
# E-commerce Backend System

This project is a Full E-commerce Backend Application built with Spring Boot.  
It includes all the core features needed for an online shopping platform like authentication, product management, cart, orders, comments, and pagination.

---

## Technologies Used

- Java 17
- Spring Boot 3
- Spring Security (JWT Authentication)
- Spring Data JPA (Hibernate ORM)
- MySQL
- Lombok
- MapStruct (DTO Mapping)
- Maven

---

## Features

### Authentication (JWT Based)

- User registration `/api/auth/register`
- User login `/api/auth/login` — returns JWT token
- Secure APIs with token authorization

### Product Management

- Create, Read (with Pagination)
- Upload and serve product images
- Update/Delete Products (future enhancement)

### Cart Management

- Add product to cart
- Increase quantity if already added
- Automatically create cart for users
- Validate product stock before adding

### Comments System

- Users can add comments on products
- Retrieve product comments

### Order Management

- Place orders from the cart
- Reduce product stock after order
- View order history for each user

### Pagination

- List products with pagination support (`/api/products?page=0&size=10`)

### Exception Handling

- Custom Exceptions (like `ResourceNotFoundException`, `InsufficientStockException`, etc.)

---

## Project Structure

```
src/main/java/com/ecom/ecommerce
│
├── controller       # REST APIs (Auth, Product, Cart, Order, Comment)
├── service          # Business logic
├── repository       # JPA Repositories
├── model            # Entity classes
├── dto              # Data Transfer Objects
├── mapper           # MapStruct Mappers
├── security         # JWT Security
├── exception        # Custom Exceptions
└── util             # Utility classes
```

---

## Setup Instructions

1. Clone the repository:

```bash
git clone https://github.com/your-username/ecommerce-backend.git
cd ecommerce-backend
```

2. Create MySQL Database:

```sql
CREATE DATABASE ecommerce;
```

3. Configure `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=5MB
```

4. Build and run:

```bash
mvn spring-boot:run
```

5. Test the APIs using Postman or any REST client.

---

## API Endpoints Samples

| Method | Endpoint                  | Description                |
|--------|----------------------------|-----------------------------|
| POST   | `/api/auth/register`        | Register a new user         |
| POST   | `/api/auth/login`           | Login and get JWT           |
| POST   | `/api/products`             | Add new product             |
| GET    | `/api/products`             | List products (with pagination) |
| POST   | `/api/cart/add`              | Add item to cart            |
| GET    | `/api/cart`                  | View cart                   |
| POST   | `/api/orders/place`          | Place an order from cart    |
| GET    | `/api/orders`                | View order history          |
| POST   | `/api/comments/add`          | Add comment to a product    |
| GET    | `/api/comments/{productId}`  | Get comments for a product  |

All endpoints (except login and register) require Authorization header:  
`Authorization: Bearer YOUR_JWT_TOKEN`

---

## Concepts Covered

- Dependency Injection
- REST APIs with Spring Boot
- DTOs and Entity Mapping with MapStruct
- Spring Data JPA and Repository Pattern
- Security with JWT Token
- File Upload and Static Resources Handling
- Exception Handling and Custom Errors
- Pagination using `Pageable`
- Eager vs Lazy Loading (relationships)

---

## Future Enhancements

- Full Product Update & Delete APIs
- Admin dashboard (CRUD users/products/orders)
- Payment Integration (Stripe, PayPal)
- Send email confirmation for orders
- Real-time stock management

---

## Developed By

Abdulrahman Ahmed  
LinkedIn: [Abdelrahman Ahmed](https://www.linkedin.com/in/abdelrahman-ahmed-0b9793221)

---

## Conclusion

Complete E-commerce backend ready for production  
JWT Authentication & Authorization  
Products, Carts, Orders, Comments, and Pagination handled  
Clean Code with layered architecture  

This backend is ready to be connected to any frontend like React, Angular, Flutter, or Mobile App.
