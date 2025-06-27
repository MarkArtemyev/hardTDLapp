# hardTDLapp

A simple Android productivity app prototype. You can create tasks with a scheduled time and choose a blocking mode (Full Focus, Focused, or Notification). Tasks can be edited or removed in the list. When a task is scheduled the app uses `AlarmManager` to launch a full-screen `BlockerActivity` at the specified time.

This project uses Jetpack Compose. To build the application locally, open it in Android Studio and run the **app** configuration. Gradle will attempt to download dependencies on the first build.
