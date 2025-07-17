# FocusApp

This project is a simple productivity app skeleton built for Android 8.0 and above. It demonstrates a layered architecture with Room, Hilt and a basic UI.

## Structure
```
com.yourcompany.focusapp
├── data
│   ├── database       // Room database and DAO
│   ├── model          // Data models
│   └── repository     // Repositories
├── di                 // Dependency injection setup
├── service            // Foreground service for focus sessions
├── ui                 // Activities, fragments and view models
└── util               // Utility classes
```

To build the project open it in Android Studio and run **app**. Gradle will download the required dependencies on first build.
