package com.mknishad.githubusers.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mknishad.githubusers.common.Constants
import com.mknishad.githubusers.presentation.ui.theme.GitHubUsersTheme
import com.mknishad.githubusers.presentation.userdetail.UserDetailScreen
import com.mknishad.githubusers.presentation.userlist.UserListScreen
import com.mknishad.githubusers.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint

/**
 * The main activity of the application, serving as the entry point for the user interface.
 *
 * This activity sets up the navigation graph and hosts the composable content. It uses Jetpack Compose
 * for the UI and manages navigation between the user list and user detail screens.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val navController = rememberNavController()
      GitHubUsersTheme {
        NavHost(
          navController = navController,
          startDestination = Screen.UserListScreen.route,
        ) {
          composable(
            route = Screen.UserListScreen.route
          ) {
            UserListScreen(navController)
          }
          composable(
            route = Screen.UserDetailScreen.route + "/{${Constants.PARAM_USERNAME}}"
          ) {
            UserDetailScreen(navController)
          }
        }
      }
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(
    text = "Hello $name!", modifier = modifier
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  GitHubUsersTheme {
    Greeting("Android")
  }
}