package com.mknishad.githubusers

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 *  Main application class for the GitHub Users App.
 *
 *  This class serves as the entry point for the application and is annotated with
 *  [HiltAndroidApp] to enable Hilt dependency injection throughout the app. Hilt
 *  manages the app's dependencies and provides a way to inject them into the different
 *  components of the app.
 *
 *  Extending [Application] allows this class to perform app-level initialization tasks
 *  such as setting up global resources, initializing libraries, and managing the app's
 *  lifecycle.  In this specific app, Hilt handles most of the initialization related
 *  to dependency injection.  You might add further initialization logic here if needed,
 *  such as configuring a logging framework or starting background services, although Hilt
 *  may provide more appropriate ways to handle some of these (e.g., using WorkManager and
 *  injecting dependencies into Worker classes).
 *
 *  By annotating with `@HiltAndroidApp`, Hilt generates the necessary components for
 *  dependency injection at compile time. These components are accessible to other parts
 *  of the app through Hilt's `@Inject` annotation and related mechanisms.
 */
@HiltAndroidApp
class GitHubUsersApp : Application()