# SmartRx

## Table of Contents
- Description
- Tools and Technologies
- General Approach
- Planning Documentation
- ERD Diagram
- MVC Diagram
- Relationships
- Usage
- Installation Instructions for Dependencies
- How To Install and Run this Application on your own Machine
- User Stories
- Unsolved Problems & Hurdles Tackled
- Contributing
- Credits


#### Description
SmartRx REST API backend is a Java and Spring Boot application that provides a robust and scalable solution for managing prescription and medication data. It serves as a reliable and efficient backend system to support various client applications such as web, mobile, or desktop applications.
### Tools and Technologies 
- Java 
- Spring Boot 
- Spring MVC 
- Spring Security 
- Spring Data JPA
- Hibernate 
- Git
- Junit 
- Maven
- IntelliJ IDEA

### General Approach
In our development process, we have adopted an agile approach with a focus on collaboration and iterative development. We have been utilizing pair programming extensively, both in duos and as a quad, to leverage the collective skills and expertise of our team members.


#### User Stories and Link 
#### ERD Diagram 
[ERD DIAGRAM]()
#### Planning Documentation 
[Link to Planning and Schedule]()
#### Unsolved Problems and Hurdles Tackled

### Key Features
##### CRUD Operations: 
SmartRx allows users to perform Create, Read, Update, and Delete (CRUD) operations on prescription and medication data. Users can create new prescriptions, retrieve prescription details, update existing prescriptions, and delete prescriptions as needed.

##### Authentication and Authorization
SmartRx implements secure authentication and authorization mechanisms to ensure that only authorized users can access the protected endpoints. It enables user registration, login, and generates JSON Web Tokens (JWT) to authenticate and authorize API requests.

##### Validation and Error Handling:
SmartRx performs thorough validation of incoming data to ensure its integrity and consistency. It provides appropriate error responses with detailed error messages and status codes when invalid requests or exceptions occur, improving the user experience.

##### Testing and Quality Assurance:
SmartRx also includes a suite of unit tests and integration tests to validate the functionality and behavior of the API. It follows coding best practices, adheres to code style guidelines, and undergoes rigorous testing to ensure high-quality and reliable software.
### Installation Instructions For Dependencies 
1. Open your project in an IDE or navigate to the project directory using the command line.
2. Locate the pom.xml file in the root directory of your project.
3. Open the pom.xml file and locate the <dependencies> section.
4. Copy the dependency block for each dependency mentioned above and paste it inside the <dependencies> section of your pom.xml file.
5. Save the pom.xml file.
6. Build the project to resolve and download the dependencies by running one of the following commands:

- If you are using an IDE like Eclipse or IntelliJ IDEA, you can use the IDE's build or compile command to resolve the dependencies.
- If you are using the command line, navigate to the project directory and run the command mvn clean install. This will trigger the Maven build process and download the dependencies specified in the pom.xml file.
7. Wait for the build process to complete. Maven will download the required dependencies from the Maven Central Repository or other specified repositories.
8. Once the build process is successful, the dependencies should be installed and available for your project to use.

   - Note: If you are using an IDE, it might automatically download the dependencies and update the project configuration. If not, you can manually refresh the project or restart the IDE to ensure that the dependencies are recognized.

### How To Install and Run this Application on your own Machine
