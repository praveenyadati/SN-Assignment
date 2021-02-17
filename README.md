Technologies Used

//Kotlin is primary langauage

//Koin as dependency injection

//Data binding

//Retrofit & coroutines for background network operations

//Room database for caching

//Navigation component

//MVVM architecture and JETPack components

// --------------- >Features Implemented -------------------->

1. On the initial screen the user enters a first name and a last name. (DONE)

2. The submit button is disabled until both fields are not empty. (DONE)

3. User input should be kept for later use as parameters. (DONE)

4. Clicking the submit button opens the second screen. (DONE)


1. The next screen shows a card with a random joke loaded from the web service (see below).(DONE)

2. User can swipe pages horizontally to load additional jokes. (DONE -> loading previous jokes from room database)

3. When swiping backward the user sees cached jokes. (DONE -> loading previous jokes from room database)

4. App should persist its state after screen rotation. (DONE)


● input length restriction (Added)

● loading indication (Added)

● network connection check (Handled network exception and loading jokes from local storage in case if there is no network connection and API is down)

● error handling (Added)