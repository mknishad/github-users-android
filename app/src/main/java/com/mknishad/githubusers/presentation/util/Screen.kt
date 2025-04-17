package com.mknishad.githubusers.presentation.util

/**
 * Sealed class representing the different screens in the application.
 *
 * Each screen is defined as an object inheriting from this class and has a unique route string.
 * This approach provides type safety and makes it easy to manage and navigate between screens.
 *
 * @param route The unique string identifier for the screen, used for navigation.
 */
sealed class Screen(val route: String) {
  object UserListScreen : Screen("user_list_screen")
  object UserDetailScreen : Screen("user_detail_screen")
}