#### Dependency Injection using Hilt
##### Steps
1. Add gradle dependency
   1. Add a plugin in project level build.gradle
    ````
    plugins {
      ...
      id("com.google.dagger.hilt.android") version "2.51.1" apply false
    }
    ````
   2. Add dependencies
   ```
    plugins {
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    }
    
    android {
    ...
    }
    
    dependencies {
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    }
    
    // Allow references to generated code
    kapt {
    correctErrorTypes = true
    }
```
2. Module/ Dependency Container
   1. Annotate the module up with @Module annotation, followed by the @InstallIn(SingletonComponent::class)  annotation
   
   2. Define the provides function annotating them with @Provides or @Binds if it is an abstraction like abstract class or interface

3. Application Class and annotate that up with @HiltAndroidApp

4. Inject dependencies can be done in any of the two Field Injection or contructor injection

5. Annotate the entrypoints like Activity,Fragment, Services etc with @AndroidEntryPoint annotation to finish up
