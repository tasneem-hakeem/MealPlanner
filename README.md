# 📱 Android Project – Meal Planner App

## 📌 Project Overview

This Android application is a **Meal Planner App** that allows users to discover meals, view detailed recipes, manage favorite meals, and plan meals for specific days.

The app follows a **Single Activity architecture with multiple Fragments** and is built using **MVP (Model–View–Presenter)** to maintain clean separation of concerns.

The project focuses on applying real Android development concepts such as reactive programming, local caching, proper UI state handling, and secure user authentication.

---

## 🔐 Authentication System

The application integrates **Firebase Authentication** to provide secure and reliable user authentication.

### Supported Authentication Methods

- Email & Password Registration
- Email & Password Login
- Google Sign-In
- Automatic session persistence (user remains logged in until logout)

Authentication is handled through Firebase, ensuring secure credential management and seamless integration with the app's user flow.

---

## 🛠️ Technologies & Tools Used

- **Language:** Java
- **Architecture Pattern:** MVP (Model – View – Presenter)
- **Reactive Programming:** RxJava3
- **Local Database:** Room
- **Networking:** Retrofit
- **Authentication:** Firebase Authentication (Email/Password + Google Sign-In)
- **UI Design:** XML + Material Design
- **Navigation:** Single Activity + Fragments + Navigation Component
- **Version Control:** Git & GitHub
- **Project Management:** GitHub Board

---

## ✨ Features

### 🔐 Authentication Features

- Register using Email & Password
- Login using Email & Password
- Sign in with Google
- Secure authentication using Firebase
- Session persistence (User remains logged in)

### 🍽️ Meal Features

- Browse meals from remote API
- Search meals by name or ingredient
- View full meal details
- Add / remove meals from Favorites
- Plan meals for specific days (Meal Planner feature)
- Proper loading, empty, and error states
- Network error handling

---

## 📂 Project Structure

The project follows a clean layered structure:


```
com.tasneem.mealplanner
│
├── data
│   ├── api
│   ├── datasource
|       ├── authentication
|       ├── meals
|       ├── plannedmeals
|       └── favoritemeals
|       
│   ├── db
│   └── utils
│
├── domain
│   └── model
│
├── presentation
│   ├── login
│   ├── register
│   ├── home
│   ├── search
│   ├── favorites
│   ├── planner
│   └── mealdetails
│
└── utils
```

---


## 📋 GitHub Board

Project planning and task tracking were managed using GitHub:

👉 [[GitHub Board](https://github.com/users/tasneem-hakeem/projects/2)]

---



Thank you for reviewing our project 🌸
