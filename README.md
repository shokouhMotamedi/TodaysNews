

```
The TodayNews app is built using Kotlin and follows the MVVM (Model-View-ViewModel) architecture to ensure a clean separation of concerns and better state management.
The app leverages Jetpack components, including LiveData, ViewModel, and StateFlow, for efficient UI updates and lifecycle-aware data handling. 
Coroutines and Flow are used for asynchronous operations, enabling seamless data fetching from both local Room database and a remote API while following an Offline-First approach. 
The app uses Repository Pattern to abstract data sources, and Use Cases (Interactor Pattern) to encapsulate business logic. 
Dependency Injection is implemented via a custom DependencyContainer at first, promoting modular and testable code. 
The UI layer consists of Fragments and Activities, with RecyclerView Adapters handling dynamic news lists. Glide is used for image loading, ensuring optimized performance.
Additionally, the app allows users to mark articles as favorites, which is managed using a local database, ensuring persistence. 
Proper error handling and state management are implemented using sealed classes and stateIn() for efficient Flow handling. 
Overall, the app is designed to be scalable, maintainable, and performant, following best practices in Android development.
```

Today's News Commits:

1- Project layout

2- RecyclerView Adapter using sample Data

3- Retrofit with Kotlin Coroutine for API Call

4- Included the Readme file to the project using a pull request to practice: 
   Pull request - rebase - branching

5- Actions for Navigation of onNewsClicked

6- implement AddToFavorite -> using Room Database to store articles locally and 

   Included the Dependency Injection Manually.

7- RemoveFromFavorite 

   and Apply Clean Architecture.
   
8- Converting ReadNewsFragment to Activity 

9- Implement Dependency injection with Hilt and 
   implemented the SavedStateHandle to handle the Article News page

10- implement Unit Test files