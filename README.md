# To-Do List Web Application

## Overview

This is a web-based To-Do List application with admin functionality and an interactive user interface. It allows users to manage their tasks efficiently and provides an admin panel for better control over the application.

## Features

- **User Registration & Login** – Users can create an account and log in to manage their tasks.
- **Task Management** – Add, edit, delete, and mark tasks as completed.
- **Admin Panel** – Admins can manage all users.
- **Interactive UI** – A clean and responsive design for a smooth user experience.
- **Real-time Updates** – Tasks update instantly without needing a page refresh.
- **User Roles** – Regular users can manage their own tasks like adding, deleting and clearing tasks while admins have extra privileges.
- **Secure Authentication** – User passwords are securely stored.

## Technologies Used

- **Backend:** Spring MVC, Hibernate, MySQL
- **Frontend:** HTML, CSS, JavaScript, Thymeleaf
- **Security:** Spring Security for authentication and authorization
- **AJAX:** For real-time updates without page reloads

## Folder Structure

```
/todolist
│── /src
│   ├── /main
│   │   ├── /java
│   │   │   ├── /com/yourpackage/config   # Configuration files (Spring, Security, etc.)
│   │   │   ├── /com/yourpackage/controller  # Controllers handling requests
│   │   │   ├── /com/yourpackage/model  # Entity classes
│   │   │   ├── /com/yourpackage/repository  # Database access layer
│   │   │   ├── /com/yourpackage/service  # Business logic layer
│   │   ├── /resources
│   │   │   ├── /static  # CSS, JavaScript, images
│   │   │   ├── /templates  # Thymeleaf HTML templates
│   │   │   ├── application.properties  # Database and app settings
│── /test  # Unit and integration tests
│── pom.xml  # Maven dependencies
│── README.md  # Project documentation
```

## Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/your-username/todolist.git
   ```
2. Open the project in Eclipse or any preferred IDE.
3. Configure the database in `application.properties`.
4. Run the project on Tomcat Server.
5. Access the application in the browser at `http://localhost:8080/`.

## How to Use

- **Users:** Register or log in to add and manage tasks.
- **Admins:** Log in with admin credentials to manage users.

## Contributions

Feel free to fork this repository and submit pull requests to improve the application.

## License

This project is open-source and free to use.

---

Made with Rahul Tripathi

