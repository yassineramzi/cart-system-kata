#### Cart and Order Service API
#### This API provides the functionality to manage a shopping cart and orders for an e-commerce system. It includes the following operations:

- Add products to a cart
- Remove products from a cart
- Confirm an order
- Update inventory based on customer actions
- Track the order status

#### Technologies Used

- Java 21
- Spring Boot
- Spring Data JPA
- Spring HATEOAS
- Swagger (SpringDoc OpenAPI)
- H2 Database (for local testing)
- Liquibase (for database version control)
- Docker

#### How to Run the Application
Clone the repository:

#### Swagger Documentation: Once the application is running, you can access the Swagger UI to explore and test the APIs:

http://localhost:8080/swagger-ui.html

#### CI 

For the CI part we used Github Actions, to build and publish the test report,

#### TO BE COMPLETED 

- Create the Docker image and publish it with JIB,
- Add a controllerAdvice to hanlde different exceptions thrown by the application,
- Test the Docker image for vulnerabilities and publish report as action artifact
